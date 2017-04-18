package Neuro.Cost;


import org.jblas.DoubleMatrix;

public class MeanSquaredError extends ACost {
    @Override
    public double lossFunction(double[] predictedLabel, double[] trueLabel) {
        double loss = 0;
        for (int i = 0; i < predictedLabel.length; i++) {
            loss += Math.pow((trueLabel[i] - predictedLabel[i]), 2);
        }
        return loss / predictedLabel.length;
    }

    @Override
    public DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y) {
        return output.sub(y);
    }
}
