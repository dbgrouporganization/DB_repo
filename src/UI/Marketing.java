package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Marketing {

    private Connection conn;

    private final String[] saleStringSearchParams = {"date"};
    private final String[] saleIntegerSearchParams = {"buyer_id", "seller_id"};
    private final String[] customerStringSearchParams = {"first_name", "last_name", "state", "city"};
    private final String[] customerIntegerSearchParams = {"id", "income", "zip"};

    public Marketing(Connection conn){
        this.conn = conn;
        System.out.println("\nWelcome Marketing Team!");
    }

    public void marketingStart(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("What would you like to search for? Customers or Sales?");
            System.out.println("If you would like to exit type 'exit'.");
            String search = console.nextLine();
            search = search.toLowerCase();
            switch (search) {
                case "exit":
                    loop = false;
                    break;
                case "customers":
                    customerLookup();
                    break;
                case "sales":
                    salesLookup();
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    public void salesLookup() {
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        boolean paramLoop = true;
        boolean between = false;
        int parameters = 0;

        ArrayList<String> stringParams = new ArrayList<>();
        for (String s : saleStringSearchParams) {
            stringParams.add(s);
        }

        ArrayList<String> integerParams = new ArrayList<>();
        for (String i : saleIntegerSearchParams) {
            integerParams.add(i);
        }

        String query = "SELECT date, vin, buyer_id, first_name, last_name, seller_id FROM marketing WHERE ";

        while(loop) {
            // General message
            System.out.println("What would you like to search by?");
            System.out.println("You can exit by typing 'exit'.");

            while(paramLoop) {
                if(parameters > 0) {
                    System.out.println("What else would you like to search by?");
                }
                System.out.println("Your options are Buyer ID, Seller ID, or Date.");
                System.out.println("To make the search, enter 'search'.");

                // what user wants to search by
                String att = console.nextLine();
                att = att.toLowerCase();

                if(att.equals("date"))
                    between = true;

                // exit?
                if(att.equals("exit")) {
                    loop = false;
                    paramLoop = false;
                }

                // run search?
                else if(att.equals("search")) {
                    paramLoop = false;
                }


                // valid search parameter?
                else if (!stringParams.contains(att) && !integerParams.contains(att)) {
                    System.out.println("'" + att + "' is not a valid search parameter.");
                }

                else {
                    parameters++;

                    if(between){
                        System.out.println("Enter the range of dates you would look to search. Example  YYYY-MM-DD");
                        System.out.print("Starting at: ");
                        String start = console.nextLine();
                        System.out.print("Ending at: ");
                        String end = console.nextLine();
                        System.out.println();
                        query += (parameters > 1 ? " and " : "") + att + " between " + start + " and " + end;
                        between = false;

                    } else {

                        System.out.println("Enter the " + att + " you would like to find: ");

                        String value = console.nextLine();

                        // if attribute value has an apostrophe, duplicate it for sql query
                        if (value.contains("'")) {
                            value = value.substring(0, value.indexOf("'")) + "'" + value.substring(value.indexOf("'"), value.length());
                        }

                        if (stringParams.contains(att)) {
                            query += (parameters > 1 ? "and " : "") + att + " like '%" + value + "%'";

                        } else {
                            query += (parameters > 1 ? "and " : "") + att + " = " + value;
                        }
                    }
                }
            }
            if(!loop) continue;
            // search parameters have been defined

            query += ";";
            executeSaleQuery(query);

            //Keep going?
            System.out.println("Would you like to make another search? (y/n)");
            if (console.nextLine().equals("n"))
                loop = false;
        }

        // no more searches to be made
    }

    /**
     * Execute the query and display the results.
     */
    public void executeSaleQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            result.beforeFirst();
            while(result.next()) {
                // VIN and ID
                System.out.printf("VIN: %d Date: %s\n",
                        result.getInt("VIN"),
                        result.getDate("DATE"));

                // Buyer info
                System.out.printf("Buyer: %s %s\n",
                        result.getInt("FIRST_NAME"),
                        result.getString("LAST_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void customerLookup() {
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        boolean paramLoop = true;
        boolean between = false;
        int parameters = 0;

        ArrayList<String> stringParams = new ArrayList<>();
        for (String s : customerStringSearchParams) {
            stringParams.add(s);
        }

        ArrayList<String> integerParams = new ArrayList<>();
        for (String i : customerIntegerSearchParams) {
            integerParams.add(i);
        }

        // First Name, Last Name, ID, State, City, Zip
        String query = "SELECT Customer.owner_id, first_name, last_name, addr_num, addr_street, addr_city, addr_state, " +
                "addr_zip, phone, gender, income FROM customer Natural Join owner WHERE ";

        while(loop) {
            // General message
            System.out.println("What would you like to search by?");
            System.out.println("You can exit by typing 'exit'.");

            while(paramLoop) {
                if(parameters > 0) {
                    System.out.println("What else would you like to search by?");
                }
                System.out.println("Your options are First Name, Last Name, ID, Income, City, State, Zip.");
                System.out.println("To make the search, enter 'search'.");

                // what user wants to search by
                String att = console.nextLine();
                att = att.toLowerCase();

                // if att is 'first name' change to 'first_name'
                if(att.contains(" "))
                    att = att.substring(0, att.indexOf(" ")) + "_" + att.substring(att.indexOf(" ") + 1, att.length());

                if(att.equals("income"))
                    between = true;

                // exit?
                if(att.equals("exit")) {
                    loop = false;
                    paramLoop = false;
                }

                // run search?
                else if(att.equals("search")) {
                    paramLoop = false;
                }

                // valid search parameter?
                else if (!stringParams.contains(att) && !integerParams.contains(att)) {
                    System.out.println("'" + att + "' is not a valid search parameter.");
                }

                else {
                    parameters++;

                    if(between){
                        System.out.println("Enter the range of " + att + " you would like to find: ");
                        System.out.print("Starting at: ");
                        String start = console.nextLine();
                        System.out.print("Ending at: ");
                        String end = console.nextLine();
                        System.out.println();
                        query += (parameters > 1 ? " and " : "") + att + " between " + start + " and " + end;
                        between = false;
                    } else {
                        System.out.println("Enter the " + att + " you would like to find: ");

                        String value = console.nextLine();

                        // if attribute value has an apostrophe, duplicate it for sql query
                        if (value.contains("'"))
                            value = value.substring(0, value.indexOf("'")) + "'" + value.substring(value.indexOf("'"), value.length());

                        if (stringParams.contains(att)) {
                            query += (parameters > 1 ? " and " : "") + att + " like '%" + value + "%'";
                        } else {
                            query += (parameters > 1 ? " and " : "") + att + " = " + value;
                        }
                    }
                }

            }
            if(!loop) continue;
            // search parameters have been defined

            query += ";";
            executeCustomerQuery(query);

            //Keep going?
            System.out.println("Would you like to make another Customer search? (y/n)");
            if (console.nextLine().equals("n"))
                break;

            // no more searches to be made
        }
    }

    /**
     * Execute the query and display the results.
     */
    public void executeCustomerQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            result.beforeFirst();
            while(result.next()) {
                // Customer info
                System.out.printf("Customer %d: %s %s, %s\n",
                        result.getInt("OWNER_ID"),
                        result.getString("FIRST_NAME"),
                        result.getString("LAST_NAME"),
                        result.getString("GENDER"));

                // Income
                System.out.printf("\tIncome: $%d\n", result.getInt("INCOME"));

                // Phone
                System.out.printf("\tPhone: %s\n", result.getString("PHONE"));

                // Address info
                System.out.printf("\tAddress: %d %s %s %s, %d\n",
                        result.getInt("ADDR_NUM"),
                        result.getString("ADDR_STREET"),
                        result.getString("ADDR_CITY"),
                        result.getString("ADDR_STATE"),
                        result.getInt("ADDR_ZIP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
