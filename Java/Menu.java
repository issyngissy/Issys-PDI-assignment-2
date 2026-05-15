// 1.23.1: These imports let the Menu class read typed input from the user.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1.23.2: The program enters the Menu class from Main step 1.23.
public class Menu {

    // 1.23.4: The menu keeps its own console reader and the readings currently loaded.
    private BufferedReader consoleInputReader;
    private SensorArray loadedReadings;
    private String csvFilename;

    // 1.23.5: Build the Menu object using the readings from Main.
    public Menu(SensorArray loadedReadings, String csvFilename) {
        this.loadedReadings = loadedReadings;
        this.csvFilename = csvFilename;
        this.consoleInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // 1.24.1: Menu.run() starts after Main step 1.24.
    public void run() {
        // 1.24.2: This flag keeps the menu running until the user chooses exit.
        boolean menuIsRunning = true;
        while (menuIsRunning == true) {
            // 1.24.3: Go into showMainMenu(), then ask for one valid menu number.
            showMainMenu();
            int userChoice = readIntegerInRange("choice: ", 1, 6);

            // 1.24.4: Send the user's choice to the correct menu action.
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
                // -------------------menu option 6---------------------
                // 2.6: If the user chooses option 6, exit the menu loop and return to Main.
                menuIsRunning = false;
            }
        }
    }


    // 1.24.3.1: showMainMenu() starts after Menu.run() step 1.24.3.
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
    // 2.1: If the user chooses option 1, show statistics for all readings.
    private void handleOverallStats() {
        showStatsSubMenu(loadedReadings, "entire greenhouse");
    }





    // -------------------menu option 2---------------------
    // 2.2: If the user chooses option 2, begin the zone statistics path.
    private void handleStatsByZone() {
        // 2.2.1: Ask the user to choose from the three greenhouse zones.
        String selectedZone = readValidZone();

        // 2.2.2: Filter the readings and then reuse the normal stats menu.
        SensorArray filteredReadings = loadedReadings.filterByZone(selectedZone);
        showStatsSubMenu(filteredReadings, "zone " + selectedZone);
    }

    // 1.24.10: Keep zone input to the three zones used by the greenhouse data.
    private String readValidZone() {
        String[] zones = {"ZoneA", "ZoneB", "ZoneC"};

        Logger.logBlank();
        Logger.log("Select zone");
        for (int i = 0; i < zones.length; i++) {
            Logger.log((i + 1) + ". " + zones[i]);
        }

        // 1.24.10.1: Store the same zone names that appear in the CSV file.
        int userSelection = readIntegerInRange("zone: ", 1, zones.length);
        return zones[userSelection - 1];
    }




    // -------------------menu option 3---------------------
    // 2.3: If the user chooses option 3, begin the sensor type statistics path.
    private void handleStatsByType() {
        // 2.3.1: Ask the user to choose from the four sensor types.
        String selectedSensorType = readValidSensorType();

        // 2.3.2: Filter the readings and then reuse the normal stats menu.
        SensorArray filteredReadings = loadedReadings.filterBySensorType(selectedSensorType);
        showStatsSubMenu(filteredReadings, "type " + selectedSensorType);
    }

    // 1.24.9: Keep sensor type input to the four sensor types used by the greenhouse data.
    private String readValidSensorType() {
        String[] sensorTypes = {"temperature", "humidity", "soilMoisture", "light"};

        Logger.logBlank();
        Logger.log("Select sensor type");
        for (int i = 0; i < sensorTypes.length; i++) {
            Logger.log((i + 1) + ". " + sensorTypes[i]);
        }

        // 1.24.9.1: Store the exact sensor type names used in the CSV file.
        int userSelection = readIntegerInRange("type: ", 1, sensorTypes.length);
        return sensorTypes[userSelection - 1];
    }






    // -------------------menu option 4---------------------
    // 2.4: If the user chooses option 4, begin adding a new sensor reading.
    private void handleAddReading() {
        Logger.logBlank();
        Logger.log("Add new reading");

        // 2.4.1: Read and validate the sensor details entered by the user.
        String sensorID = readNonEmptyString("sensor ID: ");
        while (loadedReadings.hasSensorID(sensorID) == true) {
            Logger.log("That sensor ID is already in the data.");
            sensorID = readNonEmptyString("sensor ID: ");
        }

        String sensorType = readValidSensorType();
        String zone = readValidZone();
        double sensorValue = readDoubleNumber("value: ");

        // 2.4.2: Read the timestamp one part at a time so each range can be checked.
        int day = readIntegerInRange("day (1-31): ", 1, 31);
        int month = readIntegerInRange("month (1-12): ", 1, 12);
        int year = readIntegerInRange("year (2000-2100): ", 2000, 2100);
        int hour = readIntegerInRange("hour (0-23): ", 0, 23);
        int minute = readIntegerInRange("minute (0-59): ", 0, 59);

        // 2.4.3: Build the new reading from the input values.
        Timestamp newReadingTimestamp = new Timestamp(day, month, year, hour, minute);
        SensorReading newReading = new SensorReading(sensorID, sensorType, zone, sensorValue, newReadingTimestamp);

        // 2.4.4: Add it to memory for the current program run.
        loadedReadings.add(newReading);
        Logger.log("Added: " + newReading);

        // 2.4.5: Save the updated readings back to the CSV file.
        GreenhouseDataStorage.saveCSV(csvFilename, loadedReadings);
    }





