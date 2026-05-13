import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Menu: holds the loaded SensorArray and a BufferedReader on System.in. run() loops printing the 6 option menu
// and dispatches the user's choice to handle overall stats, stats by zone, stats by sensor type, add, delete, or exit.
// Add/delete mutate the SensorArray and persist via GreenhouseDataStorage.saveCSV

public class Menu {

    private static final String CSV_FILE = "data.csv";

    private BufferedReader consoleInputReader;
    private SensorArray loadedReadings;

    public Menu(SensorArray loadedReadings) {
        this.loadedReadings = loadedReadings;
        this.consoleInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        showStartupBanner();

        boolean menuIsRunning = true;
        while (menuIsRunning == true) {
            showMainMenu();
            int userChoice = readIntegerInRange(" choice >> ", 1, 6);

            if (userChoice == 1) {
                handleOverallStats();
            } else if (userChoice == 2) {
                handleStatsByZone();
            } else if (userChoice == 3) {
                handleStatsByType();
            } else if (userChoice == 4) {
                handleAddReading();
            } else if (userChoice == 5) {
                handleDeleteReading();
            } else if (userChoice == 6) {
                menuIsRunning = false;
            }
        }

        showGoodbyeBanner();
    }

    private void showStartupBanner() {
        Logger.logBlank();
        Logger.log("   ____                     _                          ");
        Logger.log("  / ___|_ __ ___  ___ _ __ | |__   ___  _   _ ___  ___ ");
        Logger.log(" | |  _| '__/ _ \\/ _ \\ '_ \\| '_ \\ / _ \\| | | / __|/ _ \\");
        Logger.log(" | |_| | | |  __/  __/ | | | | | | | (_) | |_| \\__ \\  __/");
        Logger.log("  \\____|_|  \\___|\\___|_| |_|_| |_|\\___/ \\__,_|___/\\___|");
        Logger.logBlank();
        Logger.log("            ~~  monitoring system  v1.0  ~~");
        Logger.log("               ::  by issy  ::  2026  ::");
        Logger.logBlank();
    }

    private void showGoodbyeBanner() {
        Logger.logBlank();
        Logger.log("============================================================");
        Logger.log("    >>  session ended  --  thanks for tending the plants  <<");
        Logger.log("============================================================");
        Logger.logBlank();
    }

    private void showMainMenu() {
        Logger.logBlank();
        Logger.log("============================================================");
        Logger.log("==        SMART GREENHOUSE  ::  MAIN MENU                 ==");
        Logger.log("============================================================");
        Logger.log("    [1]  overall statistics");
        Logger.log("    [2]  stats by zone");
        Logger.log("    [3]  stats by sensor type");
        Logger.log("    [4]  add a reading");
        Logger.log("    [5]  delete a reading");
        Logger.log("    [6]  exit");
        Logger.log("------------------------------------------------------------");
    }

    private void handleOverallStats() {
        showStatsSubMenu(loadedReadings, "entire greenhouse");
    }

    private void handleStatsByZone() {
        String[] availableZones = loadedReadings.getDistinctZones();
        if (availableZones.length == 0) {
            Logger.log("  !! no zones available.");
        } else {
            Logger.logBlank();
            Logger.log("  :: available zones");
            Logger.log("  ------------------");
            for (int i = 0; i < availableZones.length; i++) {
                Logger.log("    [" + (i + 1) + "]  " + availableZones[i]);
            }
            int userSelection = readIntegerInRange(" zone >> ", 1, availableZones.length);
            String selectedZone = availableZones[userSelection - 1];
            SensorArray filteredReadings = loadedReadings.filterByZone(selectedZone);
            showStatsSubMenu(filteredReadings, "zone " + selectedZone);
        }
    }

    private void handleStatsByType() {
        String[] availableSensorTypes = loadedReadings.getDistinctTypes();
        if (availableSensorTypes.length == 0) {
            Logger.log("  !! no sensor types available.");
        } else {
            Logger.logBlank();
            Logger.log("  :: available sensor types");
            Logger.log("  -------------------------");
            for (int i = 0; i < availableSensorTypes.length; i++) {
                Logger.log("    [" + (i + 1) + "]  " + availableSensorTypes[i]);
            }
            int userSelection = readIntegerInRange(" type >> ", 1, availableSensorTypes.length);
            String selectedSensorType = availableSensorTypes[userSelection - 1];
            SensorArray filteredReadings = loadedReadings.filterBySensorType(selectedSensorType);
            showStatsSubMenu(filteredReadings, "type " + selectedSensorType);
        }
    }

    private void handleAddReading() {
        Logger.logBlank();
        Logger.log("  ::  add new reading  ::");
        Logger.log("  ------------------------");
        String sensorID = readNonEmptyString(" sensor ID >> ");
        String sensorType = readValidSensorType();
        String zone = readNonEmptyString(" zone >> ");
        double sensorValue = readDoubleNumber(" value >> ");
        int day = readIntegerInRange(" day  (1-31)      >> ", 1, 31);
        int month = readIntegerInRange(" month (1-12)     >> ", 1, 12);
        int year = readIntegerInRange(" year  (2000-2100) >> ", 2000, 2100);
        int hour = readIntegerInRange(" hour  (0-23)     >> ", 0, 23);
        int minute = readIntegerInRange(" minute (0-59)    >> ", 0, 59);

        Timestamp newReadingTimestamp = new Timestamp(day, month, year, hour, minute);
        SensorReading newReading = new SensorReading(sensorID, sensorType, zone, sensorValue, newReadingTimestamp);
        loadedReadings.add(newReading);
        GreenhouseDataStorage.saveCSV(CSV_FILE, loadedReadings);
        Logger.log("  ++ added: " + newReading);
    }

