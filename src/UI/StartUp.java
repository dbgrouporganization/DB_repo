package UI;

public class StartUp {


    public static void main(String args[]){
        Login login = new Login();
        //String access = login.getAccess();
        VehicleLookup test = new VehicleLookup(null);
        test.console();
    }

}
