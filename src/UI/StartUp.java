package UI;

import Appl.VehicleTable;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class StartUp {

    Connection conn;
    Users userType;

    public void go(){
        Scanner console = new Scanner(System.in);
        boolean not_registerd = false;
        System.out.println("Would you like to Login or Sign up?(L/S)");
        String action = console.nextLine();
        action = action.toLowerCase();
        if(action.equals("s")) {
            SignIn s = new SignIn();
            s.newUser();
            System.out.println("You must now Login.");
        }
        Login login = new Login();
        conn = login.getConnection();
        userType = login.getUserType();
        switch(userType){
            case ADMIN:
                adminStart();
                break;
            case VEHICLELOOKUP:
                vehicleStart();
                break;
            case MARKETING:
                marketingStart();
                break;
            case CUSTOMER:
                customerStart();
                break;
        }
    }

    private void customerStart(){
        Customer customer = new Customer(conn);
        customer.customerStart();
        System.out.println("You will now be logged out of the database.");
        closeConn();
    }

    private void marketingStart(){
        Marketing marketing = new Marketing(conn);
        marketing.marketingStart();
        System.out.println("You will now be logged out of the database.");
        closeConn();
    }

    private void vehicleStart(){
        VehicleLookup vehicle = new VehicleLookup(conn);
        vehicle.vehicleStart();
        System.out.println("You will now be logged out of the database.");
        closeConn();
    }

    private void adminStart(){
        Admin admin = new Admin(conn);
        admin.askQuery();
        closeConn();
    }

    private void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("You have been successfully logged out of the database.");
    }

    public static void main(String args[]){
        StartUp start = new StartUp();
        start.go();
        System.out.println("Exiting the Program");
    }

}
