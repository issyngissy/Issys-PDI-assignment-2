public class Main {

    Private static String final CSV_FILE = "data.csv"            //set CSV_FILE to "data.csv"

    public static void Main (String[] args) {
        Logger.open();                                           //open logger
                                           
        SensorArray readings = Filemanager.loadCSV(CSV_File);     //SensorArray is used to allow SensorReadings()'s size be customisable

        Menu menu = new Menu(readings);                           //bundle all the CSV info into "readings" - pass as params
        menu.run();

        Logger.close()
    }
}



