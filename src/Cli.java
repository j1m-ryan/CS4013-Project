import java.io.IOException;

public class Cli {
    
    /** 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CliMenu menu = new CliMenu();
        menu.run();
    }
}