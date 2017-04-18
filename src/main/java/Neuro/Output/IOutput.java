package Neuro.Output;

import org.jblas.DoubleMatrix;

public interface IOutput {

    DoubleMatrix forward(DoubleMatrix net);

    DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y);
}
