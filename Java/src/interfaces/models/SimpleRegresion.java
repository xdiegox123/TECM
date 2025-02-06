package src.interfaces.models;

/**
 * Interfaz para la regresión simple.
 * Esta interfaz define el método necesario para calcular los parámetros
 * de una regresión simple dada una serie de datos de entrada.
 */
public interface SimpleRegresion {

    /**
     * Método para calcular los parámetros de la regresión simple.
     * Este método toma dos arreglos de números flotantes, `x` y `y`, que representan
     * las variables independientes y dependientes, respectivamente, y devuelve un arreglo
     * con los resultados de la regresión simple (como la pendiente y la intersección).
     *
     * @param x Un arreglo de números flotantes que representa las variables independientes (entradas).
     * @param y Un arreglo de números flotantes que representa las variables dependientes (salidas).
     * @return Un arreglo de números flotantes que contiene los resultados de la regresión simple (pendiente y la intersección).
     */
    public float[] computeRegression(float[] x, float[] y);

    public float[] getRegressionComponents();
}
