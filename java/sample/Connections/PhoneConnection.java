package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.inform.Passport;
import sample.modules.inform.Phone;

import java.sql.*;

public class PhoneConnection extends Connections {


    public ObservableList<Phone> getPhoneListFromSql() {
        ObservableList<Phone> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM phone ;"
            );

            while (resultSet.next()) {
                list.add(new Phone(
                        resultSet.getInt("id"),
                        resultSet.getString("number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Phone getPhoneFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM phone WHERE id = "
                            + id + ";"
            );
            Phone phone = null;
            if (resultSet.next()) {
                phone = new Phone(
                        resultSet.getInt("id"),
                        resultSet.getString("number")
                );
            }
            return phone;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Phone insertToPhone(Phone phone) {

        if (phone!=null)
            if (isNotExist(phone.getId())) {
                long temp = System.currentTimeMillis();

                String sql = " INSERT OR IGNORE INTO phone (number, temp)" +
                        " VALUES('" + phone.getNumber() + "', " +
                        " " + temp + " );";

                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    System.out.println("insertToPhone statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String sql1 = "SELECT * FROM phone WHERE temp = " + temp + ";";

                try (Connection connection = connect()) {
                    ResultSet resultSet = connection.createStatement().executeQuery(sql1);
                    if (resultSet.next()) {
                        phone = new Phone(
                                resultSet.getInt("id"),
                                resultSet.getString("number")
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String sql3 = " UPDATE phone SET " +
                        "temp = " + null + " " +
                        "WHERE temp = '" + temp + "'" +
                        ";";

                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql3);
                    System.out.println("Phone setTempNull statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                updatePhone(phone);
            }
        return phone;
    }


    public void updatePhone(Phone phone) {

        String sql3 = " UPDATE phone SET " +
                "number = '" + phone.getNumber() + "' " +
                "WHERE id = " + phone.getId() + " " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("updatePhone statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deletePhone(Phone phone) {
        String sql = "DELETE FROM phone WHERE id = " + phone.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deletePhone statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean isNotExist(int id) {
        if (-1==id) return true;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return 0 == statement.executeQuery("SELECT Count(id) FROM phone WHERE id = " + id + ";").getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
