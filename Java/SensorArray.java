// 1.6.1: The program enters SensorArray when GreenhouseDataStorage step 1.6 creates the readings list.
public class SensorArray {

    // 1.6.2: The array starts at 100 spaces and grows if it needs more room.
    private static final int INITIAL_CAPACITY = 100;
    private static final int CAPACITY_GROWTH_FACTOR = 2;

    // 1.6.3: readings stores the objects, and numberOfReadings tracks how many are actually used.
    private SensorReading[] readings;
    private int numberOfReadings;

    // 1.6.4: Build an empty SensorArray, then return to GreenhouseDataStorage step 1.7.
    public SensorArray() {
        readings = new SensorReading[INITIAL_CAPACITY];
        numberOfReadings = 0;
    }

    // 1.16.1: SensorArray.add() is entered when a loaded or new reading needs to be stored.
    public void add(SensorReading newReading) {
        // 1.16.2: If the array is full, make a bigger one before adding the reading.
        if (numberOfReadings == readings.length) {
            growArray();
        }

        // 1.16.3: Put the new reading in the next free space.
        readings[numberOfReadings] = newReading;
        numberOfReadings = numberOfReadings + 1;
    }

    // 2.5.3.1: SensorArray.deleteAt() is entered when the menu deletes a reading.
    public void deleteAt(int indexToDelete) {
        // 2.5.3.2: Stop the delete if the index is outside the used part of the array.
        if (indexToDelete < 0 || indexToDelete >= numberOfReadings) {
            throw new IllegalArgumentException("Index out of bounds: " + indexToDelete);
        }

        // 2.5.3.3: Shift every reading after indexToDelete one spot to the left.
        for (int i = indexToDelete; i < numberOfReadings - 1; i++) {
            readings[i] = readings[i + 1];
        }

        // 2.5.3.4: Reduce the count and clear the old last spot.
        numberOfReadings = numberOfReadings - 1;
        readings[numberOfReadings] = null;
    }

    // 2.5.3.1: SensorArray.get() is entered when the menu needs one reading by number.
    public SensorReading get(int indexToFetch) {
        // 2.5.3.2: Only allow indexes that point to real readings.
        if (indexToFetch < 0 || indexToFetch >= numberOfReadings) {
            throw new IllegalArgumentException("Index out of bounds: " + indexToFetch);
        }
        return readings[indexToFetch];
    }

    // 1.24.4.1: Return how many readings are currently stored.
    public int getCount() {
        return numberOfReadings;
    }


    //-----------check if there is duplicate sensorID-----------
    // 2.4.1.1: Check whether this sensor ID is already in the readings.
    public boolean hasSensorID(String sensorIDToFind) {
        boolean sensorIDAlreadyExists = false;

        // 2.4.1.2: Look through each stored reading one by one.
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getSensorID().equals(sensorIDToFind)) {
                sensorIDAlreadyExists = true;
            }
        }
        return sensorIDAlreadyExists;
    }


    //----------zone match function---------
    // 2.2.3.1: Create a new SensorArray with only readings from one zone.
    public SensorArray filterByZone(String zoneToMatch) {
        SensorArray matchingReadings = new SensorArray();

        // 2.2.3.2: Check each reading and copy the matching ones into the new array.
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getZone().equals(zoneToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }



    //-----------Sensor type function--------------
    // 2.3.3.1: Create a new SensorArray with only readings from one sensor type.
    public SensorArray filterBySensorType(String typeToMatch) {
        SensorArray matchingReadings = new SensorArray();

        // 2.3.3.2: Check each reading and copy the matching ones into the new array.
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getSensorType().equals(typeToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }
    // 1.16.2.1: Make the storage array bigger when it runs out of room.
    private void growArray() {
        // 1.16.2.2: Double the current capacity.
        int expandedCapacity = readings.length * CAPACITY_GROWTH_FACTOR;
        SensorReading[] expandedReadings = new SensorReading[expandedCapacity];

        // 1.16.2.3: Copy the existing readings into the bigger array.
        for (int i = 0; i < numberOfReadings; i++) {
            expandedReadings[i] = readings[i];
        }

        // 1.16.2.4: Swap the old full array for the new bigger one.
        readings = expandedReadings;
    }
}
