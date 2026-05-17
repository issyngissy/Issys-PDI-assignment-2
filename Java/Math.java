// Calculates statistics for a SensorArray.
public class Math {

    public static int totalReadings(SensorArray readingsToUse) {
        return readingsToUse.getCount();
    }

    public static double average(SensorArray readingsToUse) {
        double averageValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        if (numberOfReadings == 0) {
            averageValue = 0.0;
        } else {
            double runningTotal = 0.0;

            for (int i = 0; i < numberOfReadings; i++) {
                runningTotal = runningTotal + readingsToUse.get(i).getValue();
            }
            averageValue = runningTotal / numberOfReadings;
        }
        return averageValue;
    }

    public static double minimum(SensorArray readingsToUse) {
        double minimumValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        if (numberOfReadings == 0) {
            minimumValue = 0.0;
        } else {
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

    public static double maximum(SensorArray readingsToUse) {
        double maximumValue = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        if (numberOfReadings == 0) {
            maximumValue = 0.0;
        } else {
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

    public static int outOfRangeCount(SensorArray readingsToUse) {
        int numberOutOfRange = 0;

        for (int i = 0; i < readingsToUse.getCount(); i++) {
            if (readingsToUse.get(i).isOutOfRange()) {
                numberOutOfRange = numberOutOfRange + 1;
            }
        }
        return numberOutOfRange;
    }

    public static double outOfRangePercent(SensorArray readingsToUse) {
        double percentOutOfRange = 0.0;
        int numberOfReadings = readingsToUse.getCount();

        if (numberOfReadings == 0) {
            percentOutOfRange = 0.0;
        } else {
            int numberOutOfRange = outOfRangeCount(readingsToUse);
            percentOutOfRange = ((double) numberOutOfRange / numberOfReadings) * 100;
        }
        return percentOutOfRange;
    }
}
