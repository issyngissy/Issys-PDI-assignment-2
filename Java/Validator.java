//------------------------ Validator --------------------------

// 1.15.7.1: Validator is entered whenever loadCSV or Menu needs to check input.
// 1.15.7.1.1: It keeps the input rules in one place so both parts of the program use the same checks.
public class Validator {

    // 1.15.7.2: These are the only sensor types that the program accepts.
    private static final String[] VALID_SENSOR_TYPES = {
        "temperature",
        "humidity",
        "soilMoisture",
        "light"
    };

    // 1.15.7.3: These constants define the allowed date and time ranges.
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

    // 1.15.10.1: Begin checking whether a sensor type matches one of the allowed options.
    public static boolean isValidSensorType(String sensorTypeToValidate) {
        // 1.15.10.2: Start with false, then switch to true only if a match is found.
        boolean isValid = false;

        // 1.15.10.3: A null sensor type is not valid data.
        if (sensorTypeToValidate == null) {
            isValid = false;
        } else {
            // 1.15.10.4: Compare the input against each allowed sensor type.
            for (int i = 0; i < VALID_SENSOR_TYPES.length; i++) {
                if (VALID_SENSOR_TYPES[i].equals(sensorTypeToValidate)) {
                    isValid = true;
                }
            }
        }

        // 1.15.10.5: Return whether the sensor type was accepted.
        return isValid;
    }

    // 1.15.10.6: Begin checking whether a zone value is usable.
    public static boolean isValidZone(String zoneToValidate) {
        // 1.15.10.7: A zone starts as invalid until it proves it has real text.
        boolean isValid = false;

        // 1.15.10.8: Null and blank zones are rejected, but any non-empty name is accepted.
        if (zoneToValidate == null) {
            isValid = false;
        } else if (zoneToValidate.trim().length() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.10.9: Return the zone validation result.
        return isValid;
    }

    // 1.15.10.10: Begin checking whether a sensor ID value is usable.
    public static boolean isValidSensorID(String sensorIdToValidate) {
        // 1.15.10.11: A sensor ID starts as invalid until it has real text.
        boolean isValid = false;

        // 1.15.10.12: Null and blank IDs are rejected so every reading can be identified.
        if (sensorIdToValidate == null) {
            isValid = false;
        } else if (sensorIdToValidate.trim().length() > 0) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.10.13: Return the sensor ID validation result.
        return isValid;
    }

    // 1.15.9.1: Begin checking the day value.
    public static boolean isValidDay(int dayToValidate) {
        // 1.15.9.2: The day is valid only if it sits inside the program's day range.
        boolean isValid = false;
        if (dayToValidate >= MIN_DAY && dayToValidate <= MAX_DAY) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.9.3: Return the day validation result.
        return isValid;
    }

    // 1.15.9.4: Begin checking the month value.
    public static boolean isValidMonth(int monthToValidate) {
        // 1.15.9.5: The month is valid only if it sits inside the program's month range.
        boolean isValid = false;
        if (monthToValidate >= MIN_MONTH && monthToValidate <= MAX_MONTH) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.9.6: Return the month validation result.
        return isValid;
    }

    // 1.15.9.7: Begin checking the year value.
    public static boolean isValidYear(int yearToValidate) {
        // 1.15.9.8: The year is valid only if it sits inside the program's year range.
        boolean isValid = false;
        if (yearToValidate >= MIN_YEAR && yearToValidate <= MAX_YEAR) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.9.9: Return the year validation result.
        return isValid;
    }

    // 1.15.9.10: Begin checking the hour value.
    public static boolean isValidHour(int hourToValidate) {
        // 1.15.9.11: The hour is valid only if it sits inside the 24-hour clock range.
        boolean isValid = false;
        if (hourToValidate >= MIN_HOUR && hourToValidate <= MAX_HOUR) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.9.12: Return the hour validation result.
        return isValid;
    }

    // 1.15.9.13: Begin checking the minute value.
    public static boolean isValidMinute(int minuteToValidate) {
        // 1.15.9.14: The minute is valid only if it sits inside the normal minute range.
        boolean isValid = false;
        if (minuteToValidate >= MIN_MINUTE && minuteToValidate <= MAX_MINUTE) {
            isValid = true;
        } else {
            isValid = false;
        }

        // 1.15.9.15: Return the minute validation result.
        return isValid;
    }

    // 1.15.10.14: Begin checking whether a string can be used as a decimal number.
    public static boolean isNumeric(String stringToCheck) {
        // 1.15.10.15: Start false so missing or badly formatted values stay invalid.
        boolean isValid = false;

        // 1.15.10.16: Null and empty strings cannot be converted into useful sensor values.
        if (stringToCheck == null) {
            isValid = false;
        } else if (stringToCheck.trim().length() == 0) {
            isValid = false;
        } else {
            try {
                // 1.15.10.17: Try converting the text to a double. If it works, the value is numeric.
                Double.parseDouble(stringToCheck.trim());
                isValid = true;
            } catch (NumberFormatException notANumber) {
                // 1.15.10.18: If Java cannot parse it as a double, keep the result false.
                isValid = false;
            }
        }

        // 1.15.10.19: Return whether the string was numeric.
        return isValid;
    }

    // 1.15.7.4: Begin checking whether a string can be used as an integer.
    public static boolean isInteger(String stringToCheck) {
        // 1.15.7.5: Start false so missing or badly formatted values stay invalid.
        boolean isValid = false;

        // 1.15.7.6: Null and empty strings cannot be converted into useful whole numbers.
        if (stringToCheck == null) {
            isValid = false;
        } else if (stringToCheck.trim().length() == 0) {
            isValid = false;
        } else {
            try {
                // 1.15.7.7: Try converting the text to an int. If it works, the value is an integer.
                Integer.parseInt(stringToCheck.trim());
                isValid = true;
            } catch (NumberFormatException notAnInteger) {
                // 1.15.7.8: If Java cannot parse it as an int, keep the result false.
                isValid = false;
            }
        }

        // 1.15.7.9: Return whether the string was an integer.
        return isValid;
    }
}
