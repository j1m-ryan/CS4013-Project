import java.util.ArrayList;

public class Property {
    private ArrayList<Integer> ownersPPS = new ArrayList<Integer>();
    private String address = "";
    private String eircode = "";
    private double estimatedMarketValue = 0;
    private String locationCatgeory = "";
    private boolean isPrincipalPrivateResidence = false;
    
    public Property(ArrayList<String> propDetails) {
        this.setAddress(propDetails.get(0));
        this.setEircode(propDetails.get(1));
        this.setEstimatedMarketValue(Double.parseDouble(propDetails.get(2)));
        this.setLocationCatgeory(propDetails.get(3));
        this.setPrincipalPrivateResidence(propDetails.get(4));
    }
    
    public Property(ArrayList<Integer> newOwners, ArrayList<String> propDetails) {
        this.setOwnersPps(newOwners);
        this.setAddress(propDetails.get(0));
        this.setEircode(propDetails.get(1));
        this.setEstimatedMarketValue(Double.parseDouble(propDetails.get(2)));
        this.setLocationCatgeory(propDetails.get(3));
        this.setPrincipalPrivateResidence(propDetails.get(4));
    }
    
    public boolean isPrincipalPrivateResidence() {
        return isPrincipalPrivateResidence;
    }

    public void setPrincipalPrivateResidence(String isPrincipalPrivateResidence) {
        if (isPrincipalPrivateResidence == "yes"){
            this.isPrincipalPrivateResidence = true;
        }else{
            this.isPrincipalPrivateResidence = false;
        }
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
    public double getEstimatedMarketValue() {
        return estimatedMarketValue;
    }
	
    //Setter for Estimate Market Value
    public void setEstimatedMarketValue(double estimatedMarketValue) {
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
	
    //Getter for Adress
    public String getAddress() {
        return address;
    }
	
	//Setter for address
    public void setAddress(String address) {
        this.address = address;
    }
	
    //Array list that will save Owner PPS
    public ArrayList<Integer> getOwnersPps() {
        return ownersPPS;
    }
	//Setter for Owner PPS from the Arraylist
    public void setOwnersPps(ArrayList<Integer> ids) {
        ownersPPS = ids;
    }

}
