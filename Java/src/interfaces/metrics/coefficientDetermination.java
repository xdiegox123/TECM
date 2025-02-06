package src.interfaces.metrics;

/**
 * Interfaz que define el contrato para el cálculo del coeficiente de determinación (R^2).
 * 
 * La implementación de esta interfaz debe definir cómo calcular el coeficiente de determinación
 * a partir de los valores reales, los valores predichos y la media de los valores reales.
 */
public interface coefficientDetermination {

    /**
     * Calcula el coeficiente de determinación (R^2) entre los valores reales y los valores predichos.
     * 
     * El coeficiente de determinación R^2 se calcula utilizando la fórmula:
     * R^2 = 1 - (SS_residual / SS_total)
     * Donde:
     *  - SS_residual es la suma de los cuadrados de las diferencias entre los valores reales y los valores predichos.
     *  - SS_total es la suma de los cuadrados de las diferencias entre los valores reales y la media de los valores reales.
     * 
     * @param y Un arreglo de valores reales (observaciones).
     * @param y_pred Un arreglo de valores predichos por el modelo.
     * @param y_mean Un arreglo de la media de los valores reales.
     * @return El coeficiente de determinación R^2, que es un valor entre 0 y 1, donde valores cercanos a 1 indican un buen ajuste del modelo.
     * @throws IllegalArgumentException Si la varianza total o la varianza residual es cero, lo que haría que el cálculo no sea válido.
     */
    public float calculate(float[] y, float[] y_pred, float y_mean);
}
