package src.examples;

import src.core.metrics.CoefficentOfCorrelation;
import src.core.metrics.CoefficientOfDetermination;
import src.core.models.lienear.SimpleLinearRegresion;
import src.core.utils.SplittedData;
import src.core.utils.SplitingData;
import src.core.utils.TestData;
import src.interfaces.examples.Regresions;

public class SLR implements Regresions {

    private float[] independentlyX;
    private float[] dependentlyY;
    private float[] regressionParameters;
    SimpleLinearRegresion slr = new SimpleLinearRegresion();

    /**
     * Constructor de la clase SLR.
     * 
     * @param independentlyX Arreglo de valores de la variable independiente X.
     * @param dependentlyY   Arreglo de valores de la variable dependiente Y.
     */
    public SLR(float[] independentlyX, float[] dependently) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependently;
    }

    /**
     * Entrenamiento del modelo de regresión lineal simple.
     * 
     * Este método divide los datos en un conjunto de entrenamiento y un conjunto de
     * prueba, según el modo de división y el porcentaje de segmentación
     * proporcionados,
     * luego calcula los parámetros de la regresión (b0 y b1) utilizando el conjunto
     * de entrenamiento.
     * 
     * @param splitingMode        Un número entero que representa el modo de
     *                            división:
     *                            0 - División secuencial
     *                            1 - División aleatoria
     *                            2 - División intercalada
     * @param segmentationPercent Un valor flotante entre 0 y 1 que representa el
     *                            porcentaje de datos a usar para entrenamiento (por
     *                            ejemplo, 0.7 significa
     *                            que el 70% de los datos se usarán para
     *                            entrenamiento y el 30% para prueba).
     * @return Un objeto de tipo TestData que contiene los datos de prueba.
     */
    @Override
    public TestData training(int splitingMode, float segmentationPercent) {
        SplitingData splitterData = new SplitingData(independentlyX, dependentlyY, segmentationPercent);
        SplittedData splitedData = splitterData.split(splitingMode);
        TestData testData = new TestData();
        testData.setTestDependentlyY(splitedData.getTestDependentlyY());
        testData.setTestIndependentlyX(splitedData.getTestIndependentlyX());
        regressionParameters = slr.computeRegression(splitedData.getTrainIndependentlyX(),
                splitedData.getTrainDependentlyY());
        return testData;
    }

    /**
     * Obtiene los parámetros de la regresión (b0 y b1).
     * 
     * @return Un arreglo de flotantes con los parámetros de la regresión.
     */
    @Override
    public float[] getParameters() {
        return regressionParameters;
    }

    /**
     * Obtiene los hiperparámetros del modelo de regresión.
     * 
     * Los hiperparámetros incluyen el coeficiente de correlación de Pearson y el
     * coeficiente de determinación (R²),
     * que se calculan a partir de las predicciones generadas y los valores reales.
     * 
     * @return Un arreglo de flotantes con el coeficiente de correlación y el
     *         coeficiente de determinación.
     */
    @Override
    public float[] getHyperParameters() {
        CoefficentOfCorrelation cof = new CoefficentOfCorrelation();
        CoefficientOfDetermination cod = new CoefficientOfDetermination();
        float[] predictedValues = new float[dependentlyY.length];
        float y_mean = 0.0f;
        for (int i = 0; i < dependentlyY.length; i++) {
            predictedValues[i] = (regressionParameters[0] + (regressionParameters[1] * independentlyX[i]));
        }
        for (int i = 0; i < dependentlyY.length; i++) {
            y_mean += dependentlyY[i];
        }
        y_mean /= dependentlyY.length;
        return new float[] { cof.calculatePearson(independentlyX, dependentlyY),
                cod.calculate(dependentlyY, predictedValues, y_mean) };
    }

    /**
     * Realiza predicciones para la variable dependiente Y usando el modelo de
     * regresión lineal simple.
     * 
     * @param inputValues Un arreglo de valores flotantes representando la variable
     *                    independiente (X)
     *                    para la cual se harán las predicciones.
     * @return Un arreglo de valores flotantes representando los valores predichos
     *         de la variable dependiente (Y)
     *         correspondientes a los valores de entrada.
     */
    @Override
    public float[] prediction(float[] inputValues) {
        float[] predictedValues = new float[inputValues.length];
        for (int i = 0; i < inputValues.length; i++) {
            predictedValues[i] = (regressionParameters[0] + (regressionParameters[1] * inputValues[i]));
        }
        return predictedValues;
    }

}
