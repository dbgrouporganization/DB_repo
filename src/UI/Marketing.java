package UI;

import java.sql.Connection;
import java.util.Scanner;

public class Marketing {

        private Connection conn;


        public Marketing(Connection conn){
            this.conn = conn;
            System.out.println("Welcome Marketing!");
            marketingStart();
        }

        public void marketingStart(){
            Scanner console = new Scanner(System.in);
            boolean loop = true;
            while(loop) {
                System.out.println("What would you like to search for Customers or Sales.");
                System.out.println("If you would like to exit enter exit.");
                String search = console.next();
                switch (search) {
                    case "exit":
                        loop = false;
                        return;
                    case "Customers":
                        customerLookup();
                        break;
                    case "Sales":
                        salesLookup();
                        break;
                }
            }
        }

        public void salesLookup(){
            Scanner console = new Scanner(System.in);
            //List of customer searches can be expanded.
            System.out.println("What would you like to search sales by? Seller ID, Buyer ID, Date");
            String search = console.next();
            switch (search) {
                case "Seller ID":
                    System.out.println("What is the seller id?");
                    String sid = console.next();
                    //TODO SQL Query for sid
                case "Dealer ID":
                    System.out.println("What is the dealer id?");
                    String did = console.next();
                    //TODO SQL Query for sid
                case "Date":
                    System.out.println("What is the date?");
                    String date = console.next();
                    //TODO SQL Query for date
            }
        }

        public void customerLookup(){
            Scanner console = new Scanner(System.in);
            //List of customer searches can be expanded.
            System.out.println("What would you like to search customers by? First Name, Last Name, ID, State, City, Zip");
            String search = console.next();
            switch (search) {
                case "First Name":
                    System.out.println("What is their first name?");
                    String fname = console.next();
                    //TODO SQL Query for fname
                    return;
                case "Last Name":
                    System.out.println("What is their last name?");
                    String lname = console.next();
                    //TODO SQL Query for lname
                    break;
                case "ID":
                    System.out.println("What is their ID?");
                    String id = console.next();
                    //TODO SQL Query for ID
                    break;
                case "State":
                    System.out.println("What is their state?");
                    String state = console.next();
                    //TODO SQL Query for state
                    break;
                case "City":
                    System.out.println("What is their city?");
                    String city = console.next();
                    //TODO SQL Query for city
                    break;
                case "Zip":
                    System.out.println("What is their zip?");
                    String zip = console.next();
                    //TODO SQL Query for zip
                    break;
            }
        }
}
