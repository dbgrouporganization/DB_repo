package UI;

import Appl.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class Customer {
    private Connection conn;
    public Customer(Connection conn){
        this.conn = conn;
        System.out.println("Welcome Appl.Customer!");
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
        System.out.println("Please enter the Appl.Model you would like to search for:");
        String model = console.next();
        System.out.println("Please enter the Year you would like to search for:");
        String year = console.next();
        //TODO SQL Query for model and year.

    }

    public void vinLookup(){
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the vin to search for:");
        int vin = console.nextInt();
        //TODO SQL Query for a vin.
        ResultSet vinResults = VehicleTable.queryVehicleTable(conn, )
    }


}
