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
        System.out.println("\nWelcome Customer!");
        customerStart();
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
        String naturalJoin = "NATURAL JOIN ";

        // ArrayList of where clauses
        ArrayList<String> whereClauses = new ArrayList<>();

        while(loop) {
            System.out.println("Would you like to search by name, city, state or zip?");
            String search = console.next();
            search = search.toLowerCase();
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
                    whereClauses.add("name = '" + name + "'");

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
                    whereClauses.add("addr_city = '" + city + "'");

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
                    whereClauses.add("addr_state = '" + state + "'");

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
            SELECT vin, vehicle.model, vehicle.year, price, brand, bodystyle, color, engine, transmission, navigation, bluetooth,
            heated_seats, roof_rack, name FROM vehicle NATURAL JOIN model INNER JOIN dealer ON vehicle.owner_id = dealer.owner_id
            INNER JOIN options ON vehicle.options_id = options.options_id WHERE vehicle.model = 'Aventador' AND vehicle.year = 2016;
        */

        // add attributes to ArrayList for SELECT
        String attributes[] = { "vin", "vehicle.model", "vehicle.year", "price", "brand", "bodystyle", "color", "engine",
                                "transmission", "navigation", "bluetooth", "heated_seats", "roof_rack", "name" };
        ArrayList<String> columns = new ArrayList<>();
        for(String s : attributes) {
            columns.add(s);
        }

        // natural join with model, inner join with dealer on owner_id, and inner join with options on options_id
        String join = "NATURAL JOIN model INNER JOIN dealer ON vehicle.owner_id = dealer.owner_id INNER JOIN options ON vehicle.options_id = options.options_id ";

        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("vehicle.model = '" + model + "'");
        whereClauses.add("vehicle.year = " + year);

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
            SELECT * FROM VehicleLookup WHERE vin = 10134362;
         */
        /*
            example query:
            SELECT vin, vehicle.model, vehicle.year, price, brand, bodystyle, color, engine, transmission, navigation, bluetooth,
            heated_seats, roof_rack, name FROM vehicle NATURAL JOIN model INNER JOIN dealer ON vehicle.owner_id = dealer.owner_id
            INNER JOIN options ON vehicle.options_id = options.options_id WHERE vin = 10134362;
        */

        // add attributes to ArrayList for SELECT
        String attributes[] = { "vin", "vehicle.model", "vehicle.year", "price", "brand", "bodystyle", "color", "engine",
                "transmission", "navigation", "bluetooth", "heated_seats", "roof_rack", "name" };
        ArrayList<String> columns = new ArrayList<>();
        for(String s : attributes) {
            columns.add(s);
        }

        // natural join with model, inner join with dealer on owner_id, and inner join with options on options_id
        String join = "NATURAL JOIN model INNER JOIN dealer ON vehicle.owner_id = dealer.owner_id INNER JOIN options ON vehicle.options_id = options.options_id ";

        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<>();
        whereClauses.add("vin = " + vin);

        // query and print results
        ResultSet vinResults = VehicleTable.queryVehicleTable(conn, columns, join, whereClauses);
        VehicleTable.printVehicleQueryResults(vinResults);
    }
}
