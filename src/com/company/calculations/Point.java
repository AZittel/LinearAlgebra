package com.company.calculations;

import exceptions.UnmatchedDimensionException;
import java.util.Arrays;

public class Point {


  private double[] values;
  private String name;

  public Point(double... values){
    this.values = values;
  }

  public Point(String name, double... values) throws UnmatchedDimensionException {
    if (values.length < 2) {
      throw new UnmatchedDimensionException();
    }
    this.values = values;
    this.name = name;
  }

  public Point(String name, int x, int y, double... values) {
    this.values = new double[2 + values.length];
    this.values[0] = x;
    this.values[1] = y;
    this.values = values;
    this.name = name;
  }

  /**
   * Determines if the point is only 0
   *
   * @param p the inspected point
   * @return weather the points values are only 0
   */
  public static boolean isOrigin(Point p) {
    for (double d : p.values) {
      if (d != 0) {
        return false;
      }
    }
    return true;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getX() {
    return values[0];
  }

  public void setX(double x) {
    this.values[0] = x;
  }

  public double getY() {
    return values[1];
  }

  public void setY(double y) {
    this.values[1] = y;
  }

  public void setZ(double z){
    if(values.length >= 1){
      values[2] = z;
    }
  }
  //TODO Javadoc
  public double getZ(){
    return values.length >= 2 ? values[2] : 0;
  }

  public double getValueAt(int index){
    return getValues()[index];
  }

  public double[] getValues() {
    return values;
  }

  public void setValues(double[] values) {
    this.values = values;
  }

  public int getDimension() {
    return values.length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Point))
      return false;
    Point point = (Point) o;
    return Arrays.equals(getValues(), point.getValues());
  }


  @Override
  public String toString() {
    return "Point{" +
      "values=" + Arrays.toString(values) +
      '}';
  }
}
