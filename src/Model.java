import static java.lang.Float.parseFloat;

/**
 * Hold data about a Vehicle
 *
 * @author jlb
 */
public class Model {

	private int MYear;
	private String MName;
	private String Brand;
	private String BodyStyle;

	public Model(int MYear, String MName, String Brand, String BodyStyle) {
		this.MYear = MYear;
		this.MName = MName;
		this.Brand = Brand;
		this.BodyStyle = BodyStyle;
	}

	public Model(String[] data){
		this.MYear = Integer.parseInt(data[0]);
		this.MName = data[1];
		this.Brand = data[2];
		this.BodyStyle = data[3];
	}

	public int getMYear() {
		return MYear;
	}

	public String getMName() {
		return MName;
	}

	public String getBrand() {
		return Brand;
	}

	public String getBodyStyle() {
		return BodyStyle;
	}
}
