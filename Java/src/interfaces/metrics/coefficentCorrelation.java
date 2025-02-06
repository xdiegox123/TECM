package src.interfaces.metrics;

public interface coefficentCorrelation {

    /**
     * Calcula el coeficiente de correlación de Pearson entre dos conjuntos de
     * datos.
     * 
     * @param x Un arreglo de valores numéricos del primer conjunto de datos.
     * @param y Un arreglo de valores numéricos del segundo conjunto de datos.
     * @return El coeficiente de correlación de Pearson, un valor entre -1 y 1 que
     *         indica la relación lineal entre los dos conjuntos de datos.
     *         - 1 indica una relación lineal positiva perfecta,
     *         - -1 indica una relación lineal negativa perfecta,
     *         - 0 indica ausencia de relación lineal.
     * @throws IllegalArgumentException Si los arreglos x y y no tienen la misma
     *                                  longitud, o si se intenta dividir por cero
     *                                  en el cálculo.
     */
    public float calculatePearson(float[] x, float[] y);
}
