import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.StringTokenizer;
public class SystemManager  {
    HashMap<Integer, Owner> owners = new HashMap<Integer, Owner>();
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

    private void addEircodeToLocation(String eircode, String county){
        eircodeToLocation.put(eircode, county);
    }

    public void setLocationToEircode(HashMap<String, String> codes){
        this.eircodeToLocation = codes;
    }

    // method to load all database of registered owerns, properties and records at startup
    public void loadAllRegisteredData(ArrayList<String> propertiesData,
                                      ArrayList<String> ownersData,
                                      ArrayList<String> ownerPropertylinks,
                                      ArrayList<String> paymentRecords,
                                      ArrayList<String> eircodesAndCounties){
        // loading registeredOwners data
        for(String ownerData : ownersData){
            ArrayList<String> separatedData = separateWordsInLine(ownerData);
            registerOwner(separatedData.get(0), Integer.parseInt(separatedData.get(1)), separatedData.get(2));
        }
        // loading registeredProperties data
        for(String propertyData : propertiesData){
            ArrayList<String> separatedData = separateWordsInLine(propertyData);
            loadRegisteredProps(separatedData);
        }
        // linking properties to Owners
        for(String linkData : ownerPropertylinks){
            ArrayList<String> separatedData = separateWordsInLine(linkData);
            linkPropertiesToOwners(separatedData);
        }
        // loading payment records
        for(String record : paymentRecords){
            ArrayList<String> separatedData = separateWordsInLine(record);
            loadPaymentRecords(separatedData);         
        }
        // loading eircodes
        for(String eirCounty : eircodesAndCounties){
            StringTokenizer temp = new StringTokenizer(eirCounty, " ,");
            addEircodeToLocation(temp.nextToken(), temp.nextToken());
            }
    }

    // helper method to separate each word in line and send back separated words as arraylist
    private ArrayList<String> separateWordsInLine(String line){
        ArrayList<String> separatedWords = new  ArrayList<String>();
        StringTokenizer temp = new StringTokenizer(line, ",");
        while( temp.hasMoreTokens()){
            separatedWords.add(temp.nextToken());
        }
        return separatedWords;
    }

    // helper method to load payments records to systam at start up
    private void loadPaymentRecords(ArrayList<String> recordData){
        int propertyID = Integer.parseInt(recordData.get(0));
        double taxDue = Double.parseDouble(recordData.get(1));
        String paymentStatus = recordData.get(2);
        int year = Integer.parseInt(recordData.get(3));
        registerPaymentsRecord(propertyID, taxDue, paymentStatus, year);
    }

    // helper method to link loaded properties to owerns at start up
    private void linkPropertiesToOwners(ArrayList<String> relationData){
        int propertyId = Integer.parseInt(relationData.get(0));
        relationData.remove(0);
        ArrayList<Integer> tempOwners = new ArrayList<Integer>();
        for(String id : relationData){
            Owner owner = owners.get(Integer.parseInt(id));
            if(owner.getPps() == Integer.parseInt(id)){
                tempOwners.add(owner.getPps());
            }
        }
        linkPropertyToOwners(tempOwners, propertyId);
    }

    // helper method to load registered properties at start up
    private void loadRegisteredProps(ArrayList<String> propDetails){
        uniquePropertyId++;  
        registeredProperties.put(uniquePropertyId, new Property(propDetails));
    }

    // method to register owner
    public void registerOwner(String name, int pps, String password){
        owners.put(pps, (new Owner(name, pps, password)));
    }

    // method to get properties ids linked to owner
    public ArrayList<Integer> getOwnerPropertiesIds(int owner_pps){
        return owners.get(owner_pps).getPropertiesId();
    }

    // method to link property to owners
    private void linkPropertyToOwners(ArrayList<Integer> owners_pps, int propertyId){
        for(int pps : owners_pps){
            owners.get(pps).addProperty(propertyId);
        }
    }

    // method to register new property
    public void registerProperty(int year, ArrayList<Integer> owners_pps, ArrayList<String> propDetails){
        uniquePropertyId++;  
        registeredProperties.put(uniquePropertyId, new Property(owners_pps, propDetails));
        linkPropertyToOwners(owners_pps, uniquePropertyId);
        double taxDue = calculateTax(uniquePropertyId);
        
        registerPaymentsRecord(uniquePropertyId, taxDue, "unpaid", year);
    }

    // method to register payment records
    public void registerPaymentsRecord(int propertyID, double taxDue, String paymentStatus, int year){
        ArrayList<Record> temp = new ArrayList<Record>();
        if (paymentRecords.get(year) == null){
            temp.add(new Record(propertyID, taxDue, paymentStatus, year));
            paymentRecords.put(year, temp);    
        } else {
            temp = paymentRecords.get(year);
            temp.add(new Record(propertyID, taxDue, paymentStatus, year));
            paymentRecords.put(year, temp);
        }
    }

    // method to calcuclate property tax
    private double calculateTax(int propertyId){
        return 100;
    }

    // method to get all over due properties for specified year
    public ArrayList<Record> getOverDuePropsPerYear(int year){
        return getDataFromPaymentRecords(year, "unpaid");
    }

    
    // Overloaded with Eircode   (not sure about the year as it was not mentioned in specs)
    // method to get all over due properties for specified year and eircode area
    public ArrayList<Record> getAllOverDueProps(int year, String eircode){
        if(eircodeToLocation.containsKey(eircode)){
            ArrayList<Record> allDueProps = getDataFromPaymentRecords(year, "unpaid");
            ArrayList<Record> ericodeMatchedProps = new ArrayList<Record>();
            for(Record rec : allDueProps){
                if(registeredProperties.get(rec.getPropId()).getEircode().equalsIgnoreCase(eircode)){
                    ericodeMatchedProps.add(rec);
                }
            }
            return ericodeMatchedProps;
        }else{
            return new ArrayList<Record>();
        } // will remove the else code and add code later to throw an exception if eircode not found in eircodeToLocation
    }

    // helper method to get records from payment records with specified year and payment status
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

    // method to get property data ie. address, eircode, location etc.
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
        ArrayList<Record> taxPaidProps = getDataFromPaymentRecords(year,  "paid");
        ArrayList<Record> filteredRecords =getMatchingEircoderecords(taxPaidProps, eircode);
        double sum = 0;
        for(Record rec : filteredRecords){
            sum += rec.getTaxAmount();
        }
        return sum;
    }

    //method to filter records with specified eircode
    private ArrayList<Record> getMatchingEircoderecords(ArrayList<Record> allRecords, String eircode){
        if(eircodeToLocation.containsKey(eircode)){
            ArrayList<Record> matchingRecords = new ArrayList<Record>();
            for(Record rec : allRecords){
                if(registeredProperties.get(rec.getPropId()).getEircode().equalsIgnoreCase(eircode)){
                    matchingRecords.add(rec);
                }
            }
            return matchingRecords;
        }else{
            return new ArrayList<Record>();
        } // will remove the else code and add code later to throw an exception if eircode not found in eircodeToLocation
    }
    /**

    private void updatePropertyData(Property property){}

    private void updateOwners(){}
    
    public boolean ownerExists(int pps_num){}

    public boolean transferOwnership(int propertyId, int oldOwnerPps, int newOwnerPps){}
    */

}

