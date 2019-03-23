import java.sql.*;
import java.util.ArrayList;

/**
 * This is a sample main program. 
 * You will create something similar
 * to run your database.
 * 
 * @author team 18
 */
public class DB_Main {

	//The connection to the database
	private Connection conn;
	
	/**
	 * Create a database connection with the given params
	 * @param location: path of where to place the database
	 * @param user: user name for the owner of the database
	 * @param password: password of the database owner
	 */
	public void createConnection(String location, String user, String password) {
		try {
			//This needs to be on the front of your location
			String url = "jdbc:h2:" + location;
			
			//This tells it to use the h2 driver
			Class.forName("org.h2.Driver");
			
			//creates the connection
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			//You should handle this better
			e.printStackTrace();
		}
	}
	
	/**
	 * just returns the connection
	 * @return: returns class level connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * When your database program exits 
	 * you should close the connection
	 */
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts and runs the database
	 * @param args: not used but you can use them
	 */
	public static void main(String[] args) {

		DB_Main demo = new DB_Main();
		
		// Hard drive location of the database
		String location = "./h2demo/h2demo";
		String user = "user";
		String password = "1234";
		
		// Create the database connections, basically makes the database
		demo.createConnection(location, user, password);
		
		try {
			
			/**
			 * Vehicle Table Population
			 */
			VehicleTable.createVehicleTable(demo.getConnection());
			VehicleTable.populateVehicleTableFromCSV(demo.getConnection(),"./csv files/vehicle.csv");
			//Just displays the table
			VehicleTable.printVehicleTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM vehicle");
			ResultSet results1 = VehicleTable.queryVehicleTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Brand Table Population
			 */
			BrandTable.createBrandTable(demo.getConnection());
			BrandTable.populateBrandTableFromCSV(demo.getConnection(),"./csv files/brand.csv");
			//Just displays the table
			BrandTable.printBrandTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM brand");
			ResultSet results2 = BrandTable.queryBrandTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Dealer Table Population
			 */
			DealerTable.createDealerTable(demo.getConnection());
			DealerTable.populateDealerTableFromCSV(demo.getConnection(),"./csv files/dealer.csv");
			//Just displays the table
			DealerTable.printDealerTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM dealer");
			ResultSet results3 = DealerTable.queryDealerTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Model Table Population
			 */
			ModelTable.createModelTable(demo.getConnection());
			ModelTable.populateModelTableFromCSV(demo.getConnection(),"./csv files/model.csv");
			//Just displays the table
			ModelTable.printModelTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM model");
			ResultSet results4 = ModelTable.queryModelTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Customer Table Population
			 */
			CustomerTable.createCustomerTable(demo.getConnection());
			CustomerTable.populateCustomerTableFromCSV(demo.getConnection(),"./csv files/customer.csv");
			//Just displays the table
			CustomerTable.printCustomerTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM customer");
			ResultSet results5 = CustomerTable.queryCustomerTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Stock Table Population
			 */
			StockTable.createStockTable(demo.getConnection());
			StockTable.populateStockTableFromCSV(demo.getConnection(),"./csv files/stock.csv");
			//Just displays the table
			StockTable.printStockTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM stock");
			ResultSet results6 = StockTable.queryStockTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Options Table Population
			 */
			OptionsTable.createOptionsTable(demo.getConnection());
			OptionsTable.populateOptionsTableFromCSV(demo.getConnection(),"./csv files/options.csv");
			//Just displays the table
			OptionsTable.printOptionsTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM options");
			ResultSet results7 = OptionsTable.queryOptionsTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Sale Table Population
			 */
			SaleTable.createSaleTable(demo.getConnection());
			SaleTable.populateSaleTableFromCSV(demo.getConnection(),"./csv files/sale.csv");
			//Just displays the table
			SaleTable.printSaleTable(demo.getConnection());
			//Runs a basic query on the table
			System.out.println("\n\nPrint results of SELECT * FROM sale");
			ResultSet results8 = SaleTable.querySaleTable(demo.getConnection(), new ArrayList<String>(), new ArrayList<String>());

			/**
			 * Iterates the Result set
			 * 
			 * Result Set is what a query in H2 returns
			 * 
			 * Note the columns are not 0 indexed
			 * If you give no columns it will return them in the
			 * order you created them. To guarantee order list the columns
			 * you want
			 */
			while(results1.next()){
				System.out.printf("\tVehicle %d: %s %s %f\n",
						          results1.getInt(1),
						          results1.getString(2),
						          results1.getString(3),
						          results1.getFloat(4));
			}

			/**
			 * A more complex query with columns selected and 
			 * addition conditions
			 */
			System.out.println("\n\nPrint results of SELECT "
					+ "vin, model "
					+ "FROM vehicle "
					+ "WHERE model = \'Aventador\' "
					+ "AND options_id = 2");
			
			/**
			 * This is one way to do this, but not the only
			 * 
			 * Create lists to make the whole thing more generic or
			 * you can just construct the whole query here 
			 */
			ArrayList<String> columns = new ArrayList<String>();
			columns.add("vin");
			columns.add("model");
			columns.add("options_id");
			
			/**
			 * Conditionals
			 */
			ArrayList<String> whereClauses = new ArrayList<String>();
			whereClauses.add("model = \'Aventador\'");
			whereClauses.add("options_id = 2");
			
			/**
			 * query and get the result set
			 * 
			 * parse the result set and print it
			 * 
			 * Notice not all of the columns are here because
			 * we limited what to show in the query
			 */
			ResultSet results9 = VehicleTable.queryVehicleTable(demo.getConnection(), columns, whereClauses);
			while(results9.next()){
			System.out.printf("\tVehicle %d: %s %d\n",
				          results9.getInt(1),
				          results9.getString(2),
				          results9.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
