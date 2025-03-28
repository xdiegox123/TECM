package src.core.utils;

import java.util.HashSet;
import java.util.Random;

import src.core.utils.dtos.SplitData;

/**
 * Utility class responsible for splitting the provided dataset into training
 * and testing sets.
 * It provides different modes for data splitting: sequential, random, and
 * intercalate.
 *
 * @see SplitData
 */
public class DataSplitter {

    private float[] independentlyX;
    private final float[] dependentlyY;
    private float[][] independentlyMatrixX;
    private final float trainPercent;

    /**
     * Constructor para inicializar la clase con las variables independientes,
     * dependientes y el porcentaje de entrenamiento.
     *
     * @param dependentlyY   Array de variables dependientes (objetivos).
     * @param trainPercent   Porcentaje de datos que se utilizarán para el
     *                       entrenamiento (entre 0 y 1).
     * @param independentlyX Array de variables independientes (características).
     */
    public DataSplitter(float[] dependentlyY, float trainPercent, int var, float[]... independentlyX) {
        this.independentlyMatrixX = independentlyX;
        this.dependentlyY = dependentlyY;
        this.trainPercent = trainPercent;
    }

    public DataSplitter(float[] dependentlyY, float trainPercent, float[] independentlyX) {
        this.independentlyX = independentlyX;
        this.dependentlyY = dependentlyY;
        this.trainPercent = trainPercent;
    }

    /**
     * Splits the dataset into training and testing sets based on the selected mode.
     *
     * @param mode The splitting mode (0: Sequential, 1: Random, 2: Intercalate).
     * @return An object of type SplitData containing the training and testing
     *         data.
     * @throws IllegalArgumentException If the arrays have different lengths or if
     *                                  an invalid mode is selected.
     */
    public SplitData split(int mode) {
        if (trainPercent > 1 || trainPercent < 0)
            throw new IllegalArgumentException("Train percentage must be between 0 and 1.");

        int trainSize = (int) (dependentlyY.length * trainPercent);
        return switch (mode) {
            case 1 -> sequential(trainSize);
            case 2 -> random(trainSize);
            case 3 -> splitIntercalate(trainSize);
            default -> defaultSplit(trainSize);
        };
    }

