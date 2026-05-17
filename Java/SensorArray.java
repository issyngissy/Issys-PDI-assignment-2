// Stores SensorReading objects and provides basic searching/filtering.
public class SensorArray {

    private static final int INITIAL_CAPACITY = 100;
    private static final int CAPACITY_GROWTH_FACTOR = 2;

    private SensorReading[] readings;
    private int numberOfReadings;

    public SensorArray() {
        readings = new SensorReading[INITIAL_CAPACITY];
        numberOfReadings = 0;
    }

    public void add(SensorReading newReading) {
        if (numberOfReadings == readings.length) {
            growArray();
        }

        readings[numberOfReadings] = newReading;
        numberOfReadings = numberOfReadings + 1;
    }

    public void deleteAt(int indexToDelete) {
        if (indexToDelete < 0 || indexToDelete >= numberOfReadings) {
            throw new IllegalArgumentException("Index out of bounds: " + indexToDelete);
        }

        // Move later readings left to fill the deleted space.
        for (int i = indexToDelete; i < numberOfReadings - 1; i++) {
            readings[i] = readings[i + 1];
        }

        numberOfReadings = numberOfReadings - 1;
        readings[numberOfReadings] = null;
    }

    public SensorReading get(int indexToFetch) {
        if (indexToFetch < 0 || indexToFetch >= numberOfReadings) {
            throw new IllegalArgumentException("Index out of bounds: " + indexToFetch);
        }
        return readings[indexToFetch];
    }

    public int getCount() {
        return numberOfReadings;
    }

    public boolean hasSensorID(String sensorIDToFind) {
        boolean sensorIDAlreadyExists = false;

        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getSensorID().equals(sensorIDToFind)) {
                sensorIDAlreadyExists = true;
            }
        }
        return sensorIDAlreadyExists;
    }

    // Make a new SensorArray containing only readings from one zone.
    public SensorArray filterByZone(String zoneToMatch) {
        SensorArray matchingReadings = new SensorArray();

        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getZone().equals(zoneToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }

    // Make a new SensorArray containing only readings from one sensor type.
    public SensorArray filterBySensorType(String typeToMatch) {
        SensorArray matchingReadings = new SensorArray();

        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getSensorType().equals(typeToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }

    private void growArray() {
        int expandedCapacity = readings.length * CAPACITY_GROWTH_FACTOR;
        SensorReading[] expandedReadings = new SensorReading[expandedCapacity];

        for (int i = 0; i < numberOfReadings; i++) {
            expandedReadings[i] = readings[i];
        }

        readings = expandedReadings;
    }
}
