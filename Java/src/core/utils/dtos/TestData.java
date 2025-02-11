package src.core.utils.dtos;

/**
 * Data Transfer Object (DTO) for storing the test data: test datasets for both
 * independent and dependent variables.
 */
public class TestData {

    private float[] testIndependentlyX;
    private float[] testDependentlyY;

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
