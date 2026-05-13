import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// GreenhouseDataStorage: static CSV I/O. loadCSV reads each row, validates it via Validator,
// builds a SensorReading (with a Timestamp), and returns a populated SensorArray;
// invalid rows are skipped with a warning. saveCSV writes a SensorArray back out
// in the same column order, overwriting the target file. Validator (defined at the
// bottom of this file) is the static input-checking helper used both here and by Menu.
public class GreenhouseDataStorage {

    public static SensorArray loadCSV(String csvFilename) {
        SensorArray loadedReadings = new SensorArray();
        BufferedReader csvReader = null;

        try {
            csvReader = new BufferedReader(new FileReader(csvFilename));
            // read and discard the header row
            csvReader.readLine();
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
                // ignore — file is already read
            }
        }

        return loadedReadings;
    }

    public static void saveCSV(String csvFilename, SensorArray readingsToSave) {
        BufferedWriter csvWriter = null;

        try {
            csvWriter = new BufferedWriter(new FileWriter(csvFilename, false));
            csvWriter.write("day,month,year,hour,minute,sensorID,sensorType,zone,value");
            csvWriter.newLine();

            for (int i = 0; i < readingsToSave.getCount(); i++) {
                SensorReading currentReading = readingsToSave.get(i);
                Timestamp currentTimestamp = currentReading.getTimestamp();
                String csvRowString = currentTimestamp.getDayOfMonth() + ","
                                    + currentTimestamp.getMonthOfYear() + ","
                                    + currentTimestamp.getYear() + ","
                                    + currentTimestamp.getHour() + ","
                                    + currentTimestamp.getMinute() + ","
                                    + currentReading.getSensorID() + ","
                                    + currentReading.getSensorType() + ","
                                    + currentReading.getZone() + ","
                                    + currentReading.getValue();
                csvWriter.write(csvRowString);
                csvWriter.newLine();
            }
            csvWriter.flush();
        } catch (IOException fileWriteError) {
            System.out.println("Error saving file '" + csvFilename + "': " + fileWriteError.getMessage());
        }

        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException fileCloseError) {
                // ignore — file is already written
            }
        }
    }

    private static SensorReading parseLine(String csvLine) {
        SensorReading parsedReading = null;
        String[] csvFields = splitCSV(csvLine);

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

    private static String[] splitCSV(String lineToSplit) {
        String[] splitFields = null;

        if (lineToSplit != null) {
            // count commas so we know how big to make the array
            int numberOfCommas = 0;
            for (int i = 0; i < lineToSplit.length(); i++) {
                if (lineToSplit.charAt(i) == ',') {
                    numberOfCommas = numberOfCommas + 1;
                }
            }

            splitFields = new String[numberOfCommas + 1];
            int currentFieldIndex = 0;
            int currentFieldStart = 0;
            for (int i = 0; i <= lineToSplit.length(); i++) {
                if (i == lineToSplit.length() || lineToSplit.charAt(i) == ',') {
                    splitFields[currentFieldIndex] = lineToSplit.substring(currentFieldStart, i);
                    currentFieldIndex = currentFieldIndex + 1;
                    currentFieldStart = i + 1;
                }
            }
        }

        return splitFields;
    }
}


// Validator: static input-checking helpers. Confirms sensor type is one of the four
// allowed values, zone/sensorID are non-empty, date/time fields fall in valid ranges
// (day 1-31, month 1-12, year 2000-2100, hour 0-23, minute 0-59), and that strings
// parse as int or double. Used by FileManager and Menu before accepting any reading.
class Validator {

    private static final String[] VALID_SENSOR_TYPES = {
        "temperature",
        "humidity",
        "soilMoisture",
        "light"
    };

    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2100;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;

    public static boolean isValidSensorType(String sensorTypeToValidate) {
        boolean isValid = false;
        if (sensorTypeToValidate == null) {
            isValid = false;
        } else {
            for (int i = 0; i < VALID_SENSOR_TYPES.length; i++) {
                if (VALID_SENSOR_TYPES[i].equals(sensorTypeToValidate)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    public static boolean isValidZone(String zoneToValidate) {
        boolean isValid = false;
        if (zoneToValidate == null) {
            isValid = false;
        } else if (zoneToValidate.trim().length() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidSensorID(String sensorIdToValidate) {
        boolean isValid = false;
        if (sensorIdToValidate == null) {
            isValid = false;
        } else if (sensorIdToValidate.trim().length() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidDay(int dayToValidate) {
        boolean isValid = false;
        if (dayToValidate >= MIN_DAY && dayToValidate <= MAX_DAY) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidMonth(int monthToValidate) {
        boolean isValid = false;
        if (monthToValidate >= MIN_MONTH && monthToValidate <= MAX_MONTH) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidYear(int yearToValidate) {
        boolean isValid = false;
        if (yearToValidate >= MIN_YEAR && yearToValidate <= MAX_YEAR) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidHour(int hourToValidate) {
        boolean isValid = false;
        if (hourToValidate >= MIN_HOUR && hourToValidate <= MAX_HOUR) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidMinute(int minuteToValidate) {
        boolean isValid = false;
        if (minuteToValidate >= MIN_MINUTE && minuteToValidate <= MAX_MINUTE) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isNumeric(String stringToCheck) {
        boolean isValid = false;
        if (stringToCheck == null) {
            isValid = false;
        } else if (stringToCheck.trim().length() == 0) {
            isValid = false;
        } else {
            try {
                Double.parseDouble(stringToCheck.trim());
                isValid = true;
            } catch (NumberFormatException notANumber) {
                isValid = false;
            }
        }
        return isValid;
    }

    public static boolean isInteger(String stringToCheck) {
        boolean isValid = false;
        if (stringToCheck == null) {
            isValid = false;
        } else if (stringToCheck.trim().length() == 0) {
            isValid = false;
        } else {
            try {
                Integer.parseInt(stringToCheck.trim());
                isValid = true;
            } catch (NumberFormatException notAnInteger) {
                isValid = false;
            }
        }
        return isValid;
    }
}
