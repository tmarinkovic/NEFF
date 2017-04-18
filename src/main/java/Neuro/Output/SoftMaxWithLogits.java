package Neuro.Output;

import org.jblas.DoubleMatrix;

public class SoftMaxWithLogits implements IOutput {

    @Override
    public DoubleMatrix forward(DoubleMatrix net) {
        return normalize(net);
    }

    @Override
    public DoubleMatrix backpropagate(DoubleMatrix output, DoubleMatrix y) {
        return output.sub(y);
    }

    private DoubleMatrix normalize(DoubleMatrix output){
        for ( int i = 0 ; i < output.getRows() ; i++){
            output.putRow(i, softMax(output.getRow(i)));
        }
        return output;
    }

    private DoubleMatrix softMax(DoubleMatrix row){
        double sum = 0;
        for ( int i = 0 ; i < row.getColumns() ; i++){
            sum += Math.exp(row.get(i));
        }
        for ( int i = 0 ; i < row.getColumns() ; i++){
            row.put(i, Math.exp(row.get(i))/sum);
        }
        return row;
    }

}
