package UI;

import java.sql.*;
import java.util.Scanner;

/**
 * This is the admin UI destination where the admin can insert any SQL Query they chose on any table.
 */
public class Admin {

    //created connection to the database.
    private Connection conn;

    public Admin(Connection conn){
        this.conn = conn;
        System.out.println("Welcome to the admin console.");
    }

    public void askQuery(){
        boolean loop = true;
        Scanner console = new Scanner(System.in);
        while (loop){
            System.out.println("Please enter any Query below:");
            String query = console.nextLine();
            executeQuery(query);
            System.out.println("Would you like to make another query(Y/N)?");
            String rep = console.nextLine();
            if(rep.equals("N"))
                break;
        }
    }

    public void printQuery( ResultSet rs) throws SQLException{
        ResultSetMetaData data = rs.getMetaData();
        int numCols = data.getColumnCount();
        for (int i = 1; i <= numCols; i++) {
            System.out.print(data.getColumnName(i) + "\t");
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                    if (i > 1) System.out.print(",\t");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
            }
            System.out.print("\n");
        }
    }

    public void executeQuery(String query){
        try {
            /**
             * execute the query
             */
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            printQuery(result);
            stmt.close();
        } catch (SQLException e) {
            System.err.println("That is an invalid SQL Query.");
        }
    }
}
