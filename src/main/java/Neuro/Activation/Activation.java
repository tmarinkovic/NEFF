package Neuro.Activation;

public class Activation {

    public static IActivation set(IActivation iActivation) {
        return iActivation;
    }

    public static IActivation Sigmoid() {
        return new Sigmoid();
    }

}
