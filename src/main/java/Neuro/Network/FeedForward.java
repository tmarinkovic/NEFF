package Neuro.Network;

import Data.Data;
import Evaluation.EvaluationConfiguration;
import Neuro.Activation.IActivation;
import Neuro.Cost.ICost;
import Neuro.Optimizer.IOptimizer;
import Neuro.Output.IOutput;
import org.jblas.DoubleMatrix;
import java.util.ArrayList;
import java.util.List;

import static Helper.Colors.ANSI_RED;
import static Helper.Colors.ANSI_RESET;

/**
 * Implementation of feed forward neural network
 */
public class FeedForward extends NetworkWrapper implements Network {

    private Parameters parameters = new Parameters();
    private Data data;
    private IOptimizer optimizer;
    private IActivation activation;
    private ICost cost;
    private IOutput outputActivation;
    private List<Double> trainLoss = new ArrayList<>();
    private List<Double> testLoss = new ArrayList<>();
    private EvaluationConfiguration evaluationConfiguration;

    public FeedForward(EvaluationConfiguration evaluationConfiguration){
        this.evaluationConfiguration = evaluationConfiguration;
    }

    /**
     * Set initial weights and biases
     * @param configuration String definition of layers
     */
    public void setNetworkConfiguration(String configuration) {
        parameters.setWeights(getWeightsFromConfiguration(configuration));
        parameters.setBiases(getBiasesFromConfiguration(configuration));
        parameters.initHiddenLayers();
    }//

