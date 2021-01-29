package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Kasb;
import sample.modules.davolanish.Tekshirish;
import sample.modules.inform.Phone;

import java.sql.*;

public class KasbConnection extends Connections {


    public ObservableList<Kasb> getKasbListFromSql() {
        ObservableList<Kasb> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM kasb ;"
            );

            while (resultSet.next()) {
                list.add(new Kasb(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Kasb getKasbFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM kasb WHERE id = " + id + ";"
            );
            Kasb kasb = null;
            if (resultSet.next()) {
                kasb = new Kasb(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
            }
            return kasb;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




    public Kasb insertToKasb(Kasb kasb) {
        long temp = System.currentTimeMillis();

        String sql = " INSERT OR IGNORE INTO kasb (name, temp)" +
                " VALUES('" + kasb.getName() + "', " +
                " " + temp + " );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToKasb statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql1 = "SELECT * FROM kasb WHERE temp = " + temp + ";";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql1);
            if (resultSet.next()) {
                kasb = new Kasb(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE kasb SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "' " +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Kasb setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kasb;
    }


    public void updateKasb(Kasb kasb) {

        String sql = " UPDATE kasb SET " +
                "name = '" + kasb.getName() + "' " +
                "WHERE id = " + kasb.getId() + " " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateKasb statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteKasb(Kasb kasb) {
        String sql = "DELETE FROM kasb WHERE id = " + kasb.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteKasb statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
