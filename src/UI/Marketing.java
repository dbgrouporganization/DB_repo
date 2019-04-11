package UI;

import java.sql.Connection;
import java.util.Scanner;

public class Marketing {

    private Connection conn;

    public Marketing(Connection conn){
        this.conn = conn;
        System.out.println("\nWelcome Marketing Team!");
    }

    public void marketingStart(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("What would you like to search for? Customers or Sales?");
            System.out.println("If you would like to exit type 'exit'.");
            String search = console.next();
            search = search.toLowerCase();
            switch (search) {
                case "exit":
                    loop = false;
                    break;
                case "customers":
                    customerLookup();
                    break;
                case "sales":
                    salesLookup();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    public void salesLookup(){
        Scanner console = new Scanner(System.in);
        //List of customer searches can be expanded.
        System.out.println("What would you like to search sales by? Seller ID, Buyer ID, or Date?");
        String search = console.nextLine();
        search = search.toLowerCase();
        switch (search) {
            case "seller id":
                System.out.println("What is the Seller ID?");
                String sid = console.next();
                //TODO SQL Query for sid
                break;
            case "dealer id":
                System.out.println("What is the Dealer ID?");
                String did = console.next();
                //TODO SQL Query for sid
                break;
            case "date":
                System.out.println("What was the Date?");
                String date = console.next();
                //TODO SQL Query for date
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                break;
        }
    }

    public void customerLookup(){
        Scanner console = new Scanner(System.in);
        //List of customer searches can be expanded.
        System.out.println("What would you like to search customers by? First Name, Last Name, ID, State, City, Zip");
        String search = console.nextLine();
        search = search.toLowerCase();
        switch (search) {
            case "first name":
                System.out.println("What is their first name?");
                String fname = console.nextLine();
                //TODO SQL Query for fname
                break;
            case "last name":
                System.out.println("What is their last name?");
                String lname = console.nextLine();
                //TODO SQL Query for lname
                break;
            case "id":
                System.out.println("What is their ID?");
                String id = console.next();
                //TODO SQL Query for ID
                break;
            case "state":
                System.out.println("What is their state?");
                String state = console.next();
                //TODO SQL Query for state
                break;
            case "city":
                System.out.println("What is their city?");
                String city = console.next();
                //TODO SQL Query for city
                break;
            case "zip":
                System.out.println("What is their zip?");
                String zip = console.next();
                //TODO SQL Query for zip
                break;
        }
    }
}
