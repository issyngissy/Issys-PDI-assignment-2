// 1.0: The program starts in this Main class.
public class Main {

    // 1.1: This is the CSV file the program loads from and saves to.
    private static final String CSV_FILE = "data.csv";

    // 1.2: main() begins running here.
    public static void main(String[] args) {
        // 1.2.1: Briefly go into Logger.open() so the log file is ready.
        Logger.open();
                                           
        // 1.3: Go into GreenhouseDataStorage.loadCSV() to load the saved readings.
        // 1.3.1: The next main step is 1.4 in GreenhouseDataStorage.java.
        SensorArray readings = GreenhouseDataStorage.loadCSV(CSV_FILE);

        // 1.22: Back in Main after GreenhouseDataStorage.loadCSV() has returned the "readings".
        // 1.23: Go into the Menu class with the readings and the CSV file name.
        Menu menu = new Menu(readings, CSV_FILE);

        // 1.24: Back in Main, then go into Menu.run() so the user can choose what to do.
        menu.run();

        // 3.0: Back in Main after the menu exits, then close the logger.
        Logger.close();
    }
}
