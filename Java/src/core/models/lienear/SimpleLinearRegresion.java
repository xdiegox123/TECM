package src.core.models.lienear;

import src.interfaces.models.SimpleRegresion;

/**
 * This class implements the `SimpleRegresion` interface and provides
 * functionality for performing simple linear regression.
 * It computes the regression coefficients (b0 and b1) based on the given
 * independent (x) and dependent (y) variables.
 * 
 * @see SimpleRegresion
 */
public class SimpleLinearRegresion implements SimpleRegresion {

    private float sumX = 0.0f, sumY = 0.0f, sumXY = 0.0f, sumXSquare = 0.0f;

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
    public float[] computeRegression(float[] x, float[] y) {

        if (x == null || y == null)
            throw new IllegalArgumentException("Both arrays must not be null.");
        if (x.length != y.length)
            throw new IllegalArgumentException("Both arrays must have the same length.");

        int n = x.length;
        float b0 = 0.0f, b1 = 0.0f;

        for (int i = 0; i < n; i++) {
            sumY += y[i];
            sumXSquare += x[i] * x[i];
            sumXY += x[i] * y[i];
            sumX += x[i];
        }

        b1 = ((n * sumXY) - sumX * sumY) / ((n * sumXSquare) - sumX * sumX);
        b0 = (sumY - b1 * sumX) / n;
        return new float[] { b0, b1 };
    }

    /**
     * Retrieves the computed regression components, such as sumX, sumY, sumXY, and
     * sumXSquare.
     * 
     * @return An array containing the regression components.
     */
    @Override
    public float[] getRegressionComponents() {
        return new float[] { sumX, sumY, sumXY, sumXSquare };
    }
}
