package Neuro.Optimizer;

import Neuro.Network.Parameters;
import org.jblas.DoubleMatrix;

import java.util.Collections;
import java.util.List;

public class GradientDescentOptimizer implements IOptimizer {

    private double learningRate;

    public GradientDescentOptimizer(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public void update(Parameters parameters, List<DoubleMatrix> deltaWeights) {
        Collections.reverse(deltaWeights);
        for ( int i = 0 ; i < deltaWeights.size() ; i++ ){
            DoubleMatrix gradient = deltaWeights.get(i).mul(learningRate);
            DoubleMatrix newWeights = parameters.getWeights().get(i).sub(gradient);
            parameters.getWeights().set(i, newWeights);
        }
    }
}