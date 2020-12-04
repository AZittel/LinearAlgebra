package com.company.visualization;

import java.awt.Dimension;
import javax.swing.JFrame;

public class StandardJFrame extends JFrame {

  public StandardJFrame(){
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(new Dimension(500,500));
    setLocationRelativeTo(null);
  }



}
