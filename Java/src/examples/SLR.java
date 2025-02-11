package src.examples;

import src.core.metrics.CoefficentOfCorrelation;
import src.core.metrics.CoefficientOfDetermination;
import src.core.models.lienear.SimpleLinearRegresion;
import src.core.utils.DataSplitter;
import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.SplitDataResult;
import src.core.utils.dtos.TestData;
import src.interfaces.examples.Regresions;

/**
 * Implements a simple linear regression (SLR) model.
 * This class trains a simple linear regression model using provided data,
 * computes the model parameters (intercept and slope), and makes predictions
 * for new data.
 * 
 * @see SimpleLinearRegresion
 */
public class SLR implements Regresions {

    private float[] independentlyX;
    private float[] dependentlyY;
    private RegressionParams params = new RegressionParams();
    SimpleLinearRegresion slr = new SimpleLinearRegresion();

    /**
     * Constructor to initialize SLR with independent (X) and dependent (Y) data.
     * 
     * @param independentlyX Array of independent variable values (X).
     * @param dependently    Array of dependent variable values (Y).
     */
    public SLR(float[] independentlyX, float[] dependently) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependently;
    }

    /**
     * Trains the model by calculating the regression parameters (intercept and
     * slope)
     * and returns the test data.
     * <p>
     * The data is split into training and testing sets based on the provided
     * segmentation percentage and splitting mode.
     * </p>
     * 
     * @param splitingMode        Mode for splitting data: 0 = sequential, 1 =
     *                            random,
     *                            2 = intercalated.
     * @param segmentationPercent Percentage of data used for training (between 0
     *                            and 1).
     * @return The test data containing the independent (X) and dependent (Y) test
     *         values.
     * @throws IllegalArgumentException If input data is invalid.
     * 
     * @see DataSplitter
     */
    @Override
    public TestData training(int splitingMode, float segmentationPercent) {
        DataSplitter splitterData = new DataSplitter(independentlyX, dependentlyY, segmentationPercent);
        SplitDataResult splitedData = splitterData.split(splitingMode);
        TestData testData = new TestData();
        testData.setTestDependentlyY(splitedData.getTestDependentlyY());
        testData.setTestIndependentlyX(splitedData.getTestIndependentlyX());
        float[] modelResult = slr.computeRegression(splitedData.getTrainIndependentlyX(),
                splitedData.getTrainDependentlyY());
        params.setbZero(modelResult[0]);
        params.setbOne(modelResult[1]);
        return testData;
    }

    /**
     * Retrieves the model parameters and calculates performance metrics.
     * <p>
     * This method calculates the predicted values, the coefficient of determination
     * (R²),
     * and the Pearson correlation coefficient (R) based on the model parameters.
     * </p>
     * 
     * @return The model parameters including intercept (b0), slope (b1), R², and
     *         Pearson R.
     * @throws IllegalStateException If the 'training' method has not been called to
     *                               calculate parameters.
     * 
     * @see CoefficentOfCorrelation
     * @see CoefficientOfDetermination
     */
    @Override
    public RegressionParams getParameters() {
        if (params.getbOne() == 0.0f && params.getbZero() == 0.0f) {
            throw new IllegalStateException(
                    "The 'training' method must be executed first to calculate the parameters.");
        }
        CoefficentOfCorrelation cof = new CoefficentOfCorrelation();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        float[] predictedValues = new float[dependentlyY.length];
        float y_mean = 0.0f;
        for (int i = 0; i < dependentlyY.length; i++) {
            predictedValues[i] = (params.getbZero() + (params.getbOne() * independentlyX[i]));
        }
        for (int i = 0; i < dependentlyY.length; i++) {
            y_mean += dependentlyY[i];
        }
        y_mean /= dependentlyY.length;
        params.setCod(cod.calculate(dependentlyY, predictedValues, y_mean));
        params.setCof(cof.calculatePearson(independentlyX, dependentlyY));
        return params;
    }

    /**
     * Makes predictions for the provided input values using the trained model.
     * <p>
     * Each input value is predicted based on the model's parameters (intercept and
     * slope).
     * </p>
     * 
     * @param inputValues Array of independent variable values (X) for which
     *                    predictions are needed.
     * @return Array of predicted dependent variable values (Y).
     * @throws IllegalStateException If the model parameters have not been
     *                               calculated.
     * 
     * @see SLR#getParameters()
     */
    @Override
    public float[] prediction(float[] inputValues) {
        float[] predictedValues = new float[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            predictedValues[i] = (params.getbZero() + (params.getbOne() * inputValues[i]));
        }
        return predictedValues;
    }

}
