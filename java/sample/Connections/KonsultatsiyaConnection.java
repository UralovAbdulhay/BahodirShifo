package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Xizmat;
import sample.modules.davolanish.Analise;
import sample.modules.davolanish.Konsultatsiya;

import java.sql.*;

public class KonsultatsiyaConnection extends Connections {

    public ObservableList<Konsultatsiya> getKonsultatsiyaFromSql() {
        ObservableList<Konsultatsiya> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat where type = 3;"
            );

            while (resultSet.next()) {

                Konsultatsiya konsultatsiya = new Konsultatsiya(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection()
                                .getVrachFromSql(resultSet.getInt("vrachId"))
                );
                konsultatsiya.setXizmatType(Xizmat.XizmatType.Konsultatsiya);
                list.add(konsultatsiya);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Konsultatsiya getKonsultatsiyaFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat WHERE id = " + id + " and type = 3;"
            );
            Konsultatsiya konsultatsiya = null;
            if (resultSet.next()) {
                konsultatsiya = new Konsultatsiya(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection()
                                .getVrachFromSql(resultSet.getInt("vrachId"))
                );
                konsultatsiya.setXizmatType(Xizmat.XizmatType.Konsultatsiya);
            }
            return konsultatsiya;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToKonsultatsiya(Konsultatsiya konsultatsiya) {

        String sql = "INSERT OR IGNORE INTO xizmat (name, cost, vrachId, type) VALUES('"
                + konsultatsiya.getName() + "', "
                + konsultatsiya.getCost() + ", "
                + konsultatsiya.getVrach().getId() + ", "
                + " 3 "
                + ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToKonsultatsiya statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateKonsultatsiya(Konsultatsiya konsultatsiya) {
        String sql = " UPDATE xizmat SET " +
                "name = '" + konsultatsiya.getName() + "', " +
                "cost = " + konsultatsiya.getCost() + ", " +
                "vrachId = " + konsultatsiya.getVrach().getId() + ", " +
                "WHERE id = " + konsultatsiya.getId() +
                " and type = 3;";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateKonsultatsiya statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteKonsultatsiya(Konsultatsiya konsultatsiya) {
        String sql = "DELETE FROM xizmat WHERE id = " + konsultatsiya.getId() + " and type = 3;";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteKonsultatsiya statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
