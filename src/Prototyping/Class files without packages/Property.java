import java.util.ArrayList;

public class Property {
    private ArrayList<Owner> owners;
    private String address;
    private String eircode;
    private double estimatedMarketValue;
    private String locationCatgeory = "";
    private boolean isPrincipalPrivateResidence;

    // made changes just for ease. i will go with one owner at start
    private double dueTax = 0;
    public Property(ArrayList<Owner> newOwners, String[] propDetails) {     
        //super();
        this.setOwners(newOwners);
        this.setAddress(propDetails[0]);
        this.setEircode(propDetails[1]);
        this.setEstimatedMarketValue(Double.parseDouble(propDetails[2]));
        this.setLocationCatgeory(propDetails[3]);
        this.setPrincipalPrivateResidence(propDetails[4]);
    }
        
    public void setDueTax(double calculatedTax){
        this.dueTax = calculatedTax;
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

    public ArrayList<Owner> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<Owner> owners) {
        this.owners = owners;
    }

}