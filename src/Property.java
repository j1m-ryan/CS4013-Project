import java.util.ArrayList;

public class Property extends Taxable {
    private ArrayList<Owner> owners;
    private String address;
    private String eircode;
    private double estimatedMarketValue;
    private LocationCatgeory locationCatgeory;
    private boolean isPrincipalPrivateResidence;

    public Property(ArrayList<Owner> owners, String address, String eircode, double estimatedMarketValue,
            LocationCatgeory locationCatgeory, boolean isPrincipalPrivateResidence) {
        super();
        this.setOwners(owners);
        this.setAddress(address);
        this.setEircode(eircode);
        this.setEstimatedMarketValue(estimatedMarketValue);
        this.setLocationCatgeory(locationCatgeory);
        this.setPrincipalPrivateResidence(isPrincipalPrivateResidence);
    }

    public boolean isPrincipalPrivateResidence() {
        return isPrincipalPrivateResidence;
    }

    public void setPrincipalPrivateResidence(boolean isPrincipalPrivateResidence) {
        this.isPrincipalPrivateResidence = isPrincipalPrivateResidence;
    }

    public LocationCatgeory getLocationCatgeory() {
        return locationCatgeory;
    }

    public void setLocationCatgeory(LocationCatgeory locationCatgeory) {
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