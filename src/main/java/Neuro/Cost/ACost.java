package Neuro.Cost;

import org.jblas.DoubleMatrix;

public abstract class ACost implements ICost {

    @Override
    public double calculateLost(DoubleMatrix output, DoubleMatrix trueLabel) {
        double loss = 0;
        for ( int i = 0 ; i < output.getRows() ; i++ ){
            double[] predictedLabel = output.getRow(i).toArray();
            loss += lossFunction(predictedLabel, trueLabel.getRow(i).toArray());
        }
        return loss / output.getRows();
    }

    @Override
    public abstract double lossFunction(double[] predictedLabel, double[] trueLabel);

    @Override
    public abstract DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y);
}
