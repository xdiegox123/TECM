package examples;

import src.core.predictivemodels.SimpleLinearRegression;
import src.core.utils.dtos.TestData;

public class SimpleLinearRegressionExample {
    static float[] batchSizeX = { 108, 115, 106, 97, 95, 91, 97, 83, 78, 54, 67, 56, 53, 61, 115, 81, 78, 30, 45,
            99,
            32,
            25, 28, 90, 89 };
    static float[] machineEfficienciesY = { 95, 96, 95, 97, 93, 94, 95, 93, 92, 86, 73, 80, 65, 69, 77, 96, 87, 89,
            60,
            63, 95, 61, 56, 94, 93 };

    public void initialize() {
        SimpleLinearRegression simpleRegresionSecuential = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);
        SimpleLinearRegression simpleRegresionIntercalate = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);
        SimpleLinearRegression simpleRegresionRandom = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);
        TestData testSLRSecData = simpleRegresionSecuential.training(0, 0.3f, 1);
        TestData testSLRandData = simpleRegresionRandom.training(1, 0.3f, 1);
        TestData testSLRInterData = simpleRegresionIntercalate.training(2, 0.3f, 1);

        float bestCoefficent = Math.max(simpleRegresionSecuential.getParameters().getCod(),
                Math.max(simpleRegresionRandom.getParameters().getCod(),
                        simpleRegresionIntercalate.getParameters().getCod()));
        if (bestCoefficent == simpleRegresionIntercalate.getParameters().getCod()) {
            printPredictions(simpleRegresionIntercalate, testSLRInterData, "dataset intercalados");
        } else if (bestCoefficent == simpleRegresionRandom.getParameters().getCod()) {
            printPredictions(simpleRegresionRandom, testSLRandData, "dataset Random");
        } else if (bestCoefficent == simpleRegresionSecuential.getParameters().getCod()) {
            printPredictions(simpleRegresionSecuential, testSLRSecData, "dataset Secuencial");
        } else {
            System.out.println("Error al encontrar el mejor modelo.");
            return;
        }
    }

    private static void printPredictions(SimpleLinearRegression model, TestData testData, String label) {
        float[] predictions = model.prediction(testData.getTestIndependentlyX());
        System.out.println("");
        System.out.println("Predicciones con " + label + " :");
        for (int i = 0; i < testData.getTestIndependentlyX().length; i++) {
            System.out.println((i + 1) + ") x = " + testData.getTestIndependentlyX()[i] + ": Real = "
                    + testData.getTestDependentlyY()[i] + ", predicción = " + predictions[i]);
        }
        System.out.println("Parametros beta: B0 = " + model.getParameters().getbZero() + " B1 = "
                + model.getParameters().getbOne());
        System.out.println("Correlación: " + model.getParameters().getCof());
        System.out.println("Coeficiente de Determinación: " + model.getParameters().getCod());
        System.out.println("");

    }
}
