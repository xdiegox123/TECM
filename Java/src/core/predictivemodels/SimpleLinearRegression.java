package src.core.predictivemodels;

import src.core.metrics.CoefficentOfCorrelation;
import src.core.metrics.CoefficientOfDetermination;
import src.core.models.SimpleLinearRegressionModel;
import src.core.utils.DataSplitter;
import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.SplitDataResult;
import src.core.utils.dtos.TestData;
import src.interfaces.models.PredictiveModel;

/**
 * Implements a simple linear regression (SLR) model.
 * This class trains a simple linear regression model using provided data,
 * computes the model parameters (intercept and slope), and makes predictions
 * for new data.
 * 
 * @see SimpleLinearRegresion
 */
public class SimpleLinearRegression implements PredictiveModel {

    private float[] independentlyX;
    private float[] dependentlyY;
    private RegressionParams SlrParams = new RegressionParams();
    private SimpleLinearRegressionModel slr = new SimpleLinearRegressionModel();

    /**
     * Constructor to initialize SLR with independent (X) and dependent (Y) data.
     * 
     * @param independentlyX Array of independent variable values (X).
     * @param dependently    Array of dependent variable values (Y).
     */
    public SimpleLinearRegression(float[] independentlyX, float[] dependently) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependently;
    }

    /**
     * Trains the model by calculating the regression parameters (intercept and
     * slope) and returns the test data.
     * <p>
     * The data is split into training and testing sets based on the provided
     * segmentation percentage and splitting mode.
     * </p>
     * 
     * @param splitingMode        Mode for splitting data: 0 = sequential, 1 =
     *                            random, 2 = intercalated.
     * @param segmentationPercent Percentage of data used for training (between 0
     *                            and 1).
     * @param selectModel         The regression model to use:
     *                            <ul>
     *                            <li>1 -> Linear Regression (Simple Linear
     *                            Regression).</li>
     *                            <li>2 -> Multiple or Polynomial Regression (Least
     *                            Squares Regression).</li>
     *                            </ul>
     * @return The test data containing the independent (X) and dependent (Y) test
     *         values.
     * @throws IllegalArgumentException If input data is invalid or if an invalid
     *                                  model selection is provided.
     * 
     * @see DataSplitter
     */
    @Override
    public TestData training(int splitingMode, float segmentationPercent, int selectModel) {
        if (selectModel != 1 && selectModel != 2) {
            throw new IllegalArgumentException("Invalid model selection. Use 1 for Linear Regression or 2 for Multiple/Polynomial Regression.");
        }
        DataSplitter splitterData = new DataSplitter(independentlyX, dependentlyY, segmentationPercent);
        CoefficentOfCorrelation cof = new CoefficentOfCorrelation();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        TestData testData = new TestData();
        float[] predictedValues = new float[dependentlyY.length];
        float y_mean = 0.0f;

        // SPLITING DATA
        SplitDataResult splitedData = splitterData.split(splitingMode);
        testData.setTestDependentlyY(splitedData.getTestDependentlyY());
        testData.setTestIndependentlyX(splitedData.getTestIndependentlyX());

        // SIMPLE LINEAR REGRESSION
        float[] modelResult = slr.computeRegression(splitedData.getTrainIndependentlyX(),
                splitedData.getTrainDependentlyY());
        SlrParams.setbZero(modelResult[0]);
        SlrParams.setbOne(modelResult[1]);

        // CORRELATION COEFFICENT AND DETERMINATION
        for (int i = 0; i < dependentlyY.length; i++) {
            predictedValues[i] = (SlrParams.getbZero() + (SlrParams.getbOne() * independentlyX[i]));
        }
        for (int i = 0; i < dependentlyY.length; i++) {
            y_mean += dependentlyY[i];
        }
        y_mean /= dependentlyY.length;
        SlrParams.setCod(cod.calculate(dependentlyY, predictedValues, y_mean));
        SlrParams.setCof(cof.calculatePearson(independentlyX, dependentlyY));
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
        if (SlrParams.getbOne() == 0.0f && SlrParams.getbZero() == 0.0f) {
            throw new IllegalStateException(
                    "The 'training' method must be executed first to calculate the parameters.");
        }
        return SlrParams;
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
            predictedValues[i] = (SlrParams.getbZero() + (SlrParams.getbOne() * inputValues[i]));
        }
        return predictedValues;
    }

}
