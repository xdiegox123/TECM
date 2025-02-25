package src.core.models;
import src.interfaces.models.Regressor;

public class MultiLinearRegressionModel implements Regressor {

    @Override
    public float[] computeRegression(float[] y, float[]... x) {
        float[] sumX = new float[x.length];
        float[] sumX2 = new float[x.length];
        float[] crossProd = new float[x.length - 1];
        float[] prodXY = new float[x.length];
        float[] yVector = new float[x.length + 1];
        for (int j = 0; j < x.length; j++) {
            for (int i = 0; i < y.length; i++) {
                sumX[j] += x[j][i];
                sumX2[j] += x[j][i] * x[j][i];
                prodXY[j] += x[j][i] * y[i];
                if (j > 0 && i > 0) {
                    crossProd[j - 1] += x[j][i] * x[j - 1][i];
                }
            }
        }
        for (int i = 0; i < yVector.length; i++) {
            if (i == 0) {
                for (int j = 0; j < y.length; j++) {
                    yVector[i] += y[j];
                }
            } else {
                yVector[i] = prodXY[i - 1];
            }

        }
        float[][] matrix = createRegressionMatrix(sumX, crossProd, sumX2, y);
        float[][] inverse = inverseGaussJordan(matrix);
        return multiplyMatrixVector(inverse, yVector);
    }

    private float[][] createRegressionMatrix(float[] sumX, float[] crossProd, float[] sumX2, float[] y) {
        int m = sumX.length + 1;
        float[][] matrix = new float[m][m];

        for (int col = 0; col < m; col++) {
            for (int row = 0; row < m; row++) {
                if (col == 0 && row == 0) {
                    matrix[col][row] = y.length;
                } else if (col == 0) {
                    matrix[col][row] = sumX[row - 1];
                } else if (row == 0) {
                    matrix[col][row] = sumX[col - 1];
                } else if (col == row) {
                    matrix[col][row] = sumX2[col - 1];
                } else {
                    matrix[col][row] = crossProd[Math.abs(col - row) - 1];
                }
            }
        }
        return matrix;
    }

    private float[][] inverseGaussJordan(float[][] matrix) {
        int n = matrix.length;
        float[][] inverse = new float[n][n];
        float[][] identityMatrix = new float[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                identityMatrix[i][j] = matrix[i][j];
            }
            identityMatrix[i][i + n] = 1.0f;
        }
        for (int i = 0; i < n; i++) {
            float diagElement = identityMatrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                identityMatrix[i][j] /= diagElement;
            }
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    float factor = identityMatrix[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        identityMatrix[k][j] -= factor * identityMatrix[i][j];
                    }
                }
            }
        }
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                inverse[row][col] = identityMatrix[row][col + n];
            }
        }
        return inverse;
    }

    public static float[] multiplyMatrixVector(float[][] matrix, float[] vector) {
        float[] result = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
}
