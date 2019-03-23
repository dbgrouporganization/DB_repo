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
public class ModelTable {

	/**
	 * Reads a cvs file for data and adds them to the Model table
	 * Does not create the table. It must already be created
	 * 
	 * @param conn: database connection to work with
	 * @param fileName: name of csv file
	 * @throws SQLException
	 */
	public static void populateModelTableFromCSV(Connection conn, String fileName) throws SQLException {
		/**
		 * Structure to store the data as you read it in
		 * Will be used later to populate the table
		 */
		ArrayList<Model> model = new ArrayList<Model>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				model.add(new Model(split));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Creates the SQL query to do a bulk add of all models
		 * that were read in. This is more efficient then adding one
		 * at a time
		 */
		String sql = createModelInsertSQL(model);

		/**
		 * Create and execute an SQL statement
		 *
		 * execute only returns if it was successful
		 */
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}

	/**
	 * Create the Model table with the given attributes
	 * 
	 * @param conn: the database connection to work with
	 */
	public static void createModelTable(Connection conn){
		try {
			String query = "CREATE TABLE IF NOT EXISTS model("
					     + "MYEAR INT PRIMARY KEY,"
					     + "MNAME VARCHAR(255) PRIMARY KEY ,"
					     + "BRAND VARCHAR(255),"
					     + "BODYSTYLE VARCHAR(255),"
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
     * Adds a single Model to the database
     *
     * @param conn
     * @param MYear
     * @param MName
     * @param Brand
     * @param BodyStyle
     */
	public static void addModel(Connection conn, int MYear, String MName, String Brand, String BodyStyle) {
		
		/**
		 * SQL insert statement
		 */
		String query = String.format("INSERT INTO Model "
				                   + "VALUES(%d,\'%s\',\'%s\',\'%s\');",
				                     MYear, MName, Brand, BodyStyle);
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
	 * @param model: list of Model objects to add
	 * 
	 * @return
	 */
	public static String createModelInsertSQL(ArrayList<Model> model) {
		StringBuilder sb = new StringBuilder();
		
		/**
		 * The start of the statement, tells it the table to add it to
		 * the order of the data in reference to the columns to add it to
		 */
		sb.append("INSERT INTO model (VIM, MODEL, OPTIONS_ID, PRICE) VALUES");
		
		/**
		 * For each model append a (vim, model, options_id, price) tuple
		 * 
		 * If it is not the last model add a comma to separate
		 * 
		 * If it is the last model add a semi-colon to end the statement
		 */
		for(int i = 0; i < model.size(); i++){
			Model v = model.get(i);
			sb.append(String.format("(%d,\'%s\',\'%s\',\'%s\')", 
					v.getMYear(), v.getMName(), v.getBrand(), v.getBodyStyle()));
			if( i != model.size()-1){
				sb.append(",");
			}
			else{
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Makes a query to the Model table with given columns and conditions
	 * 
	 * @param conn
	 * @param columns: columns to return
	 * @param whereClauses: conditions to limit query by
	 * @return
	 */
	public static ResultSet queryModelTable(Connection conn, ArrayList<String> columns, ArrayList<String> whereClauses) {
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
		sb.append("FROM Model ");
		
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
	public static void printModelTable(Connection conn){
		String query = "SELECT * FROM Model;";
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			while(result.next()){
				System.out.printf("Model %d: %s %s %s\n",
						          result.getInt(1),
						          result.getString(2),
						          result.getString(3),
						          result.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
