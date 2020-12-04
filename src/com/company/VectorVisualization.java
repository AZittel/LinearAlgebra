package com.company;


import com.company.SystemCalculator;
import com.company.calculations.MyVector;
import com.company.visualization.DrawableObject;
import com.company.visualization.Screen;
import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import java.awt.Graphics;

public class VectorVisualization implements DrawableObject {

  double fromX, fromY;
  double toX, toY;
  private MyVector vector;

  public VectorVisualization(MyVector vector)
    throws UnmatchedDimensionException, IllegalParameterException {
    this.vector = vector;
    calculateVectorGraphics();
    Screen.drawableObjects.add(this);

  }

  private void calculateVectorGraphics()
    throws UnmatchedDimensionException, IllegalParameterException {

    fromX = 200* SystemCalculator
      .calculatePositionX(Screen.viewFrom, Screen.viewTo, vector.getStart().getValues()[0],
        vector.getStart().getValues()[1],
        vector.getStart().getValues()[2]);
    fromY = fromX;


    toX = 200 * SystemCalculator
      .calculatePositionX(Screen.viewFrom, Screen.viewTo, vector.getEnd().getValues()[0],
        vector.getEnd().getValues()[1],
        vector.getEnd().getValues()[2]);
    toY = toX;

  }

  @Override
  public void drawObject(Graphics g) {
    g.drawLine((int)fromX,(int)fromY,(int)toX,(int)toY);
  }
}
