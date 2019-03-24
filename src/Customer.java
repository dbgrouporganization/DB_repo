/**
 * Hold data about a person
 * @author scj
 *
 */
public class Customer {

	private String fName;
	private String lName;
	private int ID;
	private int addr_num;
	private String addr_street;
	private String addr_city;
	private String addr_state;
	private int addr_zip;
	private String phone;
	private String Gender;
	private float income;

	public Customer(String fName, String lName, int ID, int addr_num, String addr_street, String addr_city, String addr_state, int addr_zip, String phone, String gender, float income) {
		this.fName = fName;
		this.lName = lName;
		this.ID = ID;
		this.addr_num = addr_num;
		this.addr_street = addr_street;
		this.addr_city = addr_city;
		this.addr_state = addr_state;
		this.addr_zip = addr_zip;
		this.phone = phone;
		Gender = gender;
		this.income = income;
	}

	public Customer (String[] data){
		this.fName = data[0];
		this.lName = data[1];
		this.ID = Integer.parseInt(data[2]);
		this.addr_num = Integer.parseInt(data[3]);
		this.addr_street = data[4];
		this.addr_city = data[5];
		this.addr_state = data[6];
		this.addr_zip = Integer.parseInt(data[7]);
		this.phone = data[8];
		this.Gender = data[9];
		this.income = Float.parseFloat(data[10]);
	}

	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

	public int getID() {
		return ID;
	}

	public int getAddr_num() {
		return addr_num;
	}

	public String getAddr_street() {
		return addr_street;
	}

	public String getAddr_city() {
		return addr_city;
	}

	public String getAddr_state() {
		return addr_state;
	}

	public int getAddr_zip() {
		return addr_zip;
	}

	public String getPhone() {
		return phone;
	}

	public String getGender() {
		return Gender;
	}

	public float getIncome() {
		return income;
	}
}
