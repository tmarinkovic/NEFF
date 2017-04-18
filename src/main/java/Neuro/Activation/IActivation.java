package Neuro.Activation;

import org.jblas.DoubleMatrix;

public interface IActivation {

    DoubleMatrix forward(DoubleMatrix net);

    DoubleMatrix backpropagate(DoubleMatrix gradient);
}
