import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Logger: static helper that mirrors output to the console and to greenhouse_log.txt
// open() appends a SESSION START marker, log()/logBlank()/logSeparator() writes
// lines to both destinations, and close() writes a SESSION END marker and closes the file.

public class Logger {

    private static final String LOG_FILE_NAME = "logged_data";
    private static BufferedWriter logFileWriter = null;

    public static void open() {
        try {
            logFileWriter = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));
            logFileWriter.write("starting session...");
            logFileWriter.newLine();
            logFileWriter.flush();
        } catch (Exception fileOpenError) {
            System.out.println("could not open log file...");
            // reset logFileWriter back to null so log() knows the file isn't open
            logFileWriter = null;
        }
    }

    // Called every time we want to write a message to the console and the log file.
    public static void log(String messageToLog) {
        System.out.println(messageToLog);
        if (logFileWriter != null) {
            try {
                logFileWriter.write(messageToLog);
                logFileWriter.newLine();
                logFileWriter.flush();
            } catch (IOException fileWriteError) {
                // ignore — the program should keep running even if logging fails
            }
        }
    }

    public static void logBlank() {
        log("");
    }

    public static void logSeparator() {
        log("----------------------------------------");
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
                // ignore — nothing useful we can do if closing fails
            }
        }
    }
}
