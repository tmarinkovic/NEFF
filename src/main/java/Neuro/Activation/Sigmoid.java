package Neuro.Activation;

import org.jblas.DoubleMatrix;


public class Sigmoid implements IActivation {

    @Override
    public DoubleMatrix forward(DoubleMatrix net) {
        for ( int i = 0 ; i < net.getRows() ; i++ ){
            for ( int j = 0 ; j < net.getColumns() ; j++ ){
                net.put(i, j, 1 / ( 1 + Math.exp( - net.get(i, j) ) ) );
            }
        }
        return net;
    }

    @Override
    public DoubleMatrix backpropagate(DoubleMatrix gradient) {
        for ( int row = 0 ; row < gradient.getRows() ; row++ ){
            for ( int column = 0 ; column < gradient.getColumns() ; column++ ){
                double element = gradient.get(row, column);
                gradient.put(row, column, element * ( 1 - element ) );
            }
        }
        return gradient;
    }
}