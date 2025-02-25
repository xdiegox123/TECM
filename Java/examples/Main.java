package examples;

import src.core.models.MultiLinearRegressionModel;

public class Main {

        public static void main(String[] args) {

                MultiLinearRegressionModel LRS = new MultiLinearRegressionModel();

                float[] y = { 1.5f, 2.3f, 4.8f, 5.1f };
                float[] X1 = { 0.5f, 1.0f, 1.5f, 2.0f };
                float[] X2 = { 1.5f, 2.0f, 2.5f, 3.0f };
                float[] X3 = { 2.5f, 3.0f, 3.5f, 4.0f };
                // Definir los arrays de x1, x2 y y
                // float[] x1 = { 1, 3, 5, 7, 9 };
                // float[] x2 = { 2, 4, 6, 8, 10 };
                // float[] y = new float[5];

                // Calcular los valores de y según la fórmula y = 3 * x1 + 2 * x2 + 10
                // for (int i = 0; i < x1.length; i++) {
                // y[i] = 3 * x1[i] + 2 * x2[i] + 10;
                // }

                // Calcular los coeficientes de la regresión lineal
                float[] result = LRS.computeRegression(y, X1, X2, X3);

                // Extraer los coeficientes B0, B1 y B2
                float B0 = result[0]; // Intercepto
                float B1 = result[1]; // Coeficiente de x1
                float B2 = result[2]; // Coeficiente de x2
                float B3 = result[3]; // Coeficiente de x2

                // Imprimir los coeficientes
                System.out.println("Coeficientes de la regresión:");
                System.out.println("B0 (Intercepto): " + B0);
                System.out.println("B1 (Coeficiente de x1): " + B1);
                System.out.println("B2 (Coeficiente de x2): " + B2);

                // Realizar las predicciones con x1 y x2
                System.out.println("\nPredicciones:");
                for (int i = 0; i < X1.length; i++) {
                        float predictedY = B0 + B1 * X1[i] + B2 * X2[i] + B3 * X3[i];
                        System.out.println("Predicción para x1 = " + X1[i] + " , x2 = " + X2[i] + " y x3 = " + X3[i]
                                        + ": " + predictedY);
                }
        }

}