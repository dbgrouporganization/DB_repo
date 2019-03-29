package Appl;

/**
 * Hold data about a Brand
 *
 * @author team 18
 */
public class Brand {

	String name;

	public Brand(String name) {
		this.name = name;
	}

	public Brand(String[] data) {
		this.name = data[0];
	}

	public String getName() {
		return name;
	}
}
