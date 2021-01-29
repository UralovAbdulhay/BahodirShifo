package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Xizmat;
import sample.modules.davolanish.Tekshirish;

import java.sql.*;

public class TekshirishConnection extends Connections {


    public ObservableList<Tekshirish> getTekshirishFromSql() {
        ObservableList<Tekshirish> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat where type = 2;"
            );

            while (resultSet.next()) {
                Tekshirish tekshirish = new Tekshirish(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection().getVrachFromSql(
                                resultSet.getInt("vrachId")
                        )
                );
                tekshirish.setXizmatType(Xizmat.XizmatType.Tekshirish);
                list.add(tekshirish);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Tekshirish getTekshirishFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat WHERE id = " + id + " and type = 2;"
            );
            Tekshirish tekshirish = null;
            if (resultSet.next()) {
                tekshirish = new Tekshirish(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        null
                );
                tekshirish.setXizmatType(Xizmat.XizmatType.Tekshirish);
            }
            return tekshirish;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToTekshirish(Tekshirish tekshirish) {

        String sql = "INSERT OR IGNORE INTO xizmat (name, cost, vrachId, type) VALUES('"
                + tekshirish.getName() + "', "
                + tekshirish.getCost() + ", "
                + tekshirish.getVrach().getId() + ", " +
                "2 "
                + ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToTekshirish statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateTekshirish(Tekshirish tekshirish) {
        String sql = " UPDATE xizmat SET " +
                "name = '" + tekshirish.getName() + "', " +
                "cost = " + tekshirish.getCost() + ", " +
                "vrachId = " + tekshirish.getVrach().getId() + ", " +
                "WHERE id = " + tekshirish.getId() + ", " +
                "type = " + 2 +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateTekshirish statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteTekshirish(Tekshirish tekshirish) {
        String sql = "DELETE FROM xizmat WHERE id = " + tekshirish.getId() + " and type = 2;";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteTekshirish statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
