/**
 * Hold data about a Dealer
 *
 * @author team 18
 */
public class Dealer{

	int owner_id;
	String name;

	public Dealer(int owner_id, String name) {
		this.owner_id = owner_id;
		this.name = name;
	}

	public Dealer(String[] data) {
		this.owner_id = Integer.parseInt(data[0]);
		this.name = data[1];
	}

	public int getOwner_id() {
		return owner_id;
	}

	public String getName() {
		return name;
	}
}
