import java.util.ArrayList;
public class Owner {

    private String name = "";
    private int pps = 0;
    private String password = "";
    private ArrayList<Integer> propertiesOwned = new ArrayList<Integer>();

    public Owner(String name, int pps, String password) {
    
        setName(name);
        setPps(pps);
        setPassword(password);
    }
	//method to set name 
    public void setName(String name) {
        this.name = name;
    }

	//method to set pps number
    public void setPps(int pps_num) {
        pps = pps_num;
    }

	//method to set password (no return type)
    public void setPassword(String password) {
        this.password = password;
    }

	//method to return name
    public String getName() {
        return name;
    }
    
    //method to show PPS number
    public int getPps() {
        return pps;
    }
    
    //Show password
    public String getPassword() {
        return password;
    }

    //Add Property
    public void addProperty(int propertyId){
        propertiesOwned.add(propertyId);
    }

    //method to Remove Property from the list 
    public void removeProperty(int propertyId){
        propertiesOwned.remove(propertyId);
    }

    //Get properties ID
    public ArrayList<Integer> getPropertiesId(){
        return propertiesOwned;
    }
}
