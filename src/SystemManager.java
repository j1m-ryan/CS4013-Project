import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.StringTokenizer;
public class SystemManager  {
    private HashMap<String, Owner> owners = new HashMap<String, Owner>();
    private HashMap<String, Employee> employees = new HashMap<String, Employee>();
    private HashMap<String, Property> registeredProperties = new HashMap<String, Property>();
    private HashMap<Integer, ArrayList<Record>> paymentRecords = new HashMap<Integer, ArrayList<Record>>();
    
    private HashMap<String, String> eircodeToLocation = new HashMap<>();
    private HashMap<String, String> locationToEircode = new HashMap<>();
    
    private LocalDate currentDate;
    private int minYear = 1990;

    private TaxTable taxTable;

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
                                      ArrayList<String> eircodesAndCounties,
                                      ArrayList<String> empolyeesData,
                                      ArrayList<String> preBuiltTaxTable){
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

        // loading registered employees data
        for(String employeeData : empolyeesData){
            StringTokenizer temp = new StringTokenizer(employeeData, " ,");
            registerEmployee(temp.nextToken(), temp.nextToken(), temp.nextToken());
        }
        // loading tax table data
        loadTaxTableData(preBuiltTaxTable);
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

    //method to set tax table 
    private void loadTaxTableData(ArrayList<String> taxTableData){
        double fixedCost = 0;
        double flatCharge = 0;
        double penaltyRate = 0;
        ArrayList<String> locationCat = new ArrayList<String>();
        ArrayList<Double> locationRates = new ArrayList<Double>();
        ArrayList<Double> valueRates = new ArrayList<Double>();
        ArrayList<Double> valueRangeMaxs = new ArrayList<Double>();
        
        String row1 = taxTableData.get(0);
        StringTokenizer tokens = new StringTokenizer(row1, ",");
        tokens.nextToken();
        fixedCost = Double.parseDouble(tokens.nextToken());
            
        String row2 = taxTableData.get(1);
        tokens = new StringTokenizer(row2, ",");
        tokens.nextToken();
        flatCharge = Double.parseDouble(tokens.nextToken());
        
        String row3 = taxTableData.get(2);
        tokens = new StringTokenizer(row3, ",");
        tokens.nextToken();
        penaltyRate = Double.parseDouble(tokens.nextToken());
        
        String row4 = taxTableData.get(3);
        tokens = new StringTokenizer(row4, ",");
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            locationCat.add(tokens.nextToken());
        }

        String row5 = taxTableData.get(4);
        tokens = new StringTokenizer(row5, ",");
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            locationRates.add(Double.parseDouble(tokens.nextToken()));
        }

        String row6 = taxTableData.get(5);
        tokens = new StringTokenizer(row6, ",");
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            valueRates.add(Double.parseDouble(tokens.nextToken()));
        }

        String row7 = taxTableData.get(6);
        tokens = new StringTokenizer(row7, ",");
        tokens.nextToken();
        while(tokens.hasMoreTokens()){
            valueRangeMaxs.add(Double.parseDouble(tokens.nextToken()));
        }

        taxTable = new TaxTable(fixedCost, flatCharge, penaltyRate, locationCat, locationRates, valueRates, valueRangeMaxs);
    }

