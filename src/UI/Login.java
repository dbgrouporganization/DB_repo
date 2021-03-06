package UI;

import java.sql.*;
import java.util.Scanner;

public class Login {

    private String user;
    private String pass;
    private Connection conn;
    private Users userType;

    public Login(){
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the Automobile Database!");
        Boolean notLoggedIn = true;
        String type = null;
        while(notLoggedIn) {
            System.out.print("Please enter your Username: ");
            user = console.next();
            System.out.print("Please enter your Password: ");
            pass = console.next();
            try {
                //This needs to be on the front of your location
                String url = "jdbc:h2:./DB/Automobile";

                //This tells it to use the h2 driver
                Class.forName("org.h2.Driver");

                //creates the connection
                conn = DriverManager.getConnection(url,
                        user,
                        pass);
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("Select * from INFORMATION_SCHEMA.ROLES;");
                result.next();
                type = result.getString(1);
                notLoggedIn = false;
                stmt.close();
            } catch (SQLException | ClassNotFoundException e) {
                System.err.println("The password or username is incorrect please try again.");
                notLoggedIn = true;
            }
        }
        //This section is for testing purposes.
        switch(type) {
            case "ADMIN":
                userType = Users.ADMIN;
                break;
            case "VEHICLELOOKUP":
                userType = Users.VEHICLELOOKUP;
                break;
            case "CUSTOMER":
                userType = Users.CUSTOMER;
                break;
            case "MARKETING":
                userType = Users.MARKETING;
                break;
        }
    }

    public Connection getConnection(){
        return conn;
    }

    public Users getUserType() {
        return userType;
    }
}
