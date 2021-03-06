package Appl;

import java.sql.*;
import java.util.ArrayList;

/**
 * This main program is used to populate the database from the provided csv files.
 * 
 * @author omg
 */
public class DB_Init {

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
	 * Getter for db connectiond
	 * @return: returns class level connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createView(){
		try {
			String vlookup = "create view VehicleLookup as select Vin, Model.Brand, Vehicle.Model, Vehicle.Year , Name as Dealer, " +
					"Owner.ADDR_CITY as CITY, Owner.ADDR_STATE as State, Owner.ADDR_ZIP as Zip, vehicle.OPTIONS_ID, COLOR , ENGINE , " +
					"TRANSMISSION , NAVIGATION , BLUETOOTH , HEATED_SEATS , ROOF_RACK , Price from "+
					"((((Vehicle inner join Options on Vehicle.OPTIONS_ID = Options.Options_ID) inner join Model on "+
					"(Vehicle.Model = Model.Model and Vehicle.year = Model.Year)) inner join Owner on "+
					"(Vehicle.OWNER_ID = Owner.OWNER_ID)) inner join Dealer on (Owner.Owner_ID  = Dealer.Owner_ID));";
			String market = "create view Marketing as select * from Owner natural join Customer inner join Sale on Owner.Owner_ID = Sale.Buyer_ID;";
			// String customer = "create view DealerLookup as select name, addr_num, addr_street, addr_city, addr_state, addr_zip from owner natural join dealer;";
			/**
			 * Create a query and execute
			 */
			Statement stmt = conn.createStatement();
			stmt.execute(vlookup + " " + market);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds all users to the DB
	 */
	public void addAccess(){
		try{
			String access = "Create User AdminTest Password 'Admin';" +
			"Create User VehicleLookupTest Password 'VehicleLookup';"+
			"Create User vlt Password 'vlpass';"+
			"Create User CustomerTest Password 'Customer';"+
            "Create User MarketingTest Password 'Marketing';"+
			"Create Role Admin;" +
			"Create Role VehicleLookup;"+
			"Create Role Customer;"+
			"Create Role Marketing;"+
			"GRANT ALL ON CUSTOMER, DEALER, MODEL, OPTIONS, OWNER, SALE, VEHICLE, VEHICLELOOKUP TO Admin;" +
			"GRANT SELECT ON VehicleLookup TO VehicleLookup;"+
			"GRANT SELECT ON Dealer, Owner, VehicleLookup to Customer;"+
			"GRANT SELECT on Marketing, CUSTOMER, DEALER, MODEL, OPTIONS, OWNER, SALE, VEHICLE, VEHICLELOOKUP To Marketing;" +
			"Grant Admin to AdminTest;" +
			"Grant Marketing to MarketingTest;" +
			"Grant Customer to CustomerTest;" +
			"Grant VehicleLookup to VehicleLookupTest;" +
			"Grant VehicleLookup to vlt;";
			Statement stmt = conn.createStatement();
			stmt.execute(access);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Starts and runs the database
	 * @param args: not used
	 */
	public static void main(String[] args) {

		DB_Init demo = new DB_Init();
		
		// Hard drive location of the database
		String location = "./DB/Automobile";
		String user = "user";
		String password = "1234";
		
		// Create the database connections
		demo.createConnection(location, user, password);
		
		try {
			
			/**
			 * Vehicle Table Population
			 */
			VehicleTable.createVehicleTable(demo.getConnection());
			VehicleTable.populateVehicleTableFromCSV(demo.getConnection(),"./csv/vehicle.csv");
			//Just displays the table
			System.out.println("Print results of SELECT * FROM vehicle");
			VehicleTable.printVehicleTable(demo.getConnection());

			/**
			 * Dealer Table Population
			 */
			DealerTable.createDealerTable(demo.getConnection());
			DealerTable.populateDealerTableFromCSV(demo.getConnection(),"./csv/dealer.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM dealer");
			DealerTable.printDealerTable(demo.getConnection());

			/**
			 * Model Table Population
			 */
			ModelTable.createModelTable(demo.getConnection());
			ModelTable.populateModelTableFromCSV(demo.getConnection(),"./csv/model.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM model");
			ModelTable.printModelTable(demo.getConnection());

			/**
			 * Customer Table Population
			 */
			CustomerTable.createCustomerTable(demo.getConnection());
			CustomerTable.populateCustomerTableFromCSV(demo.getConnection(),"./csv/customer.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM customer");
			CustomerTable.printCustomerTable(demo.getConnection());

			/**
			 * Options Table Population
			 */
			OptionsTable.createOptionsTable(demo.getConnection());
			OptionsTable.populateOptionsTableFromCSV(demo.getConnection(),"./csv/options.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM options");
			OptionsTable.printOptionsTable(demo.getConnection());

			/**
			 * Sale Table Population
			 */
			SaleTable.createSaleTable(demo.getConnection());
			SaleTable.populateSaleTableFromCSV(demo.getConnection(),"./csv/sale.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM sale");
			SaleTable.printSaleTable(demo.getConnection());

			/**
			 * Owner Table Population
			 */
			OwnerTable.createOwnerTable(demo.getConnection());
			OwnerTable.populateOwnerTableFromCSV(demo.getConnection(),"./csv/owner.csv");
			//Just displays the table
			System.out.println("\n\nPrint results of SELECT * FROM owner");
			OwnerTable.printOwnerTable(demo.getConnection());

			/*
			// Iterates the Result set
			// Result Set is what a query in H2 returns
			while(results1.next()){
				System.out.printf("\tVehicle %d: %s %s %f %d\n",
						          results1.getInt(1),
						          results1.getString(2),
						          results1.getString(3),
						          results1.getFloat(4),
								  results1.getInt(5));
			}
			*/

			/**
			 * Create the Vehicle lookup View
			 */
			demo.createView();

			demo.addAccess();

			/**
			 * A more complex query with columns selected and 
			 * additional conditions
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
			columns.add("year");
			
			/**
			 * Conditionals
			 */
			ArrayList<String> whereClauses = new ArrayList<String>();
			whereClauses.add("model = \'Aventador\'");
			whereClauses.add("year = 2016");
			
			/**
			 * query and get the result set
			 * 
			 * parse the result set and print it
			 * 
			 * Notice not all of the columns are here because
			 * we limited what to show in the query
			 */
			ResultSet results = VehicleTable.queryVehicleTable(demo.getConnection(), columns, "", whereClauses);
			while(results.next()){
			System.out.printf("\tVehicle %d: %s %d\n",
				          results.getInt(1),
				          results.getString(2),
				          results.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
