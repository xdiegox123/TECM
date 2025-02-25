package src.interfaces.models;

import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.TestData;

/**
 * Interfaz que define los métodos necesarios para implementar un modelo de
 * regresión.
 * Incluye métodos para entrenar el modelo, obtener los parámetros, y realizar
 * predicciones.
 * 
 * @see TestData
 * @see RegressionParams
 */
public interface PredictiveModel {

  /**
   * Entrena el modelo de regresión utilizando los datos de entrada y un modo de
   * división.
   * Divide los datos en conjuntos de entrenamiento y prueba y entrena el modelo
   * con los datos de entrenamiento.
   * 
   * @param splitingMode        Modo de división de los datos (por ejemplo,
   *                            entrenamiento y prueba).
   * @param segmentationPercent Porcentaje de los datos que se utilizarán para
   *                            entrenamiento.
   * @param selectModel         El modelo de regresión a utilizar:
   *                            <ul>
   *                            <li>1 -> Linear Básico: Regresión lineal simple
   *                            (SLR).</li>
   *                            <li>2 -> MultipleOrPolynomialModel: Regresión de
   *                            mínimos cuadrados con múltiples variables o modelo
   *                            polinomial.</li>
   *                            </ul>
   * @return Un objeto {@link TestData} con los datos de prueba.
   */
  public TestData training(int splitingMode, float segmentationPercent, int selectModel);

  /**
   * Obtiene los parámetros del modelo (intercepto y pendiente) junto con el
   * coeficiente de determinación (R²)
   * y el coeficiente de correlación de Pearson (R).
   * 
   * @return Un objeto {@link RegressionParams} con los parámetros calculados del
   *         modelo.
   * @throws IllegalStateException Si no se ha ejecutado previamente el método
   *                               {@link #training}.
   */
  public RegressionParams getParameters();

   /**
   * Realiza predicciones para un conjunto de valores de entrada utilizando el
   * modelo entrenado.
   * 
   * @param inputValues Valores de la variable independiente (X) para predecir la
   *                    variable dependiente (Y).
   * @return Un arreglo de valores predichos de la variable dependiente (Y).
   * @throws IllegalStateException Si no se ha entrenado el modelo previamente.
   */
  public float[] prediction(float[] inputValues);

}