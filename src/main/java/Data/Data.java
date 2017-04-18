package Data;


import Helper.Helper;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Data {

    private double[][] x;
    private double[][] y;
    private int batchSize;
    private int xCounter = 0;
    private int yCounter = 0;
    private double trainRatio;
    private double[][] xTrain;
    private double[][] xTest;
    private double[][] yTrain;
    private double[][] yTest;
    private DoubleMatrix xTestMatrix;
    private DoubleMatrix yTestMatrix;
    private int currentEpoch = 0;


    public Data(double[][] x, double[][] y, int batchSize, double trainRatio) {
        this.x = x;
        this.y = y;
        this.batchSize = batchSize;
        this.trainRatio = trainRatio;
        splitTrainTest();
    }

    public Data(double[][] x, double[] y, int batchSize, int clusters, double trainRatio) {
        this.x = x;
        this.y = oneHotTransform(y, clusters);
        this.batchSize = batchSize;
        this.trainRatio = trainRatio;
        splitTrainTest();
    }

    private void splitTrainTest() {
        if (trainRatio == 1) {
            xTrain = x;
            yTrain = y;
        } else {
            List<Integer> indexes = new ArrayList<>();
            for (int i = 0; i < x.length; i++) {
                indexes.add(i);
            }
            Collections.shuffle(indexes);
            int indexSize = indexes.size();
            int trainSize = (int) (indexSize * trainRatio);
            int testSize = indexSize - trainSize;
            xTrain = new double[trainSize][x[0].length];
            yTrain = new double[trainSize][x[0].length];
            xTest = new double[testSize][x[0].length];
            yTest = new double[testSize][x[0].length];

            for (int i = 0; i < indexSize; i++) {
                if (i < trainSize) {
                    xTrain[i] = x[indexes.get(i)];
                    yTrain[i] = y[indexes.get(i)];
                } else {
                    xTest[i - trainSize] = x[indexes.get(i)];
                    yTest[i - trainSize] = y[indexes.get(i)];
                }
            }
            xTestMatrix = new DoubleMatrix(xTest);
            yTestMatrix = new DoubleMatrix(yTest);
        }
    }

    public double[][] getNextXBatch() {
        double[][] xBatch = new double[batchSize][x[0].length];
        if (xCounter + batchSize > xTrain.length) {
            xCounter = 0;
            currentEpoch++;
        }
        for (int i = xCounter; i < batchSize + xCounter; i++) {
            xBatch[i - xCounter] = xTrain[i];
        }
        xCounter += batchSize;
        return xBatch;
    }

    public double[][] getNextYBatch() {
        double[][] yBatch = new double[batchSize][y[0].length];
        if (yCounter + batchSize >= xTrain.length) {
            yCounter = 0;
        }
        for (int i = yCounter; i < batchSize + yCounter; i++) {
            yBatch[i - yCounter] = yTrain[i];
        }
        yCounter += batchSize;
        return yBatch;
    }


    public double[][] getX() {
        return x;
    }

    public double[][] getY() {
        return y;
    }

    public double[][] getxTrain() {
        return xTrain;
    }

    public double[][] getxTest() {
        return xTest;
    }

    public double[][] getyTrain() {
        return yTrain;
    }

    public double[][] getyTest() {
        return yTest;
    }

    public DoubleMatrix getxTestMatrix() {
        return xTestMatrix;
    }

    public DoubleMatrix getyTestMatrix() {
        return yTestMatrix;
    }

    private double[][] oneHotTransform(double[] labels, int size) {
        double[][] y = new double[labels.length][size];
        for (int i = 0; i < y.length; i++) {
            y[i][(int) labels[i]] = 1;
        }
        return y;
    }

    public int getCurrentEpoch() {
        return currentEpoch;
    }

    public double[] getBoundaries() {
        double[] output = new double[4];
        double[] xDim = new double[x.length];
        double[] yDim = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            xDim[i] = x[i][0];
            yDim[i] = x[i][1];
        }
        output[0] = Arrays.stream(xDim).min().getAsDouble();
        output[1] = Arrays.stream(xDim).max().getAsDouble();
        output[2] = Arrays.stream(yDim).min().getAsDouble();
        output[3] = Arrays.stream(yDim).max().getAsDouble();

        return output;
    }

}
