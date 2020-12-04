package com.company.calculations;

import com.company.visualization.TwoDimensionalKartesianSystem;
import java.awt.Point;

public class Line {

  private Point start;
  private Point end;
  private int gradient;
  private int b;
  private int x;
  private Point pos;
  private TwoDimensionalKartesianSystem system;


  public Line(TwoDimensionalKartesianSystem system, int gradient, int x,
              int b) {
    System.out.println("creating line");
    this.system = system;
    this.x = x;
    this.b = (int) (b * system.getMagniFactor()) * (-1) ;
    this.gradient = gradient;
    setStart(findInterSection(0,TwoDimensionalKartesianSystem.getHEIGHT() - system.getCenter().getY()));
    setEnd(findInterSection(0,- system.getCenter().getY()));
  }

  public Point findInterSection(double m2, double b2) {
    if (gradient == m2) {
      return null;
    }
    double x = ((b2 ) - b) / (gradient - m2);
    double y = gradient * x + b;

    Point point = new Point();
    point.setLocation(x * (-1), y);
    System.out.println(point);
    return point;
  }

  public Point getStart() {
    setStart(findInterSection(0,TwoDimensionalKartesianSystem.getHEIGHT() - system.getCenter().getY()));
    return start;
  }

  public void setStart(Point start) {
    this.start = start;
  }

  public Point getEnd() {
    setEnd(findInterSection(0,- system.getCenter().getY()));
    return end;
  }

  public void setEnd(Point end) {
    this.end = end;
  }

  public Point getPos() {
    return pos;
  }

  public void setPos(Point pos) {
    this.pos = pos;
  }

  @Override
  public String toString() {
    return "Line{" +
      "start=" + start +
      ", end=" + end +
      ", gradient=" + gradient +
      ", b=" + b +
      ", x=" + x +
      ", pos=" + pos +
      ", system=" + system +
      '}';
  }
}
