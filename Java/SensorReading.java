// SensorReading: one row of data — sensorID, sensorType, zone, value, and a Timestamp.
// Provides getters/setters, a copy constructor, a toString for display, and
// isOutOfRange() which checks the value against hard-coded safe ranges per sensor
// type (temperature, humidity, soilMoisture, light).
public class SensorReading {

    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;

    public SensorReading() {
        sensorID = "";
        sensorType = "";
        zone = "";
        value = 0.0;
        timestamp = new Timestamp();
    }

    public SensorReading(
        String sensorID,
        String sensorType,
        String zone,
        double value,
        Timestamp timestamp
    ) {
        this.sensorID = sensorID;
        this.sensorType = sensorType;
        this.zone = zone;
        this.value = value;
        this.timestamp = timestamp;
    }

    public SensorReading(SensorReading sourceReading) {
        this.sensorID = sourceReading.sensorID;
        this.sensorType = sourceReading.sensorType;
        this.zone = sourceReading.zone;
        this.value = sourceReading.value;
        this.timestamp = new Timestamp(sourceReading.timestamp);
    }

    public String getSensorID() {
        return sensorID;
    }

    public String getSensorType() {
        return sensorType;
    }

    public String getZone() {
        return zone;
    }

    public double getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private static double[] getSafeRange(String sensorTypeToLookUp) {
        double[] safeRangeBounds = null;
        if (sensorTypeToLookUp.equals("temperature")) {
            safeRangeBounds = new double[] { 18.0, 30.0 };
        } else if (sensorTypeToLookUp.equals("humidity")) {
            safeRangeBounds = new double[] { 40.0, 70.0 };
        } else if (sensorTypeToLookUp.equals("soilMoisture")) {
            safeRangeBounds = new double[] { 30.0, 60.0 };
        } else if (sensorTypeToLookUp.equals("light")) {
            safeRangeBounds = new double[] { 300.0, 1200.0 };
        }
        return safeRangeBounds;
    }

    public boolean isOutOfRange() {
        boolean readingIsOutOfRange = false;
        double[] safeRangeBounds = getSafeRange(sensorType);
        if (safeRangeBounds == null) {
            readingIsOutOfRange = false;
        } else if (value < safeRangeBounds[0]) {
            readingIsOutOfRange = true;
        } else if (value > safeRangeBounds[1]) {
            readingIsOutOfRange = true;
        } else {
            readingIsOutOfRange = false;
        }
        return readingIsOutOfRange;
    }

    public String toString() {
        String timestampPrefix = "[" + timestamp.toString() + "] ";
        String readingDetails = sensorID + " (" + sensorType + ", " + zone + "): " + value;
        return timestampPrefix + readingDetails;
    }
}


// Timestamp: value object holding day, month, year, hour, minute as ints.
// Offers default/parameterised/copy constructors, getters/setters, and a
// toString that formats as "dd/mm/yyyy hh:mm" with zero-padding. Embedded
// inside every SensorReading.
class Timestamp {

    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private int hour;
    private int minute;

    public Timestamp() {
        dayOfMonth = 0;
        monthOfYear = 0;
        year = 0;
        hour = 0;
        minute = 0;
    }

    public Timestamp(
        int dayOfMonth,
        int monthOfYear,
        int year,
        int hour,
        int minute
    ) {
        this.dayOfMonth = dayOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public Timestamp(Timestamp sourceTimestamp) {
        this.dayOfMonth = sourceTimestamp.dayOfMonth;
        this.monthOfYear = sourceTimestamp.monthOfYear;
        this.year = sourceTimestamp.year;
        this.hour = sourceTimestamp.hour;
        this.minute = sourceTimestamp.minute;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String toString() {
        String dayPadded = padWithLeadingZero(dayOfMonth);
        String monthPadded = padWithLeadingZero(monthOfYear);
        String hourPadded = padWithLeadingZero(hour);
        String minutePadded = padWithLeadingZero(minute);
        return dayPadded + "/" + monthPadded + "/" + year + " " + hourPadded + ":" + minutePadded;
    }

    private String padWithLeadingZero(int numberToPad) {
        String paddedNumber = "";
        if (numberToPad < 10) {
            paddedNumber = "0" + numberToPad;
        } else {
            paddedNumber = "" + numberToPad;
        }
        return paddedNumber;
    }
}
