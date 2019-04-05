package Appl;

/**
 * Hold data about a Model
 *
 * @author omg
 */
public class Model {

	private int Year;
	private String Model;
	private String Brand;
	private String BodyStyle;

	public Model(int Year, String Model, String Brand, String BodyStyle) {
		this.Year = Year;
		this.Model = Model;
		this.Brand = Brand;
		this.BodyStyle = BodyStyle;
	}

	public Model(String[] data){
		this.Year = Integer.parseInt(data[0]);
		this.Model = data[1];
		this.Brand = data[2];
		this.BodyStyle = data[3];
	}

	public int getYear() {
		return Year;
	}

	public String getModel() {
		return Model;
	}

	public String getBrand() {
		return Brand;
	}

	public String getBodyStyle() {
		return BodyStyle;
	}
}
