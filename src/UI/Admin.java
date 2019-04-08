package UI;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
        askQuery();
    }

    public void askQuery(){
        boolean loop = true;
        Scanner console = new Scanner(System.in);
        while (loop){
            System.out.println("Please enter any Query below:");
            String query = console.next();
            executeQuery(query);

            System.out.println("Would you like to make another query(Y/N)?");
            String rep = console.next();
            if(rep.equals("N"))
                loop = false;
        }
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
