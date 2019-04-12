package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

    private Connection conn;

    private final String[] stringSearchParams = {"name", "city", "state"};
    private final String[] integerSearchParams = {"zip"};

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
        int parameters = 0;

        ArrayList<String> stringParams = new ArrayList<>();
        for (String s : stringSearchParams) {
            stringParams.add(s);
        }

        ArrayList<String> integerParams = new ArrayList<>();
        for (String i : integerSearchParams) {
            integerParams.add(i);
        }

        String query = "SELECT name, addr_num, addr_street, addr_city, addr_state, addr_zip FROM dealer NATURAL JOIN owner WHERE ";

        while(loop) {
            boolean paramLoop = true;

            // General message
            System.out.println("What would you like to search by?");
            System.out.println("You can exit by typing 'exit'.");

            while(paramLoop) {
                if(parameters > 0) {
                    System.out.println("What else would you like to search by?");
                }
                System.out.println("Your options are Name, City, State, or Zip.");
                System.out.println("To make the search, enter 'search'.");

                // what user wants to search by
                String att = console.nextLine();
                att = att.toLowerCase();

                // exit?
                if(att.equals("exit")) {
                    loop = false;
                    paramLoop = false;
                    continue;
                }

                // run search?
                else if(att.equals("search")) {
                    paramLoop = false;
                    continue;
                }

                // valid search parameter?
                else if (!stringParams.contains(att) && !integerParams.contains(att)) {
                    System.out.println("'" + att + "' is not a valid search parameter.");
                    continue;
                }

                else {
                    parameters++;

                    System.out.println("Enter the " + att + " you would like to find: ");

                    String value = console.nextLine();

                    // if attribute value has an apostrophe, duplicate it for sql query
                    if(value.contains("'")) {
                        value = value.substring(0, value.indexOf("'")) + "'" + value.substring(value.indexOf("'"), value.length());
                    }

                    if(stringParams.contains(att)) {
                        if(!att.equals("name"))
                            att = "addr_" + att;
                        query += (parameters > 1 ? "and " : "") + att + " like '%" + value + "%'";

                    } else {
                        if(!att.equals("name"))
                            att = "addr_" + att;
                        query += (parameters > 1 ? "and " : "") + att + " = " + value;
                    }
                }
            }
            if(!loop) continue;
            // search parameters have been defined

            query += ";";
            executeDealerQuery(query);

            //Keep going?
            System.out.println("Would you like to make another search? (y/n)");
            if (console.nextLine().equals("n"))
                loop = false;
        }

        // no more searches to be made
    }

    /**
     * Execute the query and display the results.
     */
    public void executeDealerQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            result.beforeFirst();
            while(result.next()) {
                // Dealer name
                System.out.printf("%s\n",
                        result.getInt("NAME"));

                // Location info
                System.out.printf("\tLocated at %d %s %s, %s %d\n",
                        result.getInt("ADDR_NUM"),
                        result.getString("ADDR_STREET"),
                        result.getString("ADDR_CITY"),
                        result.getString("ADDR_STATE"),
                        result.getInt("ADDR_ZIP"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    /*
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
            if(console.nextLine().equals("n"))
                loop = false;
        }
    }
    */
}
