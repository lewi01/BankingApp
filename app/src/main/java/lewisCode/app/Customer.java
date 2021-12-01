package lewisCode.app;

import java.util.Random;

public class Customer {
    private  String customerAccountNumber;
    private  String customerPinNumber;
    private  double balance ;

    public Customer() {

    }
    public Customer( String customerAccountNumber, String customerPinNumber,double balance) {
        this.customerAccountNumber = customerAccountNumber;
        this.customerPinNumber = customerPinNumber;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public String getCustomerPinNumber() {
        return customerPinNumber;
    }

}
