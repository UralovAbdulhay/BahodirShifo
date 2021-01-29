package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Kasb;
import sample.modules.Vrach;
import sample.modules.Xizmat;
import sample.modules.davolanish.Tekshirish;
import sample.modules.inform.Phone;

import java.sql.*;

public class VrachConnection extends Connections {

    public ObservableList<Vrach> getVrachFromSql() {
        ObservableList<Vrach> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM vrach;"
            );

            while (resultSet.next()) {
                list.add(new Vrach(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastName"),
                        new KasbConnection().getKasbFromSql(resultSet.getInt("kasbId")),
                        resultSet.getBoolean("gender"),
                        parseToLocalDate(resultSet.getString("birthDate")),
                        parseToLocalDate(resultSet.getString("comeDate"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Vrach getVrachFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM vrach WHERE id = " + id + ";"
            );
            Vrach vrach = null;
            if (resultSet.next()) {
                vrach = new Vrach(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastName"),
                        new KasbConnection().getKasbFromSql(resultSet.getInt("name")),
                        resultSet.getBoolean("gender"),
                        parseToLocalDate(resultSet.getString("birthDate")),
                        parseToLocalDate(resultSet.getString("comeDate"))
                );
            }
            return vrach;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Vrach insertToVrach(Vrach vrach) {
        long temp = System.currentTimeMillis();

        String sql = "INSERT OR IGNORE INTO vrach (name, surname, lastName," +
                " kasbId, birthDate, gender, temp) VALUES('"
                + vrach.getName() + "', "
                + " '" + vrach.getSurname() + "', "
                + " '" + vrach.getLastName() + "', "
                + " " + vrach.getKasb().getId() + ", "
                + " '" + localDateParseToString(vrach.getBirthDate()) + "', "
                + " " + (vrach.isGender() ? 1 : 0) + ", "
                + " " + temp + " "
                + ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToVrach statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM vrach WHERE temp = " + temp + ";"
            );

            if (resultSet.next()) {
                vrach = new Vrach(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("lastName"),
                        new KasbConnection().getKasbFromSql(resultSet.getInt("name")),
                        resultSet.getBoolean("gender"),
                        parseToLocalDate(resultSet.getString("birthDate")),
                        parseToLocalDate(resultSet.getString("comeDate"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE vrach SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Vrach setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vrach;
    }


    public void updateVrach(Vrach vrach) {
        String sql = " UPDATE vrach SET " +
                "name = '" + vrach.getName() + "', " +
                "surname = '" + vrach.getSurname() + "', " +
                "lastName = '" + vrach.getLastName() + "', " +
                "kasbId = " + vrach.getKasb().getId() + ", " +
                "birthDate = '" + localDateParseToString(vrach.getBirthDate()) + "' " +
                "WHERE id = " + vrach.getId() +
                " ;";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateVrach statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteVrach(Vrach vrach) {

        String sql = "DELETE FROM vrach WHERE id = " + vrach.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteVrach statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
