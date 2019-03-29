package Appl;

/**
 * Hold data about a Vehicle
 *
 * @author team 18
 */
public class Vehicle {

	int vin;
	String model;
	int year;
	String options_id;
	float price;
	int owner_id;

	public Vehicle(int vin, String model, int year, String options_id, float price, int owner_id) {
		this.vin = vin;
		this.model = model;
		this.year = year;
		this.options_id = options_id;
		this.price = price;
		this.owner_id = owner_id;
	}

	public Vehicle(String[] data){
		this.vin = Integer.parseInt(data[0]);
		this.model = data[1];
		this.year = Integer.parseInt(data[2]);
		this.options_id = data[3];
		this.price = Float.parseFloat(data[4]);
		this.owner_id = Integer.parseInt(data[5]);
	}

	public int getVIN() {
		return vin;
	}
	
	public String getModel() {
		return model;
	}

	public int getYear() { return year; }
	
	public String getOptions_ID() {
		return options_id;
	}
	
	public float getPrice() {
		return price;
	}

	public int getOwner_id() {
		return owner_id;
	}
}
