package src.core.predictivemodels;

import src.core.metrics.MeanSquaredError;
import src.core.metrics.coefficientOfCorrelation;
import src.core.metrics.CoefficientOfDetermination;
import src.core.models.SimpleLinearRegressionModel;
import src.core.utils.DataSplitter;
import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.SplitData;
import src.core.utils.dtos.TestData;
import src.interfaces.abstracts.Regressor;
import src.interfaces.models.PredictiveModel;

/**
 * Implements a Simple Linear Regression (SLR) model.
 * This class trains a simple linear regression model using provided data,
 * computes the model parameters (intercept and slope), and makes predictions
 * for new data.
 *
 * @see SimpleLinearRegressionModel
 */
public class SimpleLinearRegression implements PredictiveModel {

    private final float[] independentX;
    private final float[] dependentY;
    private final RegressionParams slrParams = new RegressionParams();

    /**
     * Constructor to initialize SLR with independent (X) and dependent (Y) data.
     *
     * @param independentX Array of independent variable values (X).
     * @param dependentY   Array of dependent variable values (Y).
     */
    public SimpleLinearRegression(float[] independentX, float[] dependentY) {
        this.independentX = independentX;
        this.dependentY = dependentY;
    }

    /**
     * Trains the model by calculating the regression parameters (intercept and
     * slope) and returns the test data.
     * <p>
     * The data is split into training and testing sets based on the provided
     * segmentation percentage and splitting mode.
     * </p>
     *
     * @param splittingMode       Mode for splitting data: 0 = sequential, 1 =
     *                            random, 2 = intercalate.
     * @param segmentationPercent Percentage of data used for training (between 0
     *                            and 1).
     * @param model               The regression model to use:
     *                            <ul>
     *                            <li>1 -> Simple Linear Regression.</li>
     *                            <li>2 -> Multiple or Polynomial Regression (Least
     *                            Squares Regression).</li>
     *                            </ul>
     * @return The test data containing the independent (X) and dependent (Y) test
     * values.
     * @throws IllegalArgumentException If input data is invalid or if an invalid
     *                                  model selection is provided.
     * @see DataSplitter
     */
    @Override
    public TestData training(int splittingMode, float segmentationPercent, Regressor model) {
        DataSplitter dataSplitter = new DataSplitter(dependentY, segmentationPercent, independentX);
        MeanSquaredError mse = new MeanSquaredError();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        TestData testData = new TestData();
        float[] predictedValues = new float[dependentY.length];
        float yMean = 0.0f;

        // SPLIT DATA
        SplitData splitData = dataSplitter.split(splittingMode);
        testData.setTestDependentY(splitData.getTestDependentlyY());
        testData.setTestIndependentlyX(splitData.getTestIndependentlyX());

        // SIMPLE LINEAR REGRESSION
        float[] modelResult = model.computeRegression(splitData.getTrainDependentlyY(), splitData.getTrainIndependentlyX());
        slrParams.setbZero(modelResult[0]);
        slrParams.setbOne(modelResult[1]);

        // CORRELATION COEFFICIENT AND DETERMINATION
        for (int i = 0; i < dependentY.length; i++) {
            predictedValues[i] = (slrParams.getbZero() + (slrParams.getbOne() * independentX[i]));
        }

        // CALCULATE MEAN OF Y
        for (float v : dependentY) {
            yMean += v;
        }
        yMean /= dependentY.length;

        // SET R^2 AND PEARSON COEFFICIENT
        slrParams.setCod(cod.calculate(dependentY, predictedValues, yMean));
        slrParams.setCof(mse.getMeanSquaredError(dependentY, predictedValues));
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
     * Pearson R.
     * @throws IllegalStateException If the 'training' method has not been called to
     *                               calculate parameters.
     * @see coefficientOfCorrelation
     * @see CoefficientOfDetermination
     */
    @Override
    public RegressionParams getParameters() {
        if (slrParams.getbOne() == 0.0f && slrParams.getbZero() == 0.0f) {
            throw new IllegalStateException("The 'training' method must be executed first to calculate the parameters.");
        }
        return slrParams;
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
     * @see SimpleLinearRegression#getParameters()
     */
    @Override
    public float[] prediction(float[] inputValues) {
        float[] predictedValues = new float[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            predictedValues[i] = (slrParams.getbZero() + (slrParams.getbOne() * inputValues[i]));
        }
        return predictedValues;
    }

    @Override
    public float[] multiPredictionMultiple(float[][] inputValues) {
        return new float[0];
    }
}
