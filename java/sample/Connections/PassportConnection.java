package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.inform.Passport;

import java.sql.*;

public class PassportConnection extends Connections {


    public ObservableList<Passport> getPassportListFromSql() {
        ObservableList<Passport> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM passport;"
            );

            while (resultSet.next()) {
                list.add(new Passport(
                        resultSet.getInt("id"),
                        resultSet.getString("seriya"),
                        resultSet.getInt("raqam")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Passport getPassportFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM passport WHERE id = "
                            + id + ";"
            );
            Passport passport = null;
            if (resultSet.next()) {
                passport = new Passport(
                        resultSet.getInt("id"),
                        resultSet.getString("seriya"),
                        resultSet.getInt("raqam")
                );
            }
            return passport;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Passport insertToPassport(Passport passport) {

        if (passport != null)
            if (isNotExist(passport.getId())) {
                long temp = System.currentTimeMillis();
                String sql = null;


                    sql = " INSERT OR IGNORE INTO passport (seriya, raqam, temp)" +
                            " VALUES('" + passport.getSeria() + "', " +
                            " " + passport.getRaqam() + ", " +
                            " " + temp + " );";


                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    System.out.println("insertToPassport statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String sql1 = "SELECT * FROM passport WHERE temp = " + temp + ";";

                try (Connection connection = connect()) {
                    ResultSet resultSet = connection.createStatement().executeQuery(sql1);
                    if (resultSet.next()) {
                        passport = new Passport(
                                resultSet.getInt("id"),
                                resultSet.getString("seriya"),
                                resultSet.getInt("raqam")
                        );
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }


                String sql3 = " UPDATE passport SET " +
                        "temp = " + null + " " +
                        "WHERE temp = '" + temp + "'" +
                        ";";

                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql3);
                    System.out.println("Passport setTempNull statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                updatePassport(passport);
            }

        System.out.println("insertToPassport = "+passport);
        return passport;
    }


    public void updatePassport(Passport passport) {

        String sql3 = " UPDATE passport SET " +
                "seriya = '" + passport.getSeria() + "', " +
                "raqam = " + passport.getRaqam() + " " +
                "WHERE id = " + passport.getId() + " " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("updatePassport statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deletePassport(Passport passport) {
        String sql = "DELETE FROM passport WHERE id = " + passport.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deletePassport statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isNotExist(int id) {
        if (-1 == id) return true;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return 0 == statement.executeQuery(
                    "SELECT Count(id) FROM passport WHERE id = " + id + ";").getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
