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
 * Class to make and manipulate the Dealer table
 *
 * @author jlb
 */
public class DealerTable {

	/**
	 * Reads a cvs file for data and adds them to the Dealer table
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName: name of csv file
	 * @throws SQLException
	 */
	public static void populateDealerTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 */
		ArrayList<Dealer> Dealer = new ArrayList<Dealer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				Dealer.add(new Dealer(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all Dealers
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createDealerInsertSQL(Dealer);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	/**
	 * Create the Dealer table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createDealerTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS Dealer("
						 + "owner_id INT PRIMARY KEY,"
					     + "name VARCHAR(255),"
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
	 * Adds a single Dealer to the database
	 *
	 * @param conn
	 * @param owner_id
	 * @param name
	 */
	public static void addDealer(Connection conn, int owner_id, String name) {
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO Dealer "
				                   + "VALUES(%d,\'%s\');",
				                     owner_id, name);
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
	 * This creates an sql statement to do a bulk add of dealers
	 * 
	 * @param Dealer: list of Dealer objects to add
	 * 
	 * @return
	 */
	public static String createDealerInsertSQL(ArrayList<Dealer> Dealer) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, tells it the table to add it to
		 * the order of the data in reference to the columns to add it to
		 */
		sb.append("INSERT INTO Dealer (owner_id, name) VALUES");
		
		/**
		 * For each Dealer append a (owner_id, name) tuple
		 * 
		 * If it is not the last Dealer add a comma to separate
		 * 
		 * If it is the last Dealer add a semi-colon to end the statement
		 */
		for(int i = 0; i < Dealer.size(); i++){
			Dealer v = Dealer.get(i);
			sb.append(String.format("(%d,\'%s\')",
					v.getOwner_id(), v.getName()));
			if( i != Dealer.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the Dealer table with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryDealerTable(Connection conn, ArrayList<String> columns, String innerJoin, ArrayList<String> whereClauses) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * Start the select query
		 */
		sb.append("SELECT ");
		
		/**
		 * If we gave no columns just give them all to us
		 * 
		 * otherwise add the columns to the query
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
		sb.append("FROM Dealer ");

		// add the inner join portion
		sb.append(innerJoin);
		
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
	 * Prints the results from this table
	 * @param results the ResultSet to print
	 */
	public static void printDealerResults(ResultSet results){
		try {
			while(results.next()){
				System.out.printf("Dealer %d: %s\n",
						results.getInt(1),
						results.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the results from a dealer lookup query
	 * @param results the ResultSet to print
	 */
	public static void printDealerQueryResults(ResultSet results){
		try {
			while(results.next()){
				System.out.printf("Dealer %s: %d %s %s %s %d\n",
						results.getString(1),
						results.getInt(2),
						results.getString(3),
						results.getString(4),
						results.getString(5),
						results.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Queries and print the table
	 * @param conn
	 */
	public static void printDealerTable(Connection conn){
		String query = "SELECT * FROM Dealer;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Dealer %d: %s\n",
						          result.getInt(1),
						          result.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
