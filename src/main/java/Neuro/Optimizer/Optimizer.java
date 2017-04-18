package Neuro.Optimizer;

import Neuro.Network.Parameters;
import org.jblas.DoubleMatrix;

import java.util.Collections;
import java.util.List;

public class Optimizer {

    public static IOptimizer set(IOptimizer iOptimizer){
        return iOptimizer;
    }

    public static IOptimizer GradientDescentOptimizer(double learningRate) {
        return new GradientDescentOptimizer(learningRate);
    }

}
