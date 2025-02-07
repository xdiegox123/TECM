package src.core.utils;

import java.util.HashSet;
import java.util.Random;

/**
 * La clase `splitingData` es responsable de dividir un conjunto de datos dado
 * de variables independientes y dependientes
 * en conjuntos de entrenamiento y prueba, con varios modos de división para
 * elegir.
 * La clase proporciona tres métodos diferentes para dividir los datos:
 * secuencial, aleatorio e intercalado.
 * También permite personalizar el tamaño del conjunto de entrenamiento mediante
 * un parámetro de porcentaje.
 */
public class SplitingData {

    private float[] independentlyX, dependentlyY;
    private float trainPercent;

    /**
     * Constructor para inicializar la clase con las variables independientes,
     * dependientes y el porcentaje de entrenamiento.
     * 
     * @param independentlyX Array de variables independientes (características).
     * @param dependentlyY   Array de variables dependientes (objetivos).
     * @param trainPercent   Porcentaje de datos que se utilizarán para el
     *                       entrenamiento (entre 0 y 1).
     */
    public SplitingData(float[] independentlyX, float[] dependentlyY, float trainPercent) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependentlyY;
        this.trainPercent = trainPercent;
    }

    /**
     * Divide el conjunto de datos en conjuntos de entrenamiento y prueba según el
     * modo seleccionado.
     * 
     * @param mode Un número entero que representa el modo de división:
     *             0 - División secuencial
     *             1 - División aleatoria
     *             2 - División intercalada
     * @return Un objeto `splitedData` que contiene tanto los datos de entrenamiento
     *         como los de prueba para las variables independientes y dependientes.
     * @throws IllegalArgumentException Si las longitudes de los arrays de variables
     *                                  independientes y dependientes no coinciden,
     *                                  o si el modo es inválido.
     */

    public SplittedData split(int mode) {
        if (trainPercent > 1 || trainPercent < 0)
            throw new IllegalArgumentException("Train percentage must be between 0 and 1.");

        if (dependentlyY.length != independentlyX.length)
            throw new IllegalArgumentException("Independent and dependent variables have different lengths.");

        int trainSize = (int) (independentlyX.length * trainPercent);
        switch (mode) {
            case 0:
                return secuential(trainSize);
            case 1:
                return random(trainSize);
            case 2:
                return splitIntercalated(trainSize);
            default:
                throw new IllegalArgumentException(
                        "Invalid mode. Use 0 for sequential, 1 for random or 2 for Intercalated.");
        }
    }

    /**
     * Realiza una división secuencial de los datos.
     * La primera porción de los datos se usa para el entrenamiento y el resto para
     * las pruebas.
     * 
     * @param range El número de elementos que se usarán para el entrenamiento.
     * @return Un objeto `splitedData` que contiene los conjuntos de entrenamiento y
     *         prueba de manera secuencial.
     */

    private SplittedData secuential(int range) {

        SplittedData splitedData = new SplittedData();
        float[] independent = new float[range];
        float[] dependent = new float[range];

        System.arraycopy(independentlyX, 0, independent, 0, range);
        System.arraycopy(dependentlyY, 0, dependent, 0, range);
        splitedData.setTrainIndependentlyX(independent);
        splitedData.setTrainDependentlyY(dependent);

        int testSize = independentlyX.length - range;
        independent = new float[testSize];
        dependent = new float[testSize];
        System.arraycopy(independentlyX, range, independent, 0, testSize);
        System.arraycopy(dependentlyY, range, dependent, 0, testSize);
        splitedData.setTestIndependentlyX(independent);
        splitedData.setTestDependentlyY(dependent);

        return splitedData;
    }

    /**
     * Realiza una división aleatoria de los datos.
     * Selecciona aleatoriamente índices para los conjuntos de entrenamiento y
     * prueba.
     * 
     * @param range El número de elementos que se usarán para el entrenamiento.
     * @return Un objeto `splitedData` que contiene los conjuntos de entrenamiento y
     *         prueba divididos aleatoriamente.
     */

    private SplittedData random(int range) {
        SplittedData splitedData = new SplittedData();
        Random rand = new Random();
        HashSet<Integer> selectedSet = new HashSet<>();
        int testIndex = 0, trainingIndex = 0;
        float[] selecteIndependentlydTestData = new float[range];
        float[] selecteDependentlydTestData = new float[range];
        float[] selecteIndependentlydTrainingData = new float[independentlyX.length - (range - 1)];
        float[] selecteDependentlydTrainingData = new float[dependentlyY.length - (range - 1)];

        for (int i = 0; i < range; i++) {
            int possibleIndex;
            do {
                possibleIndex = rand.nextInt(dependentlyY.length);
            } while (selectedSet.contains(possibleIndex));
            selectedSet.add(possibleIndex);
        }

        for (int i = 0; i < dependentlyY.length; i++) {
            if (selectedSet.contains(i)) {
                selecteIndependentlydTestData[testIndex] = independentlyX[i];
                selecteDependentlydTestData[testIndex] = dependentlyY[i];
                testIndex++;
            } else {
                selecteIndependentlydTrainingData[trainingIndex] = independentlyX[i];
                selecteDependentlydTrainingData[trainingIndex] = dependentlyY[i];
                trainingIndex++;
            }
        }
        splitedData.setTrainIndependentlyX(selecteIndependentlydTrainingData);
        splitedData.setTrainDependentlyY(selecteDependentlydTrainingData);
        splitedData.setTestIndependentlyX(selecteIndependentlydTestData);
        splitedData.setTestDependentlyY(selecteDependentlydTestData);
        return splitedData;
    }

    /**
     * Realiza una división intercalada de los datos.
     * La división alterna entre los datos de entrenamiento y prueba según un
     * intervalo.
     * 
     * @param range El número de elementos que se usarán para el entrenamiento.
     * @return Un objeto `splitedData` que contiene los conjuntos de entrenamiento y
     *         prueba de manera intercalada.
     */

    private SplittedData splitIntercalated(int range) {
        SplittedData splitedData = new SplittedData();
        HashSet<Integer> selectedSet = new HashSet<>();
        int testIndex = 0, trainingIndex = 0;
        float[] selecteIndependentlydTestData = new float[range];
        float[] selecteDependentlydTestData = new float[range];
        float[] selecteIndependentlydTrainingData = new float[independentlyX.length - (range - 1)];
        float[] selecteDependentlydTrainingData = new float[dependentlyY.length - (range - 1)];
        int interval = independentlyX.length / range;

        for (int i = 0; i < range; i++) {
            selectedSet.add((i * interval) + interval / 2);
        }

        for (int i = 0; i < dependentlyY.length; i++) {
            if (selectedSet.contains(i)) {
                selecteIndependentlydTestData[testIndex] = independentlyX[i];
                selecteDependentlydTestData[testIndex] = dependentlyY[i];
                testIndex++;
            } else {
                selecteIndependentlydTrainingData[trainingIndex] = independentlyX[i];
                selecteDependentlydTrainingData[trainingIndex] = dependentlyY[i];
                trainingIndex++;
            }
        }
        splitedData.setTrainIndependentlyX(selecteIndependentlydTrainingData);
        splitedData.setTrainDependentlyY(selecteDependentlydTrainingData);
        splitedData.setTestIndependentlyX(selecteIndependentlydTestData);
        splitedData.setTestDependentlyY(selecteDependentlydTestData);
        return splitedData;
    }
}

/*
 * hot keys
 * ctrl + D -> copy line down
 * alt + up -> move line
 */