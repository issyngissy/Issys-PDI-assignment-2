// Shared validation checks for file data and menu input.
public class Validator {

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
