// 2.1.3.0: The program comes into Math when the menu asks SensorArray for statistics.
public class Math {

    // 2.1.3.1: Return how many readings are being used for the statistic.
    public static int totalReadings(SensorArray readingsToUse) {
        int total = readingsToUse.getCount();
        return total;
    }

    // 2.1.3.2: Work out the average value of the selected readings.
    public static double average(SensorArray readingsToUse) {
        double averageValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        // 2.1.3.3: If there are no readings, return zero instead of dividing by zero.
        if (numberOfReadings == 0) {
            averageValue = 0.0;
        } else {
            double runningTotal = 0.0;

            // 2.1.3.4: Add the values together before dividing by the number of readings.
            for (int i = 0; i < numberOfReadings; i++) {
                runningTotal = runningTotal + readingsToUse.get(i).getValue();
            }
            averageValue = runningTotal / numberOfReadings;
        }
        return averageValue;
    }

    // 2.1.3.5: Find the lowest value in the selected readings.
    public static double minimum(SensorArray readingsToUse) {
        double minimumValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        // 2.1.3.6: If there are no readings, there is no real minimum, so return zero.
        if (numberOfReadings == 0) {
            minimumValue = 0.0;
        } else {
            // 2.1.3.7: Start with the first reading, then compare the rest against it.
            double smallestSoFar = readingsToUse.get(0).getValue();
            for (int i = 1; i < numberOfReadings; i++) {
                if (readingsToUse.get(i).getValue() < smallestSoFar) {
                    smallestSoFar = readingsToUse.get(i).getValue();
                }
            }
            minimumValue = smallestSoFar;
        }
        return minimumValue;
    }

    // 2.1.3.8: Find the highest value in the selected readings.
    public static double maximum(SensorArray readingsToUse) {
        double maximumValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        // 2.1.3.9: If there are no readings, there is no real maximum, so return zero.
        if (numberOfReadings == 0) {
            maximumValue = 0.0;
        } else {
            // 2.1.3.10: Start with the first reading, then compare the rest against it.
            double largestSoFar = readingsToUse.get(0).getValue();
            for (int i = 1; i < numberOfReadings; i++) {
                if (readingsToUse.get(i).getValue() > largestSoFar) {
                    largestSoFar = readingsToUse.get(i).getValue();
                }
            }
            maximumValue = largestSoFar;
        }
        return maximumValue;
    }

    // 2.1.3.11: Count how many readings are outside their safe range.
    public static int outOfRangeCount(SensorArray readingsToUse) {
        int numberOutOfRange = 0;

        // 2.1.3.12: Let each SensorReading decide if its own value is out of range.
        for (int i = 0; i < readingsToUse.getCount(); i++) {
            if (readingsToUse.get(i).isOutOfRange()) {
                numberOutOfRange = numberOutOfRange + 1;
            }
        }
        return numberOutOfRange;
    }

    // 2.1.3.13: Work out the percentage of readings outside their safe range.
    public static double outOfRangePercent(SensorArray readingsToUse) {
        double percentOutOfRange = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        // 2.1.3.14: Avoid dividing by zero when there are no readings.
        if (numberOfReadings == 0) {
            percentOutOfRange = 0.0;
        } else {
            // 2.1.3.15: Convert the count into a percentage of the total readings.
            int numberOutOfRange = outOfRangeCount(readingsToUse);
            percentOutOfRange = ((double) numberOutOfRange / numberOfReadings) * 100;
        }
        return percentOutOfRange;
    }
}
