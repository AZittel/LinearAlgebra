package com.company.calculations;

import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.paint.Color;

public class MyVector implements VectorOperationService {

  public static final ArrayList<MyVector> allMyVectors = new ArrayList<>();
  private double[] values;
  private Color color = Color.BLACK;

  private Point start;
  private Point end;

  private String name;

  private MyVector() {
    allMyVectors.add(this);
  }

  public MyVector(double... values) {
    this();
    this.values = values;
    this.start = new Point(new double[values.length]);
    this.end = new Point(values);
  }

  public MyVector(MyVector y) {
    this.values = cloneValueArray(y.values);
    this.color = y.color;
    this.start = y.start;
    this.end = y.end;
  }

  public MyVector(Point a, Point b) throws UnmatchedDimensionException {
    this();
    if (a.getDimension() != b.getDimension()) {
      allMyVectors.remove(this);
      throw new UnmatchedDimensionException("The points have different Dimensions!");
    }
    this.values = new double[a.getDimension()];
    for (int i = 0; i < values.length; i++) {
      values[i] = b.getValues()[i] - a.getValues()[i];
    }
    start = a;
    end = b;
  }

  /**
   * Creates a MyVector with two given points
   *
   * @param a    the start point
   * @param b    the end point
   * @param name the name for the point
   * @throws UnmatchedDimensionException thrown when the two points have different dimensions
   */
  public MyVector(String name, Point a, Point b) throws UnmatchedDimensionException {
    this();
    if (a.getDimension() != b.getDimension()) {
      allMyVectors.remove(this);
      throw new UnmatchedDimensionException("The points have different Dimensions!");
    }
    this.values = new double[a.getDimension()];
    for (int i = 0; i < values.length; i++) {
      values[i] = b.getValues()[i] - a.getValues()[i];
    }
    this.name = name;
    start = a;
    end = b;
  }

  /**
   * Create a MyVector with a name and vararg given double values.
   *
   * @param name   The name for the vector
   * @param values the varargs double values for the vector
   */
  public MyVector(String name, double... values) {
    this();
    this.values = values;
    this.name = name;
    this.start = new Point(new double[values.length]);
    this.end = new Point(values);
    System.out.println(this);
  }

  private double[] cloneValueArray(double[] values) {
    double[] copy = new double[values.length];
    System.arraycopy(values, 0, copy, 0, values.length);
    return copy;
  }


  @Override
  public MyVector scalarMultiplication(double scalar) {
    MyVector result = new MyVector(name, cloneValueArray(values));
    for (int i = 0; i < result.getValues().length; i++) {
      result.values[i] = result.values[i] * scalar;
    }
    return result;
  }


  @Override
  public MyVector vectorSubtraction(MyVector y) throws UnmatchedDimensionException {
    if (values.length != y.getValues().length) {
      throw new UnmatchedDimensionException();
    }
    MyVector result = new MyVector(cloneValueArray(values));
    for (int i = 0; i < values.length; i++) {
      result.values[i] = result.values[i] - y.values[i];
    }
    return result;
  }

  @Override
  public MyVector vectorAddition(MyVector y) throws UnmatchedDimensionException {
    if (values.length != y.getValues().length) {
      throw new UnmatchedDimensionException();
    }
    MyVector result = new MyVector(cloneValueArray(values));
    for (int i = 0; i < values.length; i++) {
      result.values[i] = result.values[i] + y.values[i];
    }
    return result;
  }

  @Override
  public MyVector crossProduct(MyVector y) throws UnmatchedDimensionException {
    if (this.values.length != 3 || y.values.length != 3) {
      throw new UnmatchedDimensionException();
    }
    int valueCount = this.values.length;
    MyVector result = new MyVector(cloneValueArray(values));
    for (int i = 0; i < values.length; i++) {
      result.values[i] =
          (this.values[(i + 1) % valueCount] * y.values[(i + 2) % valueCount]) -
              (this.values[(i + 2) % valueCount] * y.values[(i + 1) % valueCount]);
    }
    return result;
  }


  @Override
  public Point findStartWithEndPoint(Point end)
      throws UnmatchedDimensionException, IllegalParameterException {
    if (end == null) {
      throw new IllegalParameterException("The end point cannot be null");
    }
    if (end.getDimension() != values.length) {
      throw new UnmatchedDimensionException();
    }
    double[] val = new double[end.getDimension()];
    for (int i = 0; i < values.length; i++) {
      val[i] = end.getValues()[i] - values[i];
    }
    return new Point("Result", val);
  }


  @Override
  public Point findEndWithStartPoint(Point start)
      throws UnmatchedDimensionException, IllegalParameterException {
    if (start == null) {
      throw new IllegalParameterException("The end point cannot be null");
    }
    if (start.getDimension() != values.length) {
      throw new UnmatchedDimensionException();
    }
    double[] val = new double[start.getDimension()];
    for (int i = 0; i < values.length; i++) {
      val[i] = start.getValues()[i] + values[i];
    }
    return new Point("Result", val);
  }


  @Override
  public MyVector getLocationVector() throws UnmatchedDimensionException {
    return new MyVector(new Point(new double[values.length]), new Point(values));
  }


  @Override
  public boolean isLocationVector()
      throws UnmatchedDimensionException, IllegalParameterException {
    if (start == null && end == null) {
      return true;
    } else if ((start != null && Point.isOrigin(start))) {
      return true;
    } else if (start == null && Point.isOrigin(findStartWithEndPoint(end))) {
      return true;
    }

    return false;
  }

  @Override
  public double dotProduct(MyVector y) throws UnmatchedDimensionException {
    if (this.values.length != y.values.length) {
      throw new UnmatchedDimensionException();
    }
    double result = 0;
    for (int i = 0; i < values.length; i++) {
      result += values[i] * y.values[i];
    }
    return result;
  }

  @Override
  public MyVector getNormalizedVector() {
    MyVector result = new MyVector(this);
    double length = result.getLength();
    for (int i = 0; i < result.values.length; i++) {
      result.values[i] = result.values[i] / length;
    }
    return result;
  }

  @Override
  public double getLength() {
    int result = 0;
    for (double d : values) {
      result += Math.pow(d, 2);
    }
    return Math.sqrt(result);
  }

  public double getX() {
    return values.length > 0 ? this.values[0] : 0;
  }

  public double getY() {
    return values.length > 1 ? this.values[1] : 0;
  }

  public double getZ() {
    return values.length > 2 ? this.values[2] : 0;
  }


  public double[] getValues() {
    return values;
  }

  public void setValues(double[] values) {
    this.values = values;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Point getStart() throws UnmatchedDimensionException, IllegalParameterException {
    if (end != null) {
      setStart(findStartWithEndPoint(end));
    }
    return start;
  }

  public void setStart(Point start) {
    this.start = start;
  }

  public Point getEnd() throws UnmatchedDimensionException, IllegalParameterException {
    if (start != null) {
      setEnd(findEndWithStartPoint(start));
    }
    return end;
  }

  public void setEnd(Point end) {
    this.end = end;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MyVector)) {
      return false;
    }
    MyVector myVector = (MyVector) o;
    return Arrays.equals(getValues(), myVector.getValues());
  }


  @Override
  public String toString() {
    return "MyVector{" +
        "values=" + Arrays.toString(values) +
        ", vectorAmount=" + getLength() +
        ", start=" + start +
        ", end=" + end +
        ", name='" + name +
        '}';
  }


}
