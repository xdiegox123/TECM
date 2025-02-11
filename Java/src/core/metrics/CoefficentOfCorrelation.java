package src.core.metrics;

import src.interfaces.metrics.coefficentCorrelation;

/**
 * This class implements the `coefficentCorrelation` interface and provides
 * the method to calculate the Pearson correlation coefficient between two
 * arrays.
 * 
 * @see coefficentCorrelation
 */
public class CoefficentOfCorrelation implements coefficentCorrelation {

    @Override
    public float calculatePearson(float[] x, float[] y) {
        float sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0, n = x.length;

        if (n != y.length)
            throw new IllegalArgumentException("Both arrays must have the same length.");

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
            sumY2 += y[i] * y[i];
        }

        float pearson = (n * sumXY - sumX * sumY)
                / ((float) Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY)));

        return pearson;
    }

}
