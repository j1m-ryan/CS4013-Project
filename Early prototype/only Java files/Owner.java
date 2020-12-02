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

    public void setName(String name) {
        this.name = name;
    }

    public void setPps(int pps_num) {
        pps = pps_num;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    
    public int getPps() {
        return pps;
    }
    
    public String getPassword() {
        return password;
    }

    public void addProperty(int propertyId){
        propertiesOwned.add(propertyId);
    }

    public void removeProperty(int propertyId){
        propertiesOwned.remove(propertyId);
    }

    public ArrayList<Integer> getPropertiesId(){
        return propertiesOwned;
    }
}
