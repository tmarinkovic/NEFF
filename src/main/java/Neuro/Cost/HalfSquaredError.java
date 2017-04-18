package Neuro.Cost;

import org.jblas.DoubleMatrix;

public class HalfSquaredError extends ACost {

    @Override
    public double lossFunction(double[] predictedLabel, double[] trueLabel) {
        double loss = 0;
        for (int i = 0; i < predictedLabel.length; i++) {
            loss += 0.5 * Math.pow((trueLabel[i] - predictedLabel[i]), 2);
        }
        return loss;
    }

    @Override
    public DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y) {
        return output.sub(y);
    }
}
