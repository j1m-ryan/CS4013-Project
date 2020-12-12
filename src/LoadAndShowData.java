import java.util.ArrayList;
import java.io.IOException;
public class LoadAndShowData {
    public static void main(String args[]) throws IOException
    {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        owners.add(new Owner("Garry","10022","p1"));
        owners.add(new Owner("Larry","20044","p2"));
        owners.add(new Owner("Terry","30011","p3"));
        SystemManager system = new SystemManager();
        
        CSVReader reader = new CSVReader();

        // read from CSV files
        ArrayList<String> eircodes = reader.readLinesFromFile("data/eircodesAndCounties.csv");
        ArrayList<String> propertiesData = reader.readLinesFromFile("data/registeredProperties.csv");
        ArrayList<String> ownersData = reader.readLinesFromFile("data/registeredOwners.csv");
        ArrayList<String> ownerPropertylinksData = reader.readLinesFromFile("data/ownerPropertylinks.csv");
        ArrayList<String> recordsData = reader.readLinesFromFile("data/registeredPaymentRecords.csv");
        ArrayList<String> employeesData = reader.readLinesFromFile("data/registeredEmployees.csv");
        // load system with already registered  data
        system.loadAllRegisteredData(propertiesData, ownersData, ownerPropertylinksData, recordsData, 
                                    eircodes,employeesData);
        
        
        int year = 2020;
        ArrayList<Record> tempRecords;
        tempRecords = system.getOverDuePropsPerYear(year);
        System.out.println("Properties with tax over due for " + year);
        System.out.println("Eircode      Tax due        PaymentStatus     Year");
        for(Record rec : tempRecords){
            System.out.println("\n      " + rec.getEircode() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;    
        }

        System.out.println("\n\n************************************\n\n");

        System.out.println("List Owner properties");
        System.out.println("Owner      PropertyIDs");
        for(Owner owner: owners){
            System.out.println("\n\nList of " + owner.getName() + " properties are :" );
            ArrayList<String> tempEircodes = system.getOwnerPropertiesEircodes(owner.getPpsNum());
            for(String id : tempEircodes){
                Property prop = system.getPropertyData(id);
                System.out.println("\nEircodes : " + id
                                + "\nAddress : " + prop.getAddress()
                                + "\nEircode : " + prop.getEircode()
                                + "\nEst. Market Value : " + prop.getEstimatedMarketValue());   
            }
        }

        System.out.println("\n\n************************************\n\n");

        ArrayList<Record> paymentData;
        System.out.println("\nOwner PropertyIDs and their tax due, payment status");
        for(Owner owner: owners){
            System.out.println("\n\nList of " + owner.getName() + " properties and their taxes are :" );
            ArrayList<String> tempEircodes = system.getOwnerPropertiesEircodes(owner.getPpsNum());
            for(String id : tempEircodes){
                Property prop = system.getPropertyData(id);
                System.out.println("Eircode : " + id
                                + "\nAddress : " + prop.getAddress()
                                + "\nEircode : " + prop.getEircode()
                                + "\nEst. Market Value : " + prop.getEstimatedMarketValue()); 
                
                paymentData = system.getPaymentRecords(id);
                System.out.println("Eircodes      Tax due        PaymentStatus     Year");
                for(Record rec : paymentData){
                    System.out.println("\n      " + rec.getEircode() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;
                }
            }
        }

        System.out.println("\n\n************************************\n\n");

        System.out.println("\nGarry makes a payment for his property eircode V14A221");
        system.makePayment(2018, "V14A221");                

        System.out.println("\nget payment data of Property eircode V14A221");
        paymentData = system.getPaymentRecords("V14A221");
        System.out.println("Eircode      Tax due        PaymentStatus     Year");
        for(Record rec : paymentData){
            System.out.println("\n      " + rec.getEircode() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;
        }

        System.out.println("\n\n************************************\n\n");

        String testEricode = "V14";
        System.out.println("\nDisplaying tax stats for a specified Ericode area");
        double[] taxStats = system.getTaxStats(2020, testEricode);
        System.out.println("Eircode was " + testEricode);
        System.out.println("Total tax paid = " + taxStats[0] + "\nAverage tax paid = " + taxStats[1]
                    + "\nNumber of properties tax paid = " + taxStats[2]
                    + "\nPercentage of properties tax paid = " + taxStats[3] + "%");

        System.out.println("\n\n************************************\n\n");

        System.out.println("\nDisplaying all Over Due Props for year " + year +  " and " + testEricode + " Ericode area");
        ArrayList<Record> props = system.getAllOverDueProps(year, testEricode);
        for(Record r : props){
            paymentData = system.getPaymentRecords(r.getEircode());
            System.out.println("Eircode      Tax due        PaymentStatus     Year    Property eircode");
            for(Record rec : paymentData){
                Property temp = system.getPropertyData(rec.getEircode());
                System.out.println("\n      " + rec.getEircode() + "            " + rec.getTaxAmount() 
                            + "            " + rec.getPaymentStatus() + "          " + rec.getYear()
                            + "    " + temp.getEircode()) ;
            }
        }
    }
}
