package src.core.utils.dtos;

/**
 * Data Transfer Object (DTO) for storing the test data: test datasets for both
 * independent and dependent variables.
 */
public class TestData {

    private float[] testIndependentX;
    private float[] testDependentY;
    private float[][] testIndependentlyMatrixX;  // Independent variables for testing (matrix format)

    public float[] getTestIndependentlyX() {
        return testIndependentX;
    }

    public void setTestIndependentlyX(float[] testIndependentlyX) {
        this.testIndependentX = testIndependentlyX;
    }

    public float[] getTestDependentY() {
        return testDependentY;
    }

    public void setTestDependentY(float[] testDependentlyY) {
        this.testDependentY = testDependentlyY;
    }

    public float[][] getTestIndependentlyMatrixX() {
        return testIndependentlyMatrixX;
    }

    public void setTestIndependentlyMatrixX(float[][] testIndependentlyMatrixX) {
        this.testIndependentlyMatrixX = testIndependentlyMatrixX;
    }
}
