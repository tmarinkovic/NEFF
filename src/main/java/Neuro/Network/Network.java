package Neuro.Network;

import Data.Data;
import Neuro.Activation.IActivation;
import Neuro.Cost.ICost;
import Neuro.Optimizer.IOptimizer;
import Neuro.Output.IOutput;

public interface Network {

    void setData(Data data);

    void learnPass(int maxPass);

    void learnPass(int maxPass, double maxError);

    void learnEpoch(int maxEpoch);

    void learnEpoch(int maxEpoch, double maxError);

    void setOptimizer(IOptimizer optimizer);

    void setCost(ICost cost);

    void setActivation(IActivation activation);

    void setOutput(IOutput output);
}
