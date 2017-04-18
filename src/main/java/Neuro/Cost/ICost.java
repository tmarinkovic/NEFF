package Neuro.Cost;

import org.jblas.DoubleMatrix;

/**
 * Interface used to define cost functions of network
 */
public interface ICost {

    /**
     * function that define loss function
     * @param predictedLabel 1D array of labels produced by model
     * @param trueLabel double value of real label, later converted into one hot vector
     * @return double scalar loss
     */
    double lossFunction(double predictedLabel[], double[] trueLabel);

    double calculateLost(DoubleMatrix output, DoubleMatrix y);

    DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y);

}
