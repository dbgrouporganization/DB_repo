/**
 * Hold data about a stock entry
 *
 * @author jlb
 */
public class Stock {

	int vin;
	int dealer_id;
	String brand_owner;
	int customer_id;

	public Stock(int vin, int dealer_id, String brand_owner, int customer_id) {
		this.vin = vin;
		this.dealer_id = dealer_id;
		this.brand_owner = brand_owner;
		this.customer_id = customer_id;
	}

	public Stock(String[] data){
		this.vin = Integer.parseInt(data[0]);
		this.dealer_id = Integer.parseInt(data[1]);
		this.brand_owner = data[2];
		this.customer_id = Integer.parseInt(data[3]);
	}

	public int getVin() {
		return vin;
	}

	public int getDealer_id() {
		return dealer_id;
	}

	public String getBrand_owner() {
		return brand_owner;
	}

	public int getCustomer_id() {
		return customer_id;
	}
}
