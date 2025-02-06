package src.core.models.lienear;

import src.interfaces.models.SimpleRegresion;

public class SimpleLinearRegresion implements SimpleRegresion {

    private float sumX = 0.0f, sumY = 0.0f, sumXY = 0.0f, sumXSquare = 0.0f;

    /**
     * Método para calcular los parámetros de la regresión lineal simple.
     * Este método toma dos arreglos de números flotantes, `x` y `y`, que
     * representan
     * las variables independientes y dependientes, respectivamente, y devuelve un
     * arreglo
     * con los resultados de la regresión simple (la intersección `b0` y la
     * pendiente `b1`).
     *
     * El algoritmo utilizado para el cálculo es el de la regresión lineal por
     * mínimos cuadrados,
     * que busca encontrar la recta de mejor ajuste en el espacio bidimensional.
     *
     * @param x Un arreglo de números flotantes que representa las variables
     *          independientes (entradas).
     * @param y Un arreglo de números flotantes que representa las variables
     *          dependientes (salidas).
     * @return Un arreglo de números flotantes que contiene los parámetros de la
     *         regresión lineal:
     *         - `b0`: la intersección (valor en el eje Y cuando `x = 0`).
     *         - `b1`: la pendiente (cambio en `y` por cada unidad de cambio en
     *         `x`).
     * @throws IllegalArgumentException Si los arreglos `x` o `y` son nulos o tienen
     *                                  longitudes diferentes.
     */
    @Override
    public float[] computeRegression(float[] x, float[] y) {

        if (x == null || y == null)
            throw new IllegalArgumentException("Both arrays must not be null.");
        if (x.length != y.length)
            throw new IllegalArgumentException("Both arrays must have the same length.");

        int n = x.length;
        float b0 = 0.0f, b1 = 0.0f;

        for (int i = 0; i < n; i++) {
            sumY += y[i];
            sumXSquare += x[i] * x[i];
            sumXY += x[i] * y[i];
            sumX += x[i];
        }

        b1 = ((n * sumXY) - sumX * sumY) / ((n * sumXSquare) - sumX * sumX);
        b0 = (sumY - b1 * sumX) / n;
        return new float[] { b0, b1 };
    }

    /**
     * Método para obtener los componentes intermedios de la regresión lineal.
     * Este método devuelve los valores de las sumas y productos que son utilizados
     * en el cálculo de la regresión simple. Estos componentes son importantes para
     * entender cómo se calculan los parámetros de la regresión (`b0` y `b1`).
     *
     * @return Un arreglo de números flotantes que contiene los siguientes
     *         componentes:
     *         - La suma de los valores de `x` (sumX).
     *         - La suma de los valores de `y` (sumY).
     *         - La suma de los productos `x[i] * y[i]` (sumXY).
     *         - La suma de los cuadrados de los valores de `x` (sumXSquare).
     */
    @Override
    public float[] getRegressionComponents() {
        return new float[] { sumX, sumY, sumXY, sumXSquare };
    }
}
