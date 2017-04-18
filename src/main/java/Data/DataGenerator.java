package Data;

import Helper.Helper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    /**
     * Generate 2D linear separable dataset with cluster "classes" and "examples" samples each
     * @return Data object
     */
    public static Data get2DLinearSeparable(int batchSize, int clusters){
        // parameters
        int dimensionality = 2;
        int examples = 100;
        // means and sigmas
        double[][] mean = getMeans(clusters);
        double[][] sigma = getSigmas(clusters);
        // variables
        Random random = new Random();
        double[][] x = new double[examples * clusters][dimensionality];
        double[] y = new double[clusters * examples];
        // generate data
        for ( int cluster = 0 ; cluster < clusters ; cluster++ ){
            for ( int example = 0 ; example < examples ; example++ ){
                for ( int dimension = 0 ; dimension < dimensionality ; dimension++ ){
                    x[cluster * examples + example][dimension] = random.nextGaussian() * sigma[cluster][dimension] + mean[cluster][dimension];
                }
                y[cluster * examples + example] = cluster;
            }
        }
        return new Data(x, y, batchSize, clusters, 0.7);
    }//

    /**
     * Generate 2D nonlinear separable dataset
     * @return Data object
     */
    public static Data get2DNonLinearSeparable(int batchSize, int clusters){
        double[][] x = new double[100 * clusters][2];
        double[] y = new double[100 * clusters];

        for ( int i = 0 ; i < 25 ; i++ ){
            x[i][0] = i / (double)10;
            x[i][1] = 0;
            y[i] = 0;
        }
        for ( int i = 25 ; i < 50 ; i++ ){
            x[i][0] = 0;
            x[i][1] = (i - 25) / (double)10;
            y[i] = 0;
        }

        double distance = 1;

        for ( int i = 50 ; i < 75 ; i++ ){
            x[i][0] = distance + (i - 50) / (double)10;
            x[i][1] = distance;
            y[i] = 1;
        }
        for ( int i = 75 ; i < 100 ; i++ ){
            x[i][0] = distance;
            x[i][1] = distance + (i - 75) / (double)10;
            y[i] = 1;
        }

        return new Data(x, y, batchSize, clusters, 0.7);
    }//

    /**
     * Generate simple data for basic test
     * @return Data object
     */
    public static Data basicExample(int batchSize){
        double[][] x = new double[][]{{0.05, 0.1}};
        double[][] y = new double[][]{{0.01, 0.99}};
        return new Data(x, y, batchSize, 1);
    }//

    private static double[][] getMeans(int clusters){
        double[][] mean = null;
        if ( clusters == 2){
            mean = new double[][]{{-2,-2},{2,2}};
        }
        else if ( clusters == 3){
            mean = new double[][]{{-2,2},{2,2},{0,-2}};
        }
        else if ( clusters == 4){
            mean = new double[][]{{-2,2},{2,2},{2,-2},{-2,-2}};
        }
        else if ( clusters == 5){
            mean = new double[][]{{-3,3},{3,3},{3,-3},{-3,-3},{0,0}};
        }
        return mean;
    }

    private static double[][] getSigmas(int clusters){
        double[][] sigma = null;
        if ( clusters == 2){
            sigma = new double[][]{{1,1},{1,1}};
        }
        else if ( clusters == 3){
            sigma = new double[][]{{1,1},{1,1},{1,1}};
        }
        else if ( clusters == 4){
            sigma = new double[][]{{1,1},{1,1},{1,1},{1,1}};
        }
        else if ( clusters == 5){
            sigma = new double[][]{{1,1},{1,1},{1,1},{1,1},{1,1}};
        }
        return sigma;
    }

}//
