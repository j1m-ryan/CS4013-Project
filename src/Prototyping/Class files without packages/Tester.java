import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class Tester {
    public static void main(String args[]) throws IOException
    {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        
        SystemManager system = new SystemManager();
        
          // open file input stream
          BufferedReader reader = new BufferedReader(new FileReader("EircodeRoutingKeys.csv"));

          // read file line by line
          String line = null;
          Scanner scanner = null;
          HashMap<String, String> eircodeToLocation = new HashMap<>();
          HashMap<String, String> locationToEircode = new HashMap<>();
  
          while ((line = reader.readLine()) != null) {
  
              scanner = new Scanner(line);
              scanner.useDelimiter(",");
              while (scanner.hasNext()) {
                  String data = scanner.next();
                  String[] separeted = data.split("\t");
  
                  eircodeToLocation.put(separeted[0].trim(), separeted[1].trim());
                  locationToEircode.put(separeted[1].trim(), separeted[0].trim());
  
              }
          }
        reader.close();

        system.setEircodeToLocation(eircodeToLocation);
        system.setLocationToEircode(locationToEircode);



        owners.add(new Owner("Garry"));
        // array elements in sequence address, eircode, marketvalue, locationCat, principalPrivateResidence
        String[] data = {"12 shannon, co. clare, Ireland", "V14", "100000", "small town", "noPPR"};
        String[] data2 = {"2 ennis, co. clare, Ireland", "V95", "150000", "large town", "noPPR"};
        String[] data3 = {"4 somewhere, co. dublin, Ireland", "DB1", "750000", "city", "noPPR"};
        String[] data4 = {"15 shannon, co. clare, Ireland", "V14", "100000", "small town", "noPPR"};       

        system.registerProperty(2018, owners, data);
        system.registerProperty(2019, owners, data2);
        system.registerProperty(2020, owners, data3);
        
        owners.add(new Owner("Parry"));
        system.registerProperty(2020, owners, data4);
        
        int year = 2020;
        ArrayList<Record> tempRecords;
        tempRecords = system.getOverDuePropsPerYear(year);
        System.out.println("Properties with tax over due for " + year);
        System.out.println("Property Id      Tax due        PaymentStatus     Year");
        for(Record rec : tempRecords){
            System.out.println("\n      " + rec.getPropId() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;    
        }

        System.out.println("\n\n************************************\n\n");

        System.out.println("List Owner properties");
        System.out.println("Owner      PropertyIDs");
        for(Owner owner: owners){
            System.out.println("\n\nList of " + owner.getName() + " properties are :" );
            ArrayList<Integer> tempPropsIds = owner.getPropertiesId();
            for(int id : tempPropsIds){
                Property prop = system.getPropertyData(id);
                System.out.println("Property ID : " + id
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
            ArrayList<Integer> tempPropsIds = owner.getPropertiesId();
            for(int id : tempPropsIds){
                Property prop = system.getPropertyData(id);
                System.out.println("Property ID : " + id
                                + "\nAddress : " + prop.getAddress()
                                + "\nEircode : " + prop.getEircode()
                                + "\nEst. Market Value : " + prop.getEstimatedMarketValue()); 
                
                paymentData = system.getPaymentRecords(id);
                System.out.println("Property Id      Tax due        PaymentStatus     Year");
                for(Record rec : paymentData){
                    System.out.println("\n      " + rec.getPropId() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;
                }
            }
        }

        System.out.println("\n\n************************************\n\n");

        System.out.println("\nGarry makes a payment for his property id 1");
        system.makePayment(2018, 1, 100.0);                

        System.out.println("\nget payment data of Property id 1");
        paymentData = system.getPaymentRecords(1);
        System.out.println("Property Id      Tax due        PaymentStatus     Year");
        for(Record rec : paymentData){
            System.out.println("\n      " + rec.getPropId() + "            " + rec.getTaxAmount() 
                        + "            " + rec.getPaymentStatus() + "          " + rec.getYear()) ;
        }

        System.out.println("\n\n************************************\n\n");

        System.out.println("\nDisplaying tax stats for a specified Ericode area");
        double[] taxStats = system.getTaxStats(2018, "V14");
        System.out.println("Eircode was V14");
        System.out.println("Total tax paid = " + taxStats[0] + "\nAverage tax paid = " + taxStats[1]
                    + "\nNumber of properties tax paid = " + taxStats[2]
                    + "\nPercentage of properties tax paid = " + taxStats[3] + "%");

    }
}
