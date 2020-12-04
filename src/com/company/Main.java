package com.company;

import com.company.calculations.MyMatrix;
import com.company.calculations.MyVector;
import com.company.visualization.Screen;
import com.company.visualization.StandardJFrame;
import com.company.visualization.TwoDimensionalKartesianSystem;
import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {

    public static void main(String[] args) throws UnmatchedDimensionException {

        StandardJFrame frame = new StandardJFrame();

        TwoDimensionalKartesianSystem system = new TwoDimensionalKartesianSystem();
        frame.getContentPane().add(system);
        frame.setSize(frame.getPreferredSize());

        MyMatrix matrix = new MyMatrix(3, 2, 1,1,1,1,1,1);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                TwoDimensionalKartesianSystem.setHEIGHT(e.getComponent().getHeight());
                TwoDimensionalKartesianSystem.setWIDTH(e.getComponent().getWidth());
            }
        });

    }
    public static void main2(String[] args)
      throws UnmatchedDimensionException, IllegalParameterException {
        StandardJFrame frame = new StandardJFrame();

        Screen screen = new Screen();

        new VectorVisualization(new MyVector(20,20,30));

        frame.getContentPane().add(screen);
        frame.setLocationRelativeTo(null);
        frame.setSize(frame.getPreferredSize());
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }
}
