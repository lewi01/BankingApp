package lewisCode.app;

import java.sql.*;

public class SimpleBank {

    private final String url = "jdbc:sqlite:/home/lewi/Softwares/DB/banking.db";
    private  final   Customer customer = new Customer();

    public SimpleBank() {

    }
    public void createNewDataBase(){

        try(Connection connection = DriverManager.getConnection(url)) {
            if (connection !=null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void createTables(){
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
    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void insertCustomer(String number, String pin){
        String sql = "INSERT INTO customer_Account (number,pin) VALUES(?,?)";
        try (Connection connection = this.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setString(1,number);
                    preparedStatement.setString(2,pin);
                    preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public double getBalance(String number) {
        String sql = "SELECT balance FROM customer_Account WHERE number=?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("balance");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public boolean receiverCustomerAccountNumber(String number){
        String sql = "SELECT number "
                + "FROM customer_Account "
                + "WHERE  number= ? " ;
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkingCustomerAccountPin(String number, String pin){
        String sql = "SELECT number, pin "
                + "FROM customer_Account "
                + "WHERE  number= ? " +
                "AND pin= ?";
        try (Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,number);
            preparedStatement.setString(2,pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteAccount(String number){
        String sql = "DELETE FROM customer_Account WHERE number= ?";
        try(Connection connection = this.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,number);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean updateAccountBalance(String number, int balance){
        String sql = "UPDATE  customer_Account SET balance= ? WHERE number= ?";
        try(Connection connection = this.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,balance);
            preparedStatement.setString(2,number);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean doTransfer(String sender,String receiverNumber,int balance){
        String  sql = "UPDATE customer_Account SET balance= ? WHERE number= ?)";
        try( Connection connection = this.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            int senderBalance = customer.getBalance() - balance;
            updateAccountBalance(sender,senderBalance);
            int recipientBalance = customer.getBalance() + balance;
            updateAccountBalance(receiverNumber,senderBalance);
            connection.commit();
            preparedStatement.setInt(1,senderBalance);
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
