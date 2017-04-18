package Examples.FeedForward;

import Data.Data;
import Evaluation.EvaluationConfiguration;
import Neuro.Activation.Activation;
import Data.DataGenerator;
import Neuro.Cost.HalfSquaredError;
import Neuro.Network.FeedForward;
import Neuro.Optimizer.Optimizer;

public class Basic {
    public static void main(String[] args) {
        // set batch size
        int batchSize = 1;
        // set number of passes to run
        int maxPass = 10000;
        // set learning rate
        double learningRate = 0.5;
        // generate data
        Data data = DataGenerator.basicExample(batchSize);
        EvaluationConfiguration evaluationConfiguration = new EvaluationConfiguration();
        evaluationConfiguration.setEvaluteLoss(false);
        // initialize network
        FeedForward network = new FeedForward(evaluationConfiguration);
        // set network parameters
        network.setData(data);
        network.setNetworkConfiguration("2x2x2");
        network.setActivation(Activation.Sigmoid());
        network.setCost(new HalfSquaredError());
        network.setOptimizer(Optimizer.GradientDescentOptimizer(learningRate));
        // start learning
        network.learnPass(maxPass);
    }
}
