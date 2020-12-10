import java.util.ArrayList;
public class Owner {

    private String name = "";
    private String ppsNum = "";
    private String password = "";
    private ArrayList<String> propertiesOwned = new ArrayList<String>();

    public Owner(String name, String ppsNum, String password) {
    
        setName(name);
        setPpsNum(ppsNum);
        setPassword(password);
    }
	//method to set name 
    public void setName(String name) {
        this.name = name;
    }

	//method to set pps number
    public void setPpsNum(String ppsNum) {
        this.ppsNum = ppsNum;
    }

	//method to set password (no return type)
    public void setPassword(String password) {
        this.password = password;
    }

	//method to return name
    public String getName() {
        return name;
    }
    
    //method to show ppsNum number
    public String getPpsNum() {
        return ppsNum;
    }
    
    //Show password
    public String getPassword() {
        return password;
    }

    //Add Property
    public void addProperty(String eircode){
        propertiesOwned.add(eircode);
    }

    //method to Remove Property from the list 
    public void removeProperty(int propertyId){
        propertiesOwned.remove(propertyId);
    }

    //Get properties ID
    public ArrayList<String> getPropertiesEircodes(){
        return propertiesOwned;
    }
}
