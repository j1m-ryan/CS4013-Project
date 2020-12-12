import java.util.ArrayList;
/**
 * A Class that Represent a Property
 * @author User-1
 * @version 1.0.0
 */

public class Property {
    private ArrayList<String> ownersPPS = new ArrayList<String>();
    private String address = "";
    private String eircode = "";
    private String eircodeRoutingKey = "";
    private String estimatedMarketValue = "";
    private String locationCatgeory = "";
    private boolean isPrincipalPrivateResidence = false;
	
	/**
     * Initializes the Property Object
     * @param address Address of the property Object
     * @param eircode Eircode of the location
     * @param estimatedMarketValue Estimated market value of property
     * @param locationCategory location Category of property
     * @param isPrincipalPrivateResidence defines if property is Principal private residence (PPR)
     */
    
    public Property(String address, String eircode, String estimatedMarketValue, String locationCategory,
    boolean isPrincipalPrivateResidence) {
        this.setAddress(address);
        this.setEircode(eircode);
        this.eircodeRoutingKey = eircode.substring(0, 3); 
        this.setEstimatedMarketValue(estimatedMarketValue);
        this.setLocationCatgeory(locationCategory);
        this.setPrincipalPrivateResidence(isPrincipalPrivateResidence);
    }
	
    /**Getter for isPrincipalPrivateResidence
     * @return getter for isPrincipalPrivateResidence
     */
    public boolean isPrincipalPrivateResidence() {
        return isPrincipalPrivateResidence;
    }
	/**
     * Setter for isPrincipalPrivateResidence
     * @param isPrincipalPrivateResidence setter for isPrincipalPrivateResidence
     */

    public void setPrincipalPrivateResidence(Boolean isPrincipalPrivateResidence) {
        this.isPrincipalPrivateResidence = isPrincipalPrivateResidence;
    }
	/**
     * Getter for Location Category
     * @return value of locationCategory
     */
    
    public String getLocationCatgeory() {
        return locationCatgeory;
    }
    /**
    * Setter for Location
    * @param locationCatgeory set value of locationCategory
     */
    public void setLocationCatgeory(String locationCatgeory) {
        this.locationCatgeory = locationCatgeory;
    }
	
    /**
    * Getter method for Estimate market value
    * @return value of estimatedMarketValue
     */
    public String getEstimatedMarketValue() {
        return estimatedMarketValue;
    }
	
    /**
    * Setter for Estimate Market Value
    * param estimateMarketValue set estimated market value
    */
    
    public void setEstimatedMarketValue(String estimatedMarketValue) {
        this.estimatedMarketValue = estimatedMarketValue;
    }
	
   /**
   * getter for Eircode
   * @return value of eircode
   */
    public String getEircode() {
        return eircode;
    }
	
    /** 
    * Setter for Eircode
    * @param eircode set value of eircode
    */
    public void setEircode(String eircode) {
        this.eircode = eircode;
    }
	
     /**
     * Getter of eircodeFirstThreeChars
     * @return value of eircodeFirstThreeChars
     */
    
    public String getEircodeRoutingKey() {
        return eircodeRoutingKey;
    }

    /**
	* Getter for Adress
	* @return value of address
     */
    public String getAddress() {
        return address;
    }
	
	/**
     * Setter for address
     * @param address set value of address
     */
    public void setAddress(String address) {
        this.address = address;
    }
	
    
	/**
     * Get Array list that will save Owner PPS
     * @return list of owners PPS
     */
    public ArrayList<String> getOwnersPps() {
        return ownersPPS;
    }
    
	/**
     * Setter for Owner PPS from the Arraylist
     * @param ids set owner's PPS
     */
    public void setOwnersPps(ArrayList<String> ids) {
        ownersPPS = ids;
    }
    /**
     * Method to add Owners pps number
     * @param ownersPPS Personal Public Service Number of owner
     */
    public void addOwnersPps(String ppsNum) {
        ownersPPS.add(ppsNum);
    }
    
     /**
     * Method to add Owners pps number from the ArrayList
     * @param ownersPPS Personal Public Service Number of owner from Arraylist
     */
    public void addOwnersPps(ArrayList<String> ppsNums) {
        ownersPPS = ppsNums;
    }

}
