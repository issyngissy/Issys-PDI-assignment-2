// 1.2.1.1: These imports are used when Main sends the program into Logger.open().
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// 1.2.1.2: The program enters the Logger class from Main step 1.2.1.
public class Logger {

    // 1.2.1.3: This is the file where the program records the session output.
    private static final String LOG_FILE_NAME = "logged_data";

    // 1.2.1.4: This stays null if the log file cannot be opened.
    private static BufferedWriter logFileWriter = null;

    // 1.2.1.5: Logger.open() starts here.
    public static void open() {
        try {
            // 1.2.1.6: Open the log file in append mode so previous sessions are not deleted.
            logFileWriter = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));

            // 1.2.1.7: Mark where a new run of the program starts in the log.
            logFileWriter.write("starting session...");
            logFileWriter.newLine();
            logFileWriter.flush();
        } catch (Exception fileOpenError) {
            // 1.2.1.8: If the log file fails, the program still runs without file logging.
            System.out.println("could not open log file...");

            // 1.2.1.9: Keep this null so log() knows there is no open file to write to.
            logFileWriter = null;
        }
    }

    // 1.24.3.2: Logger.log() is entered whenever the menu needs to print a message.
    public static void log(String messageToLog) {
        // 1.24.3.3: Always show the message on the screen.
        System.out.println(messageToLog);

        // 1.24.3.4: Only try writing to the file if it opened properly.
        if (logFileWriter != null) {
            try {
                logFileWriter.write(messageToLog);
                logFileWriter.newLine();
                logFileWriter.flush();
            } catch (IOException fileWriteError) {
                // 1.24.3.5: Ignore this so the menu can keep running even if the log fails.
            }
        }
    }

    // 1.24.3.1: Logger.logBlank() is entered when the menu wants a blank line.
    public static void logBlank() {
        log("");
    }

    // 3.1: Logger.close() starts here after Main step 3.0.
    public static void close() {
        if (logFileWriter != null) {
            try {
                // 3.2: Add a small ending note so the log file is easier to read later.
                logFileWriter.write("ending session");
                logFileWriter.newLine();
                logFileWriter.newLine();
                logFileWriter.flush();
                logFileWriter.close();
            } catch (IOException fileCloseError) {
                // 3.3: Ignore this because there is nothing useful to recover here.
            }
        }
    }
}