/** 
        double[] propValueRates;
        private ArrayList<double[]> propValueRanges = new ArrayList<double[]>();
        private HashMap<String, Double> locatoinCatAndRates = new HashMap<String, Double>();
        
        String row6 = taxTableData.get(5);
        String row7 = taxTableData.get(6);
        String row8 = taxTableData.get(7);

        StringTokenizer temp = new StringTokenizer(data, ",");
        String currentToken = temp.nextToken();
        ArrayList<String> locationCat = new ArrayList<String>();
        ArrayList<Double> locationRates = new ArrayList<Double>();
        if(currentToken.equalsIgnoreCase("flat Charge")){
            taxTable.setFlatCharge(Double.parseDouble(currentToken));
            currentToken = temp.nextToken();
        } else if(currentToken.equalsIgnoreCase("penalty rate")){
            taxTable.setPenaltyRate(Double.parseDouble(currentToken));
            currentToken = temp.nextToken();
        } else if(currentToken.equalsIgnoreCase("location categories")){
            while(!currentToken.equalsIgnoreCase("location rates")){
                locationCat.add(currentToken);
                currentToken = temp.nextToken();
            }
        } else if(currentToken.equalsIgnoreCase("location rates")){
            while(!currentToken.equalsIgnoreCase("property value rates")){
                locationRates.add(Double.parseDouble(currentToken));
                currentToken = temp.nextToken();
            }
        } else if(currentToken.equalsIgnoreCase("property value rates")){
            while(!currentToken.equalsIgnoreCase("Property Value Range min")){
                locationRates.add(Double.parseDouble(currentToken));
                currentToken = temp.nextToken();
            }
        } else if(currentToken.equalsIgnoreCase("Property Value Range min")){
            while(!currentToken.equalsIgnoreCase("Property Value Range min")){
                locationRates.add(Double.parseDouble(currentToken));
                currentToken = temp.nextToken();
            }
        } else if(currentToken.equalsIgnoreCase("Property Value Range min")){
            while(!currentToken.equalsIgnoreCase("end tax table")){
                locationRates.add(Double.parseDouble(currentToken));
                currentToken = temp.nextToken();
            }
        }

    }
*/
    // method to register department employee
    public void registerEmployee(String name, String workId, String password){
        employees.put(workId, (new Employee(name, workId, password)));
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
        double totalCharges = 0;
        Property property = getPropertyData(eircode);
        
        // apply fixed cost
        totalCharges += taxTable.getFixedCost();

        // apply property value tax 
        double valueTaxRate = taxTable.getPropertyValueRate(property.getEstimatedMarketValue());
        double valueTaxAmount = (Double.parseDouble(property.getEstimatedMarketValue()) * valueTaxRate)/100;
        totalCharges += valueTaxAmount;

        // apply location tax
        double locationRate = taxTable.getLocationRate(property.getLocationCatgeory());
        totalCharges += locationRate;

        // if property not the principal private residence then apply flat charge
        if(!property.getPrincipalPrivateResidenceStatus()){
            double flatCharge = taxTable.getFlatCharge();
            totalCharges += flatCharge;
        }

        // Apply a penalty charge, compounded for each year that a property tax is unpaid. 
        int totalYearsUnpaid = 0;
        double penaltyCharge = taxTable.getPenaltyRate();
        ArrayList<Record> allTaxPaymentRecords = getPaymentRecords(property.getEircode());
        for(Record rec : allTaxPaymentRecords){
            if(rec.getPaymentStatus().equalsIgnoreCase("unpaid")){
                totalYearsUnpaid++;
            }
        }
        totalCharges += (totalYearsUnpaid * penaltyCharge);
        
        return totalCharges;
    }

    // method to get all over due properties for specified year
    public ArrayList<Record> getOverDuePropsPerYear(int year){
        return getDataFromPaymentRecords(year, "unpaid");
    }

    //method to return owners all properties paid records
    public ArrayList<Record> getOwnersPaidPropsRecords(String ppsNum){
        Owner owner = owners.get(ppsNum);
        ArrayList<String> propEircodes = owner.getPropertiesEircodes();
        ArrayList<Record> paidRecords = new ArrayList<Record>();
        for(String eircode : propEircodes){
            paidRecords.addAll(getPropertyRecords(eircode, "paid"));
        }
        return paidRecords;
    }

    // method to return owners all properties due records
    public ArrayList<Record> getOwnersDuePropsRecords(String ppsNum){
        Owner owner = owners.get(ppsNum);
        ArrayList<String> propEircodes = owner.getPropertiesEircodes();
        ArrayList<Record> dueRecords = new ArrayList<Record>();
        for(String eircode : propEircodes){
            dueRecords.addAll(getPropertyRecords(eircode, "unpaid"));
        }
        return dueRecords;
    }

    // method to get all paid or unpaid records for chosen
    public ArrayList<Record> getPropertyRecords(String eircode, String paymentStatus){
        ArrayList<Record> allRecords = getPaymentRecords(eircode);
        ArrayList<Record> matchingRecords = new ArrayList<Record>();
        for (Record r : allRecords){
            if(r.getPaymentStatus().equalsIgnoreCase(paymentStatus)){
                matchingRecords.add(r);
            }
        }
        return matchingRecords;
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
        ArrayList<Record> combinedRecs = new ArrayList<Record>();
        for(Integer year : paymentRecords.keySet()){
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

    // method to get records of owner's all properties records
    public ArrayList<Record> getOwnerAllRecords(String ppsNum){
        ArrayList<String> eircodes = getOwnerPropertiesEircodes(ppsNum);
        ArrayList<Record> matchingRecs = new ArrayList<Record>();
        for(String eircode : eircodes){
            matchingRecs.addAll(getPaymentRecords(eircode));
        }
        return matchingRecs;
    }

    // method to send arraylist of all years for which system has records
    public ArrayList<Integer> getRecordsYears(){
        ArrayList<Integer> years = new ArrayList<Integer>();
        for(Integer year : paymentRecords.keySet()){
            years.add(year);
        }
        return years;
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

    // method to check if owner with matching pps number exists in database
    public boolean employeeExists(String workId) {
        if (employees.containsKey(workId)) {
            return true;
        }
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

    // method to check if login details match the one's on database for an employee
    public boolean depLoginVerification(String workId, String password) {
        if (employeeExists(workId)) {
            String accCorrectPass = employees.get(workId).getPassword();
            if (accCorrectPass.equalsIgnoreCase(password)) {
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

    // returns employee object if an employee with matching workId is found
    public Employee getEmployee(String workId) {
        return employees.get(workId);
        // will be throwing error if not found for later
    }

    // 7 digits and 1 or 2 letters
    public boolean isValidppsNum(String ppsNum) {
        return ppsNum.matches("[\\d]{7}[ A-Za-z]{1,2}");
    }

    // 8 digits
    public boolean isValidWID(String ppsNum) {
        return ppsNum.matches("[\\d]{8}");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }
    
    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // check if an eircode contains the routing key, is 7 chars long and is made of letters and numbers only
    public boolean isValidEircode(String eircode) {
        String eircodeRountingKey = eircode.substring(0, 3);
        if (!eircodeToLocation.containsKey(eircodeRountingKey)) {
            return false;
        }
        return eircode.matches("[a-zA-Z0-9]{7}");
    }

    // method to check if an key is a valid eircode routing key
    public boolean isValidEircodeRouteKey(String key){
        if(eircodeToLocation.containsKey(key)){return true;}
        return false;
    }

    // method to return county name of making eircode routing key
    public String routeKeyLocation(String key){
        return eircodeToLocation.get(key);
    }

    /**

    private void updatePropertyData(Property property){}

    private void updateOwners(){}

    public boolean transferOwnership(int propertyId, int oldOwnerppsNum, int newOwnerppsNum){}
    */

}

