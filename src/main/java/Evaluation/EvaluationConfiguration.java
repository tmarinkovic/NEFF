package Evaluation;

public class EvaluationConfiguration {

    private boolean evaluteLoss;
    private int evaluteLossSteps;

    public boolean isEvaluteLoss() {
        return evaluteLoss;
    }

    public EvaluationConfiguration setEvaluteLoss(boolean evaluteLoss) {
        this.evaluteLoss = evaluteLoss;
        return this;
    }

    public int getEvaluteLossSteps() {
        return evaluteLossSteps;
    }

    public void setEvaluteLossSteps(int evaluteLossSteps) {
        this.evaluteLossSteps = evaluteLossSteps;
    }
}
