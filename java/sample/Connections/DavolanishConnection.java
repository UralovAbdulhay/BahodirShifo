package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Davolanish;
import sample.modules.Xizmat;
import sample.modules.address.Address;

import java.sql.*;

public class DavolanishConnection extends Connections {


    public ObservableList<Xizmat> getDavolanishFromSql(int qabulId) {

        ObservableList<Xizmat> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT xizmatId FROM davolanish WHERE qabulId = " + qabulId + ";"
            );

            while (resultSet.next()) {
                list.add(new XizmatConnection().getXizmatFromSql(
                        resultSet.getInt("xizmatId"))
                );
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToDavolanishTable(int qabulId, int xizmatId) {

        String sql = "INSERT OR IGNORE INTO davolanish (qabulId, xizmatId) VALUES (" +
                " " + qabulId + ", " +
                " " + xizmatId +
                 ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToDavolanishTable statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
