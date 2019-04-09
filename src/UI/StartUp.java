package UI;

import Appl.VehicleTable;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StartUp {

    Connection conn;
    Users userType;

    public void go(){
        Login login = new Login();
        conn = login.getConnection();
        userType = login.getUserType();
        switch(userType){
            case ADMIN:
                adminStart();
            case VEHICLELOOKUP:
                vehicleStart();
            case MARKETING:
                marketingStart();
            case CUSTOMER:
                customerStart();
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
        System.out.println("You will now be logged out of the database.");
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
