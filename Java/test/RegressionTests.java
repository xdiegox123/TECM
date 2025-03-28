package test;
import examples.LeastSquaredRegressionExample;
import examples.SimpleLinearRegressionExample;
import org.junit.jupiter.api.Test;
import src.core.models.LeastSquaredRegressionModel;
import src.core.models.SimpleLinearRegressionModel;
import src.core.predictivemodels.LeastSquaredRegression;
import src.core.predictivemodels.SimpleLinearRegression;
import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class RegressionTests {

    @Test
    void testSimpleLinearRegressionTrainingSequential() {
        // Arrange
        SimpleLinearRegressionExample example = new SimpleLinearRegressionExample();
        SimpleLinearRegressionModel model = new SimpleLinearRegressionModel();
        SimpleLinearRegression slr = new SimpleLinearRegression(example.batchSizeX, example.machineEfficienciesY);

        // Act
        TestData testData = slr.training(2, 0.3f, model);
        RegressionParams params = slr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyX());
        assertNotNull(testData.getTestDependentY());
        assertNotEquals(0.0f, params.getbOne());
        assertNotEquals(0.0f, params.getbZero());
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testSimpleLinearRegressionTrainingRandom() {
        // Arrange
        SimpleLinearRegressionExample example = new SimpleLinearRegressionExample();
        SimpleLinearRegressionModel model = new SimpleLinearRegressionModel();
        SimpleLinearRegression slr = new SimpleLinearRegression(example.batchSizeX, example.machineEfficienciesY);

        // Act
        TestData testData = slr.training(1, 0.3f, model);
        RegressionParams params = slr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyX());
        assertNotNull(testData.getTestDependentY());
        assertNotEquals(0.0f, params.getbOne());
        assertNotEquals(0.0f, params.getbZero());
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testSimpleLinearRegressionTrainingIntercalate() {
        // Arrange
        SimpleLinearRegressionExample example = new SimpleLinearRegressionExample();
        SimpleLinearRegressionModel model = new SimpleLinearRegressionModel();
        SimpleLinearRegression slr = new SimpleLinearRegression(example.batchSizeX, example.machineEfficienciesY);

        // Act
        TestData testData = slr.training(3, 0.3f, model);
        RegressionParams params = slr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyX());
        assertNotNull(testData.getTestDependentY());
        assertNotEquals(0.0f, params.getbOne());
        assertNotEquals(0.0f, params.getbZero());
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testSimpleLinearRegressionPrediction() {
        // Arrange
        SimpleLinearRegressionExample example = new SimpleLinearRegressionExample();
        SimpleLinearRegressionModel model = new SimpleLinearRegressionModel();
        SimpleLinearRegression slr = new SimpleLinearRegression(example.batchSizeX, example.machineEfficienciesY);
        slr.training(2, 0.3f, model);
        float[] inputValues = {100, 110, 120};

        // Act
        float[] predictions = slr.prediction(inputValues);

        // Assert
        assertNotNull(predictions);
        assertEquals(inputValues.length, predictions.length);
    }

    @Test
    void testSimpleLinearRegressionGetParametersBeforeTraining() {
        // Arrange
        SimpleLinearRegressionExample example = new SimpleLinearRegressionExample();
        SimpleLinearRegression slr = new SimpleLinearRegression(example.batchSizeX, example.machineEfficienciesY);

        // Act & Assert
        assertThrows(IllegalStateException.class, slr::getParameters);
    }

    @Test
    void testLeastSquaredRegressionTrainingSequentialDegree1() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);

        // Act
        TestData testData = lsr.training(2, 0.3f, 1, model);
        RegressionParams params = lsr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyMatrixX());
        assertNotNull(testData.getTestDependentY());
        assertNotNull(params.getBn());
        assertTrue(params.getBn().length > 0);
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testLeastSquaredRegressionTrainingRandomDegree2() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);

        // Act
        TestData testData = lsr.training(1, 0.3f, 2, model);
        RegressionParams params = lsr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyMatrixX());
        assertNotNull(testData.getTestDependentY());
        assertNotNull(params.getBn());
        assertTrue(params.getBn().length > 0);
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testLeastSquaredRegressionTrainingIntercalateDegree3() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);

        // Act
        TestData testData = lsr.training(3, 0.3f, 3, model);
        RegressionParams params = lsr.getParameters();

        // Assert
        assertNotNull(testData);
        assertNotNull(testData.getTestIndependentlyMatrixX());
        assertNotNull(testData.getTestDependentY());
        assertNotNull(params.getBn());
        assertTrue(params.getBn().length > 0);
        assertTrue(params.getCod() >= 0 && params.getCod() <= 1);
        assertTrue(params.getCof() >= 0);
    }

    @Test
    void testLeastSquaredRegressionMultiPredictionMultiple() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);
        lsr.training(2, 0.3f, 2, model);
        float[][] inputValues = {{100, 110, 120}, {90, 80, 70}};

        // Act
        float[] predictions = lsr.multiPredictionMultiple(inputValues);

        // Assert
        assertNotNull(predictions);
        assertEquals(inputValues[0].length * inputValues.length, predictions.length);
    }

    @Test
    void testLeastSquaredRegressionGetParametersBeforeTraining() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);

        // Act & Assert
        assertThrows(IllegalStateException.class, lsr::getParameters);
    }

    @Test
    void testLeastSquaredRegressionPredictionUnsupported() {
        // Arrange
        LeastSquaredRegressionExample example = new LeastSquaredRegressionExample();
        LeastSquaredRegressionModel model = new LeastSquaredRegressionModel();
        LeastSquaredRegression lsr = new LeastSquaredRegression(example.machineEfficienciesY, example.batchSizeX);
        lsr.training(2, 0.3f, 2, model);
        float[] inputValues = {100, 110, 120};

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> lsr.prediction(inputValues));
    }
}

