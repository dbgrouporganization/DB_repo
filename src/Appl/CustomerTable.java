package Appl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the customer table
 * @author team 18
 */
public class CustomerTable {

	/**
	 * Reads a csv file for data and adds them to the customer table
	 * 
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName
	 * @throws SQLException
	 */
	public static void populateCustomerTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 *
		 * You can do the reading and adding to the table in one
		 * step, I just broke it up for example reasons
		 */
		ArrayList<Customer> Customer = new ArrayList<Customer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				Customer.add(new Customer(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all Customer
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createCustomerInsertSQL(Customer);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
	/**
	 * Create the customer table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createCustomerTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS customer("
						 + "OWNER_ID INT PRIMARY KEY,"
					     + "FIRST_NAME VARCHAR(255),"
					     + "LAST_NAME VARCHAR(255),"
						 + "PHONE VARCHAR(255),"
					 	 + "GENDER VARCHAR(255),"
						 + "INCOME NUMERIC(10,2)"
					     + ");" ;
			
			/**
			 * Create a query and execute
			 */
			Statement stmt = conn.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a single customer to the database
	 *
	 * @param conn
	 * @param owner_id
	 * @param fName
	 * @param lName
	 * @param phone
	 * @param gender
	 * @param income
	 */
	public static void addCustomer(Connection conn, int owner_id, String fName, String lName, String phone, String gender, float income){
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO customer "
				                   + "VALUES(%d,\'%s\',\'%s\',\'%s\',\'%s\',%f);",
									owner_id, fName, lName, phone, gender, income);
		try {
			/**
			 * create and execute the query
			 */
			Statement stmt = conn.createStatement();
			stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This creates an sql statement to do a bulk add of customer
	 * 
	 * @param customer: list of Customer objects to add
	 * 
	 * @return
	 */
	public static String createCustomerInsertSQL(ArrayList<Customer> customer){
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, 
		 * tells it the table to add it to
		 * the order of the data in reference 
		 * to the columns to ad dit to
		 */
		sb.append("INSERT INTO customer (OWNER_ID, FIRST_NAME, LAST_NAME, PHONE, GENDER, INCOME) VALUES");
		
		/**
		 * For each customer append a (OWNER_ID, FIRST_NAME, LAST_NAME, PHONE, GENDER, INCOME) tuple
		 * 
		 * If it is not the last customer add a comma to separate
		 * 
		 * If it is the last customer add a semi-colon to end the statement
		 */
		for(int i = 0; i < customer.size(); i++){
			Customer c = customer.get(i);
			sb.append(String.format("(%d,\'%s\',\'%s\',\'%s\',\'%s\',%f)",
					c.getOwner_id(), c.getfName(), c.getlName(), c.getPhone(), c.getGender(), c.getIncome()));
			if( i != customer.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the customer table
	 * with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryCustomerTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses){
		StringBuilder sb = new StringBuilder();
		
		/**
		 * Start the select query
		 */
		sb.append("SELECT ");
		
		/**
		 * If we gave no columns just give them all to us
		 * 
		 * other wise add the columns to the query
		 * adding a comma top separate
		 */
		if(columns.isEmpty()){
			sb.append("* ");
		}
		else{
			for(int i = 0; i < columns.size(); i++){
				if(i != columns.size() - 1){
				    sb.append(columns.get(i) + ", ");
				}
				else{
					sb.append(columns.get(i) + " ");
				}
			}
		}
		
		/**
		 * Tells it which table to get the data from
		 */
		sb.append("FROM customer ");
		
		/**
		 * If we gave it conditions append them
		 * place an AND between them
		 */
		if(!whereClauses.isEmpty()){
			sb.append("WHERE ");
			for(int i = 0; i < whereClauses.size(); i++){
				if(i != whereClauses.size() -1){
					sb.append(whereClauses.get(i) + " AND ");
				}
				else{
					sb.append(whereClauses.get(i));
				}
			}
		}
		
		/**
		 * close with semi-colon
		 */
		sb.append(";");
		
		//Print it out to verify it made it right
		System.out.println("Query: " + sb.toString());
		try {
			/**
			 * Execute the query and return the result set
			 */
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Queries and print the table
	 * @param conn
	 */
	public static void printCustomerTable(Connection conn){
		String query = "SELECT * FROM customer;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Customer: %d %s %s %s %s %f\n",
						          result.getInt(1),
						          result.getString(2),
								  result.getString(3),
							   	  result.getString(4),
								  result.getString(5),
							 	  result.getFloat(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
