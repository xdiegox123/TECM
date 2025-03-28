package src.core.metrics;

import src.interfaces.metrics.coefficientCorrelation;

/**
 * This class implements the `coefficientCorrelation` interface and provides
 * methods to calculate the Pearson correlation coefficient between two
 * arrays and multiple correlation coefficients for multiple independent
 * variables.
 *
 * @see coefficientCorrelation
 */
public class coefficientOfCorrelation implements coefficientCorrelation {

    /**
     * Calculates the Pearson correlation coefficient between two arrays.
     * The Pearson correlation measures the linear relationship between two
     * variables, returning a value between -1 and 1.
     *
     * @param x Array of independent variable values.
     * @param y Array of dependent variable values.
     * @return Pearson correlation coefficient.
     * @throws IllegalArgumentException If the arrays have different lengths.
     */
    @Override
    public float calculatePearson(float[] x, float[] y) {
        float sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;
        int n = x.length;

        // CHECK ARRAY LENGTHS
        if (n != y.length) throw new IllegalArgumentException("Both arrays must have the same length.");

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumXY += x[i] * y[i];
            sumX2 += x[i] * x[i];
            sumY2 += y[i] * y[i];
        }

        return (n * sumXY - sumX * sumY) / ((float) Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY)));
    }

    /**
     * Calculates the multiple correlation coefficient for multiple independent
     * variables. This method computes the Pearson correlation between each
     * independent variable and the dependent variable, then returns the square
     * root of the sum of the squared correlations.
     *
     * @param y Array of dependent variable values.
     * @param x Array of arrays of independent variable values.
     * @return The square root of the sum of the squared Pearson correlations.
     */
    public float calculateMultipleCorrelation(float[] y, float[]... x) {
        int n = x.length;
        float[] correlations = new float[x[0].length];

        // CALCULATE CORRELATIONS
        for (int i = 0; i < x[0].length; i++) {
            float[] predictor = new float[n];
            for (int j = 0; j < n; j++) {
                predictor[j] = x[j][i];
            }
            correlations[i] = calculatePearson(predictor, y);
        }

        // CALCULATE SUM OF SQUARED CORRELATIONS
        double sumSquaredCorrelations = 0.0;
        for (double correlation : correlations) {
            sumSquaredCorrelations += Math.pow(correlation, 2);
        }

        return (float) Math.sqrt(sumSquaredCorrelations);
    }
}

