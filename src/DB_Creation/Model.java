import static java.lang.Float.parseFloat;

/**
 * Hold data about a Vehicle
 *
 * @author jlb
 */
public class Model {

	private int Year;
	private String Name;
	private String Brand;
	private String BodyStyle;

	public Model(int Year, String Name, String Brand, String BodyStyle) {
		this.Year = Year;
		this.Name = Name;
		this.Brand = Brand;
		this.BodyStyle = BodyStyle;
	}

	public Model(String[] data){
		this.Year = Integer.parseInt(data[0]);
		this.Name = data[1];
		this.Brand = data[2];
		this.BodyStyle = data[3];
	}

	public int getYear() {
		return Year;
	}

	public String getName() {
		return Name;
	}

	public String getBrand() {
		return Brand;
	}

	public String getBodyStyle() {
		return BodyStyle;
	}
}
