package UI;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VehicleLookup {

    private Connection conn;

    private final String[] possibleSearchParams = {"", "", "", "", ""};

    public VehicleLookup(Connection conn){
        this.conn = conn;
    }

    public void console(){

        ArrayList<String> params = new ArrayList<>();
        for(String s : possibleSearchParams) {
            params.add(s);
        }

        boolean queryLoop = true;
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to vehicle lookup!");
        System.out.println("You can exit by saying 'exit'.");


        while(queryLoop) {
            boolean paramLoop = true;

            //General message
            System.out.println("What would you like to search by?");
            int parameters = 0;

            String query = "SELECT * FROM "+viewName+" WHERE ";

            while(paramLoop) {
                if(parameters > 0) {
                    System.out.println("What else would you like to search by?");
                }
                System.out.println("The options are Vin, Model and year, Brand, Dealer, State, or Zip.");
                System.out.println("To make the search, enter 'search'.");

                // what user wants to search by
                String att = console.next();

                // exit?
                if(att.equals("exit")) {
                    queryLoop = false;
                    paramLoop = false;
                    continue;
                }

                // run search?
                if(att.equals("search")) {
                    paramLoop = false;
                    continue;
                }

                // valid search parameter?
                if(!params.contains(att)) {
                     System.out.println(att+" is not a valid search parameter.");
                     continue;
                }

                parameters++;

                System.out.println("Enter the "+att+" you would like to find: ");

                String value = console.next();

                if(!(att.equals("vin") || att.equals("year") || att.equals("zip")))
                    value = "'"+value+"'";

                query += (parameters > 1 ? "and " : "") + att + " = " + value;
            }
            // search parameters have been defined

            query += ";";
            executeQuery(query);

            //Keep going?
            System.out.println("Would you like to make another search?(Y/N)");
            if(console.next().equals("N"))
                queryLoop = false;
        }

        // no more searches to be made
    }

    public void executeQuery(String query){
        try {
            /**
             * execute the query
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
