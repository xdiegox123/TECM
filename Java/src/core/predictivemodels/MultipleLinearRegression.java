package src.core.predictivemodels;

import src.core.utils.dtos.RegressionParams;
import src.core.utils.dtos.TestData;
import src.interfaces.models.PredictiveModel;

public class MultipleLinearRegression implements PredictiveModel{

    @Override
    public TestData training(int splitingMode, float segmentationPercent, int selectModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'training'");
    }

    @Override
    public RegressionParams getParameters() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getParameters'");
    }

    @Override
    public float[] prediction(float[] inputValues) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prediction'");
    }
    
}
