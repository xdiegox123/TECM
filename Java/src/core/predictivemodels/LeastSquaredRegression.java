package src.core.predictivemodels;

import src.core.metrics.MeanSquaredError;
import src.core.metrics.CoefficientOfDetermination;
import src.core.utils.DataSplitter;
import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.SplitData;
import src.core.utils.dtos.TestData;
import src.interfaces.abstracts.Regressor;
import src.interfaces.models.PredictiveModel;

/**
 * Implements the Least Squares Regression (LSR) model.
 * This class uses the Least Squares method to perform regression on a set of data,
 * compute the regression parameters, and make predictions for new data.
 *
 * @see src.core.models.LeastSquaredRegressionModel
 */
public class LeastSquaredRegression implements PredictiveModel {

    private final float[] dependentY;  // Dependent variable (Y) values
    private final float[][] independentX;  // Independent variable (X) values
    private final RegressionParams slrParams = new RegressionParams();  // Stores regression parameters (coefficients)

    /**
     * Constructor to initialize the Least Squares Regression with dependent (Y) and independent (X) data.
     *
     * @param y Array of dependent variable values (Y).
     * @param x Varargs array of independent variable values (X).
     */
    public LeastSquaredRegression(float[] y, float[]... x) {
        this.dependentY = y;
        this.independentX = x;
    }

    /**
     * Trains the regression model using the Least Squares method.
     * <p>
     * The method splits the data into training and testing sets, calculates the regression
     * parameters (coefficients), and computes performance metrics like the coefficient of determination (R²)
     * and the Mean Squared Error (MSE).
     * </p>
     *
     * @param splittingMode       The mode of splitting the data:
     *                            <ul>
     *                            <li>0: Original</li>
     *                            <li>1: Sequential split</li>
     *                            <li>2: Random split</li>
     *                            <li>3: Intercalate split</li>
     *                            </ul>
     * @param segmentationPercent The percentage of data to be used for training (between 0 and 1).
     * @param model               The regression model to use for calculation.
     * @return A TestData object containing the test data after splitting.
     * @throws IllegalArgumentException If there is an error with the data or model.
     */
    @Override
    public TestData training(int splittingMode, float segmentationPercent, Regressor model) {
        DataSplitter dataSplitter = new DataSplitter(dependentY, segmentationPercent, 1, independentX);
        MeanSquaredError mse = new MeanSquaredError();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        TestData testData = new TestData();
        float[] predictedValues = new float[dependentY.length];
        float yMean = 0.0f;

        // SPLIT DATA
        SplitData splitData = dataSplitter.split(splittingMode);
        testData.setTestDependentY(splitData.getTestDependentlyY());
        testData.setTestIndependentlyMatrixX(splitData.getTestIndependentlyMatrixX());

        // LEAST SQUARES REGRESSION: Calculate coefficients using the regression model
        slrParams.setBn(model.computeRegression(3, splitData.getTrainDependentlyY(), splitData.getTrainIndependentlyMatrixX()));

        // CORRELATION COEFFICIENT AND DETERMINATION: Calculate predicted values
        for (int i = 0; i < independentX.length; i++) {
            predictedValues[i] = slrParams.getBn()[0];
            for (int j = 1; j < slrParams.getBn().length; j++) {
                predictedValues[i] += (slrParams.getBn()[j] * independentX[i][j - 1]);
            }
        }

        // CALCULATE MEAN OF Y: Compute the mean of the dependent variable values (Y)
        for (float v : dependentY) {
            yMean += v;
        }
        yMean /= dependentY.length;

        // SET R^2 AND PEARSON COEFFICIENT: Calculate performance metrics
        slrParams.setCod(cod.calculate(dependentY, predictedValues, yMean));
        slrParams.setCof(mse.getMeanSquaredError(dependentY, predictedValues));
        return testData;
    }

    public TestData training(int splittingMode, float segmentationPercent, int degree, Regressor model) {
        DataSplitter dataSplitter = new DataSplitter(dependentY, segmentationPercent, degree, independentX);
        MeanSquaredError mse = new MeanSquaredError();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        TestData testData = new TestData();
        float[] predictedValues = new float[dependentY.length];
        float yMean = 0.0f;

        // SPLIT DATA
        SplitData splitData = dataSplitter.split(splittingMode);
        testData.setTestDependentY(splitData.getTestDependentlyY());
        testData.setTestIndependentlyMatrixX(splitData.getTestIndependentlyMatrixX());

        // LEAST SQUARES REGRESSION: Calculate coefficients using the regression model
        slrParams.setBn(model.computeRegression(degree, splitData.getTrainDependentlyY(), splitData.getTrainIndependentlyMatrixX()));

        // CORRELATION COEFFICIENT AND DETERMINATION: Calculate predicted values
        for (int i = 0; i < independentX.length; i++) {
            predictedValues[i] = slrParams.getBn()[0];
            for (int j = 1; j < slrParams.getBn().length; j++) {
                predictedValues[i] += (slrParams.getBn()[j] * independentX[i][j - 1]);
            }
        }

        // CALCULATE MEAN OF Y: Compute the mean of the dependent variable values (Y)
        for (float v : dependentY) {
            yMean += v;
        }
        yMean /= dependentY.length;

        // SET R^2 AND PEARSON COEFFICIENT: Calculate performance metrics
        slrParams.setCod(cod.calculate(dependentY, predictedValues, yMean));
        slrParams.setCof(mse.getMeanSquaredError(dependentY, predictedValues));
        return testData;
    }

    /**
     * Retrieves the regression model parameters (coefficients) after training.
     *
     * @return A RegressionParams object containing the coefficients and performance metrics.
     * @throws IllegalStateException If the training method has not been executed yet.
     */
    @Override
    public RegressionParams getParameters() {
        if (slrParams.getBn() == null) {
            throw new IllegalStateException("The 'training' method must be executed first to calculate the parameters.");
        }
        return slrParams;
    }

    /**
     * Makes predictions for a set of input values using the trained model.
     * <p>
     * This method is not implemented for LeastSquaresRegression, as it is designed to
     * handle multiple input variables.
     * </p>
     *
     * @param inputValues Array of independent variable values (X) for prediction.
     * @return Array of predicted dependent variable values (Y).
     * @throws UnsupportedOperationException If this method is called directly.
     */
    @Override
    public float[] prediction(float[] inputValues) {
        throw new UnsupportedOperationException("This method is not supported for LeastSquaredRegression. Use multiPrediction methods.");
    }


    /**
     * Makes multiple predictions using the trained model for multiple sets of input values.
     *
     * @param inputValues A 2D array where each row is a set of independent variable values (X).
     * @return Array of predicted dependent variable values (Y).
     */
    @Override
    public float[] multiPredictionMultiple(float[][] inputValues) {
        // Asumiendo que el número de coeficientes es el número de términos polinomiales
        int index = 0;
        float[] predictedValues = new float[inputValues[0].length];  // Predicciones para cada conjunto de valores de entrada

        // Iterar sobre cada conjunto de datos de entrada (cada fila de inputValues)
        float prediction = slrParams.getBn()[0];
        for (float[] inputValue : inputValues) {  // Iterar sobre el número de características (columnas)
            // Agregar los términos del polinomio: b1 * x + b2 * x^2 + b3 * x^3 + ...
            for (float value : inputValue) {
                for (int i = 1; i < slrParams.getBn().length; i++) {
                    prediction += (float) (slrParams.getBn()[i] * Math.pow(value, i));
                }
                predictedValues[index] = prediction;
                index++;
            }
        }
        return predictedValues;
    }
}
