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
            }
        }
    }

    public void dealerLookup(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
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
                    //TODO SQL Query for dealer's name

                    // if name of dealer has an apostrophe duplicate it for sql
                    if(name.contains("'")) {
                        name = name.substring(0, name.indexOf("'")) + "'" + name.substring(name.indexOf("'"), name.length());
                    }
                    // create where clause for sql query
                    ArrayList<String> whereClauses = new ArrayList<String>();
                    whereClauses.add("name = " + name);
                    // query and print results
                    ResultSet nameResults = DealerTable.queryDealerTable(conn, new ArrayList<>(), whereClauses);
                    DealerTable.printDealerResults(nameResults);

                    break;
                case "city":
                    System.out.println("Please enter the dealer's city to search for:");
                    String city = console.next();
                    //TODO SQL Query for dealer's city
                    break;
                case "state":
                    System.out.println("Please enter the dealer's state to search for:");
                    String state = console.next();
                    //TODO SQL Query for dealer's state
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
        //TODO SQL Query for model and year.

        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<String>();
        whereClauses.add("vin = " + vin);
        // query and print results
        ResultSet vinResults = VehicleTable.queryVehicleTable(conn, new ArrayList<>(), whereClauses);
        VehicleTable.printVehicleResults(vinResults);
    }

    public void vinLookup(){
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the vin to search for:");
        int vin = console.nextInt();
        // create where clause for sql query
        ArrayList<String> whereClauses = new ArrayList<String>();
        whereClauses.add("vin = " + vin);
        // query and print results
        ResultSet vinResults = VehicleTable.queryVehicleTable(conn, new ArrayList<>(), whereClauses);
        VehicleTable.printVehicleResults(vinResults);
    }
}