    private void handleDeleteReading() {
        if (loadedReadings.getCount() == 0) {
            Logger.log("  !! no readings to delete.");
        } else {
            Logger.logBlank();
            Logger.log("  ::  current readings  ::");
            Logger.log("  ------------------------");
            for (int i = 0; i < loadedReadings.getCount(); i++) {
                Logger.log("    [" + (i + 1) + "]  " + loadedReadings.get(i));
            }
            int userSelection = readIntegerInRange(" delete # >> ", 1, loadedReadings.getCount());
            SensorReading removedReading = loadedReadings.get(userSelection - 1);
            loadedReadings.deleteAt(userSelection - 1);
            GreenhouseDataStorage.saveCSV(CSV_FILE, loadedReadings);
            Logger.log("  -- deleted: " + removedReading);
        }
    }

    private void showStatsSubMenu(SensorArray filteredReadings, String contextLabel) {
        Logger.logBlank();
        Logger.log("  ::  stats for [" + contextLabel + "]");
        Logger.log("  ----------------------------------------");
        Logger.log("    [1]  total readings");
        Logger.log("    [2]  average value");
        Logger.log("    [3]  minimum value");
        Logger.log("    [4]  maximum value");
        Logger.log("    [5]  # outside safe range");
        Logger.log("    [6]  % outside safe range");
        Logger.log("    [7]  all of the above");
        Logger.log("  ----------------------------------------");

        int userChoice = readIntegerInRange(" stat >> ", 1, 7);
        Logger.logBlank();

        if (userChoice == 1) {
            Logger.log("    total ............ " + filteredReadings.totalReadings());
        } else if (userChoice == 2) {
            Logger.log("    average .......... " + filteredReadings.average());
        } else if (userChoice == 3) {
            Logger.log("    minimum .......... " + filteredReadings.minimum());
        } else if (userChoice == 4) {
            Logger.log("    maximum .......... " + filteredReadings.maximum());
        } else if (userChoice == 5) {
            Logger.log("    out of range # ... " + filteredReadings.outOfRangeCount());
        } else if (userChoice == 6) {
            Logger.log("    out of range % ... " + filteredReadings.outOfRangePercent());
        } else if (userChoice == 7) {
            Logger.log("    total ............ " + filteredReadings.totalReadings());
            Logger.log("    average .......... " + filteredReadings.average());
            Logger.log("    minimum .......... " + filteredReadings.minimum());
            Logger.log("    maximum .......... " + filteredReadings.maximum());
            Logger.log("    out of range # ... " + filteredReadings.outOfRangeCount());
            Logger.log("    out of range % ... " + filteredReadings.outOfRangePercent());
        }
    }

    private String readLineFromConsole(String promptToShow) {
        System.out.print(promptToShow);
        String trimmedInputLine = "";
        try {
            String rawInputLine = consoleInputReader.readLine();
            if (rawInputLine == null) {
                trimmedInputLine = "";
            } else {
                trimmedInputLine = rawInputLine.trim();
            }
        } catch (IOException inputError) {
            trimmedInputLine = "";
        }
        return trimmedInputLine;
    }

    private int readIntegerInRange(String promptToShow, int minimumAllowedValue, int maximumAllowedValue) {
        int validatedInteger = 0;
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            String userInputString = readLineFromConsole(promptToShow);
            if (Validator.isInteger(userInputString) == true) {
                int parsedInteger = Integer.parseInt(userInputString);
                if (parsedInteger >= minimumAllowedValue && parsedInteger <= maximumAllowedValue) {
                    validatedInteger = parsedInteger;
                    inputAccepted = true;
                } else {
                    Logger.log("  !! integer must be between " + minimumAllowedValue + " and " + maximumAllowedValue + ".");
                }
            } else {
                Logger.log("  !! integer must be between " + minimumAllowedValue + " and " + maximumAllowedValue + ".");
            }
        }
        return validatedInteger;
    }

    private double readDoubleNumber(String promptToShow) {
        double validatedDouble = 0.0;
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            String userInputString = readLineFromConsole(promptToShow);
            if (Validator.isNumeric(userInputString) == true) {
                validatedDouble = Double.parseDouble(userInputString);
                inputAccepted = true;
            } else {
                Logger.log("  !! must be a number.");
            }
        }
        return validatedDouble;
    }

    private String readNonEmptyString(String promptToShow) {
        String validatedString = "";
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            String userInputString = readLineFromConsole(promptToShow);
            if (userInputString.length() > 0) {
                validatedString = userInputString;
                inputAccepted = true;
            } else {
                Logger.log("  !! cannot be empty.");
            }
        }
        return validatedString;
    }

    private String readValidSensorType() {
        String validatedSensorType = "";
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            String userInputString = readLineFromConsole(" type (temperature/humidity/soilMoisture/light) >> ");
            if (Validator.isValidSensorType(userInputString) == true) {
                validatedSensorType = userInputString;
                inputAccepted = true;
            } else {
                Logger.log("  !! unknown sensor type.");
            }
        }
        return validatedSensorType;
    }
}
