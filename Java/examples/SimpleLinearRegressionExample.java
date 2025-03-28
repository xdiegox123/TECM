package examples;

import src.core.models.SimpleLinearRegressionModel;
import src.core.predictivemodels.SimpleLinearRegression;
import src.core.utils.dtos.TestData;
import src.interfaces.examples.RegressionExample;

/**
 * Example of using Simple Linear Regression with different data splitting methods:
 * sequential, random, and intercalate.
 * This class demonstrates the initialization of models, training with different
 * data splitting techniques, and evaluating their performance.
 */
public class SimpleLinearRegressionExample extends RegressionExample {

    /**
     * Initializes the models, trains them using different data splitting methods,
     * and evaluates the results to print the best model's predictions and performance.
     */
    public void initialize(float segmentation) {
        // 1. Initialize model instance
        SimpleLinearRegressionModel model = new SimpleLinearRegressionModel();

        // 2. Create models using different splitting techniques
        SimpleLinearRegression modelSequential = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);
        SimpleLinearRegression modelIntercalate = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);
        SimpleLinearRegression modelRandom = new SimpleLinearRegression(batchSizeX, machineEfficienciesY);

        // 3. Train models using different splitting methods
        TestData testSequentialData = modelSequential.training(2, 0.3f, model);
        TestData testRandomData = modelRandom.training(1, 0.3f, model);
        TestData testIntercalateData = modelIntercalate.training(3, 0.3f, model);

        // 4. Evaluate and find the best model based on the coefficient of determination (R^2)
        float bestCoefficient = Math.max(modelSequential.getParameters().getCod(), Math.max(modelRandom.getParameters().getCod(), modelIntercalate.getParameters().getCod()));

        // 5. Print the best model's predictions and performance
        if (bestCoefficient == modelIntercalate.getParameters().getCod()) {
            printPredictions(modelIntercalate, testIntercalateData, "Intercalate dataset");
        } else if (bestCoefficient == modelRandom.getParameters().getCod()) {
            printPredictions(modelRandom, testRandomData, "Random dataset");
        } else if (bestCoefficient == modelSequential.getParameters().getCod()) {
            printPredictions(modelSequential, testSequentialData, "Sequential dataset");
        } else {
            System.out.println("Error: Could not determine the best model.");
        }

    }
}

/*
1- Corrige la documentación y agrega la necesaria.
2- Verifica que las variables estén nombradas de manera constante. Por ejemplo: si una variable realiza lo mismo en diferentes métodos debe tener el mismo nombre en todos.
3- separa bloques de procesos. Algunas funciones están separadas por bloques por ejemplo: //splitData
3.1- Todas las funciones deben estar separadas por bloques con una descripción de lo que hace ese bloque.
3.2- la descripción del bloque no debe ser mayor a 15 letras.
*/