/**
 * Hold data about a Sale
 *
 * @author team 18
 */
public class Sale {

	String date;
	int vin;
	int customer_id;

	public Sale(String date, int vin, int customer_id) {
		this.date = date;
		this.vin = vin;
		this.customer_id = customer_id;
	}

	public Sale(String[] data) {
        this.date = data[0];
		this.vin = Integer.parseInt(data[1]);
        this.customer_id = Integer.parseInt(data[2]);
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
}
