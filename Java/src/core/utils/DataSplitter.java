package src.core.utils;

import java.util.HashSet;
import java.util.Random;

import src.core.utils.dtos.SplitDataResult;

/**
 * Utility class responsible for splitting the provided dataset into training
 * and testing sets.
 * It provides different modes for data splitting: sequential, random, and
 * intercalated.
 * 
 * @see SplitDataResult
 */
public class DataSplitter {

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
    public DataSplitter(float[] independentlyX, float[] dependentlyY, float trainPercent) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependentlyY;
        this.trainPercent = trainPercent;
    }

    /**
     * Splits the dataset into training and testing sets based on the selected mode.
     * 
     * @param mode The splitting mode (0: Sequential, 1: Random, 2: Intercalated).
     * @return An object of type SplittedData containing the training and testing
     *         data.
     * @throws IllegalArgumentException If the arrays have different lengths or if
     *                                  an invalid mode is selected.
     */
    public SplitDataResult split(int mode) {
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
     * Splits the dataset sequentially, using the first portion for training and the
     * remainder for testing.
     * 
     * @param range The number of elements to use for training.
     * @return An object of type SplittedData containing the sequential training and
     *         testing data.
     */
    private SplitDataResult secuential(int range) {

        SplitDataResult splitedData = new SplitDataResult();
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
     * Splits the dataset randomly, selecting random indices for the training and
     * testing data.
     * 
     * @param range The number of elements to use for training.
     * @return An object of type SplittedData containing the randomly split training
     *         and testing data.
     */
    private SplitDataResult random(int range) {
        SplitDataResult splitedData = new SplitDataResult();
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
     * Splits the dataset in an intercalated manner, alternating between training
     * and testing data.
     * 
     * @param range The number of elements to use for training.
     * @return An object of type SplittedData containing the intercalated training
     *         and testing data.
     */
    private SplitDataResult splitIntercalated(int range) {
        SplitDataResult splitedData = new SplitDataResult();
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