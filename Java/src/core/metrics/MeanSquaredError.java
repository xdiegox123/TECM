package src.core.metrics;

/**
 * This class provides a method to calculate the Mean Squared Error (MSE).
 * <p>
 * Mean Squared Error (MSE) is a metric used to evaluate the accuracy of a model's
 * predictions. It measures the average of the squared differences between the
 * actual values and the predicted values.
 * <p>
 * The formula for MSE is:
 * <p>
 * MSE = (1/n) * Σ(y_i - ŷ_i)²
 * <p>
 * Where:
 * - y_i is the actual value.
 * - ŷ_i is the predicted value.
 * - n is the number of data points.
 */
public class MeanSquaredError {

    /**
     * Calculates the Mean Squared Error (MSE) between the actual and predicted values.
     * <p>
     * The method computes the average of the squared differences between the
     * corresponding elements of the actual values (y) and the predicted values (yhat).
     *
     * @param y    The array of actual dependent variable values.
     * @param yhat The array of predicted dependent variable values.
     * @return The Mean Squared Error (MSE) value.
     * @throws IllegalArgumentException If the length of the input arrays do not match.
     */
    public float getMeanSquaredError(float[] y, float[] yhat) {
        if (y.length != yhat.length) {
            throw new IllegalArgumentException("Input arrays must have the same length.");
        }

        float mse = 0f;

        // CALCULATE MSE
        for (int i = 0; i < y.length; i++) {
            mse += (float) Math.pow((y[i] - yhat[i]), 2);
        }

        return mse / y.length;
    }
}
