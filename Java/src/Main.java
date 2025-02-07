package src;

import src.core.utils.TestData;
import src.examples.SLR;

public class Main {

        static float[] batchSizeX = { 108, 115, 106, 97, 95, 91, 97, 83, 78, 54, 67, 56, 53, 61, 115, 81, 78, 30, 45,
                        99,
                        32,
                        25, 28, 90, 89 };
        static float[] machineEfficienciesY = { 95, 96, 95, 97, 93, 94, 95, 93, 92, 86, 73, 80, 65, 69, 77, 96, 87, 89,
                        60,
                        63, 95, 61, 56, 94, 93 };

        public static void main(String[] args) {
                SLR simpleRegresionSecuential = new SLR(batchSizeX, machineEfficienciesY);
                SLR simpleRegresionIntercalate = new SLR(batchSizeX, machineEfficienciesY);
                SLR simpleRegresionRandom = new SLR(batchSizeX, machineEfficienciesY);
                TestData testSLRSecData = simpleRegresionSecuential.training(0, 0.3f);
                TestData testSLRandData = simpleRegresionRandom.training(1, 0.3f);
                TestData testSLRInterData = simpleRegresionIntercalate.training(2, 0.3f);

                float bestCoefficent = Math.max(simpleRegresionSecuential.getHyperParameters()[1],
                                Math.max(simpleRegresionRandom.getHyperParameters()[1],
                                                simpleRegresionIntercalate.getHyperParameters()[1]));
                if (bestCoefficent == simpleRegresionIntercalate.getHyperParameters()[1]) {
                        printPredictions(simpleRegresionIntercalate, testSLRInterData, "dataset intercalados");
                } else if (bestCoefficent == simpleRegresionRandom.getHyperParameters()[1]) {
                        printPredictions(simpleRegresionRandom, testSLRandData, "dataset Random");
                } else if (bestCoefficent == simpleRegresionSecuential.getHyperParameters()[1]) {
                        printPredictions(simpleRegresionSecuential, testSLRSecData, "dataset Secuencial");
                } else {
                        System.out.println("Error al encontrar el mejor modelo.");
                        return;
                }
        }

        private static void printPredictions(SLR model, TestData testData, String label) {
                float[] predictions = model.prediction(testData.getTestIndependentlyX());
                System.out.println("");
                System.out.println("Predicciones con " + label + " :");
                for (int i = 0; i < testData.getTestIndependentlyX().length; i++) {
                        System.out.println((i + 1) + ") x = " + testData.getTestIndependentlyX()[i] + ": Real = "
                                        + testData.getTestDependentlyY()[i] + ", predicción = " + predictions[i]);
                }
                System.out.println("Parametros beta: B0 = " + model.getParameters()[0] + " B1 = "
                                + model.getParameters()[1]);
                System.out.println("Correlación: " + model.getHyperParameters()[0]);
                System.out.println("Coeficiente de Determinación: " + model.getHyperParameters()[1]);
                System.out.println("");

        }
}