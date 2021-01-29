package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.address.Viloyat;
import sample.modules.davolanish.Analise;

import java.sql.*;

public class ViloyatConnection extends Connections {


    public ObservableList<Viloyat> getViloyatFromSql() {
        ObservableList<Viloyat> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM viloyatlar;"
            );

            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                list.add(new Viloyat(
                        id1,
                        resultSet.getString("name"),
                        new TumanConnection().getTumanListFromSql(id1)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Viloyat getViloyatFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM viloyatlar WHERE id = " + id + ";"
            );
            Viloyat viloyat = null;
            if (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                viloyat = new Viloyat(
                        id1,
                        resultSet.getString("name"),
                        new TumanConnection().getTumanListFromSql(id1)
                );
            }
            return viloyat;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
