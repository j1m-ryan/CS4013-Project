import java.util.ArrayList;

public class Property {
    private ArrayList<String> ownersPPS = new ArrayList<String>();
    private String address = "";
    private String eircode = "";
    private String eircodeRoutingKey = "";
    private String estimatedMarketValue = "";
    private String locationCatgeory = "";
    private boolean isPrincipalPrivateResidence = false;
    
    public Property(String address, String eircode, String estimatedMarketValue, String locationCategory,
    boolean isPrincipalPrivateResidence) {
        this.setAddress(address);
        this.setEircode(eircode);
        this.eircodeRoutingKey = eircode.substring(0, 3); 
        this.setEstimatedMarketValue(estimatedMarketValue);
        this.setLocationCatgeory(locationCategory);
        this.setPrincipalPrivateResidence(isPrincipalPrivateResidence);
    }
    
    public boolean isPrincipalPrivateResidence() {
        return isPrincipalPrivateResidence;
    }

    public void setPrincipalPrivateResidence(Boolean isPrincipalPrivateResidence) {
        this.isPrincipalPrivateResidence = isPrincipalPrivateResidence;
    }
	
    //Getter for Location Category
    public String getLocationCatgeory() {
        return locationCatgeory;
    }
    //Setter for Location
    public void setLocationCatgeory(String locationCatgeory) {
        this.locationCatgeory = locationCatgeory;
    }
	
    //Getter method for Estimate market value
    public String getEstimatedMarketValue() {
        return estimatedMarketValue;
    }
	
    //Setter for Estimate Market Value
    public void setEstimatedMarketValue(String estimatedMarketValue) {
        this.estimatedMarketValue = estimatedMarketValue;
    }
	
   //getter for Eircode
    public String getEircode() {
        return eircode;
    }
	
    //Setter for Eircode
    public void setEircode(String eircode) {
        this.eircode = eircode;
    }
    
    //Getter for Eircode first three characters
    public String getEircodeRoutingKey() {
        return eircodeRoutingKey;
    }

    //Getter for Adress
    public String getAddress() {
        return address;
    }
	
	//Setter for address
    public void setAddress(String address) {
        this.address = address;
    }
	
    //Array list that will save Owner PPS
    public ArrayList<String> getOwnersPps() {
        return ownersPPS;
    }
    
    //method to add single Owner PPS number a time 
    public void addOwnersPps(String ppsNum) {
        ownersPPS.add(ppsNum);
    }
    
    //method to add Owners PPS numbers from the Arraylist
    public void addOwnersPps(ArrayList<String> ppsNums) {
        ownersPPS = ppsNums;
    }

}
