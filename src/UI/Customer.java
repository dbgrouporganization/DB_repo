package UI;

import Appl.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

    private Connection conn;

    public Customer(Connection conn){
        this.conn = conn;
        System.out.println("\nWelcome Customer!");
    }

    public void customerStart(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("What would you like to search for? Vehicles or Dealers?");
            System.out.println("If you would like to exit enter 'exit'.");
            String search = console.next();
            search = search.toLowerCase();
            switch (search) {
                case "exit":
                    loop = false;
                    break;
                case "dealers":
                    dealerLookup();
                    break;
                case "vehicles":
                    VehicleLookup vehicleLookup = new VehicleLookup(conn);
                    vehicleLookup.vehicleStart();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    public void dealerLookup(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;

        // add attributes to ArrayList for SELECT key word
        String attributes[] = { "name", "addr_num", "addr_street", "addr_city", "addr_state", "addr_zip" };
        ArrayList<String> columns = new ArrayList<>();
        for(String s : attributes) {
            columns.add(s);
        }

        // join with owner on owner_id
        String naturalJoin = "NATURAL JOIN owner ";

        // ArrayList of where clauses
        ArrayList<String> whereClauses = new ArrayList<>();

        while(loop) {
            System.out.println("Would you like to search by name, city, state or zip?");
            String search = console.nextLine();
            search = search.toLowerCase();
            switch (search) {
                case "exit":
                    loop = false;
                    return;
                case "name":
                    System.out.println("Please enter the dealer's name to search for:");
                    String name = console.nextLine();

                    // if name of dealer has an apostrophe, duplicate it for sql query
                    if(name.contains("'")) {
                        name = name.substring(0, name.indexOf("'")) + "'" + name.substring(name.indexOf("'"), name.length());
                    }

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("name = '" + name + "'");

                    // query and print results
                    ResultSet nameResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(nameResults);
                    break;
                case "city":
                    System.out.println("Please enter the dealer's city to search for:");
                    String city = console.nextLine();

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("addr_city = '" + city + "'");

                    // query and print results
                    ResultSet cityResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(cityResults);
                    break;
                case "state":
                    System.out.println("Please enter the dealer's state to search for:");
                    String state = console.nextLine();

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("addr_state = '" + state + "'");

                    // query and print results
                    ResultSet stateResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(stateResults);
                    break;
                case "zip":
                    System.out.println("Please enter the dealer's zip to search for:");
                    String zipString = console.next();
                    int zip = Integer.parseInt(zipString);

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("addr_zip = " + zip);

                    // query and print results
                    ResultSet zipResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(zipResults);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
            System.out.println("Would you like to make another search? (y/n)");
            if(console.nextLine().equals("n")) {
                loop = false;
            }
        }
    }
}
