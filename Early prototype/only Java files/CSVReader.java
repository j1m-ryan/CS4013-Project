import java.util.ArrayList;

import org.w3c.dom.css.CSSCharsetRule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class CSVReader{

    public CSVReader(){
        // do nothing
    }
    
    // reading program to read the csv files lines ie helper program
    public ArrayList<String> readLinesFromFile(String fileLocation) throws IOException{
                
        // open file input stream
        BufferedReader reader = new BufferedReader(new FileReader(fileLocation));

        // store all read lines in this arraylist
        ArrayList<String> readLines = new ArrayList<String>();

        // read file line by line
        String line = null;
        // line below makes program skip first line when reading csv files as they contains description 
        // about the stored data
        line = reader.readLine();        
        while ((line = reader.readLine()) != null) {
            readLines.add(line);
        }

        reader.close();
        
        return readLines;
    }
}