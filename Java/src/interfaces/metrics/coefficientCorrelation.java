package src.interfaces.metrics;

/**
 * Interfaz para calcular el coeficiente de correlación de Pearson entre dos
 * conjuntos de datos.
 * 
 * @see #calculatePearson(float[], float[])
 */
public interface coefficientCorrelation {

    /**
     * Calcula el coeficiente de correlación de Pearson entre dos arreglos de datos.
     * El coeficiente de Pearson mide la relación lineal entre dos conjuntos de
     * datos.
     * 
     * @param x El primer conjunto de datos (variable independiente).
     * @param y El segundo conjunto de datos (variable dependiente).
     * @return El coeficiente de correlación de Pearson entre ambos conjuntos de
     *         datos.
     * @throws IllegalArgumentException Si los arreglos tienen longitudes
     *                                  diferentes.
     */
    public float calculatePearson(float[] x, float[] y);
}
