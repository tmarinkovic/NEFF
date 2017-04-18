package Neuro.Cost;

public class Cost {

    public static ICost set(ICost iCost){
        return iCost;
    }

    public static ICost HalfSquaredError(){
        return new HalfSquaredError();
    }
}
