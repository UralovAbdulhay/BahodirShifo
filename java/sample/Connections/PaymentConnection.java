package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Payment;
import sample.modules.inform.Passport;

import java.sql.*;

public class PaymentConnection extends Connections {


    public ObservableList<Payment> getPaymentListFromSql() {
        ObservableList<Payment> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM payment;"
            );

            while (resultSet.next()) {
                Payment payment = new Payment(
                        resultSet.getInt("id"),
                        resultSet.getDouble("payAmount"),
                        resultSet.getBoolean("payType"),
                        resultSet.getBoolean("payCheck"),
                        resultSet.getString("payComment")
                );
                payment.setSale(resultSet.getDouble("paySale"));
                list.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Payment getPaymentFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM payment WHERE id = "
                            + id + ";"
            );
            Payment payment = null;
            if (resultSet.next()) {
                payment = new Payment(
                        resultSet.getInt("id"),
                        resultSet.getDouble("payAmount"),
                        resultSet.getBoolean("payType"),
                        resultSet.getBoolean("payCheck"),
                        resultSet.getString("payComment")
                );
                payment.setSale(resultSet.getDouble("paySale"));
            }
            return payment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Payment insertToPayment(Payment payment) {
        long temp = System.currentTimeMillis();

        String sql = " INSERT OR IGNORE INTO payment (payAmount, payType, payCheck," +
                " paySale, payComment, temp)" +
                " VALUES( " + payment.getPaymentAmount() + ", " +
                " " + (payment.isPaymentType() ? 1 : 0) + ", " +
                " " + (payment.isChecked() ? 1 : 0) + ", " +
                " " + payment.getSale() + ", " +
                " " + payment.getPaymentComment() + ", " +
                " " + temp + " );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToPayment statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql1 = "SELECT * FROM passport WHERE temp = " + temp + ";";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql1);
            if (resultSet.next()) {
                payment = new Payment(
                        resultSet.getInt("id"),
                        resultSet.getDouble("payAmount"),
                        resultSet.getBoolean("payType"),
                        resultSet.getBoolean("payCheck"),
                        resultSet.getString("payComment")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE payment SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Payment setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payment;
    }


    public void updatePayment(Payment payment) {

        String sql3 = " UPDATE payment SET " +
                "payAmount = " + payment.getPaymentAmount() + ", " +
                "payType = " + (payment.isPaymentType() ? 1 : 0) + ", " +
                "payCheck = " + (payment.isChecked() ? 1 : 0) + ", " +
                "paySale = " + payment.getSale() + ", " +
                "payComment = '" + payment.getPaymentComment() + "', " +
                "WHERE id = " + payment.getId() + " " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("updatePassport statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deletePayment(Payment payment) {
        String sql = "DELETE FROM payment WHERE id = " + payment.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deletePayment statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
