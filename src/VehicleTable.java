import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the Vehicle table
 *
 * @author jlb
 */
public class VehicleTable {

	/**
	 * Reads a cvs file for data and adds them to the vehicle table
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName: name of csv file
	 * @throws SQLException
	 */
	public static void populateVehicleTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 */
		ArrayList<Vehicle> vehicle = new ArrayList<Vehicle>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				vehicle.add(new Vehicle(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all vehicles
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createVehicleInsertSQL(vehicle);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	/**
	 * Create the Vehicle table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createVehicleTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS vehicle("
					     + "VIN INT PRIMARY KEY,"
					     + "MODEL VARCHAR(255),"
						 + "YEAR INT,"
					     + "OPTIONS_ID VARCHAR(255),"
					     + "PRICE NUMERIC(10,2),"
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
	 * Adds a single Vehicle to the database
	 *
	 * @param conn
	 * @param vin
	 * @param model
	 * @param options_id
	 * @param price
	 */
	public static void addVehicle(Connection conn, int vin, String model, int year, String options_id, float price) {
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO Vehicle "
				                   + "VALUES(%d,\'%s\',%d, \'%s\',\'%f\');",
				                     vin, model, year, options_id, price);
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
	 * @param vehicle: list of Vehicle objects to add
	 * 
	 * @return
	 */
	public static String createVehicleInsertSQL(ArrayList<Vehicle> vehicle) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, tells it the table to add it to
		 * the order of the data in reference to the columns to add it to
		 */
		sb.append("INSERT INTO vehicle (vin, model, year, options_id, price) VALUES");
		
		/**
		 * For each vehicle append a (vin, model, options_id, price) tuple
		 * 
		 * If it is not the last vehicle add a comma to separate
		 * 
		 * If it is the last vehicle add a semi-colon to end the statement
		 */
		for(int i = 0; i < vehicle.size(); i++){
			Vehicle v = vehicle.get(i);
			sb.append(String.format("(%d,\'%s\', %d, \'%s\',\'%s\')",
					v.getVIN(), v.getModel(), v.getYear(), v.getOptions_ID(), v.getPrice()));
			if( i != vehicle.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the Vehicle table with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryVehicleTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses) {
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
		sb.append("FROM Vehicle ");
		
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
	public static void printVehicleTable(Connection conn){
		String query = "SELECT * FROM Vehicle;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Vehicle %d: %s %d %s %f\n",
						          result.getInt(1),
						          result.getString(2),
						          result.getInt(3),
						          result.getString(4),
						          result.getFloat(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
