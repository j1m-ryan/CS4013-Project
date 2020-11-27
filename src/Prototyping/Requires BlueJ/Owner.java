import java.util.ArrayList;
public class Owner {
    private String name;
    private ArrayList<Integer> propertiesOwned = new ArrayList<Integer>();

    public Owner(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProperty(int propertyId){
        propertiesOwned.add(propertyId);
    }

    public ArrayList<Integer> getPropertiesId(){
        return propertiesOwned;
    }
}
