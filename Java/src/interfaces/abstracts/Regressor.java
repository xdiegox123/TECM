package src.interfaces.abstracts;

/**
 * Interfaz para la regresión lineal simple.
 * Define los métodos para calcular los parámetros de una regresión simple dados
 * los datos de entrada.
 * 
 * @see #computeRegression(float[], float[])
 */
public abstract class Regressor {
  /**
   * Calcula los parámetros de la regresión lineal simple (intercepto y
   * pendiente).
   * Utiliza el método de mínimos cuadrados para encontrar la recta de mejor
   * ajuste.
   * 
   * @param x Arreglo de valores de la variable independiente (X).
   * @param y Arreglo de valores de la variable dependiente (Y).
   * @return Un arreglo con los parámetros de la regresión: [b0, b1].
   * @throws IllegalArgumentException Si los arreglos `x` o `y` son nulos o tienen
   *                                  longitudes diferentes.
   */

  // Multiple
  public float[] computeRegression(float[] y, float[]... x) {
    return null;
  }

  // Polinomial
  public float[] computeRegression(int degree, float[] y, float[]... x) {
    return null;
  }

  // Simple
  public float[] computeRegression(float[] y, float[] x) {
    return null;
  }

}