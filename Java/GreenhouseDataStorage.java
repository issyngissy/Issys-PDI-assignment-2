// 1.3.1: These imports are used once Main sends the program into this file.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// 1.4: The program enters the GreenhouseDataStorage class from Main step 1.3.
public class GreenhouseDataStorage {

    // 1.5: Begin loadCSV(), which reads data.csv and returns the loaded readings to Main.
    public static SensorArray loadCSV(String csvFilename) {
        // 1.6: Create an empty SensorArray to hold each valid reading found in the file.
        SensorArray loadedReadings = new SensorArray();

        // 1.7: The reader starts as null so it can still be checked safely after the try block.
        BufferedReader csvReader = null;

        try {
            // 1.8: Open the requested CSV file.
            csvReader = new BufferedReader(new FileReader(csvFilename));

            // 1.9: Skip the header row because it only contains the column names.
            csvReader.readLine();

            // 1.10: Keep track of the real CSV line number, which helps when showing warnings.
            int currentLineNumber = 1;

            // 1.11: Read the first actual data row before entering the loop.
            String currentLine = csvReader.readLine();
            while (currentLine != null) {
                // 1.12: Move the line counter forward as soon as a row is being handled.
                currentLineNumber = currentLineNumber + 1;

                // 1.13: Trim the line so blank spaces at the start or end do not affect parsing.
                currentLine = currentLine.trim();

                // 1.14: Ignore empty rows. They are not useful data, but they also are not a crash.
                if (currentLine.length() > 0) {
                    // 1.15: HUGE METHOD CALL Go into parseLine() to turn the raw CSV text into a SensorReading.
                    SensorReading parsedReading = parseLine(currentLine);

                    // 1.16: Back from parseLine(); only store the reading if every field passed validation.
                    if (parsedReading != null) {
                        loadedReadings.add(parsedReading);
                    } else {
                        // 1.17: Invalid rows are skipped, but the user is told exactly where it happened.
                        System.out.println("Warning: skipped invalid row " + currentLineNumber + ": " + currentLine);
                    }
                }

                // 1.18: Move on to the next row in the file.
                currentLine = csvReader.readLine();
            }
        } catch (IOException fileReadError) {
            // 1.19: If the file cannot be opened or read, show the problem instead of stopping suddenly.
            System.out.println("Error loading file '" + csvFilename + "': " + fileReadError.getMessage());
        }

        // 1.20: Close the file reader once loading is finished.
        if (csvReader != null) {
            try {
                csvReader.close();
            } catch (IOException fileCloseError) {
                // 1.20.1: Ignore this because the file has already been read by this point.
            }
        }

        // 1.21: Return the loaded readings, then the program goes back to Main step 1.22.
        return loadedReadings;
    }




    // ----------------------saveCSV ----------------------
    // 2.4.5.1: Save the current readings back into the CSV file.
    public static void saveCSV(String csvFilename, SensorArray readingsToSave) {
        // 2.4.5.2: The writer starts as null so it can be closed safely later.
        BufferedWriter csvWriter = null;

        try {
            // 2.4.5.3: false means overwrite the old CSV with the current readings.
            csvWriter = new BufferedWriter(new FileWriter(csvFilename, false));

            // 2.4.5.4: Write the header row first.
            csvWriter.write("day,month,year,hour,minute,sensorID,sensorType,zone,value");
            csvWriter.newLine();

            // 2.4.5.5: Write each sensor reading on its own CSV line.
            for (int i = 0; i < readingsToSave.getCount(); i++) {
                SensorReading currentReading = readingsToSave.get(i);
                Timestamp currentTimestamp = currentReading.getTimestamp();

                String csvLine = currentTimestamp.getDayOfMonth() + ","
                               + currentTimestamp.getMonthOfYear() + ","
                               + currentTimestamp.getYear() + ","
                               + currentTimestamp.getHour() + ","
                               + currentTimestamp.getMinute() + ","
                               + currentReading.getSensorID() + ","
                               + currentReading.getSensorType() + ","
                               + currentReading.getZone() + ","
                               + currentReading.getValue();

                csvWriter.write(csvLine);
                csvWriter.newLine();
            }
        } catch (IOException fileWriteError) {
            // 2.4.5.6: Show the error if Java cannot write to the file.
            System.out.println("Error saving file '" + csvFilename + "': " + fileWriteError.getMessage());
        }

        // 2.4.5.7: Close the writer after saving.
        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException fileCloseError) {
                // 2.4.5.8: Ignore this because the save has already been attempted.
            }
        }
    }





    //--------------- 1.15.1: parseLine() starts here after loadCSV step 1.15. -----------------------

    private static SensorReading parseLine(String csvLine) {
        // 1.15.2: A null result means the row was invalid or could not be parsed.
        SensorReading parsedReading = null;

        // 1.15.3: Split the CSV row directly before checking each piece of data.
        String[] csvFields = null;
        if (csvLine != null) {
            csvFields = csvLine.split(",", -1);
        }

        // 1.15.4: A valid greenhouse row should have exactly nine columns.
        if (csvFields != null && csvFields.length == 9) {
            // 1.15.5: Trim each field so spaces in the CSV do not break otherwise valid data.
            String dayString = csvFields[0].trim();
            String monthString = csvFields[1].trim();
            String yearString = csvFields[2].trim();
            String hourString = csvFields[3].trim();
            String minuteString = csvFields[4].trim();
            String sensorID = csvFields[5].trim();
            String sensorType = csvFields[6].trim();
            String zone = csvFields[7].trim();
            String valueString = csvFields[8].trim();

            // 1.15.6: Assume the row is valid until one of the checks proves otherwise.
            boolean rowIsValid = true;

            // 1.15.7: Go into Validator.isInteger() for date and time fields before parsing them.
            if (Validator.isInteger(dayString) == false) { rowIsValid = false; }
            if (Validator.isInteger(monthString) == false) { rowIsValid = false; }
            if (Validator.isInteger(yearString) == false) { rowIsValid = false; }
            if (Validator.isInteger(hourString) == false) { rowIsValid = false; }
            if (Validator.isInteger(minuteString) == false) { rowIsValid = false; }

            // 1.15.8: Only convert the date and time values after the integer checks pass.
            if (rowIsValid == true) {
                int day = Integer.parseInt(dayString);
                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);
                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                // 1.15.9: Go into Validator again to check the accepted date and time ranges.
                if (Validator.isValidDay(day) == false) { rowIsValid = false; }
                if (Validator.isValidMonth(month) == false) { rowIsValid = false; }
                if (Validator.isValidYear(year) == false) { rowIsValid = false; }
                if (Validator.isValidHour(hour) == false) { rowIsValid = false; }
                if (Validator.isValidMinute(minute) == false) { rowIsValid = false; }

                // 1.15.10: Check the sensor details and the reading value before making the object.
                if (Validator.isValidSensorID(sensorID) == false) { rowIsValid = false; }
                if (Validator.isValidSensorType(sensorType) == false) { rowIsValid = false; }
                if (Validator.isValidZone(zone) == false) { rowIsValid = false; }
                if (Validator.isNumeric(valueString) == false) { rowIsValid = false; }

                // 1.15.11: If the whole row is valid, build the Timestamp and SensorReading.
                if (rowIsValid == true) {
                    double sensorValue = Double.parseDouble(valueString);
                    Timestamp readingTimestamp = new Timestamp(day, month, year, hour, minute);
                    parsedReading = new SensorReading(sensorID, sensorType, zone, sensorValue, readingTimestamp);
                }
            }
        }

        // 1.15.12: Return the completed reading, then go back to loadCSV step 1.16.
        return parsedReading;
    }
}
