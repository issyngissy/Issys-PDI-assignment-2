public class Main {

    private static final String CSV_FILE = "data.csv";           //set CSV_FILE to "data.csv"

    public static void main(String[] args) {
        Logger.open();                                           //open logger
                                           
        SensorArray readings = GreenhouseDataStorage.loadCSV(CSV_FILE);   //SensorArray is used to allow SensorReadings()'s size be customisable

        Menu menu = new Menu(readings);                           //bundle all the CSV info into "readings" - pass as params
        menu.run();

        Logger.close();
    }
}



