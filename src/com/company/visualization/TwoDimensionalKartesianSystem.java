package com.company.visualization;

import com.company.calculations.Line;
import com.company.calculations.MyVector;
import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

public class TwoDimensionalKartesianSystem extends JPanel {

  private static int WIDTH = 500;
  private static int HEIGHT = 500;
  private final Point center = new Point(WIDTH / 2, HEIGHT / 2);


  private double magniFactor = (float) HEIGHT / 10;

  private ArrayList<Line> lines = new ArrayList<>();
  private ArrayList<MyVector> vectors = new ArrayList<>();

  public TwoDimensionalKartesianSystem() {
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

  }

  public static int getWIDTH() {
    return WIDTH;
  }

  public static void setWIDTH(int WIDTH) {
    TwoDimensionalKartesianSystem.WIDTH = WIDTH;
  }

  public static int getHEIGHT() {
    return HEIGHT;
  }

  public static void setHEIGHT(int HEIGHT) {
    TwoDimensionalKartesianSystem.HEIGHT = HEIGHT;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    drawKartesienLines(g);
    drawTwoDimensionalLines(g);
    drawMyVectors(g);
  }

  private void drawMyVectors(Graphics g) {
    for (MyVector vector : vectors) {
      if (vector.getValues().length == 2) {
        try {
          g.drawLine((int) (vector.getStart().getX() * magniFactor + getCenter().getX()),
            (int) (-vector.getStart().getY() * magniFactor + getCenter().getY()),
            (int) (vector.getEnd().getX() * magniFactor + getCenter().getX()),
            (int) (-vector.getEnd().getY() * magniFactor + getCenter().getY())
          );
        } catch (UnmatchedDimensionException | IllegalParameterException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void drawKartesienLines(Graphics g) {
    System.out.println(getCenter());
    //horizontal
    g.drawLine(0, getCenter().y, getWidth(), getCenter().y);
    g.drawLine(getCenter().x, 0, getCenter().x, getHeight());
  }

  private void drawTwoDimensionalLines(Graphics g) {
    for (Line line : lines) {
      System.out.println(line.getStart() + "  " + line.getEnd());
      g.drawLine(line.getStart().x + (int) getCenter().getX(),
        line.getStart().y + (int) getCenter().getY(), line.getEnd().x + (int) getCenter().getX(),
        line.getEnd().y + (int) getCenter().getY());
      //Arrow head right side

//      g.drawLine(line.getEnd().x + (int) getCenter().getX(),
//        line.getEnd().y + (int) getCenter().getY(), );
      //Arrow head left side
//      g.drawLine(line.getEnd().x + (int) getCenter().getX(),
//        line.getEnd().y + (int) getCenter().getY(), );
    }
  }

  public Point getCenter() {
    center.setLocation(WIDTH / 2, HEIGHT / 2);
    return center;
  }

  public ArrayList<Line> getLines() {
    return lines;
  }

  public void addLine(Line line) {
    this.lines.add(line);
  }

  public void addVector(MyVector vector) {
    this.vectors.add(vector);
  }

  public double getMagniFactor() {
    return magniFactor;
  }

  public void setMagniFactor(double magniFactor) {
    this.magniFactor = magniFactor;
  }


}
