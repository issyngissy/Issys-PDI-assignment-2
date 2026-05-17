public class Main {

    private static final String CSV_FILE = "data.csv";

    public static void main(String[] args) {
        Logger.open();
                                           
        SensorArray loadedReadings = GreenhouseDataStorage.loadCSV(CSV_FILE);

        // Pass the loaded readings into the menu so the user can work with them.
        Menu menu = new Menu(loadedReadings, CSV_FILE);

        menu.run();

        Logger.close();
    }
}
