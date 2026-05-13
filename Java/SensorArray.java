// SensorArray: dynamic-array container of SensorReading objects. Starts at capacity
// 100 and doubles when full. Provides add/deleteAt/get plus domain operations:
// filterByZone, filterBySensorType, average, minimum, maximum, outOfRangeCount/Percent,
// and distinct zone/type listings. Used everywhere readings are passed around.

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
        // shift every reading after indexToDelete one slot to the left
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

    public int totalReadings() {
        return numberOfReadings;
    }

    public SensorArray filterByZone(String zoneToMatch) {
        SensorArray matchingReadings = new SensorArray();
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getZone().equals(zoneToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }

    public SensorArray filterBySensorType(String typeToMatch) {
        SensorArray matchingReadings = new SensorArray();
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].getSensorType().equals(typeToMatch)) {
                matchingReadings.add(readings[i]);
            }
        }
        return matchingReadings;
    }

    public double average() {
        double averageValue = 0.0;
        if (numberOfReadings == 0) {
            averageValue = 0.0;
        } else {
            double runningTotal = 0.0;
            for (int i = 0; i < numberOfReadings; i++) {
                runningTotal = runningTotal + readings[i].getValue();
            }
            averageValue = runningTotal / numberOfReadings;
        }
        return averageValue;
    }

    public double minimum() {
        double minimumValue = 0.0;
        if (numberOfReadings == 0) {
            minimumValue = 0.0;
        } else {
            double smallestSoFar = readings[0].getValue();
            for (int i = 1; i < numberOfReadings; i++) {
                if (readings[i].getValue() < smallestSoFar) {
                    smallestSoFar = readings[i].getValue();
                }
            }
            minimumValue = smallestSoFar;
        }
        return minimumValue;
    }

    public double maximum() {
        double maximumValue = 0.0;
        if (numberOfReadings == 0) {
            maximumValue = 0.0;
        } else {
            double largestSoFar = readings[0].getValue();
            for (int i = 1; i < numberOfReadings; i++) {
                if (readings[i].getValue() > largestSoFar) {
                    largestSoFar = readings[i].getValue();
                }
            }
            maximumValue = largestSoFar;
        }
        return maximumValue;
    }

    public int outOfRangeCount() {
        int numberOutOfRange = 0;
        for (int i = 0; i < numberOfReadings; i++) {
            if (readings[i].isOutOfRange()) {
                numberOutOfRange = numberOutOfRange + 1;
            }
        }
        return numberOutOfRange;
    }

    public double outOfRangePercent() {
        double percentOutOfRange = 0.0;
        if (numberOfReadings == 0) {
            percentOutOfRange = 0.0;
        } else {
            int numberOutOfRange = outOfRangeCount();
            percentOutOfRange = ((double) numberOutOfRange / numberOfReadings) * 100;
        }
        return percentOutOfRange;
    }

    public String[] getDistinctZones() {
        String[] allZonesIncludingDuplicates = new String[numberOfReadings];
        for (int i = 0; i < numberOfReadings; i++) {
            allZonesIncludingDuplicates[i] = readings[i].getZone();
        }
        return getUniqueValues(allZonesIncludingDuplicates);
    }

    public String[] getDistinctTypes() {
        String[] allTypesIncludingDuplicates = new String[numberOfReadings];
        for (int i = 0; i < numberOfReadings; i++) {
            allTypesIncludingDuplicates[i] = readings[i].getSensorType();
        }
        return getUniqueValues(allTypesIncludingDuplicates);
    }

    private String[] getUniqueValues(String[] valuesWithDuplicates) {
        String[] uniqueValueBuffer = new String[valuesWithDuplicates.length];
        int numberOfUniqueValues = 0;
        for (int i = 0; i < valuesWithDuplicates.length; i++) {
            boolean alreadySeen = false;
            for (int j = 0; j < numberOfUniqueValues; j++) {
                if (uniqueValueBuffer[j].equals(valuesWithDuplicates[i])) {
                    alreadySeen = true;
                }
            }
            if (alreadySeen == false) {
                uniqueValueBuffer[numberOfUniqueValues] = valuesWithDuplicates[i];
                numberOfUniqueValues = numberOfUniqueValues + 1;
            }
        }
        // copy the unique values into a properly sized array
        String[] uniqueValuesTrimmed = new String[numberOfUniqueValues];
        for (int i = 0; i < numberOfUniqueValues; i++) {
            uniqueValuesTrimmed[i] = uniqueValueBuffer[i];
        }
        return uniqueValuesTrimmed;
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
