package src.interfaces.examples;

import src.core.utils.dtos.TestData;
import src.interfaces.models.PredictiveModel;

import java.util.Arrays;

public abstract class RegressionExample {

    // Data for the examples
    public float[] batchSizeX = {108, 115, 106, 97, 95, 91, 97, 83, 78, 54, 67, 56, 53, 61, 115, 81, 78, 30, 45, 99, 32, 25, 28, 90, 89};
    public float[] machineEfficienciesY = {95, 96, 95, 97, 93, 94, 95, 93, 92, 86, 73, 80, 65, 69, 77, 96, 87, 89, 60, 63, 95, 61, 56, 94, 93};
    public float bestCoefficient;
    public float[] predictions;
    public float[] testY, testx;
    public float[][] testX;

    public void initialize(float segmentation) {
    }

    public void initialize(float segmentation, int degree) {
    }

    /**
     * Prints the predictions made by the model along with the actual values,
     * model parameters, and performance metrics.
     *
     * @param model       The trained linear regression model.
     * @param testData    The test data used for evaluation.
     * @param datasetType The type of dataset used for the model training
     *                    (sequential, random, intercalate).
     */
    public void printPredictions(Object model, TestData testData, String datasetType) {
        if (model instanceof PredictiveModel) {
            if (testData.getTestIndependentlyMatrixX() != null) {
                predictions = ((PredictiveModel) model).multiPredictionMultiple(testData.getTestIndependentlyMatrixX());
                testX = testData.getTestIndependentlyMatrixX();
                testY = testData.getTestDependentY();
            } else {
                predictions = ((PredictiveModel) model).prediction(testData.getTestIndependentlyX());
                testx = testData.getTestIndependentlyX();
            }

            // 2. Print predictions and performance metrics
            System.out.println();
            System.out.println("Predictions with " + datasetType + ":");

            if (testData.getTestIndependentlyX() != null) {
                for (int i = 0; i < testData.getTestIndependentlyX().length; i++) {
                    System.out.println((i + 1) + ") x = " + testData.getTestIndependentlyX()[i] + ": Real = " + testData.getTestDependentY()[i] + ", Prediction = " + predictions[i]);
                }
            } else if (testData.getTestIndependentlyMatrixX() != null) {
                for (float[] test : testData.getTestIndependentlyMatrixX()) {
                    for (int i = 0; i < testData.getTestDependentY().length; i++) {
                        System.out.println("x = " + test[i] + ": Real = " + testData.getTestDependentY()[i] + ", Prediction = " + predictions[i]);
                    }
                }
            }

            if (testData.getTestIndependentlyMatrixX() != null) {
                System.out.print("Model parameters: [");
                for (int i = 0; i < ((PredictiveModel) model).getParameters().getBn().length; i++) {
                    System.out.print(" B" + i + " = " + ((PredictiveModel) model).getParameters().getBn()[i]);
                }
                System.out.println(" ]");
            } else {

                System.out.println("Model parameters: B0 = " + ((PredictiveModel) model).getParameters().getbZero() + " B1 = " + ((PredictiveModel) model).getParameters().getbOne());
            }
            System.out.println("Correlation: " + ((PredictiveModel) model).getParameters().getCof());
            System.out.println("Coefficient of Determination (R^2): " + ((PredictiveModel) model).getParameters().getCod());
            System.out.println();
        }
    }

