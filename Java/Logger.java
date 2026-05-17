import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Logger prints messages to the screen and also writes them to the log file.
public class Logger {

    private static final String LOG_FILE_NAME = "logged_data";

    private static BufferedWriter logFileWriter = null;

    public static void open() {
        try {
            // Append mode keeps previous runs in the same log file.
            logFileWriter = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));

            logFileWriter.write("starting session...");
            logFileWriter.newLine();
            logFileWriter.flush();
        } catch (Exception fileOpenError) {
            System.out.println("could not open log file...");
            logFileWriter = null;
        }
    }


    // Print to the console first, then try to write the same message to the log.
    public static void log(String messageToLog) {
        System.out.println(messageToLog);


        
        if (logFileWriter != null) {
            try {
                logFileWriter.write(messageToLog);
                logFileWriter.newLine();
                logFileWriter.flush();
            } catch (IOException fileWriteError) {
                // Keep the program running even if logging fails.
            }
        }
    }

    public static void logBlank() {
        log("");
    }

    public static void close() {
        if (logFileWriter != null) {
            try {
                logFileWriter.write("ending session");
                logFileWriter.newLine();
                logFileWriter.newLine();
                logFileWriter.flush();
                logFileWriter.close();
            } catch (IOException fileCloseError) {
                // Nothing useful to recover here.
            }
        }
    }
}