    /**
     * Splits the dataset sequentially, using the first portion for training and the
     * remainder for testing.
     *
     * @param range The number of elements to use for training.
     * @return An object of type SplitData containing the sequential training and
     *         testing data.
     */
    private SplitData sequential(int range) {
        SplitData splitData = new SplitData();

        // Prepare training data
        float[] trainingX = new float[range];
        float[] trainingY = new float[range];
        float[][] copyTestMatrix = null, copyTrainingMatrix = null;
        if (independentlyMatrixX != null) {
            copyTrainingMatrix = new float[independentlyMatrixX.length][range];
            copyTestMatrix = new float[independentlyMatrixX.length][dependentlyY.length - range];
        }

        // Split training data
        if (independentlyMatrixX != null) {
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < independentlyMatrixX.length; j++) {
                    copyTrainingMatrix[j][i] = independentlyMatrixX[j][i];
                }
            }
            splitData.setTrainIndependentlyMatrixX(copyTrainingMatrix);
        } else {
            System.arraycopy(independentlyX, 0, trainingX, 0, range);
            splitData.setTrainIndependentlyX(trainingX);
        }
        System.arraycopy(dependentlyY, 0, trainingY, 0, range);
        splitData.setTrainDependentlyY(trainingY);

        // Prepare testing data
        float[] testX = new float[range];
        float[] testY = new float[range];
        int index = 0;

        // Split testing data
        if (independentlyMatrixX != null && copyTestMatrix != null) {
            for (int i = (dependentlyY.length - range); i < independentlyMatrixX[0].length; i++) {
                for (int j = 0; j < independentlyMatrixX.length; j++) {
                    copyTestMatrix[j][index] = independentlyMatrixX[j][i];
                }
                index++;
            }
            splitData.setTestIndependentlyMatrixX(copyTestMatrix);
        } else {
            System.arraycopy(independentlyX, dependentlyY.length - range, testX, 0, range);
            splitData.setTestIndependentlyX(testX);
        }
        System.arraycopy(dependentlyY, dependentlyY.length - range, testY, 0, range);
        splitData.setTestDependentlyY(testY);

        return splitData;
    }

    /**
     * Splits the dataset randomly, selecting random indices for the training and
     * testing data.
     *
     * @param range The number of elements to use for training.
     * @return An object of type SplitData containing the randomly split training
     *         and testing data.
     */
    private SplitData random(int range) {
        SplitData splitData = new SplitData();

        Random rand = new Random();
        int testIndex = 0, trainingIndex = 0;
        HashSet<Integer> selectedRows = new HashSet<>();
        float[] selectedTestDataY = new float[range];
        float[] selectedTrainingDataY = new float[dependentlyY.length - range];
        float[] selectedTestDataX = new float[range];
        float[] selectedTrainingDataX = new float[dependentlyY.length - range];
        float[][] copyTestMatrix = null, copyTrainingMatrix = null;
        if (independentlyMatrixX != null) {
            copyTestMatrix = new float[independentlyMatrixX.length][range];
            copyTrainingMatrix = new float[independentlyMatrixX.length][dependentlyY.length - range];
        }

        // Select random rows for test data
        for (int i = 0; i < range; i++) {
            int possibleIndex;
            do {
                possibleIndex = rand.nextInt(dependentlyY.length);
            } while (selectedRows.contains(possibleIndex));
            selectedRows.add(possibleIndex);
        }

        // Split data into training and test sets
        for (int i = 0; i < dependentlyY.length; i++) {
            if (selectedRows.contains(i)) {
                if (independentlyMatrixX != null && copyTestMatrix != null) {
                    for (int j = 0; j < independentlyMatrixX.length; j++) {
                        copyTestMatrix[j][testIndex] = independentlyMatrixX[j][i];
                    }
                } else {
                    selectedTestDataX[testIndex] = independentlyX[i];
                }
                selectedTestDataY[testIndex] = dependentlyY[i];
                testIndex++;
            } else {
                if (independentlyMatrixX != null && copyTrainingMatrix != null) {
                    for (int j = 0; j < independentlyMatrixX.length; j++) {
                        copyTrainingMatrix[j][trainingIndex] = independentlyMatrixX[j][i];
                    }
                } else {
                    selectedTrainingDataX[trainingIndex] = independentlyX[i];
                }
                selectedTrainingDataY[trainingIndex] = dependentlyY[i];
                trainingIndex++;
            }
        }

        // Set training and testing data in splitData
        if (independentlyMatrixX != null) {
            splitData.setTrainIndependentlyMatrixX(copyTrainingMatrix);
            splitData.setTestIndependentlyMatrixX(copyTestMatrix);
        } else {
            splitData.setTrainIndependentlyX(selectedTrainingDataX);
            splitData.setTestIndependentlyX(selectedTestDataX);
        }
        splitData.setTrainDependentlyY(selectedTrainingDataY);
        splitData.setTestDependentlyY(selectedTestDataY);

        return splitData;
    }

    /**
     * Splits the dataset in an intercalate manner, alternating between training
     * and testing data.
     *
     * @param range The number of elements to use for training.
     * @return An object of type SplitData containing the intercalate training
     *         and testing data.
     */
    private SplitData splitIntercalate(int range) {
        SplitData splitData = new SplitData();

        HashSet<Integer> selectedRows = new HashSet<>();
        int testIndex = 0, trainingIndex = 0;
        float[] selectedTestDataY = new float[range];
        float[] selectedTrainingDataY = new float[dependentlyY.length - range];
        float[] selectedTestDataX = new float[range];
        float[] selectedTrainingDataX = new float[dependentlyY.length - range];
        float[][] copyTestMatrix = null, copyTrainingMatrix = null;
        if (independentlyMatrixX != null) {
            copyTestMatrix = new float[independentlyMatrixX.length][range];
            copyTrainingMatrix = new float[independentlyMatrixX.length][dependentlyY.length - range];
        }
        int interval = dependentlyY.length / range;

        // Select rows for intercalate split
        for (int i = 0; i < range; i++) {
            selectedRows.add((i * interval) + interval / 2);
        }

        // Split data into training and test sets
        for (int i = 0; i < dependentlyY.length; i++) {
            if (selectedRows.contains(i)) {
                if (independentlyMatrixX != null) {
                    for (int j = 0; j < independentlyMatrixX.length; j++) {
                        copyTestMatrix[j][testIndex] = independentlyMatrixX[j][i];
                    }
                } else {
                    selectedTestDataX[testIndex] = independentlyX[i];
                }
                selectedTestDataY[testIndex] = dependentlyY[i];
                testIndex++;
            } else {
                if (independentlyMatrixX != null) {
                    for (int j = 0; j < independentlyMatrixX.length; j++) {
                        copyTrainingMatrix[j][trainingIndex] = independentlyMatrixX[j][i];
                    }
                } else {
                    selectedTrainingDataX[trainingIndex] = independentlyX[i];
                }
                selectedTrainingDataY[trainingIndex] = dependentlyY[i];
                trainingIndex++;
            }
        }

        // Set training and testing data in splitData
        if (independentlyMatrixX != null) {
            splitData.setTrainIndependentlyMatrixX(copyTrainingMatrix);
            splitData.setTestIndependentlyMatrixX(copyTestMatrix);
        } else {
            splitData.setTrainIndependentlyX(selectedTrainingDataX);
            splitData.setTestIndependentlyX(selectedTestDataX);
        }
        splitData.setTrainDependentlyY(selectedTrainingDataY);
        splitData.setTestDependentlyY(selectedTestDataY);

        return splitData;
    }

    /**
     * Default splitting of the dataset.
     *
     * @param range The number of elements to use for testing.
     * @return An object of type SplitData containing the default split data.
     */
    private SplitData defaultSplit(int range) {
        SplitData splitData = new SplitData();

        // Set train data
        if (independentlyMatrixX != null) {
            splitData.setTrainIndependentlyMatrixX(independentlyMatrixX);
            splitData.setTrainDependentlyY(dependentlyY);
        } else {
            splitData.setTrainIndependentlyX(independentlyX);
            splitData.setTrainDependentlyY(dependentlyY);
        }

        // Set test data
        float[] copyY = new float[range];
        System.arraycopy(dependentlyY, (dependentlyY.length - range), copyY, 0, range);
        splitData.setTestDependentlyY(copyY);

        if (independentlyMatrixX != null) {
            float[][] copyMatrix = new float[range][];
            int testSize = (dependentlyY.length - range);
            for (int i = testSize; i < independentlyMatrixX[0].length; i++) {
                copyMatrix[i] = independentlyMatrixX[i].clone();
            }
            splitData.setTestIndependentlyMatrixX(copyMatrix);
        } else {
            float[] independent = new float[range];
            System.arraycopy(independentlyX, (dependentlyY.length - range), independent, 0, range);
            splitData.setTestIndependentlyX(independent);
        }

        return splitData;
    }
}