    @Override
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public void setOptimizer(IOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    @Override
    public void setCost(ICost cost) {
        this.cost = cost;
    }

    @Override
    public void setActivation(IActivation activation) {
        this.activation = activation;
    }

    @Override
    public void setOutput(IOutput output) {
        this.outputActivation = output;
    }

    @Override
    public void learnPass(int maxPass) {
        for (int i = 0; i < maxPass; i++) {
            measureTestLoss(i);
            double loss = singlePass();
            System.out.println("Pass: " + i + " Train Loss: " + loss);
        }
    }

    @Override
    public void learnEpoch(int maxEpoch) {
        int numberOfEpochs = 0;
        int numberOfPasses = 0;
        for (int i = 0; i < maxEpoch; i++) {
            while ( data.getCurrentEpoch() == numberOfEpochs ){
                measureTestLoss(numberOfPasses);
                double loss = singlePass();
                numberOfPasses++;
                System.out.println("Epoch: " + numberOfEpochs + " Pass: " + numberOfPasses + " Train Loss: " + loss);
            }
            numberOfEpochs++;
        }
    }

    @Override
    public void learnPass(int maxPass, double maxError) {

    }

    @Override
    public void learnEpoch(int maxEpoch, double maxError) {

    }

    private double measureTestLoss(int i){
        double loss = Double.MAX_VALUE;
        // if evaluate loss on test set is turned ON
        if ( evaluationConfiguration.isEvaluteLoss() ){
            // every evaluate step
            if ( i % evaluationConfiguration.getEvaluteLossSteps() == 0){
                loss = calculateTestLoss();
                System.out.println(ANSI_RED + "Test loss: "+ loss + ANSI_RESET);
                testLoss.add(loss);
            }
        }
        return loss;
    }

    private double singlePass() {
        // input data
        DoubleMatrix x = new DoubleMatrix(data.getNextXBatch());
        DoubleMatrix y = new DoubleMatrix(data.getNextYBatch());
        // forward pass
        DoubleMatrix output = forwardPass(x);
        // calcute loss
        double loss = cost.calculateLost(output, y);
        // add loss to list for graph
        trainLoss.add(loss);
        // backpropagation
        backpropagation(output, x, y);
        return loss;
    }

    private double calculateTestLoss(){
        DoubleMatrix output = forwardPass(data.getxTestMatrix());
        return cost.calculateLost(output, data.getyTestMatrix());
    }


    public DoubleMatrix forwardPass(DoubleMatrix x) {
        DoubleMatrix input;
        DoubleMatrix currentWeights;
        DoubleMatrix output = null;

        // for each layer do forward pass
        for (int layer = 0; layer < parameters.getLayersCount(); layer++) {
            // if first layer take input data
            if (layer == 0) {
                input = x;
            }
            // else take output of last layer
            else {
                input = output;
            }
            currentWeights = parameters.getWeights().get(layer);
            // if we are at last layer, use softmax
            if ( layer == parameters.getLayersCount() - 1 ){
                output = outputActivation.forward(input.mmul(currentWeights));//.add(parameters.getBiases().get(layer)));
            }
            // if not use set activation function set in model parameters
            else{
                output = activation.forward(input.mmul(currentWeights));//.add(parameters.getBiases().get(layer)));
            }
            parameters.getHiddenLayers().set(layer, output);
        }
        return output;
    }

    /**
     * Handles backpropagation
     * @param output is output of neural netowrk
     * @param x is input data
     * @param y is real labels
     */
    private void backpropagation(DoubleMatrix output, DoubleMatrix x, DoubleMatrix y) {
        /////////////////////////////////////////update last layer//////////////////////////////////////////////////////
        // in backpropagation we need to looks weights from last layer to first so we transpose then
        transposeWeights();
        // save weights changes
        List<DoubleMatrix> deltaWeights = new ArrayList<>();
        // get index of last layer
        int lastLayer = parameters.getLayersCount() - 1;
        // backpropagate through loss function
        DoubleMatrix lossBackpropagation = outputActivation.backpropagate(output, y);
        // backpropagate through activation function
        DoubleMatrix outputBackpropagation = activation.backpropagate(parameters.getHiddenLayers().get(lastLayer));
        // multiple element-wise lossBackpropagation and outputBackpropagation
        DoubleMatrix saveGradient = lossBackpropagation.mul(outputBackpropagation);
        // get delta weights for last layer
        deltaWeights.add(saveGradient.transpose().mmul(parameters.getHiddenLayers().get(lastLayer - 1)));
        /////////////////////////////////////////update rest layers/////////////////////////////////////////////////////
        // get last weight index
        int weightIndex = parameters.getWeights().size() - 1;
        for ( int layer = lastLayer - 1 ; layer > 0 ; layer-- ) {
            // backpropagate through layer
            DoubleMatrix layerBackpropagation = saveGradient.mmul(parameters.getWeights().get(weightIndex));
            // backpropagate through activation function
            DoubleMatrix activationBackpropagation = activation.backpropagate(parameters.getHiddenLayers().get(layer));
            // multiple element-wise layerBackpropagation and activationBackpropagation
            saveGradient = layerBackpropagation.mul(activationBackpropagation);
            // get delta weights for current layer
            deltaWeights.add(saveGradient.transpose().mmul(parameters.getHiddenLayers().get(layer)));
            // get weight index of previous layer
            weightIndex--;
        }
        /////////////////////////////////////////update first layer/////////////////////////////////////////////////////
        // backpropagate through layer
        DoubleMatrix layerBackpropagation = saveGradient.mmul(parameters.getWeights().get(weightIndex));
        // backpropagate through activation function
        DoubleMatrix activationBackpropagation = activation.backpropagate(parameters.getHiddenLayers().get(0));
        // multiple element-wise layerBackpropagation and activationBackpropagation
        saveGradient = layerBackpropagation.mul(activationBackpropagation);
        // get delta weights for first layer
        deltaWeights.add(saveGradient.transpose().mmul(x));
        ///////////////////////////////////////////update weights///////////////////////////////////////////////////////
        optimizer.update(parameters, deltaWeights);
        // next step is new forward pass so we need to transpose weights again
        transposeWeights();
    }//

    private void transposeWeights(){
        for ( int i = 0 ; i < parameters.getWeights().size() ; i++ ){
            parameters.getWeights().set(i, parameters.getWeights().get(i).transpose());
        }
    }

    public List<Double> getTrainLoss(){
        return trainLoss;
    }
    public List<Double> getTestLoss(){
        return testLoss;
    }

}
