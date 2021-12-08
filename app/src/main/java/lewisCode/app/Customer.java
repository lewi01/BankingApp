package lewisCode.app;

import java.util.Random;

public class Customer {
    private  String customerAccountNumber;
    private  String customerPinNumber;
    private   int balance ;

    public Customer() {

    }
    public Customer( String customerAccountNumber, String customerPinNumber,int balance) {
        this.customerAccountNumber = customerAccountNumber;
        this.customerPinNumber = customerPinNumber;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public String getCustomerPinNumber() {
        return customerPinNumber;
    }

}
