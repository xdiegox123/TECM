package src.core.utils.dtos;

/**
 * Data Transfer Object (DTO) for storing the split data, including both the
 * training and testing datasets for independent and dependent variables.
 * This object is used to encapsulate the data into a single structure for
 * easier handling when performing data splitting operations.
 */
public class SplitData {

    // Instance attributes
    private float[] trainIndependentlyX; // Independent variables for training
    private float[] trainDependentlyY;   // Dependent variables for training
    private float[] testIndependentlyX;  // Independent variables for testing
    private float[][] trainIndependentlyMatrixX; // Independent variables for training (matrix format)
    private float[][] testIndependentlyMatrixX;  // Independent variables for testing (matrix format)
    private float[] testDependentlyY;    // Dependent variables for testing

    // Getters and Setters

    /**
     * Gets the matrix of independent variables for the training set.
     *
     * @return The matrix of independent variables for training.
     */
    public float[][] getTrainIndependentlyMatrixX() {
        return trainIndependentlyMatrixX;
    }

    /**
     * Sets the matrix of independent variables for the training set.
     *
     * @param trainIndependentlyMatrixX The matrix of independent variables for training.
     */
    public void setTrainIndependentlyMatrixX(float[][] trainIndependentlyMatrixX) {
        this.trainIndependentlyMatrixX = trainIndependentlyMatrixX;
    }

    /**
     * Gets the matrix of independent variables for the testing set.
     *
     * @return The matrix of independent variables for testing.
     */
    public float[][] getTestIndependentlyMatrixX() {
        return testIndependentlyMatrixX;
    }

    /**
     * Sets the matrix of independent variables for the testing set.
     *
     * @param testIndependentlyMatrixX The matrix of independent variables for testing.
     */
    public void setTestIndependentlyMatrixX(float[][] testIndependentlyMatrixX) {
        this.testIndependentlyMatrixX = testIndependentlyMatrixX;
    }

    /**
     * Gets the array of independent variables for the training set.
     *
     * @return The array of independent variables for training.
     */
    public float[] getTrainIndependentlyX() {
        return trainIndependentlyX;
    }

    /**
     * Sets the array of independent variables for the training set.
     *
     * @param trainIndependentlyX The array of independent variables for training.
     */
    public void setTrainIndependentlyX(float[] trainIndependentlyX) {
        this.trainIndependentlyX = trainIndependentlyX;
    }

    /**
     * Gets the array of dependent variables for the training set.
     *
     * @return The array of dependent variables for training.
     */
    public float[] getTrainDependentlyY() {
        return trainDependentlyY;
    }

    /**
     * Sets the array of dependent variables for the training set.
     *
     * @param trainDependentlyY The array of dependent variables for training.
     */
    public void setTrainDependentlyY(float[] trainDependentlyY) {
        this.trainDependentlyY = trainDependentlyY;
    }

    /**
     * Gets the array of independent variables for the testing set.
     *
     * @return The array of independent variables for testing.
     */
    public float[] getTestIndependentlyX() {
        return testIndependentlyX;
    }

    /**
     * Sets the array of independent variables for the testing set.
     *
     * @param testIndependentlyX The array of independent variables for testing.
     */
    public void setTestIndependentlyX(float[] testIndependentlyX) {
        this.testIndependentlyX = testIndependentlyX;
    }

    /**
     * Gets the array of dependent variables for the testing set.
     *
     * @return The array of dependent variables for testing.
     */
    public float[] getTestDependentlyY() {
        return testDependentlyY;
    }

    /**
     * Sets the array of dependent variables for the testing set.
     *
     * @param testDependentlyY The array of dependent variables for testing.
     */
    public void setTestDependentlyY(float[] testDependentlyY) {
        this.testDependentlyY = testDependentlyY;
    }
}