    // -------------------menu option 5---------------------
    // 2.5: If the user chooses option 5, begin deleting a sensor reading.
    private void handleDeleteReading() {
        // 2.5.1: If the array is empty, there is nothing the user can delete.
        if (loadedReadings.getCount() == 0) {
            Logger.log("No readings to delete.");
        } else {
            // 2.5.2: Show each reading with a number the user can choose.
            Logger.logBlank();
            Logger.log("Current readings");
            for (int i = 0; i < loadedReadings.getCount(); i++) {
                Logger.log((i + 1) + ". " + loadedReadings.get(i));
            }

            // 2.5.3: Convert the user's display number back into a zero-based array index.
            int userSelection = readIntegerInRange("delete number: ", 1, loadedReadings.getCount());
            SensorReading removedReading = loadedReadings.get(userSelection - 1);
            loadedReadings.deleteAt(userSelection - 1);

            // 2.5.4: Delete it from memory for the current program run.
            Logger.log("Deleted: " + removedReading);

            

            // -------------------delete row from CSV---------------------
            // 2.5.5: Re-save the CSV after deletion, so the removed reading is no longer in data.csv.
            GreenhouseDataStorage.saveCSV(csvFilename, loadedReadings);
        }
    }



    // -------------------statistics submenu---------------------
    // 2.1.1: Show the statistics options for whichever group of readings was chosen.
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

        // 2.1.1.1: Call Math directly for only the statistic the user asked for.
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
            // 2.1.1.2: Option 7 prints the full set of statistics.
            Logger.log("Total: " + Math.totalReadings(filteredReadings));
            Logger.log("Average: " + Math.average(filteredReadings));
            Logger.log("Minimum: " + Math.minimum(filteredReadings));
            Logger.log("Maximum: " + Math.maximum(filteredReadings));
            Logger.log("Number outside safe range: " + Math.outOfRangeCount(filteredReadings));
            Logger.log("Percent outside safe range: " + Math.outOfRangePercent(filteredReadings));
        }
    }

    // -------------------input helpers---------------------
    // 1.24.5: This helper reads one line from the keyboard and trims it.
    private String readLineFromConsole(String promptToShow) {
        // 1.24.5.1: Show the prompt before waiting for input.
        System.out.print(promptToShow);
        String trimmedInputLine = "";
        try {
            // 1.24.5.2: Read what the user typed.
            String rawInputLine = consoleInputReader.readLine();
            if (rawInputLine == null) {
                trimmedInputLine = "";
            } else {
                // 1.24.5.3: Trim spaces so accidental spaces do not count as part of the answer.
                trimmedInputLine = rawInputLine.trim();
            }
        } catch (IOException inputError) {
            // 1.24.5.4: If input fails, return an empty string and let the validation ask again.
            trimmedInputLine = "";
        }
        return trimmedInputLine;
    }

    // 1.24.6: Keep asking until the user enters an integer inside the allowed range.
    private int readIntegerInRange(String promptToShow, int minimumAllowedValue, int maximumAllowedValue) {
        int validatedInteger = 0;
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            // 1.24.6.1: Read the input as text first so it can be checked before parsing.
            String userInputString = readLineFromConsole(promptToShow);
            if (Validator.isInteger(userInputString) == true) {
                int parsedInteger = Integer.parseInt(userInputString);

                // 1.24.6.2: The number has to be inside the range for this question.
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

    // 1.24.7: Keep asking until the user enters a decimal number.
    private double readDoubleNumber(String promptToShow) {
        double validatedDouble = 0.0;
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            // 1.24.7.1: Read the value as text so it can be checked by Validator first.
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

    // 1.24.8: Keep asking until the user enters some text.
    private String readNonEmptyString(String promptToShow) {
        String validatedString = "";
        boolean inputAccepted = false;
        while (inputAccepted == false) {
            // 1.24.8.1: A trimmed input length above zero means the field is usable.
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
