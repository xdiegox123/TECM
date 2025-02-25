package src.core.models;

import src.interfaces.models.Regressor;

/**
 * This class implements the `SimpleRegresion` interface and provides
 * functionality for performing simple linear regression.
 * It computes the regression coefficients (b0 and b1) based on the given
 * independent (x) and dependent (y) variables.
 * 
 * @see SimpleRegresion
 */
public class SimpleLinearRegressionModel implements Regressor {

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
    public float[] computeRegression(float[] y, float[]... x) {
        if (x == null || y == null)
            throw new IllegalArgumentException("Both arrays must not be null.");
        if (x.length == 1 && x[0].length != y.length)
            throw new IllegalArgumentException("Both arrays must have the same length.");
        if (x.length > 1)
            throw new IllegalArgumentException("Please select a multiple regression, not a simple one.");

        float sumY = 0.0f, sumX = 0.0f, sumXY = 0.0f, sumXSquare = 0.0f;
        float[] xData = x[0];
        int n = xData.length;

        for (int i = 0; i < n; i++) {
            sumY += y[i];
            sumXSquare += xData[i] * xData[i];
            sumXY += xData[i] * y[i];
            sumX += xData[i];
        }
        float b1 = ((n * sumXY) - sumX * sumY) / ((n * sumXSquare) - sumX * sumX);
        float b0 = (sumY - b1 * sumX) / n;

        return new float[] { b0, b1 };
    }

}
