package Neuro.Optimizer;

import Neuro.Network.Parameters;
import org.jblas.DoubleMatrix;

import java.util.List;

public interface IOptimizer {

    void update(Parameters parameters, List<DoubleMatrix> deltaWeights);

}
