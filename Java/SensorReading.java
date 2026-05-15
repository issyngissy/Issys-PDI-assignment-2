// 1.15.11.1: The program enters SensorReading when parseLine builds one loaded reading.
public class SensorReading {

    // 1.15.11.2: These fields store the sensor details, value, and time of the reading.
    private String sensorID;
    private String sensorType;
    private String zone;
    private double value;
    private Timestamp timestamp;

    // 1.15.11.3: Create an empty reading with safe default values.
    public SensorReading() {
        sensorID = "";
        sensorType = "";
        zone = "";
        value = 0.0;
        timestamp = new Timestamp();
    }

    // 1.15.11.4: Create a reading from values supplied by the program or user.
    public SensorReading(
        String sensorID,
        String sensorType,
        String zone,
        double value,
        Timestamp timestamp
    ) {
        // 1.15.11.5: Store each value in the matching field.
        this.sensorID = sensorID;
        this.sensorType = sensorType;
        this.zone = zone;
        this.value = value;
        this.timestamp = timestamp;
    }

    // 1.15.11.6: Create a separate copy of an existing reading.
    public SensorReading(SensorReading sourceReading) {
        this.sensorID = sourceReading.sensorID;
        this.sensorType = sourceReading.sensorType;
        this.zone = sourceReading.zone;
        this.value = sourceReading.value;

        // 1.15.11.7: Copy the timestamp too, instead of sharing the same timestamp object.
        this.timestamp = new Timestamp(sourceReading.timestamp);
    }

    // 1.15.11.24: Return the sensor ID when another class needs it.
    public String getSensorID() {
        return sensorID;
    }

    // 1.15.11.25: Return the sensor type when another class needs it.
    public String getSensorType() {
        return sensorType;
    }

    // 1.15.11.26: Return the greenhouse zone when another class needs it.
    public String getZone() {
        return zone;
    }

    // 2.1.3.4: Return the sensor value when statistics or saving need it.
    public double getValue() {
        return value;
    }

    // 1.15.11.27: Return the timestamp object when another class needs it.
    public Timestamp getTimestamp() {
        return timestamp;
    }

    // 1.15.11.8: Update the sensor ID.
    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    // 1.15.11.9: Update the sensor type.
    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    // 1.15.11.10: Update the greenhouse zone.
    public void setZone(String zone) {
        this.zone = zone;
    }

    // 1.15.11.11: Update the sensor value.
    public void setValue(double value) {
        this.value = value;
    }

    // 1.15.11.12: Update the timestamp.
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // 2.1.3.12.1: Look up the safe value range for a sensor type.
    private static double[] getSafeRange(String sensorTypeToLookUp) {
        // 2.1.3.12.2: A null range means the type was not recognised.
        double[] safeRangeBounds = null;

        // 2.1.3.12.3: Each range is stored as {minimum, maximum}.
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

    // 2.1.3.12.4: Check whether this reading is outside its safe range.
    public boolean isOutOfRange() {
        boolean readingIsOutOfRange = false;

        // 2.1.3.12.5: First get the range that belongs to this reading's sensor type.
        double[] safeRangeBounds = getSafeRange(sensorType);

        // 2.1.3.12.6: Unknown sensor types are treated as not out of range here.
        if (safeRangeBounds == null) {
            readingIsOutOfRange = false;
        } else if (value < safeRangeBounds[0]) {
            // 2.1.3.12.7: Values below the minimum are out of range.
            readingIsOutOfRange = true;
        } else if (value > safeRangeBounds[1]) {
            // 2.1.3.12.8: Values above the maximum are out of range.
            readingIsOutOfRange = true;
        } else {
            readingIsOutOfRange = false;
        }
        return readingIsOutOfRange;
    }

    // 2.5.2.1: Turn this reading into text for menus and logs.
    public String toString() {
        // 2.5.2.2: Put the timestamp first so readings are easier to scan.
        String timestampPrefix = "[" + timestamp.toString() + "] ";
        String readingDetails = sensorID + " (" + sensorType + ", " + zone + "): " + value;
        return timestampPrefix + readingDetails;
    }
}
