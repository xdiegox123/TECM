package src.core.models;

import src.core.utils.MatrixOperations;
import src.interfaces.abstracts.Regressor;

public class LeastSquaredRegressionModel extends Regressor {

    @Override
    public float[] computeRegression(int degree, float[] y, float[]... x) {
        int cols = x.length;
        int rows = x[0].length;

        float[][] X = new float[rows][(cols + degree)];
        for (int i = 0; i < rows; i++) {
            X[i][0] = 1;
            for (int j = 1; j <= degree; j++) {
                X[i][j] = (float) Math.pow(x[0][i], j);
            }
            for (int j = degree + 1; j < (cols + degree); j++) {
                X[i][j] = x[j - degree - 1][i];
            }
        }

        MatrixOperations Mo = new MatrixOperations();
        float[][] XT = Mo.transpose(X);
        float[][] XTX = Mo.matrixProduct(XT, X);
        float[][] XTX_inv = Mo.inverseGaussJordan(XTX);
        float[][] B = Mo.matrixProduct(XTX_inv, XT);
        return Mo.multiplyMatrixVector(B, y);
    }
}
