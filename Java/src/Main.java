package src;

import src.core.utils.TestData;
import src.examples.SLR;

public class Main {

    static float[] batchSizeX = { 108, 115, 106, 97, 95, 91, 97, 83, 78, 54, 67, 56, 53, 61, 115, 81, 78, 30, 45, 99,
            32,
            25, 28, 90, 89 };
    static float[] machineEfficienciesY = { 95, 96, 95, 97, 93, 94, 95, 93, 92, 86, 73, 80, 65, 69, 77, 96, 87, 89, 60,
            63, 95, 61, 56, 94, 93 };

    public static void main(String[] args) {
        SLR simpleRegresion = new SLR(batchSizeX, machineEfficienciesY);
        TestData testData = simpleRegresion.training(1, 0.3f);
        System.out.println("Coeficiente de correlación de Pearson: " + simpleRegresion.getHyperParameters()[0]);
        // System.out.println("Error cuadrático medio: " + testData.calculateMSE());
        System.out.println("Coeficiente de determinación: " + simpleRegresion.getHyperParameters()[1]);
        System.out.println("Coeficientes de regresión: a = " + simpleRegresion.getParameters()[0] + ", b = "
                + simpleRegresion.getParameters()[1]);

    }
}