package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Bemor;
import sample.modules.Davolanish;
import sample.modules.Payment;
import sample.modules.Xizmat;
import sample.modules.davolanish.Tekshirish;

import java.sql.*;
import java.time.LocalDate;

public class QabulConnection extends Connections {


    public ObservableList<Davolanish> getDavolanishList() {
        ObservableList<Davolanish> davolanishes = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM qabul"
            );

            while (resultSet.next()) {
                int qabId = resultSet.getInt("id");
                LocalDate localDate = parseToLocalDate(resultSet.getString("qabulDate"));
                int bemorId = resultSet.getInt("bemorId");
                int recomVrachId = resultSet.getInt("recommentDocId");
                int paymentId = resultSet.getInt("paymentId");

                Davolanish davolanish = new Davolanish();
                davolanish.setId(qabId);
                davolanish.setBemor(new BemorConnection().getBemorFromSql(bemorId));
                davolanish.setRecommendVrach(new VrachConnection().getVrachFromSql(recomVrachId));
                davolanish.setPayment(new PaymentConnection().getPaymentFromSql(paymentId));
                davolanish.setQabulDate(localDate);
                davolanish.addAnaliseLists(new DavolanishConnection().getDavolanishFromSql(qabId));

                davolanishes.add(davolanish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return davolanishes;
    }



    public Davolanish insertToQabul(Davolanish davolanish) {
        long temp = System.currentTimeMillis();
        davolanish.setPayment(new PaymentConnection().insertToPayment(davolanish.getPayment()));
        davolanish.setBemor(new BemorConnection().insertToBemor(davolanish.getBemor()));


        String sql = "INSERT OR IGNORE INTO qabul (bemorId, recommentDocId, " +
                "  paymentId, temp ) VALUES("
                + davolanish.getBemor().getId() + ", "
                + davolanish.getRecommendVrach().getId() + ", "
                + davolanish.getPayment().getId() + ", "
                + temp + " "
                + " );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToQabul statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, qabulDate FROM qabul WHERE temp = " + temp + ";"
            );

            if (resultSet.next()) {
               davolanish.setId(resultSet.getInt("id"));
               davolanish.setQabulDate(parseToLocalDate(resultSet.getString("qabulDate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE qabul SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Qabul setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        davolanish.getXizmats().forEach(e->{
            new DavolanishConnection().insertToDavolanishTable(davolanish.getId(), e.getId());
        });

        return davolanish;
    }


}
