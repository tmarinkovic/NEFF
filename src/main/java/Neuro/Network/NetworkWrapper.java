package Neuro.Network;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class that is used for common tasks needed for network to work, e.g initializing weights and biases
 */
public class NetworkWrapper {

    /**
     * Get weights necessary for model to work
     * @return List of weights
     */
    protected List<DoubleMatrix> getWeightsFromConfiguration(String configuration){
        List<List<Integer>> parseConfiguration = parseConfiguration(configuration);
        return populateRandomWeights(parseConfiguration);
    }//

    /**
     * Get biases necessary for model to work
     * @return List of biases
     */
    protected List<DoubleMatrix> getBiasesFromConfiguration(String configuration){
        List<List<Integer>> parseConfiguration = parseConfiguration(configuration);
        return populateBiases(parseConfiguration);
    }//

    /**
     * Parses network configuration based on string given from user
     * @return List of network configuration
     */
    private List<List<Integer>> parseConfiguration(String configuration){
        List<List<Integer>> parsedConfiguration = new ArrayList<>();
        String[] layers = configuration.split("x");
        for ( int i = 0 ; i < layers.length ; i++ ){
            if ( i != layers.length - 1 ) {
                List<Integer> layer = new ArrayList<>();
                layer.add(Integer.parseInt(layers[i]));
                layer.add(Integer.parseInt(layers[i + 1]));
                parsedConfiguration.add(layer);
            }
        }
        return parsedConfiguration;
    }//

    /**
     * Calculates initial matrix weights based on normal distribution with sigma based on number of
     * input and output connections
     * @return List of all weights necessary in model
     */
    private List<DoubleMatrix> populateRandomWeights(List<List<Integer>> parseConfiguration){
        Random random = new Random();
        // initialize list to hold weights
        List<DoubleMatrix> weights = new ArrayList<>();
        // loop through layers
        for ( int layer = 0 ; layer < parseConfiguration.size() ; layer++ ){
            List<Integer> layerConfiguration = parseConfiguration.get(layer);
            // initialize array
            double[][] weight = new double[layerConfiguration.get(0)][layerConfiguration.get(1)];
            // populate array with random values from normal distribution
            for ( int i = 0 ; i < weight.length ; i++ ){
                for ( int j = 0 ; j < weight[0].length ; j++ ){
                    weight[i][j] = random.nextGaussian() * getSigma(parseConfiguration, layer);
                }
            }
            // add array to list
            weights.add(new DoubleMatrix(weight));
        }
        return weights;
    }//


    /**
     * Calculate distribution to randomly populate initial weight matrix
     * @return sigma value
     */
    private double getSigma(List<List<Integer>> parseConfiguration, int layer){
        // if we are on last layer each neuron have one output
        if ( parseConfiguration.size() - 1 == layer ){
            return 6 / ( Math.sqrt(parseConfiguration.get(layer).get(0)) );
        }
        // else its defines in parseConfiguration
        else{
            return 6 / ( Math.sqrt(parseConfiguration.get(layer).get(0) + parseConfiguration.get(layer).get(1) ) );
        }
    }//

    /**
     * Populate biases with value 0.1
     * @return List of biases
     */
    private List<DoubleMatrix> populateBiases(List<List<Integer>> parseConfiguration){
        List<DoubleMatrix> biases = new ArrayList<>();
        for ( int layer = 0 ; layer < parseConfiguration.size() ; layer++ ){
            List<Integer> layerConfiguration = parseConfiguration.get(layer);
            double[] bias = new double[layerConfiguration.get(1)];
            Arrays.fill(bias, 0.1);
            biases.add(new DoubleMatrix(bias));
        }
        return biases;
    }//

}//
