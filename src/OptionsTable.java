import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the Options table
 *
 * @author jlb
 */
public class OptionsTable {

	/**
	 * Reads a cvs file for data and adds them to the Options table
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName: name of csv file
	 * @throws SQLException
	 */
	public static void populateOptionsTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 */
		ArrayList<Options> Options = new ArrayList<Options>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				Options.add(new Options(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all Options
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createOptionsInsertSQL(Options);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	/**
	 * Create the Options table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createOptionsTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS Options("
					     + "options_id INT PRIMARY KEY,"
					     + "color VARCHAR(255),"
					     + "engine VARCHAR(255),"
					     + "transmission VARCHAR(255),"
						 + "navigation BOOLEAN,"
						 + "bluetooth BOOLEAN,"
					 	 + "heated_seats BOOLEAN,"
						 + "roof_rack BOOLEAN,"
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
	 * Adds a single Options to the database
	 *
	 * @param conn
	 * @param options_id
	 * @param color
	 * @param engine
	 * @param transmission
	 * @param navigation
	 * @param bluetooth
	 * @param heated_seats
	 * @param roof_rack
	 */
	public static void addOptions(Connection conn, int options_id, String color, String engine, String transmission,
								  Boolean navigation, Boolean bluetooth, Boolean heated_seats, Boolean roof_rack) {
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO Options "
				                   + "VALUES(%d,\'%s\',\'%s\',\'%s\',%d,\'%s\',\'%s\',%d);",
				                     id, name, addr_street, addr_num, addr_city, addr_state, addr_zip);
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
	 * This creates an sql statement to do a bulk add of Options
	 * 
	 * @param Options: list of Options objects to add
	 * 
	 * @return
	 */
	public static String createOptionsInsertSQL(ArrayList<Options> Options) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, tells it the table to add it to
		 * the order of the data in reference to the columns to add it to
		 */
		sb.append("INSERT INTO Options (id, name, addr_street, addr_num, addr_city, addr_state, addr_zip) VALUES");
		
		/**
		 * For each Options append a (id, name, addr_street, addr_num, addr_city, addr_state, addr_zip) tuple
		 * 
		 * If it is not the last Options add a comma to separate
		 * 
		 * If it is the last Options add a semi-colon to end the statement
		 */
		for(int i = 0; i < Options.size(); i++){
			Options v = Options.get(i);
			sb.append(String.format("(%d,\'%s\',\'%s\',%d,\'%s\',\'%s\',%d)",
					v.getId(), v.getName(), v.getAddr_street(), v.getAddr_num(), v.getAddr_city(), v.getAddr_state(), v.getAddr_zip()));
			if( i != Options.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the Options table with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryOptionsTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses) {
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
		sb.append("FROM Options ");
		
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
	public static void printOptionsTable(Connection conn){
		String query = "SELECT * FROM Options;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Options %d: %s %s %d %s %s %d\n",
						          result.getInt(1),
						          result.getString(2),
								  result.getString(3),
								  result.getInt(4),
								  result.getString(5),
						          result.getString(6),
						          result.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
