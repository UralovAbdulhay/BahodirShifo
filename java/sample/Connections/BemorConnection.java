package sample.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.modules.Bemor;

import java.sql.*;

public class BemorConnection extends Connections {


    public ObservableList<Bemor> getBemorListFromSql() {
        ObservableList<Bemor> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM bemor;"
            );

            while (resultSet.next()) {
                Bemor bemor = new Bemor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getBoolean("gender"),
                        new PhoneConnection().getPhoneFromSql(
                                resultSet.getInt("phone1Id")
                        ),
                        parseToLocalDate(resultSet.getString("birthDate")),
                        new AddressConnection().getAddressFromSql(
                                resultSet.getInt("addressId")
                        )
                );

                bemor.setPhone2(new PhoneConnection().getPhoneFromSql(
                        resultSet.getInt("phone2Id")
                ));

                bemor.setPassport(
                        new PassportConnection()
                                .getPassportFromSql(resultSet.getInt("passportId"))
                );

                bemor.setComeDate(parseToLocalDate(resultSet.getString("comeDate")));


                list.add(bemor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Bemor getBemorFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM bemor WHERE id = " + id + ";"
            );
            Bemor bemor = null;
            if (resultSet.next()) {
                bemor = new Bemor(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getBoolean("gender"),
                        new PhoneConnection().getPhoneFromSql(
                                resultSet.getInt("phone1Id")
                        ),
                        parseToLocalDate(resultSet.getString("birthDate")),
                        new AddressConnection().getAddressFromSql(
                                resultSet.getInt("addressId")
                        )
                );

                bemor.setPhone2(new PhoneConnection().getPhoneFromSql(
                        resultSet.getInt("phone2Id")
                ));

                bemor.setPassport(
                        new PassportConnection()
                                .getPassportFromSql(resultSet.getInt("passportId"))
                );

                bemor.setComeDate(parseToLocalDate(resultSet.getString("comeDate")));

            }
            return bemor;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Bemor insertToBemor(Bemor bemor) {

        if (isNotExist(bemor.getId())) {

            bemor.setAddress(new AddressConnection().insertToAddress(bemor.getAddress()));
            bemor.setPhone1(new PhoneConnection().insertToPhone(bemor.getPhone1()));
            bemor.setPhone2(new PhoneConnection().insertToPhone(bemor.getPhone2()));
            bemor.setPassport(new PassportConnection().insertToPassport(bemor.getPassport()));


            int phone2Id = 0;
            boolean isValid = false;
            if (bemor.getPhone2() != null) {
                isValid = true;
                phone2Id = bemor.getPhone2().getId();
            }


            long temp = System.currentTimeMillis();

            String sql = "INSERT OR IGNORE INTO bemor (name, gender, addressId, birthDate, " +
                    " surname, phone1Id, phone2Id, passportId, temp) VALUES ( "
                    + " '" + bemor.getName().replace("'", "`") + "', "
                    + " " + (bemor.getGender() ? 1 : 0) + ", "
                    + " " + bemor.getAddress().getId() + ", "
                    + " '" + localDateParseToString(bemor.getBirthDate()) + "', "
                    + " '" + bemor.getSurname().replace("'", "`") + "', "
                    + " " + bemor.getPhone1().getId() + ", "
                    + " " + (isValid ? phone2Id : null) + ", "
                    + " " + (bemor.getPassport() != null ? bemor.getPassport().getId() : null) + ", "
                    + " " + temp + " "
                    + ");";

            try (Connection connection = connect()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println("insertToBemor statement = " + statement.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try (Connection connection = connect()) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * FROM bemor WHERE temp = " + temp + ";"
                );

                if (resultSet.next()) {
                    bemor = new Bemor(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getBoolean("gender"),
                            new PhoneConnection().getPhoneFromSql(
                                    resultSet.getInt("phone1Id")
                            ),
                            parseToLocalDate(resultSet.getString("birthDate")),
                            new AddressConnection().getAddressFromSql(
                                    resultSet.getInt("addressId")
                            )
                    );

                    bemor.setPhone2(new PhoneConnection().getPhoneFromSql(
                            resultSet.getInt("phone2Id")
                    ));

                    bemor.setPassport(
                            new PassportConnection()
                                    .getPassportFromSql(resultSet.getInt("passportId"))
                    );

                    bemor.setComeDate(parseToLocalDate(resultSet.getString("comeDate")));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            String sql3 = " UPDATE bemor SET " +
                    "temp = " + null + " " +
                    "WHERE temp = '" + temp + "'" +
                    ";";

            try (Connection connection = connect()) {
                PreparedStatement statement = connection.prepareStatement(sql3);
                System.out.println("Bemor setTempNull statement = " + statement.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            updateBemor(bemor);
        }
        return bemor;
    }

    private boolean isNotExist(int id) {
        if (-1 == id) return true;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return 0 == statement.executeQuery("SELECT Count(id) FROM bemor WHERE id = " + id + ";").getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateBemor(Bemor bemor) {

        new AddressConnection().updateAddress(bemor.getAddress());
        new PhoneConnection().updatePhone(bemor.getPhone1());
        new PhoneConnection().updatePhone(bemor.getPhone2());
        bemor.setPassport(new PassportConnection().insertToPassport(bemor.getPassport()));


        String sql = "UPDATE OR IGNORE bemor SET " +
                "name = '" + bemor.getName() + "', " +
                "surname = '" + bemor.getSurname() + "', " +
                "gender = " + (bemor.getGender() ? 1 : 0) + ", " +
                "addressId = " + bemor.getAddress().getId() + ", " +
                "birthDate = '" + localDateParseToString(bemor.getBirthDate()) + "', " +
                "phone1Id = " + bemor.getPhone1().getId() + ", " +
                "phone2Id = " + bemor.getPhone2().getId() + ", " +
                "passportId = " +
                (bemor.getPassport() != null ? bemor.getPassport().getId() : null) + " " +
                "WHERE id = " + bemor.getId() +
                " ;";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateBemor statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteBemor(Bemor bemor) {

        String sql = "DELETE FROM bemor WHERE id = " + bemor.getId() + ";";

        new AddressConnection().deleteAddress(bemor.getAddress());
        new PhoneConnection().deletePhone(bemor.getPhone1());
        new PhoneConnection().deletePhone(bemor.getPhone2());
        new PassportConnection().deletePassport(bemor.getPassport());

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteBemor statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
