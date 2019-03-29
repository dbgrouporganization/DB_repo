package Appl;

import static java.lang.Float.parseFloat;

/**
 * Hold data about a Appl.Model
 *
 * @author omg
 */
public class Model {

	private int MYear;
	private String Model;
	private String Brand;
	private String BodyStyle;

	public Model(int MYear, String Model, String Brand, String BodyStyle) {
		this.MYear = MYear;
		this.Model = Model;
		this.Brand = Brand;
		this.BodyStyle = BodyStyle;
	}

	public Model(String[] data){
		this.MYear = Integer.parseInt(data[0]);
		this.Model = data[1];
		this.Brand = data[2];
		this.BodyStyle = data[3];
	}

	public int getMYear() {
		return MYear;
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
