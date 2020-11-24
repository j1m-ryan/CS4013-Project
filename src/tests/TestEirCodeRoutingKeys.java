package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class TestEirCodeRoutingKeys {
    public static void main(String[] args) throws IOException {
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

        // close reader
        System.out.println(
                "The V95 is the rout code for ennis: " + (eircodeToLocation.get("V95").equalsIgnoreCase("Ennis")));
        reader.close();

    }

}
