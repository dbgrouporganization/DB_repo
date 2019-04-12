package UI;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VehicleLookup {

    private Connection conn;

    private final String[] viewAttributes = {"VIN", "BRAND", "MODEL", "YEAR", "DEALER", "CITY","STATE", "ZIP",
                                            "OPTIONS_ID", "COLOR", "ENGINE", "TRANSMISSION", "NAVIGATION",
                                            "BLUETOOTH", "HEATED_SEATS", "ROOF_RACK", "PRICE"};

    private final String[] stringSearchParams = {"model", "brand", "dealer", "city", "state"};
    private final String[] integerSearchParams = {"vin", "year", "zip"};

    public VehicleLookup(Connection conn) {
        this.conn = conn;
    }

    public void vehicleStart() {
        ArrayList<String> stringParams = new ArrayList<>();
        for (String s : stringSearchParams) {
            stringParams.add(s);
        }

        ArrayList<String> integerParams = new ArrayList<>();
        for (String i : integerSearchParams) {
            integerParams.add(i);
        }

        boolean queryLoop = true;
        Scanner console = new Scanner(System.in);
        System.out.println("\nWelcome to vehicle lookup!");
        System.out.println("You can exit by typing 'exit'.");

        while (queryLoop) {
            boolean paramLoop = true;

            //General message
            System.out.println("What would you like to search by?");
            int parameters = 0;

            String query = "SELECT * FROM VEHICLELOOKUP WHERE ";

            while (paramLoop) {
                if (parameters > 0) {
                    System.out.println("What else would you like to search by?");
                }
                System.out.println("The options are Vin, Model, Year, Brand, Dealer, City, State, or Zip.");
                System.out.println("To make the search, enter 'search'.");

                // what user wants to search by
                String att = console.nextLine();
                att = att.toLowerCase();

                // exit?
                if (att.equals("exit")) {
                    queryLoop = false;
                    paramLoop = false;
                    continue;
                }

                // run search?
                else if (att.equals("search")) {
                    paramLoop = false;
                    continue;
                }

                // valid search parameter?
                else if (!stringParams.contains(att) && !integerParams.contains(att)) {
                    System.out.println("'" + att + "' is not a valid search parameter.");
                    continue;
                }

                else {
                    parameters++;

                    System.out.println("Enter the " + att + " you would like to find: ");

                    String value = console.nextLine();

                    // if attribute value has an apostrophe, duplicate it for sql query
                    if(value.contains("'")) {
                        value = value.substring(0, value.indexOf("'")) + "'" + value.substring(value.indexOf("'"), value.length());
                    }

                    if (stringParams.contains(att)) {
                        query += (parameters > 1 ? "and " : "") + att + " like '%" + value + "%'";

                    } else {
                        query += (parameters > 1 ? "and " : "") + att + " = " + value;
                    }
                }
            }
            if(!queryLoop) continue;
            // search parameters have been defined

            query += ";";
            executeQuery(query);

            //Keep going?
            System.out.println("Would you like to make another search? (y/n)");
            if (console.nextLine().equals("n"))
                queryLoop = false;
        }

        // no more searches to be made
    }

    /**
     * Execute the query and display the results.
     */
    public void executeQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            result.beforeFirst();
            while(result.next()) {
                // Model info
                System.out.printf("%d %s %s $%d\n",
                            result.getInt("YEAR"),
                            result.getString("BRAND"),
                            result.getString("MODEL"),
                            result.getInt("PRICE"));

                //Vin info
                System.out.printf("\tVin: %d\n", result.getInt("VIN") );

                // Options info
                System.out.printf("\tColor: %s\n", result.getString("COLOR") );
                System.out.printf("\tEngine: %s\n", result.getString("ENGINE") );
                System.out.printf("\tTransmission: %s\n", result.getString("TRANSMISSION") );

                String optionsStr = "Options: ";
                int options = 0;

                if(result.getBoolean("NAVIGATION"))
                    optionsStr += "GPS";

                if(result.getBoolean("BLUETOOTH"))
                    optionsStr += (options > 0 ? ", " : "") + "Bluetooth" ;

                if(result.getBoolean("HEATED_SEATS"))
                    optionsStr += (options > 0 ? ", " : "") + "Heated Seats" ;

                if(result.getBoolean("ROOF_RACK"))
                    optionsStr +=(options > 0 ? ", " : "") + "Roof Rack" ;

                System.out.printf("\t%s\n", optionsStr);

                // Owner info
                System.out.printf("\tOwner: %s located in %s, %s %d\n",
                            result.getString("DEALER"),
                            result.getString("CITY"),
                            result.getString("STATE"),
                            result.getInt("ZIP"));
            }

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }
}
