package sample.Connections;

import sample.modules.address.Address;
import sample.modules.inform.Phone;

import java.sql.*;

public class AddressConnection extends Connections {


    public Address getAddressFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM address WHERE id = " + id + ";"
            );

            Address address = null;

            if (resultSet.next()) {
                int vilId = resultSet.getInt("viloyatId");
                int tumId = resultSet.getInt("tumanId");
                address = new Address(
                        resultSet.getInt("id"),
                        resultSet.getString("uy"),
                        new ViloyatConnection().getViloyatFromSql(vilId),
                        new TumanConnection().getTumanFromSql(tumId)
                );
            }
            return address;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Address insertToAddress(Address address) {

        if (address!=null)
            if (isNotExist(address.getId())) {

                long temp = System.currentTimeMillis();

                String sql = " INSERT OR IGNORE INTO address (uy, viloyatId, tumanId, temp)" +
                        " VALUES('" + address.getUy().replace("'", "`") + "', " +
                        " " + address.getViloyat().getId() + ", " +
                        " " + address.getTuman().getId() + ", " +
                        " " + temp + " );";

                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    System.out.println("insertToAddress statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String sql1 = "SELECT * FROM address WHERE temp = " + temp + ";";

                try (Connection connection = connect()) {
                    ResultSet resultSet = connection.createStatement().executeQuery(sql1);
                    if (resultSet.next()) {
                        address.setId(resultSet.getInt("id"));
                        address.setTuman(new TumanConnection()
                                .getTumanFromSql(resultSet.getInt("tumanId")));
                        address.setViloyat(new ViloyatConnection()
                                .getViloyatFromSql(resultSet.getInt("viloyatId")));
                        address.setUy(resultSet.getString("uy"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String sql3 = " UPDATE address SET " +
                        "temp = " + null + " " +
                        "WHERE temp = '" + temp + "'" +
                        ";";

                try (Connection connection = connect()) {
                    PreparedStatement statement = connection.prepareStatement(sql3);
                    System.out.println("Address setTempNull statement = " + statement.executeUpdate());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                updateAddress(address);
            }

        return address;
    }


    public void updateAddress(Address address) {

        String sql3 = " UPDATE address SET " +
                "uy = '" + address.getUy() + "', " +
                "viloyatId = " + address.getViloyat().getId() + ", " +
                "tumanId = " + address.getTuman().getId() + " " +
                "WHERE id = " + address.getId() + " " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("updatePhone statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteAddress(Address address) {
        String sql = "DELETE FROM address WHERE id = " + address.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteAddress statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean isNotExist(int id) {

        if(-1==id) return true;
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            return 0 == statement.executeQuery("SELECT Count(id) FROM address WHERE id = " + id + ";").getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
