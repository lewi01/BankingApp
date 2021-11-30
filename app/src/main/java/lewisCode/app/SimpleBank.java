package lewisCode.app;

import java.sql.*;

public class SimpleBank {
    public void createNewDataBase(String fileName){
        String url = "jdbc:sqlite:/home/lewi/Softwares/DB/"+fileName;
        try(Connection connection = DriverManager.getConnection(url)) {
            if (connection !=null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void createTables(String path){
        String url ="jdbc:sqlite:/home/lewi/Softwares/DB/"+path ;
        String sql = "CREATE TABLE IF NOT EXISTS customer_Account (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	number TEXT NOT NULL,\n"
                + "	pin TEXT NOT NULL,\n"
                + "	balance INTEGER DEFAULT 0 \n"
                + ");";
        try(Connection connection = DriverManager.getConnection(url)) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void insertCustomer(String number,String pin){
        String url = "INSERT INTO customer_Account (number,pin) VALUES(?,?)";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(url)){
            preparedStatement.setString(1,number);
            preparedStatement.setString(2,pin);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public Customer selectCustomer(String number, String pin){
        String sql = "SELECT number,balance "
                + "FROM customer_Account"
                + "WHERE  number= '" +number+"'" +
                "AND"+

                "pin = '"+ pin+ "'";
        try (Connection connection = DriverManager.getConnection(sql)) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet == null){
                    return null;
                }
                return new Customer(resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
