package lewisCode.app;

import java.util.Scanner;

public class Controller {
    public static SimpleBank simpleBank = new SimpleBank();
    public static CreditCardCreator creditCardCreator = new CreditCardCreator();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        simpleBank.createNewDataBase();
        simpleBank.createTables();
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
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }
    public static void userCreatingAccount(){
        System.out.println("Your card has been created");
        String number = creditCardCreator.customerNumberGenerator();
        String pin = creditCardCreator.customerPinGenerator();
        System.out.println("Your card number:" + "\n" +number );
        System.out.println("Your card PIN:" + "\n" + pin);
        simpleBank.insertCustomer(number,pin);
    }
    public static void userLogin(){
        System.out.println("Enter your card number:");
        long num = scanner.nextLong();
        String number = String.valueOf(num);
        System.out.println("Enter your PIN:");
        int pin = scanner.nextInt();
        String pins = String.valueOf(pin);
        boolean customers = simpleBank.checkingCustomerAccountPin(number,pins);
        if (customers){
            System.out.println("You have successfully logged in!");
            int print;
            while (true){
                successfulLogInMenu();
                print =scanner.nextInt();
                if(print == 0) {
                    System.out.println("Exit!");
                    break;
                }else if(print == 1) {
                    System.out.println("Balance: " + simpleBank.getBalance(number));
                }else if(print == 5){
                    System.out.println("You have successfully logged out!");
                    break;
                }else if (print == 4){
                    boolean accountDeletion = simpleBank.deleteAccount(number);
                    if (accountDeletion) {
                        System.out.println("The account has been closed!");
                    }else {
                        System.out.println(number + " is not deleted, check the account number for correct input");
                    }
                    break;
                }else if(print == 2){
                    System.out.println("Enter income:");
                    int balance = scanner.nextInt();
                    boolean addingIncome = simpleBank.updateAccountBalance(number,balance);
                    if (addingIncome){
                        System.out.println("Income was added!");
                    }else {
                        System.out.println(balance + " was not added to " + number);
                    }
                }
            }

        }else {
            System.out.println("Wrong card number or PIN!");
        }

    }
}
