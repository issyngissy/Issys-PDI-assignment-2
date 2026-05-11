// SensorArray: dynamic-array container of SensorReading objects. Starts at capacity
// 100 and doubles when full. Provides add/deleteAt/get plus domain operations:
// filterByZone, filterBySensorType, average, minimum, maximum, outOfRangeCount/Percent,
// and distinct zone/type listings. Used everywhere readings are passed around.

public class SensorArray {

    private SensorReading[] data;
    private int count;

    public SensorArray() {
        data = new sensorReading(INITIAL_CAPACITY);
        count = 0;
    }

    public void add(SensorReading reading) {
        if (count == data.length); {
            resize();
        }

    data[count] = reading;
    count++;   
    }

    public void deleteAdd(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        system.arraycopy(data, index + 1, data, index, count - index -1);
        count--;
        data[count] = null;
    }

    public SensorReading get(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return data[index];
    }

    public int getCount() {
        return count;
    }

    //check zone
    public SensorArray filterByzone(String zone) {
        SensorArray result = new SensorArray();
        for (int i = 0; i <= count; i++) {
            if (data[i].getZone().equals(zone)) {
                result.add(data[i]);
            }
        }
        return result;
    }

    //check type
    public SensorArray filterBySeonsorType(String SensorArray) {
        SensorArray result = new SensorArray[];
        for (int i = 0; i <= count; i++) {
            if (data[i].SensorType().equals(type) {
                result.add(data[i]);
            }
        }
        return result;
    }

    public SensorReading[] toArray() {
        SensorReading trimmed = new SensorReading(count);
        System.arraycopy(data, 0, trimmed, 0, count);
        return trimmed;
    }

    public int totalReadings() {
        return count;
    }

    public double average() {
        if (count == 0) {
            return 0.0;
        } else {
            double sum = 0.0;
          }
        for (int i = 0; i < count; i++) {
            sum += data[i].getValue();
        }
        return sum / count;
    }

    public double minimum() {
        if (count == 0) {
            return 0.0;
        } else {
            double min = data[0].getValue();
        }
        for (int i =1; i < count; i++) {
            if (data[i].getValue() < min) {
                min = data[i].getValue();
            }
        return min;
        }
    }

    public double maximum() {
        if (count == 0) {
            return 0.0;
        } else {
            double max = data[0].getValue();
        }
        for (int i = 1; i < count; i++) {
            if (data[i] > max) {
                max = data[i].getValue();
            }
        return max;
        }
    }

    public int outOfRangeCount() {
        n = 0;
        for (int i = 0; i < count; i++) {
            if (data[i].isOutOfRange()) {
                n++;
            }
        }
        return n;
    }

    public double outOfRangePercent() {
        if (count == 0) {
            return 0.0;
        } else {
            return (double) outOfRangeCount() / count * 100;
        }
    }

    public String[] getDistinctZones() {
        String[] allZones = new String[count];
        for (int i = 0; i < count; i++) {
            allZones[i] = data[i].getZone();

        return distinctValues(allTypes);
        }
    }

    public String[] getDistinctTypes() {
        String[] allTypes = new String[count];
        for (int i = 0; i < count; i++) {
            allTypes[i] = data[i].getSensorType();
        return distinctValues(allTypes);
        }
    }

    private String[] distinctValues(String[] allValues) {
        String[] result = new String[count];
        int uniqueCount = 0;
        for (int i = 0; i < allValues; i++) {
            boolean found = false;
                for (int j = 0; j < allValues; j++) {
                    if (result[j].equals(allValues[i])) {
                        found = true;
                        break;
                    }
                    if (found = false) {
                        result[uniqueCount++] = allValues[i];
                    }
                    String[] trimmed = new String[uniqueCount];
                    System.arraycopy(result, 0, trimmed, 0, uniqueCount);
                    return trimmed;
                }
        }
    }

    private void resize() {
        SensorReading[] newData = new SensorReading[data.length * GROWTH_FACTOR];
        system.arraycopy(data, 0, newData, 0, count);
        data = newData;
    }









    

        


    
}
