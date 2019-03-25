/**
 * Hold data about a Customer
 * @author omg
 *
 */
public class Customer extends Owner{

	private String fName;
	private String lName;
	private String phone;
	private String gender;
	private float income;

	public Customer(String fName, String lName, int id, int addr_num, String addr_street, String addr_city,
					String addr_state, int addr_zip, String phone, String gender, float income) {
		this.fName = fName;
		this.lName = lName;
		this.id = id;
		this.addr_num = addr_num;
		this.addr_street = addr_street;
		this.addr_city = addr_city;
		this.addr_state = addr_state;
		this.addr_zip = addr_zip;
		this.phone = phone;
		this.gender = gender;
		this.income = income;
	}

	public Customer (String[] data){
		this.fName = data[0];
		this.lName = data[1];
		this.id = Integer.parseInt(data[2]);
		this.addr_num = Integer.parseInt(data[3]);
		this.addr_street = data[4];
		this.addr_city = data[5];
		this.addr_state = data[6];
		this.addr_zip = Integer.parseInt(data[7]);
		this.phone = data[8];
		this.gender = data[9];
		this.income = Float.parseFloat(data[10]);
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public String getPhone() {
		return phone;
	}

	public String getGender() {
		return gender;
	}

	public float getIncome() {
		return income;
	}
}
