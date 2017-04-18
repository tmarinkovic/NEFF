package Neuro.Network;

import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    private List<DoubleMatrix> weights;
    private List<DoubleMatrix> biases;

    private List<DoubleMatrix> weightGradient;
    private List<DoubleMatrix> biasGradient;

    private List<DoubleMatrix> hiddenLayers = new ArrayList<>();

    public int getLayersCount(){
        return weights.size();
    }

    public List<DoubleMatrix> getWeights() {
        return weights;
    }

    public void setWeights(List<DoubleMatrix> weights) {
        this.weights = weights;
    }

    public List<DoubleMatrix> getBiases() {
        return biases;
    }

    public void setBiases(List<DoubleMatrix> biases) {
        this.biases = biases;
    }

    public List<DoubleMatrix> getWeightGradient() {
        return weightGradient;
    }

    public void setWeightGradient(List<DoubleMatrix> weightGradient) {
        this.weightGradient = weightGradient;
    }

    public List<DoubleMatrix> getBiasGradient() {
        return biasGradient;
    }

    public void setBiasGradient(List<DoubleMatrix> biasGradient) {
        this.biasGradient = biasGradient;
    }

    public List<DoubleMatrix> getHiddenLayers() {
        return hiddenLayers;
    }

    public void setHiddenLayers(List<DoubleMatrix> hiddenLayers) {
        this.hiddenLayers = hiddenLayers;
    }

    public void addHiddenLayer(DoubleMatrix matrix){
        hiddenLayers.add(matrix);
    }

    public void initHiddenLayers(){
        for ( int i = 0 ; i < getLayersCount() ; i++ ){
            hiddenLayers.add(null);
        }
    }
}
