package src.core.models;

import src.interfaces.models.Regressor;

// B = (X^tX)^-1 (X^tY)

// 1 | X | X | X

public class LeastSquaredRegressionModel implements Regressor {

    @Override
    public float[] computeRegression(float[] y, float[]... x) {
        float[][] X = new float[x.length][x[0].length + 1];
        for (int r = 0; r < x.length; r++) {
            X[r][0] = 1;
            for (int c = 0; c < x[r].length; c++) {
                X[r][c + 1] = x[r][c];
            }
        }

        //float[][] XT = transpose(X);
        //float[][] XTX = matrixProduct(XT, X);
        //float[][] XTX_inv = inverse(XTX);
        //float[] XTY = multiplyMatrixVector(XT, y);
        // float[][] B = matrixProduct(XTX_inv, XTY);
        return new float[] {};
    }
/* 
    private void inverse(float[][] matrix) {
    }

    private float[][] transpose(float[][] m) {
        float[][] t = new float[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                t[j][i] = m[i][j];
            }
        }
        return t;
    }

    private float[][] matrixProduct(float[][] A, float[][] B) {
        float[][] result = new float[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    result[i][j] += A[i][k] * B[j][k];
                }
            }
        }
        return result;
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
    }*/
}
