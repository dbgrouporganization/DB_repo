package UI;

import java.sql.Connection;
import java.util.Scanner;

public class VehicleLookup {

    private Connection conn;

    public VehicleLookup(Connection conn){
        //this.conn = conn;
        vehicleStart();
    }

    public void vehicleStart(){
        boolean loop = true;
        Scanner console = new Scanner(System.in);
        while(loop) {
            //General message
            System.out.println("Welcome to vehicle lookup, what would you like to search by?");
            System.out.println("The options are Vin, Model and year, Brand, Dealer, State or Zip.");
            System.out.println("You can also exit by saying exit.");
            // what user wants to search by
            String search = console.next();
            switch (search) {
                case "exit":
                    loop = false;
                    return;
                case "Vin":
                    System.out.println("Please enter the Vin you want to find.");
                    String vin = console.next();
                    searchVin(vin);
                    return;
                case "Model":
                    System.out.println("Please enter the model you would like to find:");
                    String Model = console.next();
                    System.out.println("Please enter the year you would like to find:");
                    String Year = console.next();
                    searchModel(Model, Year);
                    break;
                case "Brand":
                    System.out.println("Please enter the Brand you would like to find:");
                    String Brand = console.next();
                    searchBrand(Brand);
                    break;
                case "Dealer":
                    System.out.println("Please enter the Dealer you would like to find:");
                    String Dealer = console.next();
                    searchDealer(Dealer);
                    break;
                case "State":
                    System.out.println("Please enter the state you would like to search in:");
                    String State = console.next();
                    searchState(State);
                    break;
                case "Zip":
                    System.out.println("Please enter the zip code you would like to search in:");
                    String Zip = console.next();
                    searchZip(Zip);
                    break;
            }

            //Keep going?
            System.out.println("Would you like to make another search?(Y/N)");
            String res = console.next();
            if( loop && res.equals("N"))
                loop = false;
        }
    }

    public void searchBrand(String brand){
        //TODO SQL querry and print response
    }
    public void searchVin(String vin){
        //TODO SQL querry and print response
    }
    public void searchZip(String zip){
        //TODO SQL querry and print response
    }
    public void searchDealer(String dealer){
        //TODO SQL querry and print response
    }
    public void searchState(String state){
        //TODO SQL querry and print response
    }
    public void searchModel(String model, String year){
        //TODO SQL querry and print response
    }
}
