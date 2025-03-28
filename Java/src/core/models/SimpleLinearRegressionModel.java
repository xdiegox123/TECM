package src.core.models;

import src.interfaces.abstracts.Regressor;

/**
 * This class implements the `SimpleRegression` interface and provides
 * functionality for performing simple linear regression.
 * It computes the regression coefficients (b0 and b1) based on the given
 * independent (x) and dependent (y) variables.
 * 
 * @see src.core.predictivemodels.SimpleLinearRegression
 */
public class SimpleLinearRegressionModel extends Regressor {

    /**
     * Computes the coefficients for the simple linear regression model (b0 and b1).
     * 
     * @param x The independent variable values.
     * @param y The dependent variable values.
     * @return An array containing the calculated values for b0 and b1.
     * @throws IllegalArgumentException If the input arrays are null or have
     *                                  different lengths.
     */
    @Override
    public float[] computeRegression(float[] y, float[] x) {
        if (x == null || y == null)
            throw new IllegalArgumentException("Both arrays must not be null.");

        float sumY = 0.0f, sumX = 0.0f, sumXY = 0.0f, sumXSquare = 0.0f;
        int n = x.length;

        for (int i = 0; i < n; i++) {
            sumY += y[i];
            sumXSquare += x[i] * x[i];
            sumXY += x[i] * y[i];
            sumX += x[i];
        }
        float b1 = ((n * sumXY) - sumX * sumY) / ((n * sumXSquare) - sumX * sumX);
        float b0 = (sumY - b1 * sumX) / n;

        return new float[] { b0, b1 };
    }

}
