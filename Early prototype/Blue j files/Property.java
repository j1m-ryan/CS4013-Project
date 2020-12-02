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

    public String getLocationCatgeory() {
        return locationCatgeory;
    }

    public void setLocationCatgeory(String locationCatgeory) {
        this.locationCatgeory = locationCatgeory;
    }

    public double getEstimatedMarketValue() {
        return estimatedMarketValue;
    }

    public void setEstimatedMarketValue(double estimatedMarketValue) {
        this.estimatedMarketValue = estimatedMarketValue;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Integer> getOwnersPps() {
        return ownersPPS;
    }

    public void setOwnersPps(ArrayList<Integer> ids) {
        ownersPPS = ids;
    }

}