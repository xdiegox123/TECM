package src.interfaces.examples;

import src.core.utils.TestData;

/**
 * Interfaz que define los métodos para una implementación de regresión.
 * Esta interfaz incluye métodos para el entrenamiento, obtención de parámetros,
 * hiperparámetros y la predicción en un modelo de regresión.
 */
public interface Regresions {

    /**
     * Método para entrenar el modelo de regresión.
     * Este método debe implementar el proceso de entrenamiento que ajusta los parámetros
     * del modelo utilizando un conjunto de datos de entrenamiento.
     * 
     * @return Un arreglo de números flotantes con los resultados del entrenamiento.
     */
    public TestData training(int splitingMode, float segmentationPercent);

    /**
     * Método para obtener los parámetros del modelo de regresión.
     * Este método debe devolver los parámetros actuales del modelo después de que se haya entrenado.
     * 
     * @return Un arreglo de números flotantes que representa los parámetros del modelo.
     */
    public float[] getParameters();

     /**
     * Método para obtener los hiperparámetros del modelo de regresión.
     * Este método debe devolver los valores de los hiperparámetros utilizados en el modelo,
     * que son configuraciones previas al entrenamiento del modelo.
     * 
     * @return Un arreglo de números flotantes que representa los hiperparámetros del modelo.
     */
    public float[] getHyperParameters();

     /**
     * Método para realizar una predicción con el modelo de regresión.
     * Este método debe utilizar los datos de entrada para generar una predicción utilizando el modelo entrenado.
     * 
     * @param x Un arreglo de números flotantes que representa las características de entrada para la predicción.
     * @param y Un arreglo de números flotantes que representa las etiquetas o valores correspondientes a las características de entrada.
     * @return Un arreglo de números flotantes que contiene las predicciones generadas por el modelo para los datos de entrada.
     */
    public float[] prediction(float[] inputValues);
    
}