package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.address.Tuman;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TumanConnection extends Connections {


    public ObservableList<Tuman> getTumanListFromSql(int vilId) {
        ObservableList<Tuman> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM tumanlar where viloyatId = " + vilId + " ;"
            );

            while (resultSet.next()) {
                list.add(new Tuman(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Tuman getTumanFromSql(int tumId) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM tumanlar WHERE id = "
                            + tumId + ";"
            );
            Tuman tuman = null;
            if (resultSet.next()) {
                tuman = new Tuman(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
            }
            return tuman;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
