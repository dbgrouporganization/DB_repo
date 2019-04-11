package UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SignIn {

    Connection conn;

    public SignIn(){
        try {
            //This needs to be on the front of your location
            String url = "jdbc:h2:" + "./DB/Automobile";

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url, "user", "1234");
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    public void newUser(){
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter a username: ");
        String user = console.nextLine();
        System.out.println("Please enter a password: ");
        String pass = console.nextLine();
        boolean loop = true;
        while (loop) {
            System.out.println("What kind of user are you?(Marketing, Customer or Dealer)");
            String type = console.nextLine();
            switch (type) {
                case "Marketing":
                    createMarketer(user, pass);
                    loop = false;
                    break;
                case "Customer":
                    createCustomer(user, pass);
                    loop = false;
                    break;
                case "Dealer":
                    createLookup(user, pass);
                    loop = false;
                    break;
                default:
                    System.out.println("You have not entered a valid type, please try again.");
                    loop = true;
                    break;
            }
        }
        System.out.println("Successfully created user.");
        try {
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createCustomer(String user, String pass){
        try{
            String login = "Create User " + user + " Password '" + pass + "'; ";
            String access = " GRANT Customer to " + user + " ;";
            Statement stmt = conn.createStatement();
            stmt.execute(login + access);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createLookup(String user, String pass){
        try{
            String login = "Create User " + user + " Password '" + pass + "'; ";
            String access = "GRANT VehicleLookup to " + user + ";";
            Statement stmt = conn.createStatement();
            stmt.execute(login + access);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createMarketer(String user, String pass){
        try{
            String login = "Create User " + user + " Password '" + pass + "'; ";
            String access = " Grant Marketing to " + user + ";";
            Statement stmt = conn.createStatement();
            stmt.execute(login + access);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
