/**
 * Hold data about a Dealer
 *
 * @author jlb
 */
public class Dealer extends Owner{

	String name;

	public Dealer(int id, String name, String addr_street, int addr_num,
				  String addr_city, String addr_state, int addr_zip) {
		this.id = id;
		this.name = name;
		this.addr_street = addr_street;
		this.addr_num = addr_num;
		this.addr_city = addr_city;
		this.addr_state = addr_state;
		this.addr_zip = addr_zip;
	}

	public Dealer(String[] data) {
		this.id = Integer.parseInt(data[0]);
		this.name = data[1];
		this.addr_street = data[2];
		this.addr_num = Integer.parseInt(data[3]);
		this.addr_city = data[4];
		this.addr_state = data[5];
		this.addr_zip = Integer.parseInt(data[6]);
	}

	public String getName() {
		return name;
	}
}
