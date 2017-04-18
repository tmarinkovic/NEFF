package Visualize;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartPanel;


public class GUI {

    public void display(
            ChartPanel plotDataAndDecisionLine,
            ChartPanel plotLossFunction
        ) {
        JFrame f = new JFrame("Visualizer");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JTabbedPane jtp = new JTabbedPane();
        jtp.add("Solution", plotDataAndDecisionLine);
        jtp.add("Loss function", plotLossFunction);
        f.add(jtp, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}