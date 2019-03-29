package Appl;

/**
 * Hold data about Options
 *
 * @author jlb
 */
public class Options {

	int options_id;
	String color;
	String engine;
	String transmission;
	Boolean navigation;
	Boolean bluetooth;
	Boolean heated_seats;
	Boolean roof_rack;

    public Options(int options_id, String color, String engine, String transmission,
                   Boolean navigation, Boolean bluetooth, Boolean heated_seats, Boolean roof_rack) {
        this.options_id = options_id;
        this.color = color;
        this.engine = engine;
        this.transmission = transmission;
        this.navigation = navigation;
        this.bluetooth = bluetooth;
        this.heated_seats = heated_seats;
        this.roof_rack = roof_rack;
    }

    public Options(String[] data) {
		this.options_id = Integer.parseInt(data[0]);
		this.color = data[1];
		this.engine = data[2];
		this.transmission = data[3];
        this.navigation = Boolean.parseBoolean(data[4]);
        this.bluetooth = Boolean.parseBoolean(data[5]);
        this.heated_seats = Boolean.parseBoolean(data[6]);
        this.roof_rack = Boolean.parseBoolean(data[7]);
	}

    public int getOptions_id() {
        return options_id;
    }

    public String getColor() {
        return color;
    }

    public String getEngine() {
        return engine;
    }

    public String getTransmission() {
        return transmission;
    }

    public Boolean getNavigation() {
        return navigation;
    }

    public Boolean getBluetooth() {
        return bluetooth;
    }

    public Boolean getHeated_seats() {
        return heated_seats;
    }

    public Boolean getRoof_rack() {
        return roof_rack;
    }
}
