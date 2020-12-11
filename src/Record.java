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

    public String getEircode(){
        return this.eircode;
    }

    public double getTaxAmount(){
        return this.taxAmount;
    }

    public String getPaymentStatus(){
        return this.status;
    }

    public int getYear(){
        return this.year;
    }

    public String getEircodeRoutingKey(){
        return this.eircodeRoutingKey;
    }

    public void setPaymentStatus(String newStatus){
        this.status = newStatus;
    }
}
