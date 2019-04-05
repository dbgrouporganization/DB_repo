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
 * Class to make and manipulate the owner table
 * @author team 18
 */
public class OwnerTable {

	/**
	 * Reads a csv file for data and adds them to the owner table
	 * 
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName
	 * @throws SQLException
	 */
	public static void populateOwnerTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 *
		 * You can do the reading and adding to the table in one
		 * step, I just broke it up for example reasons
		 */
		ArrayList<Owner> Owner = new ArrayList<Owner>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				Owner.add(new Owner(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all Owner
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createOwnerInsertSQL(Owner);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
	/**
	 * Create the owner table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createOwnerTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS owner("
						 + "OWNER_ID INT PRIMARY KEY,"
					     + "ADDR_NUM INT,"
						 + "ADDR_STREET VARCHAR(255),"
						 + "ADDR_CITY VARCHAR(255),"
						 + "ADDR_STATE CHAR(2),"
						 + "ADDR_ZIP INT,"
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
	 * Adds a single owner to the database
	 *
	 * @param conn
	 * @param owner_id
	 * @param addr_num
	 * @param addr_street
	 * @param addr_city
	 * @param addr_state
	 * @param addr_zip
	 */
	public static void addOwner(Connection conn, int owner_id, int addr_num, String addr_street,
								   String addr_city, String addr_state, int addr_zip){
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO owner "
				                   + "VALUES(%d,%sd,\'%s\',\'%s\',\'%s\',%d);",
									owner_id, addr_num, addr_street, addr_city, addr_state, addr_zip);
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
	 * This creates an sql statement to do a bulk add of owner
	 * 
	 * @param owner: list of Owner objects to add
	 * 
	 * @return
	 */
	public static String createOwnerInsertSQL(ArrayList<Owner> owner){
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, 
		 * tells it the table to add it to
		 * the order of the data in reference 
		 * to the columns to ad dit to
		 */
		sb.append("INSERT INTO owner (owner_id, addr_num, addr_street, addr_city, addr_state, addr_zip) VALUES");
		
		/**
		 * For each owner append a (owner_id, addr_num, addr_street, addr_city, addr_state, addr_zip) tuple
		 * 
		 * If it is not the last owner add a comma to separate
		 * 
		 * If it is the last owner add a semi-colon to end the statement
		 */
		for(int i = 0; i < owner.size(); i++){
			Owner c = owner.get(i);
			sb.append(String.format("(%d,%d,\'%s\',\'%s\',\'%s\',%d)",
					c.getOwner_id(), c.getAddr_num(), c.getAddr_street(), c.getAddr_city(), c.getAddr_state(), c.getAddr_zip()));
			if( i != owner.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the owner table
	 * with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryOwnerTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses){
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
		sb.append("FROM owner ");
		
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
	public static void printOwnerTable(Connection conn){
		String query = "SELECT * FROM owner;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Owner: %d %d %s %s %s %d\n",
						          result.getInt(1),
						          result.getInt(2),
						          result.getString(3),
						          result.getString(4),
								  result.getString(5),
							   	  result.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
