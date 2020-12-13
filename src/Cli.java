/**
 * A Class to run a Command Line menu
 * @version Final
 */
import java.io.IOException;
public class Cli {
    
    /** 
     * Method to launch the CliMenu
     * @param args Takes in arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CliMenu menu = new CliMenu();
        menu.run();
    }
}