package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Xizmat;
import sample.modules.davolanish.Analise;

import java.sql.*;

public class XizmatConnection extends Connections {

    public ObservableList<Xizmat> getAnaliseListFromSql() {
        ObservableList<Xizmat> list = FXCollections.observableArrayList();

        list.addAll(new AnaliseConnection().getAnaliseListFromSql());
        list.addAll(new TekshirishConnection().getTekshirishFromSql());
        list.addAll(new KonsultatsiyaConnection().getKonsultatsiyaFromSql());

        return list;
    }


    public Xizmat getXizmatFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT type FROM xizmat WHERE id = " + id + ";"
            );

            if (resultSet.next()) {

                switch (resultSet.getInt("type")) {
                    case 1: {
                        return new AnaliseConnection().getAnaliseListFromSql(id);

                    }
                    case 2: {
                        return new TekshirishConnection().getTekshirishFromSql(id);

                    }
                    case 3: {
                        return new KonsultatsiyaConnection().getKonsultatsiyaFromSql(id);

                    }
                    default: {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

      return null;
    }



}
