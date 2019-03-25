package UI;

import java.sql.Connection;
import java.util.Scanner;

public class Customer {
    private Connection conn;
    public Customer(Connection conn){
        this.conn = conn;
        System.out.println("Welcome Customer!");

    }

    public void customerStart(){
        Scanner console = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("What would you like to search for dealers, models, or cars.");
            String search = console.next();
            switch (search) {
                case "exit":
                    loop = false;
                    return;
            }
        }
    }


}
