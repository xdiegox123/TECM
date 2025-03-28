package src.core.utils;

public class MatrixOperations {

    public float[][] matrixProduct(float[][] A, float[][] B) {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException(
                    "Las matrices no se pueden multiplicar: El número de columnas de A debe ser igual al número de filas de B.");
        }
        float[][] result = new float[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }

    public float[] multiplyMatrixVector(float[][] matrix, float[] vector) {

        float[] result = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public float[][] transpose(float[][] m) {
        float[][] t = new float[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                t[j][i] = m[i][j];
            }
        }
        return t;
    }

    public float[][] inverseGaussJordan(float[][] matrix) {
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
}
