package UI;

import Appl.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private Connection conn;
    public Customer(Connection conn){
        this.conn = conn;
        System.out.println("Welcome Customer!");
        customerStart();
    }

    public void customerStart(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("What would you like to search for dealers, models, or vin");
            System.out.println("If you would like to exit enter exit.");
            String search = console.next();
            switch (search) {
                case "exit":
                    loop = false;
                    return;
                case "dealers":
                    dealerLookup();
                    break;
                case "models":
                    modelLookup();
                    break;
                case "vin":
                    vinLookup();
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
        String naturalJoin = "NATURAL JOIN ";

        // ArrayList of where clauses
        ArrayList<String> whereClauses = new ArrayList<>();

        while(loop) {
            System.out.println("Would you like to search by name, city, state or zip?");
            String search = console.next();
            switch (search) {
                case "exit":
                    loop = false;
                    return;
                case "name":
                    System.out.println("Please enter the dealer's name to search for:");
                    String name = console.next();
                    /*
                        example query:
                        SELECT name, addr_num, addr_street, addr_city, addr_state, addr_zip FROM dealer
                        NATURAL JOIN owner WHERE name = 'Owen''s Imports';
                    */

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
                    whereClauses.add("name = " + name);

                    // query and print results
                    ResultSet nameResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(nameResults);
                    break;
                case "city":
                    System.out.println("Please enter the dealer's city to search for:");
                    String city = console.next();
                    /*
                        example query:
                        SELECT name, addr_num, addr_street, addr_city, addr_state, addr_zip FROM dealer
                        NATURAL JOIN owner WHERE addr_city = 'Rochester';
                    */

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("addr_city = " + city);

                    // query and print results
                    ResultSet cityResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(cityResults);
                    break;
                case "state":
                    System.out.println("Please enter the dealer's state to search for:");
                    String state = console.next();
                    /*
                        example query:
                        SELECT name, addr_num, addr_street, addr_city, addr_state, addr_zip FROM dealer
                        NATURAL JOIN owner WHERE addr_state = 'NY';
                    */

                    // make sure where clause array is empty then add new where clause for sql query
                    if(!whereClauses.isEmpty()) {
                        for(String s : whereClauses) {
                            whereClauses.remove(s);
                        }
                    }
                    whereClauses.add("addr_state = " + state);

                    // query and print results
                    ResultSet stateResults = DealerTable.queryDealerTable(conn, columns, naturalJoin, whereClauses);
                    DealerTable.printDealerQueryResults(stateResults);
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    public void modelLookup(){
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the Model you would like to search for:");
        String model = console.next();
        System.out.println("Please enter the Year you would like to search for:");
        int year = console.nextInt();
        /*
            example query:
            SELECT vin, vehicle.model, year, price, brand, bodystyle, color, engine, transmission, navigation, bluetooth,
            heated_seats, roof_rack, name FROM vehicle NATURAL JOIN dealer INNER JOIN options ON vehicle.options_id=options.options_id INNER JOIN model ON
            vehicle.model=model.model AND vehicle.year=model.year WHERE vehicle.model = 'Aventador' AND year = 2016;
        */

        // add attributes to ArrayList for SELECT
        String attributes[] = { "vin", "vehicle.model", "year", "price", "brand", "bodystyle", "color", "engine",
                                "transmission", "navigation", "bluetooth", "heated_seats", "roof_rack", "name" };
        ArrayList<String> columns = new ArrayList<>();
        for(String s : attributes) {
            columns.add(s);
        }

        // join with dealer on owner_id, options on options_id, and model on year and model
        String join = "NATURAL JOIN dealer NATURAL JOIN options INNER JOIN model ON vehicle.model=model.model AND vehicle.year=model.year ";

        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("vehicle.model = '" + model + "'");
        whereClauses.add("year = " + year);

        // query and print results
        ResultSet modelResults = VehicleTable.queryVehicleTable(conn, columns, join, whereClauses);
        VehicleTable.printVehicleQueryResults(modelResults);
    }

    public void vinLookup(){
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the vin to search for:");
        int vin = console.nextInt();
        /*
            example query:
            SELECT vin, vehicle.model, year, price, brand, bodystyle, color, engine, transmission, navigation,
            bluetooth, heated_seats, roof_rack, name FROM vehicle NATURAL JOIN dealer NATURAL JOIN options
            INNER JOIN model ON vehicle.model=model.model AND vehicle.year=model.year WHERE vin = 12345;
        */

        // add attributes to ArrayList for SELECT
        String attributes[] = { "vin", "vehicle.model", "year", "price", "brand", "bodystyle", "color", "engine",
                "transmission", "navigation", "bluetooth", "heated_seats", "roof_rack", "name" };
        ArrayList<String> columns = new ArrayList<>();
        for(String s : attributes) {
            columns.add(s);
        }

        // join with dealer on owner_id, options on options_id, and model on year and model
        String innerJoin = "NATURAL JOIN dealer NATURAL JOIN options INNER JOIN model ON vehicle.model=model.model AND vehicle.year=model.year ";

        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("vin = " + vin);

        // query and print results
        ResultSet vinResults = VehicleTable.queryVehicleTable(conn, columns, innerJoin, whereClauses);
        VehicleTable.printVehicleQueryResults(vinResults);
    }
}
