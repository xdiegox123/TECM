package src.core.metrics;

import src.interfaces.metrics.coefficientDetermination;

/**
 * This class implements the `coefficientDetermination` interface to compute the
 * coefficient of determination (R²). The coefficient of determination is a
 * statistical measure that indicates the proportion of the variance in the
 * dependent variable that is predictable from the independent variables.
 * <p>
 * The calculation is based on the actual values, predicted values, and the mean
 * of the actual values, following the standard formula:
 * <p>
 * R² = 1 - (SS_residual / SS_total)
 * <p>
 * Where:
 * - SS_residual is the sum of squared residuals (differences between actual
 * and predicted values).
 * - SS_total is the total sum of squares (differences between actual values and
 * the mean of the actual values).
 *
 * @see coefficientDetermination
 */
public class CoefficientOfDetermination implements coefficientDetermination {

    /**
     * Calculates the coefficient of determination (R²) for a set of actual and
     * predicted values. This metric represents the proportion of variance in the
     * dependent variable that can be explained by the model.
     * <p>
     * The formula for R² is:
     * <p>
     * R² = 1 - (SS_residual / SS_total)
     * <p>
     * Where SS_residual is the sum of squared differences between actual and
     * predicted values, and SS_total is the sum of squared differences between
     * actual values, and they're mean.
     *
     * @param y      Array of actual dependent variable values.
     * @param y_hat  Array of predicted dependent variable values.
     * @param y_mean The mean of the actual values (y).
     * @return The coefficient of determination (R²).
     * @throws IllegalArgumentException If the total sum of squares (SS_total)
     *                                  or the residual sum of squares (SS_residual) is zero.
     */
    @Override
    public float calculate(float[] y, float[] y_hat, float y_mean) {
        float ss_total = 0;
        float ss_residual = 0;

        // CALCULATE SS_TOTAL AND SS_RESIDUAL
        for (int i = 0; i < y.length; i++) {
            ss_total += (float) Math.pow(y[i] - y_mean, 2);
            ss_residual += (float) Math.pow(y[i] - y_hat[i], 2);
        }

        if (ss_total == 0 || ss_residual == 0) {
            throw new IllegalArgumentException("Cannot calculate R-squared with zero variance");
        }

        return 1 - (ss_residual / ss_total);
    }
}
