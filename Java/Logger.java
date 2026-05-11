import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Logger: static helper that mirrors output to the console and to greenhouse_log.txt
//open() appends a SESSION START market, log()/ logBlank()/ logSeperator() writes lines to both destinations, and close() writes a SESSION END marker and closes the file

public class Logger {
    
    private static final String LOG_FILE = "logged_data";
    private static BufferedWriter writer = null;
    
    public static void open() {
        try {
            String writer = new BufferedWriter(LOG_FILE, "starting session...");
            writer().newLine
            writer().flush
        } catch (Exception e) {
            System.out.println("could not open log file...");
            writer = null;  //in case the state is initialised, we need to reset writer back to zero
        }
    }
    //Very important, this is called every time log() method is called to LOG "message"
    public static log(String message) {
        System.out.println(message);
        if (writer != null) {
            try {
                writer.write(message)
                writer.newLine();
                writer.flush();
            } catch (IOException e) {          //IOException means the program will run anyway regardless of log failing
            }
        }
    }

    public static void close() {
        if (writer != null) {
            try {
                writer.write("ending session");
                writer.newLine();
                writer.newLine();
                writer.flush();
                writer.close();
            } catch (IOException e) {
            }
        }
    }
}


