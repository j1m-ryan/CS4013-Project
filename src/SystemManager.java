import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.StringTokenizer;
public class SystemManager  {
    HashMap<String, Owner> owners = new HashMap<String, Owner>();
    HashMap<String, Property> registeredProperties = new HashMap<String, Property>();
    HashMap<Integer, ArrayList<Record>> paymentRecords = new HashMap<Integer, ArrayList<Record>>();
    
    HashMap<String, String> eircodeToLocation = new HashMap<>();
    HashMap<String, String> locationToEircode = new HashMap<>();
    
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
            registerOwner(separatedData.get(0), separatedData.get(1), separatedData.get(2));
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

    // methods to update all database of registered owerns, properties and records
   




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
        String eircode = recordData.get(0);
        double taxDue = Double.parseDouble(recordData.get(1));
        String paymentStatus = recordData.get(2);
        int year = Integer.parseInt(recordData.get(3));
        registerPaymentsRecord(eircode, taxDue, paymentStatus, year);
    }

    // helper method to link loaded properties to owerns at start up
    private void linkPropertiesToOwners(ArrayList<String> relationData){
        String eircode = relationData.get(0);
        relationData.remove(0);
        ArrayList<String> tempOwners = new ArrayList<String>();
        for(String id : relationData){
            Owner owner = owners.get(id);
            if(owner.getPpsNum().equalsIgnoreCase(id)){
                tempOwners.add(owner.getPpsNum());
            }
        }
        linkPropertyToOwners(tempOwners, eircode);
    }

    // helper method to load registered properties at start up
    private void loadRegisteredProps(ArrayList<String> propDetails){
        boolean isPrincipalPrivateResidence = false;
        if(propDetails.get(4).equalsIgnoreCase("true")){
            isPrincipalPrivateResidence = true;
        }else{
            isPrincipalPrivateResidence = false;
        }
        registeredProperties.put(propDetails.get(1), new Property(propDetails.get(0),propDetails.get(1),
        propDetails.get(2),propDetails.get(3), isPrincipalPrivateResidence));
    }

    // method to register owner
    public void registerOwner(String name, String ppsNum, String password){
        owners.put(ppsNum, (new Owner(name, ppsNum, password)));
    }

    // method to get properties ids linked to owner
    public ArrayList<String> getOwnerPropertiesEircodes(String owner_ppsNum){
        return owners.get(owner_ppsNum).getPropertiesEircodes();
    }

    // method to link property to owners
    private void linkPropertyToOwners(ArrayList<String> owners_ppsNums, String eircode){
        for(String ppsNum : owners_ppsNums){
            owners.get(ppsNum).addProperty(eircode);
            registeredProperties.get(eircode).addOwnersPps(ppsNum);
        }
    }

    // method to register new property
    public void registerProperty(int year, String owners_ppsNums, String address, String eircode, 
                                String estimatedMarketValue, String locationCategory,boolean isPrincipalPrivateResidence)
    {
        registeredProperties.put(eircode, new Property(address, eircode, 
                                 estimatedMarketValue,  locationCategory, isPrincipalPrivateResidence));
        linkPropertyToOwners(getOwnersAsArrayList(owners_ppsNums), eircode);
        registeredProperties.get(eircode).addOwnersPps(getOwnersAsArrayList(owners_ppsNums));
        double taxDue = calculateTax(eircode);
        
        registerPaymentsRecord(eircode, taxDue, "unpaid", year);
    }

    // method to return owners pps in array 
    private ArrayList<String> getOwnersAsArrayList(String owners_ppsNums){
        ArrayList<String> ppsNumsArray = new ArrayList<String>();
        StringTokenizer separatedPpsNums = new StringTokenizer(owners_ppsNums, " ,");
        while( separatedPpsNums.hasMoreTokens()){
                ppsNumsArray.add(separatedPpsNums.nextToken());
        }
        return ppsNumsArray;
    }

    // method to register payment records
    public void registerPaymentsRecord(String eircode, double taxDue, String paymentStatus, int year){
        ArrayList<Record> temp = new ArrayList<Record>();
        if (paymentRecords.get(year) == null){
            temp.add(new Record(eircode, taxDue, paymentStatus, year));
            paymentRecords.put(year, temp);    
        } else {
            temp = paymentRecords.get(year);
            temp.add(new Record(eircode, taxDue, paymentStatus, year));
            paymentRecords.put(year, temp);
        }
    }

    // method to calcuclate property tax
    public double calculateTax(String eircode){
        return 100;
    }

    // method to get all over due properties for specified year
    public ArrayList<Record> getOverDuePropsPerYear(int year){
        return getDataFromPaymentRecords(year, "unpaid");
    }

    
    // Overloaded with Eircode   (not sure about the year as it was not mentioned in specs)
    // method to get all over due properties for specified year and eircode area
    public ArrayList<Record> getAllOverDueProps(int year, String eircodeRoutingKey){
        if(eircodeToLocation.containsKey(eircodeRoutingKey)){
            ArrayList<Record> allDueProps = getDataFromPaymentRecords(year, "unpaid");
            ArrayList<Record> ericodeMatchedProps = new ArrayList<Record>();
            for(Record rec : allDueProps){
                if(rec.getEircodeRoutingKey().equalsIgnoreCase(eircodeRoutingKey)){
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
    public Property getPropertyData(String eircode){
        return registeredProperties.get(eircode);
    }

    // method to get property owners
    public ArrayList<String> getPropertyOwners(String eircode){
        return registeredProperties.get(eircode).getOwnersPps();
    }
        
    // method to get property payments records for all registered years
    public ArrayList<Record> getPaymentRecords(String eircode){
        currentDate = LocalDate.now();
        ArrayList<Record> combinedRecs = new ArrayList<Record>();
        for(int year = minYear;year <= currentDate.getYear(); year++){
            combinedRecs.addAll(getPaymentRecords(year, eircode));
        }
        return combinedRecs;
    }

    // method returns all payments records of desired property for specified year
    public ArrayList<Record> getPaymentRecords(int year, String eircode){
        ArrayList<Record> matchingRecs = new ArrayList<Record>();
        if(paymentRecords.get(year) != null){
            for(Record rec : paymentRecords.get(year)){
                if(rec.getEircode().equalsIgnoreCase(eircode)){
                    matchingRecs.add(rec);
                }
            }
        }
        return matchingRecs; 
    }

    // method to pay the tax for specified year of a property
    public boolean makePayment(int year, String eircode){
        ArrayList<Record> temp = new ArrayList<Record>();
        temp = getPaymentRecords(year, eircode);
        for(Record rec : temp){
            rec.setPaymentStatus("paid");
            return true;
        }
        return false;
    }
    
    // method to display tax stats for a specified eircodeRoutingKey 
    // returned is an array of {totalTaxPaid, avgTaxPaid, numOfTaxPaidProperties, percentageOfPropTaxPaid}
    public double[] getTaxStats(int year, String eircodeRoutingKey){
        double totalTaxPaid = totalTaxPaid(year, eircodeRoutingKey);
        int totalProps = totalNumOfProperties(eircodeRoutingKey);
        ArrayList<Record> taxNotPaidProps = getAllOverDueProps( year,  eircodeRoutingKey);
        double numOfTaxPaidProperties = totalProps - taxNotPaidProps.size();
        double avgTaxPaid = totalTaxPaid/numOfTaxPaidProperties;
        double percentageOfPropTaxPaid = ((numOfTaxPaidProperties/totalProps) * 100);
        double[] allCombined = {totalTaxPaid, avgTaxPaid, numOfTaxPaidProperties, percentageOfPropTaxPaid};
        return allCombined;
    }
    
    // method for counting number of registered properties for a specified eircodeRoutingKey
    private int totalNumOfProperties(String eircodeRoutingKey){
        int count = 0;
        for(String k : registeredProperties.keySet()){
            if(registeredProperties.get(k).getEircodeRoutingKey().equalsIgnoreCase(eircodeRoutingKey)){
                count++;
            }
        }
        return count;
    }

    // method to get total tax paid by all properties in specified eircodeRoutingKey
    private double totalTaxPaid(int year, String eircodeRoutingKey){
        ArrayList<Record> taxPaidProps = getDataFromPaymentRecords(year,  "paid");
        ArrayList<Record> filteredRecords =getMatchingEircoderecords(taxPaidProps, eircodeRoutingKey);
        double sum = 0;
        for(Record rec : filteredRecords){
            sum += rec.getTaxAmount();
        }
        return sum;
    }

    //method to filter records with specified eircodeRoutingKey
    private ArrayList<Record> getMatchingEircoderecords(ArrayList<Record> allRecords, String eircodeRoutingKey){
        if(eircodeToLocation.containsKey(eircodeRoutingKey)){
            ArrayList<Record> matchingRecords = new ArrayList<Record>();
            for(Record rec : allRecords){
                if(rec.getEircodeRoutingKey().equalsIgnoreCase(eircodeRoutingKey)){
                    matchingRecords.add(rec);
                }
            }
            return matchingRecords;
        }else{
            return new ArrayList<Record>();
        } // will remove the else code and add code later to throw an exception if eircode not found in eircodeToLocation
    }

    public boolean areAllAdditionalOwnersRegestered(String ppsNums){
        StringTokenizer separatedPpsNums = new StringTokenizer(ppsNums, " ,");
        while( separatedPpsNums.hasMoreTokens()){
            if(ownerExists(separatedPpsNums.nextToken()) == false){
                return false;
            }
        }
        return true;
    }

    // method to check if owner with matching pps number exists in database
    public boolean ownerExists(String ppsNum) {
        if (owners.containsKey(ppsNum)){return true;}
        return false;
    }

    // method to check if login details match the one's on database
    public boolean loginVerification(String ppsNum, String pass) {
        if(ownerExists(ppsNum)){
            String accCorrectPass = owners.get(ppsNum).getPassword();
            if(accCorrectPass.equalsIgnoreCase(pass)){
                return true;
            }
        }
        return false;
    }

    // returns owner object if an owner with matching ppsNum is found
    public Owner getOwner(String ppsNum) {
        return owners.get(ppsNum);
        // will be throwing error if not found for later
    }

    // 7 digits and 1 or 2 letters
    public boolean isValidppsNum(String ppsNum) {
        return ppsNum.matches("[\\d]{7}[ A-Za-z]{1,2}");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }
    

    /**

    private void updatePropertyData(Property property){}

    private void updateOwners(){}

    public boolean transferOwnership(int propertyId, int oldOwnerppsNum, int newOwnerppsNum){}
    */

}

