/**
 * Hold data about a Vehicle
 *
 * @author jlb
 */
public class Vehicle {

	int vin;
	String model;
	int year;
	String options_id;
	float price;

	public Vehicle(int vin, int year, String model, String options_id, float price) {
		this.vin = vin;
		this.year = year;
		this.model = model;
		this.options_id = options_id;
		this.price = price;
	}

	public Vehicle(String[] data){
		this.vin = Integer.parseInt(data[0]);
		this.model = data[1];
		this.year = Integer.parseInt(data[2]);
		this.options_id = data[3];
		this.price = Float.parseFloat(data[4]);
	}

	public int getVIN() {
		return vin;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getOptions_ID() {
		return options_id;
	}
	
	public float getPrice() {
		return price;
	}

	public int getYear() { return year; }
}
