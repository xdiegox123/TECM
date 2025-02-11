package src.core.utils.dtos;

/**
 * Data Transfer Object (DTO) for storing the split data: training and testing
 * datasets for both independent and dependent variables.
 */
public class SplitDataResult {
    private float[] trainIndependentlyX;
    private float[] trainDependentlyY;
    private float[] testIndependentlyX;
    private float[] testDependentlyY;

    public float[] getTrainIndependentlyX() {
        return trainIndependentlyX;
    }

    public void setTrainIndependentlyX(float[] trainIndependentlyX) {
        this.trainIndependentlyX = trainIndependentlyX;
    }

    public float[] getTrainDependentlyY() {
        return trainDependentlyY;
    }

    public void setTrainDependentlyY(float[] trainDependentlyY) {
        this.trainDependentlyY = trainDependentlyY;
    }

    public float[] getTestIndependentlyX() {
        return testIndependentlyX;
    }

    public void setTestIndependentlyX(float[] testIndependentlyX) {
        this.testIndependentlyX = testIndependentlyX;
    }

    public float[] getTestDependentlyY() {
        return testDependentlyY;
    }

    public void setTestDependentlyY(float[] testDependentlyY) {
        this.testDependentlyY = testDependentlyY;
    }

}
