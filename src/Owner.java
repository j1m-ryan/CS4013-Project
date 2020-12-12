import java.util.ArrayList;


/**
 * A Class Representing Owner's Object
 * @author User-1
 * @version 1.0.0
 */
public class Owner {

    private String name = "";
    private String ppsNum = "";
    private String password = "";
    private ArrayList<String> propertiesOwned = new ArrayList<String>();

    /**
     * @param name Name of owner
     * @param ppsNum Personal Public Service Number (ppsNum) of owner
     * @param password Password of owner
     */
    public Owner(String name, String ppsNum, String password) {
        setName(name);
        setPpsNum(ppsNum);
        setPassword(password);
    }


    /**
     * Method to set name of owner
     * @param name name of the owner
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to set pps number 
     * @param ppsNum Personal Public Service Number of owner
     */
    public void setPpsNum(String ppsNum) {
        this.ppsNum = ppsNum;
    }


    /**
     * Method to set password (no return type)
     * @param password Password of the wner
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Method to return name
     * @return value of name
     */
    public String getName() {
        return name;
    }


    /**
     * Personal Public Service Number
     * @return return Personal Public Service Number of the owner
     */
    public String getPpsNum() {
        return ppsNum;
    }


    /**
     * Show password
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Add Property to Owner's property list
     * @param eircode eircode of Property
     */
    public void addProperty(String eircode){
        propertiesOwned.add(eircode);
    }

    /**
     * Method to Remove Property from the list using id
     * @param propertyId id of Property
     */
    public void removeProperty(int propertyId){
        propertiesOwned.remove(propertyId);
    }


    /**
     * Get properties list
     * @return List of properties Owned
     */
    public ArrayList<String> getPropertiesEircodes(){
        return propertiesOwned;
    }
}