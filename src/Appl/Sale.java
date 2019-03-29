package Appl;

/**
 * Hold data about a Sale
 *
 * @author team 18
 */
public class Sale {

	String date;
	int vin;
	int buyer_id;
	int seller_id;

	public Sale(String date, int vin, int buyer_id, int seller_id) {
		this.date = date;
		this.vin = vin;
		this.buyer_id = buyer_id;
		this.seller_id = seller_id;
	}

	public Sale(String[] data) {
        this.date = data[0];
		this.vin = Integer.parseInt(data[1]);
        this.buyer_id = Integer.parseInt(data[2]);
        this.seller_id = Integer.parseInt(data[3]);
	}

    public String getDate() {
        return date;
    }

    public int getVin() {
        return vin;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public int getSeller_id() {
	    return seller_id;
    }
}
