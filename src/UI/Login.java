package UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    private String user;
    private String pass;
    private Connection conn;
    private Users userType;

    public Login(){
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the Automobile Database!");
        System.out.print("PLease enter your Username: ");
        user = console.next();
        System.out.print("Please enter your Password: ");
        pass = console.next();
        //This section is for testing purposes.
        switch(user) {
            case "Admin":
                userType = Users.ADMIN;
                break;
            case "VehicleLookup":
                userType = Users.VEHICLELOOKUP;
                break;
            case "Customer":
                userType = Users.CUSOTMER;
                break;
            case "Marketing":
                userType = Users.MARKETING;
                break;
        }
        try {
            //This needs to be on the front of your location
            String url = "jdbc:h2:./DB/Automobile";

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            Connection conn = DriverManager.getConnection(url,
                    user,
                    pass);

        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }

    }

    public Connection getConnection(){
        return conn;
    }

    public Users getUserType(){
        return userType;
    }


}
