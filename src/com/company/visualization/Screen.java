package com.company.visualization;

import com.company.calculations.MyVector;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Screen extends JPanel {

  public static MyVector viewFrom = new MyVector("View from vector", 10,10,10);
  public static MyVector viewTo = new MyVector("View to vector", 5,0,0);
  public static ArrayList<DrawableObject> drawableObjects = new ArrayList<>();

  public Screen(){

  }


  @Override
  public void paintComponent(Graphics g){
    for(DrawableObject o : drawableObjects){
        o.drawObject(g);
      System.out.println("Test");
    }

  }

}