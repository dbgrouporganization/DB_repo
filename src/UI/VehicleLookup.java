package UI;

import java.sql.Connection;
import java.util.Scanner;

public class VehicleLookup {

    private Connection conn;

    public VehicleLookup(Connection conn){
        //this.conn = conn;
    }

    public void console(){
        System.out.println("Welcome to vehicle lookup, what would you like to search by?");
        System.out.println("The options are Model, Brand, Dealer and Dealers in state.");
        System.out.println("You can also exit by saying exit.");
        Scanner console = new Scanner(System.in);
        String search = console.next();
        switch(search){
            case "exit":
                return;
            case "Model":
                System.out.println("Please enter the model you would like to find:");
                String Model = console.next();
                break;
            case "Brand":
                System.out.println("Please enter the Brand you would like to find:");
                String Brand = console.next();
                break;
            case "Dealer":
                System.out.println("Please enter the Dealer you would like to find:");
                String Dealer = console.next();
                break;
            case "Dealers in state":
                System.out.println("Please enter the State you would like to find dealers in:");
                String State = console.next();
                break;
        }

    }

}
