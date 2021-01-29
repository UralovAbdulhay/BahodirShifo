package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Xizmat;
import sample.modules.davolanish.Analise;
import sample.modules.davolanish.Tekshirish;
import sample.modules.inform.Phone;

import java.sql.*;

public class AnaliseConnection extends Connections {

    public ObservableList<Analise> getAnaliseListFromSql() {
        ObservableList<Analise> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat where type = 1;"
            );

            while (resultSet.next()) {
                Analise analise = new Analise(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection()
                                .getVrachFromSql(resultSet.getInt("vrachId"))
                );
                analise.setXizmatType(Xizmat.XizmatType.Analise);
                list.add(analise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Analise getAnaliseListFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat WHERE id = " + id + " and type = 1;"
            );
            Analise analise = null;
            if (resultSet.next()) {
                analise = new Analise(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection()
                                .getVrachFromSql(resultSet.getInt("vrachId"))
                );
                analise.setXizmatType(Xizmat.XizmatType.Analise);
            }
            return analise;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Analise insertToAnalise(Analise analise) {

        long temp = System.currentTimeMillis();

        String sql = "INSERT OR IGNORE INTO xizmat (name, cost, vrachId, type) VALUES('"
                + analise.getName() + "', "
                + analise.getCost() + ", "
                + analise.getVrach().getId() + ",  " +
                " 1 "
                + ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToAnalise statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xizmat WHERE temp = " + temp + ";"
            );
            if (resultSet.next()) {
                analise = new Analise(
                        resultSet.getString("name"),
                        resultSet.getDouble("cost"),
                        new VrachConnection()
                                .getVrachFromSql(resultSet.getInt("vrachId"))
                );
                analise.setXizmatType(Xizmat.XizmatType.Analise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE xizmat SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Analise setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return analise;
    }


    public void updateAnalise(Analise analise) {
        String sql = " UPDATE xizmat SET " +
                "name = '" + analise.getName() + "', " +
                "cost = " + analise.getCost() + ", " +
                "vrachId = " + analise.getVrach().getId() + " " +
                "WHERE id = " + analise.getId() + " " +
                "and type = 1" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateAnalise statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAnalise(Analise analise) {
        String sql = "DELETE FROM xizmat WHERE id = " + analise.getId() + " and type = 1;";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteAnalise statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
