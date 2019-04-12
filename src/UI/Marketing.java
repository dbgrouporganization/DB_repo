package UI;

import Appl.SaleTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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
        boolean loop = true;

        // ArrayList of where clauses
        ArrayList<String> whereClauses = new ArrayList<>();

        while(loop) {
            //List of customer searches can be expanded.
            System.out.println("What would you like to search sales by? Buyer ID, Seller ID, or Date?");
            String search = console.nextLine();
            search = search.toLowerCase();

            switch (search) {
                case "buyer id":
                    System.out.println("What is the Buyer ID?");
                    String did = console.next();
                    // TODO test this and possibly make prettier

                    // make sure where clause array is empty then add new where clause for sql query
                    if (!whereClauses.isEmpty()) {
                        for (String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("buyer_id = " + did);

                    // query and print results
                    ResultSet didResults = SaleTable.querySaleTable(conn, new ArrayList<>(), whereClauses);
                    SaleTable.printSaleTable(didResults);
                    break;
                case "seller id":
                    System.out.println("What is the Seller ID?");
                    String sid = console.nextLine();
                    // TODO test this and possibly make prettier

                    // make sure where clause array is empty then add new where clause for sql query
                    if (!whereClauses.isEmpty()) {
                        for (String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("seller_id = " + sid);

                    // query and print results
                    ResultSet sidResults = SaleTable.querySaleTable(conn, new ArrayList<>(), whereClauses);
                    SaleTable.printSaleTable(sidResults);
                    break;
                case "date":
                    System.out.println("What was the Date? (MM/DD/YYYY)");
                    String date = console.next();
                    // TODO test this and possibly make prettier

                    // make sure where clause array is empty then add new where clause for sql query
                    if (!whereClauses.isEmpty()) {
                        for (String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("date = '" + date + "'");

                    // query and print results
                    ResultSet dateResults = SaleTable.querySaleTable(conn, new ArrayList<>(), whereClauses);
                    SaleTable.printSaleTable(dateResults);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
            System.out.println("Would you like to make another search? (y/n)");
            if(console.nextLine().equals("n"))
                loop = false;
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
