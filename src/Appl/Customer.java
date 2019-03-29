package Appl;

/**
 * Hold data about a Customer
 *
 * @author team 18
 */
public class Customer{

	private int owner_id;
	private String fName;
	private String lName;
	private String phone;
	private String gender;
	private float income;

	public Customer(int owner_id, String fName, String lName, String phone, String gender, float income) {
		this.owner_id = owner_id;
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
		this.gender = gender;
		this.income = income;
	}

	public Customer(String[] data) {
		this.owner_id = Integer.parseInt(data[0]);
		this.fName = data[1];
		this.lName = data[2];
		this.phone = data[3];
		this.gender = data[4];
		this.income = Float.parseFloat(data[5]);
	}

	public int getOwner_id() {
		return owner_id;
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
