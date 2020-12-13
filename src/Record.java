public class Record {
    private String eircode = "";
    private String eircodeRoutingKey = "";
    private double taxAmount = 0;
    private String status = "";
    private int year = 0;

    public Record(){
        // do nothing
    }

    public Record(String eircode, double taxAmount, String status, int year){
        this.eircode = eircode;
        this.eircodeRoutingKey = eircode.substring(0, 3);  
        this.taxAmount = taxAmount;
        this.status = status;
        this.year = year;
    }

    
    /** 
     * @return String
     */
    public String getEircode(){
        return this.eircode;
    }

    
    /** 
     * @return double
     */
    public double getTaxAmount(){
        return this.taxAmount;
    }

    
    /** 
     * @return String
     */
    public String getPaymentStatus(){
        return this.status;
    }

    
    /** 
     * @return int
     */
    public int getYear(){
        return this.year;
    }

    
    /** 
     * @return String
     */
    public String getEircodeRoutingKey(){
        return this.eircodeRoutingKey;
    }

    
    /** 
     * @param newStatus
     */
    public void setPaymentStatus(String newStatus){
        this.status = newStatus;
    }
}
