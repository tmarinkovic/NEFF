package Visualize;

import Data.Data;
import Evaluation.EvaluationConfiguration;
import Helper.Helper;
import Neuro.Network.FeedForward;
import org.jblas.DoubleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Visualize {

    public static void plot(Data data, FeedForward network, EvaluationConfiguration evaluationConfiguration) {
        EventQueue.invokeLater(() -> new GUI().display(
                plotDataAndDecisionLine(data, network),
                plotLossFunction(network, evaluationConfiguration)
        ));
    }

    private static ChartPanel plotLossFunction(FeedForward network, EvaluationConfiguration evaluationConfiguration){
        final XYSeries seriesTrain = new XYSeries( "Train error" );
        final XYSeries seriesTest = new XYSeries( "Test error" );
        for (int i = 0; i < network.getTrainLoss().size() ; i++ ){
            seriesTrain.add(i, network.getTrainLoss().get(i));
        }
        for (int i = 0; i < network.getTestLoss().size() ; i++ ){
            seriesTest.add(i * evaluationConfiguration.getEvaluteLossSteps(), network.getTestLoss().get(i));
        }
        final XYSeriesCollection dataset = new XYSeriesCollection( );

        dataset.addSeries( seriesTest );
        dataset.addSeries( seriesTrain );


        JFreeChart chart = ChartFactory.createXYLineChart(
                "Loss function" ,
                "Step" ,
                "Loss" ,
                dataset ,
                PlotOrientation.VERTICAL ,
                true , true , false);


        return new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 500);
            }
        };
    }

    private static ChartPanel plotDataAndDecisionLine(Data data, FeedForward network) {
        // container for both graphs
        XYPlot plot = new XYPlot();
        ////////////////////////////////////////////Create scatter plot/////////////////////////////////////////////////
        XYSeriesCollection scatterPlot = new XYSeriesCollection();
        XYSeries cluster1 = new XYSeries("Train Cluster 1");
        XYSeries cluster2 = new XYSeries("Train Cluster 2");
        // fill out data
        for (int i = 0; i < data.getxTrain().length; i++) {
            double x = data.getxTrain()[i][0];
            double y = data.getxTrain()[i][1];
            // get class
            if (argOne(data.getyTrain()[i]) == 0) {
                cluster1.add(x, y);
            } else {
                cluster2.add(x, y);
            }
        }
        XYSeries cluster3 = new XYSeries("Test Cluster 1");
        XYSeries cluster4 = new XYSeries("Test Cluster 2");
        // fill out data
        for (int i = 0; i < data.getxTest().length; i++) {
            double x = data.getxTest()[i][0];
            double y = data.getxTest()[i][1];
            // get class
            if (argOne(data.getyTest()[i]) == 0) {
                cluster3.add(x, y);
            } else {
                cluster4.add(x, y);
            }
        }
        // add data to series
        scatterPlot.addSeries(cluster1);
        scatterPlot.addSeries(cluster3);
        scatterPlot.addSeries(cluster2);
        scatterPlot.addSeries(cluster4);
        // Create the scatter data, renderer, and axis
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        // Set markers as dots
        renderer1.setSeriesPaint(0, new Color(57,106,177));
        renderer1.setSeriesPaint(1, new Color(57,106,177));
        renderer1.setSeriesPaint(2, new Color(218,124,48));
        renderer1.setSeriesPaint(3, new Color(218,124,48));
        renderer1.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer1.setSeriesShape(2, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer1.setSeriesShape(1, new Rectangle2D.Double(-3, -3, 6, 6));
        renderer1.setSeriesShape(3, new Rectangle2D.Double(-3, -3, 6, 6));
        ValueAxis domain = new NumberAxis("X");
        ValueAxis range = new NumberAxis("Y");
        // Set the scatter data, renderer, and axis into plot
        plot.setDataset(0, scatterPlot);
        plot.setRenderer(0, renderer1);
        plot.setDomainAxis(0, domain);
        plot.setRangeAxis(0, range);
        // Map the scatter to the first Domain and first Range
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToRangeAxis(0, 0);
        /////////////////////////////////////////////Create line plot 1/////////////////////////////////////////////////
        XYSeriesCollection linePlot = new XYSeriesCollection();
        linePlot.addSeries(getLine(data.getBoundaries(), network));
        // Create the line data, renderer, and axis
        XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true,false);
        renderer2.setSeriesPaint(0, new Color(0x00, 0x00, 0x00));
        // Set the line data, renderer, and axis into plot
        plot.setDataset(1, linePlot);
        plot.setRenderer(1, renderer2);
        plot.setDomainAxis(1, domain);
        plot.setRangeAxis(1, range);
        // Map the line to the second Domain and second Range
        plot.mapDatasetToDomainAxis(1, 1);
        plot.mapDatasetToRangeAxis(1, 1);
        /////////////////////////////////////////////Create line plot 2/////////////////////////////////////////////////
        XYSeriesCollection linePlot2 = new XYSeriesCollection();
        linePlot.addSeries(getLine2(data.getBoundaries(), network));
        // Create the line data, renderer, and axis
        XYItemRenderer renderer3 = new XYLineAndShapeRenderer(true,false);
        renderer3.setSeriesPaint(0, new Color(0x00, 0x00, 0x00));
        // Set the line data, renderer, and axis into plot
        plot.setDataset(1, linePlot);
        plot.setRenderer(1, renderer3);
        plot.setDomainAxis(1, domain);
        plot.setRangeAxis(1, range);
        // Map the line to the second Domain and second Range
        plot.mapDatasetToDomainAxis(1, 1);
        plot.mapDatasetToRangeAxis(1, 1);
        // Create the chart with the plot and a legend
        JFreeChart chart = new JFreeChart("Problem plot", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        return new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 500);
            }
        };
    }

    private static XYSeries getLine(double[] boundaries, FeedForward network) {
        int xDensity = 100;
        int yDensity = 100;
        XYSeries dots = new XYSeries("Decision line");
        for (double x = boundaries[0]; x < boundaries[1]; x += 1 / (double) xDensity) {
            int initialClass = 0;
            for (double y = boundaries[2]; y < boundaries[3]; y += 1 / (double) yDensity) {
                double[] output = network.forwardPass(new DoubleMatrix(new double[]{x, y}).transpose()).toArray();
                if ( y == boundaries[2] ){
                    initialClass = argMax(output);
                }
                else{
                    if ( initialClass != argMax(output)){
                        dots.add(x, y);
                        break;
                    }
                }
            }

        }
        return dots;
    }

    private static XYSeries getLine2(double[] boundaries, FeedForward network) {
        int xDensity = 100;
        int yDensity = 100;
        XYSeries dots = new XYSeries("Decision line");
        for (double y = boundaries[2]; y < boundaries[3]; y += 1 / (double) yDensity) {
            int initialClass = 0;
            for (double x = boundaries[0]; x < boundaries[1]; x += 1 / (double) xDensity) {
                double[] output = network.forwardPass(new DoubleMatrix(new double[]{x, y}).transpose()).toArray();
                if ( y == boundaries[2] ){
                    initialClass = argMax(output);
                }
                else{
                    if ( initialClass != argMax(output)){
                        dots.add(x, y);
                        break;
                    }
                }
            }

        }
        return dots;
    }

    private static int argOne(double[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    private static int argMax(double[] array) {
        double max = Double.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max ) {
                max = array[i];
                index = i;
            }
        }
        return index;
    }

}