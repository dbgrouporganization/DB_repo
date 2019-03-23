import static java.lang.Float.parseFloat;

/**
 * Hold data about a Vehicle
 *
 * @author jlb
 */
public class Vehicle {

	int VIN;
	String Model;
	String Options_ID;
	float Price;

	public Vehicle(int VIN, String Model, String Options_ID, float Price) {
		this.VIN = VIN;
		this.Model = Model;
		this.Options_ID = Options_ID;
		this.Price = Price;
	}

	public Vehicle(String[] data){
		this.VIN = Integer.parseInt(data[0]);
		this.Model = data[1];
		this.Options_ID = data[2];
		this.Price = parseFloat(data[3]);
	}

	public int getVIN() {
		return VIN;
	}
	
	public String getModel() {
		return Model;
	}
	
	public String getOptions_ID() {
		return Options_ID;
	}
	
	public float getPrice() {
		return Price;
	}
}
