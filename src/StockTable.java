import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class to make and manipulate the stock table
 *
 * @author team 18
 */
public class StockTable {

	/**
	 * Reads a csv file for data and adds them to the stock table
	 * 
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName
	 * @throws SQLException
	 */
	public static void populateStockTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 *
		 * You can do the reading and adding to the table in one
		 * step, I just broke it up for example reasons
		 */
		ArrayList<Stock> stock = new ArrayList<Stock>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				stock.add(new Stock(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all customer
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createStockInsertSQL(stock);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
	/**
	 * Create the stock table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createStockTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS stock("
					     + "VIN INT PRIMARY KEY,"
                         + "DEALER_ID INT,"
                         + "BRAND_OWNER VARCHAR(255),"
					     + "CUSTOMER_ID INT,"
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
     * Adds a single stock to the database
     *
     * @param conn
     * @param vin
     * @param dealer_id
     * @param brand_owner
     * @param customer_id
     */
	public static void addStock(Connection conn, int vin, int dealer_id, String brand_owner, int customer_id){
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO stock "
				                   + "VALUES(%d,%d,\'%s\',%d);",
				                     vin, dealer_id, brand_owner, customer_id);
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
	 * @param stock: list of stock objects to add
	 * 
	 * @return
	 */
	public static String createStockInsertSQL(ArrayList<Stock> stock){
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, 
		 * tells it the table to add it to
		 * the order of the data in reference 
		 * to the columns to ad dit to
		 */
		sb.append("INSERT INTO stock (vin, dealer_id, brand_owner, customer_id) VALUES");
		
		/**
		 * For each person append a (vin, dealer_id, brand_owner, customer_id) tuple
		 * 
		 * If it is not the last person add a comma to separate
		 * 
		 * If it is the last person add a semi-colon to end the statement
		 */
		for(int i = 0; i < stock.size(); i++){
			Stock s = stock.get(i);
			sb.append(String.format("(%d,%d,\'%s\',%d)",
					s.getVin(), s.getDealer_id(), s.getBrand_owner(), s.getCustomer_id()));
			if( i != stock.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the person table 
	 * with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryStockTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses){
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
		sb.append("FROM stock ");
		
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
	public static void printStockTable(Connection conn){
		String query = "SELECT * FROM Stock;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Stock %d: %d %s %d\n",
						          result.getInt(1),
                                  result.getInt(2),
                                  result.getString(3),
						          result.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
