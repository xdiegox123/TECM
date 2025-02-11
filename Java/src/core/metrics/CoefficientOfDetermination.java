package src.core.metrics;

import src.interfaces.metrics.coefficientDetermination;

/**
 * This class implements the `coefficientDetermination` interface to compute the
 * coefficient of determination (R²).
 * The calculation is based on the actual values, predicted values, and the mean
 * of the actual values, following the standard formula.
 * 
 * @see coefficientDetermination
 */
public class CoefficientOfDetermination implements coefficientDetermination {
    @Override
    public float calculate(float[] y, float[] y_pred, float y_mean) {
        float ss_total = 0;
        float ss_residual = 0;

        for (int i = 0; i < y.length; i++) {
            ss_total += Math.pow(y[i] - y_mean, 2);
            ss_residual += Math.pow(y[i] - y_pred[i], 2);
        }

        if (ss_total == 0 || ss_residual == 0) {
            throw new IllegalArgumentException("Cannot calculate R-squared with zero variance");
        }

        float r_squared = 1 - (ss_residual / ss_total);
        return r_squared;
    }

}
