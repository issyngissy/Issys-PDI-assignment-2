import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Handles loading readings from data.csv and saving them back again.
public class GreenhouseDataStorage {

    public static SensorArray loadCSV(String csvFilename) {
        SensorArray loadedReadings = new SensorArray();
        BufferedReader csvReader = null;

        try {
            csvReader = new BufferedReader(new FileReader(csvFilename));
            csvReader.readLine(); // skip header row

            int currentLineNumber = 1;
            String currentLine = csvReader.readLine();

            while (currentLine != null) {
                currentLineNumber = currentLineNumber + 1;
                currentLine = currentLine.trim();

                if (currentLine.length() > 0) {
                    SensorReading parsedReading = parseLine(currentLine);

                    if (parsedReading != null) {
                        loadedReadings.add(parsedReading);
                    } else {
                        System.out.println("Warning: skipped invalid row " + currentLineNumber + ": " + currentLine);
                    }
                }

                currentLine = csvReader.readLine();
            }
        } catch (IOException fileReadError) {
            System.out.println("Error loading file '" + csvFilename + "': " + fileReadError.getMessage());
        }

        if (csvReader != null) {
            try {
                csvReader.close();
            } catch (IOException fileCloseError) {
                // The file has already been read, so there is nothing useful to do here.
            }
        }

        return loadedReadings;
    }


    
    // ----------------------saveCSV----------------------
    public static void saveCSV(String csvFilename, SensorArray readingsToSave) {
        BufferedWriter csvWriter = null;

        try {
            csvWriter = new BufferedWriter(new FileWriter(csvFilename, false));

            csvWriter.write("day,month,year,hour,minute,sensorID,sensorType,zone,value");
            csvWriter.newLine();

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
            System.out.println("Error saving file '" + csvFilename + "': " + fileWriteError.getMessage());
        }

        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException fileCloseError) {
                // Saving was already attempted, so ignore close errors.
            }
        }
    }

    private static SensorReading parseLine(String csvLine) {
        SensorReading parsedReading = null;

        String[] csvFields = null;
        if (csvLine != null) {
            csvFields = csvLine.split(",", -1);
        }

        if (csvFields != null && csvFields.length == 9) {
            String dayString = csvFields[0].trim();
            String monthString = csvFields[1].trim();
            String yearString = csvFields[2].trim();
            String hourString = csvFields[3].trim();
            String minuteString = csvFields[4].trim();
            String sensorID = csvFields[5].trim();
            String sensorType = csvFields[6].trim();
            String zone = csvFields[7].trim();
            String valueString = csvFields[8].trim();

            boolean rowIsValid = true;

            if (Validator.isInteger(dayString) == false) { rowIsValid = false; }
            if (Validator.isInteger(monthString) == false) { rowIsValid = false; }
            if (Validator.isInteger(yearString) == false) { rowIsValid = false; }
            if (Validator.isInteger(hourString) == false) { rowIsValid = false; }
            if (Validator.isInteger(minuteString) == false) { rowIsValid = false; }

            if (rowIsValid == true) {
                int day = Integer.parseInt(dayString);
                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);
                int hour = Integer.parseInt(hourString);
                int minute = Integer.parseInt(minuteString);

                if (Validator.isValidDay(day) == false) { rowIsValid = false; }
                if (Validator.isValidMonth(month) == false) { rowIsValid = false; }
                if (Validator.isValidYear(year) == false) { rowIsValid = false; }
                if (Validator.isValidHour(hour) == false) { rowIsValid = false; }
                if (Validator.isValidMinute(minute) == false) { rowIsValid = false; }
                if (Validator.isValidSensorID(sensorID) == false) { rowIsValid = false; }
                if (Validator.isValidSensorType(sensorType) == false) { rowIsValid = false; }
                if (Validator.isValidZone(zone) == false) { rowIsValid = false; }
                if (Validator.isNumeric(valueString) == false) { rowIsValid = false; }

                if (rowIsValid == true) {
                    double sensorValue = Double.parseDouble(valueString);
                    Timestamp readingTimestamp = new Timestamp(day, month, year, hour, minute);
                    parsedReading = new SensorReading(sensorID, sensorType, zone, sensorValue, readingTimestamp);
                }
            }
        }

        return parsedReading;
    }
}
