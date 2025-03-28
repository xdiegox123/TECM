package examples;

import src.core.models.LeastSquaredRegressionModel;
import src.core.predictivemodels.LeastSquaredRegression;
import src.core.utils.dtos.TestData;
import src.interfaces.examples.RegressionExample;

public class LeastSquaredRegressionExample extends RegressionExample {

    @Override
    public void initialize(float segmentation, int degree) {
        // 1. Initialize model instance
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();

        // 2. Create models using different splitting techniques
        LeastSquaredRegression modelSequential = new LeastSquaredRegression(machineEfficienciesY, batchSizeX);
        LeastSquaredRegression modelIntercalate = new LeastSquaredRegression(machineEfficienciesY, batchSizeX);
        LeastSquaredRegression modelRandom = new LeastSquaredRegression(machineEfficienciesY, batchSizeX);

        // 3. Train models using different splitting methods
        TestData testSequentialData = modelSequential.training(2, 0.3f, degree, model);
        TestData testRandomData = modelRandom.training(1, 0.3f, degree, model);
        TestData testIntercalateData = modelIntercalate.training(3, 0.3f, degree, model);

        // 4. Evaluate and find the best model based on the coefficient of determination
        // (R^2)
        bestCoefficient = Math.max(modelSequential.getParameters().getCod(),
                Math.max(modelRandom.getParameters().getCod(), modelIntercalate.getParameters().getCod()));

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
        printGraph(convertFloatArrayToDouble(testX[0]), convertFloatArrayToDouble(predictions),
                convertFloatArrayToDouble(testY));
    }
}
