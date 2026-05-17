// One sensor reading from the greenhouse data.
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
