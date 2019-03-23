/**
 * Hold data about a stock entry
 * @author scj
 *
 */
public class Stock {

	int VIN;
	String OwnerId;

	public Stock(int VIN,
                 String OwnerId){
		this.VIN = VIN;
		this.OwnerId = OwnerId;
	}

	public Stock(String[] data){
		this.VIN = Integer.parseInt(data[0]);
		this.OwnerId = data[1];
	}

	public int getVIN() {
		return VIN;
	}
	
	public String getOwnerId() {
		return OwnerId;
	}

}
