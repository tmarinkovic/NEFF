import Evaluation.EvaluationConfiguration;
import Data.Data;
import Neuro.Activation.Activation;
import Data.DataGenerator;
import Neuro.Cost.Cost;
import Neuro.Cost.HalfSquaredError;
import Neuro.Network.FeedForward;
import Neuro.Optimizer.Optimizer;
import Neuro.Output.Output;
import Visualize.Visualize;

public class Main {
    public static void main(String[] args) {
        // set batch size
        int batchSize = 16;
        // set number of passes to run
        int maxPass = 25;
        // set number of epochs to run
        int maxEpoch = 3;
        // set maximum error
        double maxError = 1e-5;
        // set learning rate
        double learningRate = 0.65;
        // generate data
        Data data = DataGenerator.get2DLinearSeparable(batchSize, 2);
        //Data data = DataGenerator.get2DNonLinearSeparable(batchSize, 2);
        // set evaluation configuration
        EvaluationConfiguration evaluationConfiguration = new EvaluationConfiguration();
        evaluationConfiguration.setEvaluteLoss(true).setEvaluteLossSteps(1);
        // initialize network
        FeedForward network = new FeedForward(evaluationConfiguration);
        // set network parameters
        network.setData(data);
        network.setNetworkConfiguration("2x5x2");
        network.setActivation(Activation.Sigmoid());
        network.setCost(Cost.HalfSquaredError());
        network.setOptimizer(Optimizer.GradientDescentOptimizer(learningRate));
        network.setOutput(Output.SoftMaxWithLogits());
        // start learning
        network.learnEpoch(maxEpoch);
        Visualize.plot(data, network, evaluationConfiguration);
    }
}
