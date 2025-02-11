package src.interfaces.metrics;

/**
 * Interfaz para calcular el coeficiente de determinación (R²) entre los valores
 * reales y predichos.
 * 
 * @see #calculate(float[], float[], float)
 */
public interface coefficientDetermination {

    /**
     * Calcula el coeficiente de determinación (R²) basado en los valores reales,
     * los valores predichos
     * y la media de los valores reales.
     * 
     * @param y      Valores reales (dependientes).
     * @param y_pred Valores predichos (dependientes).
     * @param y_mean La media de los valores reales.
     * @return El valor de R² que indica qué tan bien se ajusta la línea de
     *         regresión a los datos.
     * @throws IllegalArgumentException Si los valores de los datos tienen varianza
     *                                  cero.
     */
    public float calculate(float[] y, float[] y_pred, float y_mean);
}
