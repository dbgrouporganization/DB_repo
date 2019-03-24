import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the Sale table
 *
 * @author omg
 */
public class SaleTable {

	/**
	 * Reads a cvs file for data and adds them to the Sale table
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName: name of csv file
	 * @throws SQLException
	 */
	public static void populateSaleTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 */
		ArrayList<Sale> sale = new ArrayList<Sale>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				sale.add(new Sale(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all sales
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createSaleInsertSQL(sale);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	/**
	 * Create the Sale table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createSaleTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS sale("
					     + "DATE VARCHAR(255),"
					     + "VIN INT,"
					     + "CUSTOMER_ID INT,"
					     + "DEALER_ID INT,"
						 + "PRIMARY KEY(DATE, VIN, CUSTOMER_ID),"
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
	 * Adds a single Sale to the database
	 *
	 * @param conn
	 * @param date
	 * @param vin
	 * @param customer_id
	 */
	public static void addSale(Connection conn, String date, int vin, int customer_id, int dealer_id) {
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO Sale "
				                   + "VALUES(\'%s\',%d,\'%s\',%d);",
				                     date, vin, customer_id, dealer_id);
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
	 * This creates an sql statement to do a bulk add of people
	 * 
	 * @param sale: list of Sale objects to add
	 * 
	 * @return
	 */
	public static String createSaleInsertSQL(ArrayList<Sale> sale) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, tells it the table to add it to
		 * the order of the data in reference to the columns to add it to
		 */
		sb.append("INSERT INTO sale (date, vin, customer_id, dealer_id) VALUES");
		
		/**
		 * For each sale append a (date, vin, customer_id) tuple
		 * 
		 * If it is not the last sale add a comma to separate
		 * 
		 * If it is the last sale add a semi-colon to end the statement
		 */
		for(int i = 0; i < sale.size(); i++){
			Sale v = sale.get(i);
			sb.append(String.format("(\'%s\',%d,\'%s\',%d)",
					v.getDate(), v.getVin(), v.getCustomer_id(), v.getDealer_id()));
			if( i != sale.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the Sale table with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet querySaleTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses) {
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
		sb.append("FROM Sale ");
		
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
	 * Queries and prints the table
	 * @param conn
	 */
	public static void printSaleTable(Connection conn){
		String query = "SELECT * FROM Sale;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Sale %s: %d %s %d\n",
						          result.getString(1),
						          result.getInt(2),
						          result.getString(3),
								  result.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
