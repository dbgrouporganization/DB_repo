package Appl;

/**
 * Hold data about an Owner
 * @author team 18
 */
public class Owner {

    int owner_id;
    int addr_num;
    String addr_street;
    String addr_city;
    String addr_state;
    int addr_zip;

    public Owner(int owner_id, int addr_num, String addr_street, String addr_city, String addr_state, int addr_zip) {
        this.owner_id = owner_id;
        this.addr_num = addr_num;
        this.addr_street = addr_street;
        this.addr_city = addr_city;
        this.addr_state = addr_state;
        this.addr_zip = addr_zip;
    }

    public Owner(String[] data) {
        this.owner_id = Integer.parseInt(data[0]);
        this.addr_num = Integer.parseInt(data[1]);
        this.addr_street = data[2];
        this.addr_city = data[3];
        this.addr_state = data[4];
        this.addr_zip = Integer.parseInt(data[5]);
    }

    public int getOwner_id() {
        return owner_id;
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
}
