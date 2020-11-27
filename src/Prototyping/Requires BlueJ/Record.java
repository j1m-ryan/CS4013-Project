public class Record {
    private int propertyId = 0;
    private double taxAmount = 0;
    private String status = "";
    private int year = 0;

    public Record(){
        // do nothing
    }

    public Record(int propertyId, double taxAmount, String status, int year){
        this.propertyId = propertyId;
        this.taxAmount = taxAmount;
        this.status = status;
        this.year = year;
    }

    public int getPropId(){
        return this.propertyId;
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

    public void setPaymentStatus(String newStatus){
        this.status = newStatus;
    }
}
