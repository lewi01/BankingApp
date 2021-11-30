package lewisCode.app;

import java.util.Scanner;

public class Controller {
    public static SimpleBank simpleBank = new SimpleBank();
    public static CardGenerator cardGenerator = new CardGenerator();
    public static Scanner scanner = new Scanner(System.in);
    public static String fileName = "banking.db";
    public static void main(String[] args) {
        simpleBank.createNewDataBase(fileName);
        simpleBank.createTables(fileName);
        boolean quite = false;
        while (!quite){
            printMenu();
            int choice = scanner.nextInt();
            switch (choice){
                case 0:
                    System.out.println("Bye!");
                    quite = true;
                    break;
                case 1:
                    userCreatingAccount();
                    break;
                case 2:
                    userLogin();
                    break;
            }
        }
    }
    public static void printMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }
    public  static  void successfulLogInMenu(){
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
    }
    public static void userCreatingAccount(){
        System.out.println("Your card has been created");
        String customerNum = cardGenerator.customerNumberGenerator();
        String customerPin =cardGenerator.customerPinGenerator();
        System.out.println("Your card number:" + "\n" + customerNum);
        System.out.println("Your card PIN:" + "\n" + customerPin);
        simpleBank.insertCustomer(customerNum,customerPin);
    }
    public static void userLogin(){
        System.out.println("Enter your card number:");
        long num = scanner.nextLong();
        String number = String.valueOf(num);
        System.out.println("Enter your PIN:");
        int pin = scanner.nextInt();
        String pins = String.valueOf(pin);
        Customer customerDetail = simpleBank.selectCustomer(number,pins);
        if (customerDetail.getCustomerAccountNumber() == customerDetail.getCustomerPinNumber()){
            System.out.println("You have successfully logged in!");
            int balance =0;
            int print;
            while (true){
                successfulLogInMenu();
                print =scanner.nextInt();
                if(print == 0) {
                    System.out.println("Bye!");
                    break;
                }else if(print == 1) {
                    System.out.println("Balance: " + balance);
                }else if(print == 2){
                    System.out.println("You have successfully logged out!");
                    break;

                }
            }

        }else {
            System.out.println("Wrong card number or PIN!");
        }

    }
}
