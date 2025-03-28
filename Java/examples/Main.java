package examples;

/**
 * Main class for running examples of regression models.
 * This class demonstrates the usage of Simple Linear Regression and Least Squared Regression.
 */
public class Main {

    /**
     * Main method to execute the examples.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Run Simple Linear Regression example.
        handsOn2();
        // Run Least Squared Regression example.
        handsOn3();
    }

    /**
     * Demonstrates the Least Squared Regression example.
     * Initializes and runs the Least Squared Regression with different degrees.
     */
    private static void handsOn3() {
        // Init degree 1.
        LeastSquaredRegressionExample leastSquaredRegDeg1 = new LeastSquaredRegressionExample();
        // Init degree 2.
        LeastSquaredRegressionExample leastSquaredRegDeg2 = new LeastSquaredRegressionExample();
        // Init degree 3.
        LeastSquaredRegressionExample leastSquaredRegDeg3 = new LeastSquaredRegressionExample();
        // Run degree 1.
        leastSquaredRegDeg1.initialize(3f, 1);
        // Run degree 2.
        leastSquaredRegDeg2.initialize(3f, 2);
        // Run degree 3.
        leastSquaredRegDeg3.initialize(3f, 3);
    }

    /**
     * Demonstrates the Simple Linear Regression example.
     * Initializes and runs the Simple Linear Regression.
     */
    private static void handsOn2() {
        // Init SLR example.
        SimpleLinearRegressionExample simpleLinearReg = new SimpleLinearRegressionExample();
        // Run SLR example.
        simpleLinearReg.initialize(.3f);
    }
}
