/**
 * Hold data about a Sale
 *
 * @author omg
 */
public class Sale {

	String date;
	int vin;
	int customer_id;
	int dealer_id;

	public Sale(String date, int vin, int customer_id, int dealer_id) {
		this.date = date;
		this.vin = vin;
		this.customer_id = customer_id;
		this.dealer_id = dealer_id;
	}

	public Sale(String[] data) {
        this.date = data[0];
		this.vin = Integer.parseInt(data[1]);
        this.customer_id = Integer.parseInt(data[2]);
        this.dealer_id = Integer.parseInt(data[3]);
	}

    public String getDate() {
        return date;
    }

    public int getVin() {
        return vin;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getDealer_id(){ return dealer_id; }
}
