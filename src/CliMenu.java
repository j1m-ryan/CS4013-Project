import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class CliMenu {
    private Scanner in;
    private SystemManager sm = new SystemManager();
    Owner owner;
    Employee employee;
    private CSVReader reader = new CSVReader();

    public CliMenu() {
        in = new Scanner(System.in);
    }

    
    /** 
     * @throws IOException
     */
    public void run() throws IOException {

        sm = new SystemManager();
        reader = new CSVReader();
        ArrayList<String> eircodes = reader.readLinesFromFile("data/eircodesAndCounties.csv");
        ArrayList<String> propertiesData = reader.readLinesFromFile("data/registeredProperties.csv");
        ArrayList<String> ownersData = reader.readLinesFromFile("data/registeredOwners.csv");
        ArrayList<String> ownerPropertylinksData = reader.readLinesFromFile("data/ownerPropertylinks.csv");
        ArrayList<String> recordsData = reader.readLinesFromFile("data/registeredPaymentRecords.csv");
        ArrayList<String> employeesData = reader.readLinesFromFile("data/registeredEmployees.csv");
        ArrayList<String> preBuiltTaxTable = reader.readLinesFromFile("data/preBuiltTaxTable.csv");
        // load system with already registered data
        sm.loadAllRegisteredData(propertiesData, ownersData, ownerPropertylinksData, recordsData, eircodes,
                employeesData, preBuiltTaxTable);
        boolean more = true;

        while (more) {
            System.out.println("A)bout  L)ogin  S)ignup  D)epartment Login Q)uit");
            String command = in.nextLine().toUpperCase();

            if (command.equals("A")) {
                about();

            } else if (command.equals("L")) {
                login();
            } else if (command.equals("S")) {
                signup();
            } else if (command.equals("D")) {
                depLogin();
            } else if (command.equals("Q")) {
                more = false;
            }
        }
    }

    public void about() {
        System.out.println(
                "Our software company Tax Ireland Solutions Ltd under contract from \nthe Department of Environment has developed this Property Charge Management System. \nThe system will allow property owners to register each of their properties and to pay the \nproperty tax due for the properties. Property tax is a yearly tax on a property and it is due to be \npaid on Jan 1st each year. Property owners are able to view a list of their properties \nand the tax that is due currently per property and also any overdue tax \n(hasn't been paid for a previous year) and are able to query specific previous years and get a balancing statement \nfor any particular year or property. The system maintains a record of all payments of the \nproperty charge on a yearly basis.");
    }

    
    /** 
     * @throws IOException
     */
    public void login() throws IOException {
        System.out.println("Login Menu");
        System.out.println("Enter your PPS number: ");
        String pps = in.nextLine().trim().toUpperCase();
        if (!sm.isValidppsNum(pps)) {
            System.out.println("This is not a valid PPS number");
        } else if (!sm.ownerExists(pps)) {
            System.out.println("This PPS number is not associated with an account. Would you like to signup? y/n");
            String ans = in.nextLine().trim().toLowerCase();
            if (ans.equals("y")) {
                signup();
            } else {
                run();
            }
        }
        System.out.println("Enter your password");
        String password = in.nextLine();

        while (!sm.loginVerification(pps, password)) {
            System.out.println("This password is incorrect. Would you like to try again? y/n");
            String ans = in.nextLine().trim().toLowerCase();
            if (!ans.equals("y")) {
                run();
            }
            password = in.nextLine();
        }
        owner = sm.getOwner(pps);

        dash();
    }

    public void signup() {
        System.out.println("Signup Menu");
        System.out.println("Enter your PPS number: ");
        String pps = in.nextLine().trim().toUpperCase();
        if (sm.ownerExists(pps)) {
            System.out.println("User already exists! Please log in!");
        } else if (!sm.isValidppsNum(pps)) {
            System.out.println("This is not a valid PPS number");
        } else {
            System.out.println("Enter your name");
            String name = in.nextLine().trim();
            System.out.println("Enter your password");
            String password = in.nextLine().trim();
            if (sm.isValidPassword(password)) {
                sm.registerOwner(name, pps, password);
                System.out.println("You have registered successfully!");
                owner = sm.getOwner(pps);
                dash();
            } else {
                System.out.println("Your password is invalid please try again.");
            }
        }
    }

    public void dash() {
        boolean more = true;
        while (more) {
            System.out.println("Welcome to your dashboard " + owner.getName());
            System.out.println(
                    "R)egister Property  V)iew All Properties  P)ay Tax PR)evious Payments B)alancing Statement  L)ogout");
            String command = in.nextLine().toUpperCase();

            if (command.equals("R")) {
                register();
            } else if (command.equals("V")) {
                view();
            } else if (command.equals("P")) {
                pay();
            } else if (command.equals("PR")) {
                prevPayments();
            } else if (command.equals("B")) {
                balStatement();
            } else if (command.equals("L")) {
                more = false;
            }
        }
    }

    public void register() {
        System.out.println("Register Property Menu");
        ArrayList<String> additionalOwners = new ArrayList<>();
        System.out.println("Are there any additional owners? y/n");

        String ans = in.nextLine().trim().toUpperCase();
        while (ans.equals("Y")) {
            System.out.println("What is their PPS number? 1 at a time");
            String pps = in.nextLine();
            if (sm.isValidppsNum(pps) && sm.ownerExists(pps)) {
                additionalOwners.add(pps);
            }
            System.out.println("Are there any additional owners? y/n");
            ans = in.nextLine().trim().toUpperCase();
        }
        System.out.println("What is the address? Please print in a single line");
        String address = in.nextLine();
        System.out.println("What is the eircode?");
        String eircode = in.nextLine().trim().toUpperCase();
        if (!sm.isValidEircode(eircode)) {
            System.out.println("Not a valid Eircode!");
        } else {
            System.out.println("What is the estimated Market value? Only input digit characters");
            String estimatedMarketValue = in.nextLine();

            String locationCategory = "";

            boolean more = true;

            while (more) {
                more = false;
                System.out.println("What is the location category");
                System.out.println("CI)ty L)arge Town S)mall Town V)illage CO)untryside");
                String command = in.nextLine().toUpperCase();

                if (command.equals("CI")) {
                    locationCategory = "City";

                } else if (command.equals("L")) {
                    locationCategory = "Large Town";
                } else if (command.equals("S")) {
                    locationCategory = "Small Town";
                } else if (command.equals("V")) {
                    locationCategory = "Village";
                } else if (command.equals("CO")) {
                    locationCategory = "Countryside";
                } else {
                    more = true;
                }
            }
            System.out.println("Is this the principal private residence? y/n");
            ans = in.nextLine().trim().toUpperCase();
            boolean isPrincipalPrivateResidence = false;
            if (ans.equals("Y")) {
                isPrincipalPrivateResidence = true;
            }
            System.out.println("");
            System.out.println("Review of inputted data");
            System.out.println("Additional Owners: " + additionalOwners);
            System.out.println("Address: " + address);
            System.out.println("Eircode: " + eircode);
            System.out.println("Estimated Market Value: " + estimatedMarketValue);
            System.out.println("Location Category: " + locationCategory);
            System.out.println("Is principal private residence: " + isPrincipalPrivateResidence);
            System.out.println("");
            System.out.println("Would you like to register this property? y/n");
            ans = in.nextLine().trim().toUpperCase();
            if (ans.equals("Y")) {
                System.out.println("Property Registered");
                int year = Calendar.getInstance().get(Calendar.YEAR);
                StringBuilder sb = new StringBuilder();
                sb.append(owner.getPpsNum());
                for (String s : additionalOwners) {
                    sb.append(", ");
                    sb.append(s);
                }
                sm.registerProperty(year, sb.toString(), address, eircode, estimatedMarketValue, locationCategory,
                        isPrincipalPrivateResidence);
            }
        }
    }

    public void view() {
        System.out.println("View Property Screen");
        ArrayList<String> properties = sm.getOwnerPropertiesEircodes(owner.getPpsNum());
        System.out.println(Arrays.deepToString(properties.toArray()));
        System.out.println("Please enter the Eircode of the property you wish to view the details of.");
        String eircode = in.nextLine().trim().toUpperCase();
        // Do a check to return a balance for a specific eircode
        if (!sm.isValidEircode(eircode)) {
            System.out.println("Not a valid Eircode!");
        } else {
            if (eircode == null) {
                System.out.println("No Eircode Entered.");
            } else if (properties.contains(eircode)) {
                Property p = sm.getPropertyData(eircode);
                String txtEircode = p.getEircode();
                String txtAddrs = p.getAddress();
                String txtLocationCategory = p.getLocationCatgeory();
                String txtEstMarketValue = p.getEstimatedMarketValue();
                System.out.println("Eircode: " + txtEircode + "\nAddress: " + txtAddrs + "\nLocation Category: "
                        + txtLocationCategory + "\nEstimated Market Value: " + txtEstMarketValue);
            } else {
                System.out.println("Eircode not listed!");
            }
        }
    }

    public void pay() {
        System.out.println("Pay Tax Screen");
        ArrayList<String> properties = sm.getOwnerPropertiesEircodes(owner.getPpsNum());
        System.out.println(Arrays.deepToString(properties.toArray()));
        System.out.println("Please enter the Eircode of the property you wish to pay tax for.");
        String eircode = in.nextLine().trim().toUpperCase();
        if (!sm.isValidEircode(eircode)) {
            System.out.println("Not a valid Eircode!");
        } else {
            LocalDateTime year = LocalDateTime.now();
            // Do a check to return a balance for a specific eircode
            if (eircode == null) {
                System.out.println("No Eircode Entered.");
            } else if (properties.contains(eircode)) {
                double bal = sm.calculateTax(eircode);
                System.out.println("Balance Due: " + bal);
                System.out.println("Pay balance? y/n");
                String ans = in.nextLine().trim().toLowerCase();
                if (ans.equals("y")) {
                    System.out.println("Balance paid!");
                    sm.makePayment(year.getYear(), eircode);
                } else {
                    System.out.println("Balance not paid!");
                }
            } else {
                System.out.println("Eircode not listed!");
            }
        }
    }

    public void prevPayments() {
        System.out.println("Previous Payments");
        ArrayList<Record> paidRecords = new ArrayList<Record>();
        ArrayList<String> eircodes = sm.getOwnerPropertiesEircodes(owner.getPpsNum());
        for (String eircode : eircodes) {
            ArrayList<Record> recordsTemp = sm.getPaymentRecords(eircode);
            for (Record r : recordsTemp) {
                if (r.getPaymentStatus().equalsIgnoreCase("paid")) {
                    paidRecords.add(r);
                }
            }
        }
        if (paidRecords.size() == 0) {
            System.out.println("You have no payment records");
        }
        for (int i = 0; i < paidRecords.size(); i++) {
            Record r = paidRecords.get(i);
            System.out.println("Record " + (i + 1));
            System.out.println("Eircode: " + r.getEircode());
            System.out.println("Status: " + r.getPaymentStatus());
            System.out.println("Tax Amount: " + r.getTaxAmount());
            System.out.println("Year of Payment: " + r.getYear());
        }
    }

    public void balStatement() {
        System.out.println("Balancing Statement");
        ArrayList<Record> unpaidRecords = new ArrayList<Record>();
        ArrayList<String> eircodes = sm.getOwnerPropertiesEircodes(owner.getPpsNum());
        for (String eircode : eircodes) {
            ArrayList<Record> recordsTemp = sm.getPaymentRecords(eircode);
            for (Record r : recordsTemp) {
                if (!r.getPaymentStatus().equalsIgnoreCase("paid")) {
                    unpaidRecords.add(r);
                }
            }
        }
        if (unpaidRecords.size() == 0) {
            System.out.println("You have no outstanding tax.");
        }
        for (int i = 0; i < unpaidRecords.size(); i++) {
            Record r = unpaidRecords.get(i);
            System.out.println("Record " + (i + 1));
            System.out.println("Eircode: " + r.getEircode());
            System.out.println("Status: " + r.getPaymentStatus());
            System.out.println("Tax Amount: " + r.getTaxAmount());
        }
    }

    
    /** 
     * @throws IOException
     */
    // Department Side
    public void depLogin() throws IOException {
        System.out.println("Department Login Menu");
        System.out.println("Enter your Worker ID: ");
        String wid = in.nextLine().trim().toUpperCase();
        if (!sm.isValidWID(wid)) {
            System.out.println("This is not a valid Worker ID");
        } else if (!sm.employeeExists(wid)) {
            System.out.println(
                    "This Worker ID number is not associated with an account. Would you like to register? y/n");
            String ans = in.nextLine().trim().toLowerCase();
            if (ans.equals("y")) {
                depSignup();
            } else {
                run();
            }
        }
        System.out.println("Enter your password");
        String password = in.nextLine();

        while (!sm.depLoginVerification(wid, password)) {
            System.out.println("This password is incorrect. Would you like to try again? y/n");
            String ans = in.nextLine().trim().toLowerCase();
            if (!ans.equals("y")) {
                run();
            }
            password = in.nextLine();
        }
        employee = sm.getEmployee(wid);

        depDash();
    }

    public void depSignup() {
        System.out.println("Department Registration Menu");
        System.out.println("Enter your Work ID: ");
        String wid = in.nextLine().trim().toUpperCase();
        if (sm.employeeExists(wid)) {
            System.out.println("User already exists! Please log in!");
        } else if (!sm.isValidWID(wid)) {
            System.out.println("This is not a valid Worker ID number");
        } else {
            System.out.println("Enter your name");
            String name = in.nextLine().trim();
            System.out.println("Enter your password");
            String password = in.nextLine().trim();
            if (sm.isValidPassword(password)) {
                sm.registerEmployee(name, wid, password);
                System.out.println("You have registered successfully!");
                employee = sm.getEmployee(wid);
                depDash();
            } else {
                System.out.println("Your password is invalid please try again.");
            }
        }
    }

    public void depDash() {
        boolean more = true;

        while (more) {
            System.out.println("Welcome to your work dashboard " + employee.getName());
            System.out.println("O)verdue Properties T)ax Statistics by Area  P)roperty Tax Payment Data L)ogout");
            String command = in.nextLine().toUpperCase();

            if (command.equals("O")) {
                viewOverdueProp();
            } else if (command.equals("T")) {
                PropTaxStats();
            } else if (command.equals("P")) {
                ViewPropTaxData();
            } else if (command.equals("L")) {
                more = false;
            }
        }
    }

    public void viewOverdueProp() {
        System.out.println("Overdue Properties");
        ArrayList<Record> unpaidRecords = new ArrayList<Record>();
        System.out.println("Please enter an Eircode Prefix e.g. V42");
        String eirRoutingKey = in.nextLine().trim();
        ArrayList<Record> overdueProperties = sm.getAllOverDueProps(eirRoutingKey);
        for (Record r : overdueProperties) {
            if (!r.getPaymentStatus().equalsIgnoreCase("paid")) {
                unpaidRecords.add(r);
            }
            if (unpaidRecords.size() == 0) {
                System.out.println("There are no Overdue Properties.");
            } else {
                for (int i = 0; i < unpaidRecords.size(); i++) {
                    Record k = unpaidRecords.get(i);
                    System.out.println("Record " + (i + 1));
                    System.out.println("Eircode: " + k.getEircode());
                    System.out.println("Status: " + k.getPaymentStatus());
                    System.out.println("Tax Amount: " + k.getTaxAmount());
                }
            }
        }

    }

    public void PropTaxStats() {
        System.out.println("Property Tax Statistics By Area");
        System.out.println("Please enter the Eircode Routing Key of the area you wish to view the statistics of:");
        String eirRoutingKey = in.nextLine().trim();
        double[] taxStats = sm.getTaxStats(eirRoutingKey);
        NumberFormat nf = new DecimalFormat("0.#");
        System.out.println(taxStats);
        for (double stats : taxStats) {
            System.out.println(nf.format(stats));
        }
    }

    public void ViewPropTaxData() {
        System.out.println("Property Tax Payment Data");
        System.out.println("Please enter Eircode Routing key");
        String eircodeRoutKey = in.nextLine().trim();
        double[] taxStats = sm.getTaxStats(eircodeRoutKey);

    }

}