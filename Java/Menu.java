import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Handles all user menu input and sends work to the other classes.
public class Menu {

    private BufferedReader consoleInputReader;
    private SensorArray loadedReadings;
    private String csvFilename;

    public Menu(SensorArray loadedReadings, String csvFilename) {
        this.loadedReadings = loadedReadings;
        this.csvFilename = csvFilename;
        this.consoleInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        boolean menuIsRunning = true;
        while (menuIsRunning == true) {
            showMainMenu();
            int userChoice = readIntegerInRange("choice: ", 1, 6);

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
    }

    private void showMainMenu() {
        Logger.logBlank();
        Logger.log("Main menu");
        Logger.log("1. overall statistics");
        Logger.log("2. stats by zone");
        Logger.log("3. stats by sensor type");
        Logger.log("4. add a reading");
        Logger.log("5. delete a reading");
        Logger.log("6. exit");
    }



    // -------------------menu option 1---------------------
    private void handleOverallStats() {
        showStatsSubMenu(loadedReadings, "entire greenhouse");
    }



    // -------------------menu option 2---------------------
    private void handleStatsByZone() {
        String selectedZone = readValidZone();

        SensorArray filteredReadings = loadedReadings.filterByZone(selectedZone);
        showStatsSubMenu(filteredReadings, "zone " + selectedZone);
    }

    private String readValidZone() {
        String[] zones = {"ZoneA", "ZoneB", "ZoneC"};

        Logger.logBlank();
        Logger.log("Select zone");
        for (int i = 0; i < zones.length; i++) {
            Logger.log((i + 1) + ". " + zones[i]);
        }

        int userSelection = readIntegerInRange("zone: ", 1, zones.length);
        return zones[userSelection - 1];
    }



    // -------------------menu option 3---------------------
    private void handleStatsByType() {
        String selectedSensorType = readValidSensorType();

        SensorArray filteredReadings = loadedReadings.filterBySensorType(selectedSensorType);
        showStatsSubMenu(filteredReadings, "type " + selectedSensorType);
    }

    private String readValidSensorType() {
        String[] sensorTypes = {"temperature", "humidity", "soilMoisture", "light"};

        Logger.logBlank();
        Logger.log("Select sensor type");
        for (int i = 0; i < sensorTypes.length; i++) {
            Logger.log((i + 1) + ". " + sensorTypes[i]);
        }

        int userSelection = readIntegerInRange("type: ", 1, sensorTypes.length);
        return sensorTypes[userSelection - 1];
    }



    // -------------------menu option 4---------------------
    private void handleAddReading() {
        Logger.logBlank();
        Logger.log("Add new reading");

        String sensorID = readNonEmptyString("sensor ID: ");
        while (loadedReadings.hasSensorID(sensorID) == true) {
            Logger.log("That sensor ID is already in the data.");
            sensorID = readNonEmptyString("sensor ID: ");
        }

        String sensorType = readValidSensorType();
        String zone = readValidZone();
        double sensorValue = readDoubleNumber("value: ");

        int day = readIntegerInRange("day (1-31): ", 1, 31);
        int month = readIntegerInRange("month (1-12): ", 1, 12);
        int year = readIntegerInRange("year (2000-2100): ", 2000, 2100);
        int hour = readIntegerInRange("hour (0-23): ", 0, 23);
        int minute = readIntegerInRange("minute (0-59): ", 0, 59);

        Timestamp newReadingTimestamp = new Timestamp(day, month, year, hour, minute);
        SensorReading newReading = new SensorReading(sensorID, sensorType, zone, sensorValue, newReadingTimestamp);

        loadedReadings.add(newReading);
        Logger.log("Added: " + newReading);
        GreenhouseDataStorage.saveCSV(csvFilename, loadedReadings);
    }



    // -------------------menu option 5---------------------
    private void handleDeleteReading() {
        if (loadedReadings.getCount() == 0) {
            Logger.log("No readings to delete.");
        } else {
            Logger.logBlank();
            Logger.log("Current readings");
            for (int i = 0; i < loadedReadings.getCount(); i++) {
                Logger.log((i + 1) + ". " + loadedReadings.get(i));
            }

            int userSelection = readIntegerInRange("delete number: ", 1, loadedReadings.getCount());
            SensorReading removedReading = loadedReadings.get(userSelection - 1);
            loadedReadings.deleteAt(userSelection - 1);

            Logger.log("Deleted: " + removedReading);

            // Re-save after deletion so the row is removed from data.csv.
            GreenhouseDataStorage.saveCSV(csvFilename, loadedReadings);
        }
    }



    // -------------------statistics submenu---------------------
    private void showStatsSubMenu(SensorArray filteredReadings, String contextLabel) {
        Logger.logBlank();
        Logger.log("Stats for " + contextLabel);
        Logger.log("1. total readings");
        Logger.log("2. average value");
        Logger.log("3. minimum value");
        Logger.log("4. maximum value");
        Logger.log("5. number outside safe range");
        Logger.log("6. percent outside safe range");
        Logger.log("7. all of the above");

        int userChoice = readIntegerInRange("stat: ", 1, 7);
        Logger.logBlank();

        if (userChoice == 1) {
            Logger.log("Total: " + Math.totalReadings(filteredReadings));
        } else if (userChoice == 2) {
            Logger.log("Average: " + Math.average(filteredReadings));
        } else if (userChoice == 3) {
            Logger.log("Minimum: " + Math.minimum(filteredReadings));
        } else if (userChoice == 4) {
            Logger.log("Maximum: " + Math.maximum(filteredReadings));
        } else if (userChoice == 5) {
            Logger.log("Number outside safe range: " + Math.outOfRangeCount(filteredReadings));
        } else if (userChoice == 6) {
            Logger.log("Percent outside safe range: " + Math.outOfRangePercent(filteredReadings));
        } else if (userChoice == 7) {
            Logger.log("Total: " + Math.totalReadings(filteredReadings));
            Logger.log("Average: " + Math.average(filteredReadings));
            Logger.log("Minimum: " + Math.minimum(filteredReadings));
            Logger.log("Maximum: " + Math.maximum(filteredReadings));
            Logger.log("Number outside safe range: " + Math.outOfRangeCount(filteredReadings));
            Logger.log("Percent outside safe range: " + Math.outOfRangePercent(filteredReadings));
        }
    }


    
    // -------------------input helpers---------------------
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
                    Logger.log("Enter a number between " + minimumAllowedValue + " and " + maximumAllowedValue + ".");
                }
            } else {
                Logger.log("Enter a number between " + minimumAllowedValue + " and " + maximumAllowedValue + ".");
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
                Logger.log("Enter a number.");
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
                Logger.log("This cannot be empty.");
            }
        }
        return validatedString;
    }
}
