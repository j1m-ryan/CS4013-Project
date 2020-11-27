import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.time.LocalDate;
public class SystemManager  {
    ArrayList<Owner> owners = new ArrayList<Owner>();
    HashMap<Integer, Property> registeredProperties = new HashMap<Integer, Property>();
    HashMap<Integer, ArrayList<Record>> paymentRecords = new HashMap<Integer, ArrayList<Record>>();
    
    HashMap<String, String> eircodeToLocation = new HashMap<>();
    HashMap<String, String> locationToEircode = new HashMap<>();
    // Thinak again HashMap<Integer, ArrayList<Record>> taxdueRecords = new HashMap<Integer, ArrayList<Record>>();
    
    private int uniquePropertyId = 0;
    private LocalDate currentDate;
    private int minYear = 1900;

    public SystemManager(){
        // do nothing for now but later will do something like run() method
    }

    public void setEircodeToLocation(HashMap<String, String> codes){
        this.eircodeToLocation = codes;
    }

    public void setLocationToEircode(HashMap<String, String> codes){
        this.eircodeToLocation = codes;
    }

    // Method to assist loadin registered properties from csv file (For Future)
    private void loadRegisteredProps(int year, ArrayList<Owner> owners, String[] propDetails){
        registerProperty(year, owners, propDetails);
    }

    public void registerProperty(int year, ArrayList<Owner> owners, String[] propDetails){
        uniquePropertyId++;  
        registeredProperties.put(uniquePropertyId, new Property(owners, propDetails));
        for(Owner owner : owners){
            owner.addProperty(uniquePropertyId);
        }
        double taxDue = calculateTax(uniquePropertyId);
        
        ArrayList<Record> temp = new ArrayList<Record>();
        if (paymentRecords.get(year) == null){
            temp.add(new Record(uniquePropertyId, taxDue, "unpaid", year));
            paymentRecords.put(year, temp);    
        } else {
            temp = paymentRecords.get(year);
            temp.add(new Record(uniquePropertyId, taxDue, "unpaid", year));
            paymentRecords.put(year, temp);
        }
        //taxdueRecords.put(currentDate.getYear()).add(new Record(uniquePropertyId, taxDue, "Unpaid"))
        
    }

    private double calculateTax(int propertyId){
        return 100;
    }

    public ArrayList<Record> getOverDuePropsPerYear(int year){
        return getDataFromPaymentRecords(year, "unpaid");
    }

    
    // Overloaded with Eircode   (not sure about the year as it was not mentioned in specs)
    public ArrayList<Record> getAllOverDueProps(int year, String eircode){
        if(eircodeToLocation.containsKey(eircode)){
            ArrayList<Record> allDueProps = getDataFromPaymentRecords(year, "unpaid");
            ArrayList<Record> ericodeMatchedProps = new ArrayList<Record>();
            for(Record rec : allDueProps){
                if(registeredProperties.get(rec.getPropId()).getEircode() == eircode){
                    ericodeMatchedProps.add(rec);
                }
            }
            return ericodeMatchedProps;
        }else{
            return new ArrayList<Record>();
        } // will remove the else code and add code later to throw an exception if eircode not found in eircodeToLocation
    }

    // helper method
    private ArrayList<Record> getDataFromPaymentRecords(int year, String paymentStatus){
        String searchFor = "";
        if (paymentStatus.equalsIgnoreCase("paid")){
            searchFor = "paid";
        }else{
            searchFor = "unpaid";
        }
        ArrayList<Record> matchingRecs = new ArrayList<Record>();
        for(Record rec : paymentRecords.get(year)){
            if(rec.getPaymentStatus().equalsIgnoreCase(searchFor)){
                matchingRecs.add(rec);
            }
        }
        return matchingRecs;
    }

    public Property getPropertyData(int propertyId){
        return registeredProperties.get(propertyId);
    }
        
    // method to get property payments records for all registered years
    public ArrayList<Record> getPaymentRecords(int propertyId){
        currentDate = LocalDate.now();
        ArrayList<Record> combinedRecs = new ArrayList<Record>();
        for(int year = minYear;year <= currentDate.getYear(); year++){
            combinedRecs.addAll(getPaymentRecords(year, propertyId));
        }
        return combinedRecs;
    }

    // method returns all payments records of desired property for specified year
    public ArrayList<Record> getPaymentRecords(int year, int propertyId){
        ArrayList<Record> matchingRecs = new ArrayList<Record>();
        if(paymentRecords.get(year) != null){
            for(Record rec : paymentRecords.get(year)){
                if(rec.getPropId() == propertyId){
                    matchingRecs.add(rec);
                }
            }
        }
        return matchingRecs; 
    }

    // method to pay the tax for specified year of a property
    public Boolean makePayment(int year, int propertyId, double paid){
        ArrayList<Record> temp = new ArrayList<Record>();
        temp = getPaymentRecords(year, propertyId);
        for(Record rec : temp){
            if(rec.getTaxAmount() == paid){
                rec.setPaymentStatus("paid");
                return true;
            }
        }
        return false;
    }
    
    // method to display tax stats for a specified Eircode area
    // returned is an array of {totalTaxPaid, avgTaxPaid, numOfTaxPaidProperties, percentageOfPropTaxPaid}
    public double[] getTaxStats(int year, String eircode){
        double totalTaxPaid = totalTaxPaid(year, eircode);
        int totalProps = totalNumOfProperties(eircode);
        ArrayList<Record> taxNotPaidProps = getAllOverDueProps( year,  eircode);
        double numOfTaxPaidProperties = totalProps - taxNotPaidProps.size();
        double avgTaxPaid = totalTaxPaid/numOfTaxPaidProperties;
        double percentageOfPropTaxPaid = ((numOfTaxPaidProperties/totalProps) * 100);
        double[] allCombined = {totalTaxPaid, avgTaxPaid, numOfTaxPaidProperties, percentageOfPropTaxPaid};
        return allCombined;
    }
    
    // method for counting number of registered properties for a specified Eircode
    private int totalNumOfProperties(String eircode){
        int count = 0;
        for(int k : registeredProperties.keySet()){
            if(registeredProperties.get(k).getEircode().equalsIgnoreCase(eircode)){
                count++;
            }
        }
        return count;
    }

    // method to get total tax paid by all properties in specified eircode
    private double totalTaxPaid(int year, String eircode){
        ArrayList<Record> taxPaidProps = getDataFromPaymentRecords( year,  "paid");
        double sum = 0;
        for(Record rec : taxPaidProps){
            sum += rec.getTaxAmount();
        }
        return sum;
    }
    /**

    private void updatePaymentsData(){

    }

    private void updatePropertyData(Property property){

    }

    private void updateOwners(){}
    
    public boolean ownerExists(String name, int id){}

    public boolean transferOwnership(String name, int id){}
    */

}

