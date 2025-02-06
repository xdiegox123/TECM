package src.core.metrics;

import src.interfaces.metrics.coefficientDetermination;


/**
 * Implementación de la interfaz `coefficientDetermination` para calcular el coeficiente de determinación (R^2).
 * Esta clase proporciona la lógica para calcular el valor de R^2 a partir de los valores reales, los valores predichos
 * y la media de los valores reales, como se define en la fórmula estándar.
 */
public class CoefficientOfDetermination implements coefficientDetermination {

    /**
     * Calcula el coeficiente de determinación (R^2) a partir de los valores reales, los valores predichos y la media
     * de los valores reales.
     * 
     * La fórmula para R^2 es la siguiente:
     * R^2 = 1 - (SS_residual / SS_total)
     * 
     * Donde:
     *  - SS_residual (Suma de los cuadrados de los residuos) es la suma de las diferencias al cuadrado entre los
     *    valores reales y los valores predichos.
     *  - SS_total (Suma total de los cuadrados) es la suma de las diferencias al cuadrado entre los valores reales
     *    y la media de los valores reales.
     * 
     * El coeficiente de determinación indica la proporción de la varianza total en los datos que es explicada por el
     * modelo.
     * 
     * @param y Un arreglo de valores reales (observaciones).
     * @param y_pred Un arreglo de valores predichos por el modelo.
     * @param y_mean Un arreglo de la media de los valores reales.
     * @return El coeficiente de determinación R^2, que varía entre 0 y 1, donde valores cercanos a 1 indican un
     *         buen ajuste del modelo.
     * @throws IllegalArgumentException Si la varianza total o la varianza residual es cero, lo que haría que el cálculo
     *                                   no sea válido.
     */
    @Override
    public float calculate(float[] y, float[] y_pred, float y_mean) {
        float ss_total = 0;
        float ss_residual = 0;
        
        for (int i = 0; i < y.length; i++) {
            ss_total += Math.pow(y[i] - y_mean, 2);
            ss_residual += Math.pow(y[i] - y_pred[i], 2);
        }
        
        if (ss_total == 0 || ss_residual == 0) {
            throw new IllegalArgumentException("Cannot calculate R-squared with zero variance");
        }

        float r_squared = 1 - (ss_residual / ss_total);
        return r_squared;
    }
    
}