    public static void printGraph(double[] x, double[] predictedY, double[] realY) {


        // Normalizar los valores de x, predictedY, realY para que estén entre 0 y 1
        double xMin = Arrays.stream(x).min().getAsDouble();
        double xMax = Arrays.stream(x).max().getAsDouble();
        double yMin = Math.min(Arrays.stream(predictedY).min().getAsDouble(), Arrays.stream(realY).min().getAsDouble());
        double yMax = Math.max(Arrays.stream(predictedY).max().getAsDouble(), Arrays.stream(realY).max().getAsDouble());

        // Normalizar x
        double[] normalizedX = Arrays.stream(x).map(val -> (val - xMin) / (xMax - xMin)).toArray();

        // Normalizar predictedY
        double[] normalizedPredictedY = Arrays.stream(predictedY).map(val -> (val - yMin) / (yMax - yMin)).toArray();

        // Normalizar realY
        double[] normalizedRealY = Arrays.stream(realY).map(val -> (val - yMin) / (yMax - yMin)).toArray();

        // Definir las dimensiones del "plano" ASCII
        int width = 10; // Reducido el ancho del gráfico
        int height = 10; // Reducido el alto del gráfico

        // Dibuja el plano en la terminal (gráfico de texto)
        char[][] graph = new char[height][width];

        // Rellenar la matriz con espacios en blanco
        for (int i = 0; i < height; i++) {
            Arrays.fill(graph[i], ' ');
        }

        // Convertir las predicciones a posiciones en el gráfico
        for (int i = 0; i < normalizedX.length; i++) {
            int graphX = (int) (normalizedX[i] * (width - 1)); // Normalizar y mapear a las coordenadas del gráfico
            int graphPredictedY = height - (int) (normalizedPredictedY[i] * (height - 1)) - 1; // Normalizar y mapear a
            // las coordenadas del
            // gráfico
            int graphRealY = height - (int) (normalizedRealY[i] * (height - 1)) - 1; // Normalizar y mapear a las
            // coordenadas del gráfico

            // Marcar la predicción con '*'
            if (graphPredictedY >= 0 && graphPredictedY < height && graphX >= 0 && graphX < width) {
                graph[graphPredictedY][graphX] = '*';
            }

            // Marcar el valor real con 'o'
            if (graphRealY >= 0 && graphRealY < height && graphX >= 0 && graphX < width) {
                graph[graphRealY][graphX] = 'o';
            }
        }

        // Imprimir el gráfico con las escalas
        for (int i = 0; i < height; i++) {
            // Imprimir la escala del eje Y
            if (i == height - 1) {
                System.out.print("0");
            } else {
                System.out.print(String.format("%2d", (int) (1 - (i / (double) (height - 1))))); // Imprimir en escala
                // [0,1]
            }
            for (int j = 0; j < width; j++) {
                // Imprimir el gráfico con colores para las predicciones y los valores reales
                if (graph[i][j] == '*') {
                    System.out.print("\033[0;32m" + graph[i][j] + "\033[0m"); // Verde para las predicciones
                } else if (graph[i][j] == 'o') {
                    System.out.print("\033[0;34m" + graph[i][j] + "\033[0m"); // Azul para los valores reales
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // Imprimir la escala del eje X
        System.out.print("  ");
        for (int i = 0; i < width; i++) {
            double xVal = i * 1.0 / (width - 1); // Imprimir valores de X normalizados [0, 1]
            if (i % (width / 5) == 0) {
                System.out.print(String.format("%5.1f", xVal));
            } else {
                System.out.print("     ");
            }
        }
        System.out.println();

        // Imprimir los valores de X y Y en las escalas con un decimal
        System.out.println("\nEjes X e Y (normalizados):");

        // Formatear y mostrar cada valor de normalizedX con 1 decimal
        System.out.print("Batch Size (X): ");
        for (double value : normalizedX) {
            System.out.print(String.format("%.2f ", (value * 10))); // Formato con un decimal
        }
        System.out.println(); // Nueva línea después de imprimir todos los valores

        // Formatear y mostrar cada valor de normalizedPredictedY con 1 decimal
        System.out.print("Predicciones (Y): ");
        for (double value : normalizedPredictedY) {
            System.out.print(String.format("%.2f ", (value * 10))); // Formato con un decimal
        }
        System.out.println(); // Nueva línea después de imprimir todos los valores

        // Formatear y mostrar cada valor de normalizedRealY con 1 decimal
        System.out.print("Valores reales (Y): ");
        for (double value : normalizedRealY) {
            System.out.print(String.format("%.2f ", (value * 10))); // Formato con un decimal
        }
        System.out.println(); // Nueva línea después de imprimir todos los valoresX
    }

    public static double[] convertFloatArrayToDouble(float[] floatArray) {
        // Crear un arreglo de double con el mismo tamaño que el arreglo de float
        double[] doubleArray = new double[floatArray.length];

        // Copiar los valores de float a double
        for (int i = 0; i < floatArray.length; i++) {
            doubleArray[i] = floatArray[i]; // Conversión implícita de float a double
        }

        return doubleArray;
    }

}
