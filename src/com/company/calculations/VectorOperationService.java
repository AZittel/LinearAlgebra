package com.company.calculations;

import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;

public interface VectorOperationService {

  /**
   * Calculates vector scaling multiplication.
   *
   * @param scalar value the vector is multiplied by
   * @return a new MyVector with the new values
   */
  public MyVector scalarMultiplication(double scalar);

  /**
   * Calculates vector subtraction.
   *
   * @param y the subtracting vector
   * @return a new MyVector with the new values
   * @throws UnmatchedDimensionException thrown when the other vector has different dimensions
   */
  public MyVector vectorSubtraction(MyVector y) throws UnmatchedDimensionException;

  /**
   * Calculates vector addition.
   *
   * @param y the adding vector
   * @return a new MyVector with the new values
   * @throws UnmatchedDimensionException thrown when the other vector has different dimensions
   */
  public MyVector vectorAddition(MyVector y) throws UnmatchedDimensionException;

  /**
   * Caluclates the crossproduct from two three dimensional vectors
   *
   * @param y the second vector
   * @return a new vector
   * @throws UnmatchedDimensionException thrown if the two vectors dimensions do not match the third
   *                                     dimension
   */
  public MyVector crossProduct(MyVector y) throws UnmatchedDimensionException;

  /**
   * Calculates the start point of the vector by a given end point.
   *
   * @param end the point where the vector is pointing to
   * @return a new Point where the vector originates
   * @throws UnmatchedDimensionException thrown if the point has different dimension as the vector
   */
  public Point findStartWithEndPoint(Point end)
      throws UnmatchedDimensionException, IllegalParameterException;

  /**
   * Calculates the end point of the vector by a given start point.
   *
   * @param start point where the vector originates from
   * @return a new Point where the vector is pointing to
   * @throws UnmatchedDimensionException thrown if the point has different dimension as the *
   *                                     vector
   */
  public Point findEndWithStartPoint(Point start)
      throws UnmatchedDimensionException, IllegalParameterException;

  /**
   * Calculates the locationVector
   *
   * @return a new location vector
   */
  public MyVector getLocationVector() throws UnmatchedDimensionException;

  /**
   * Checks if the vector is a locationVector
   *
   * @return Return true if start and end point are not set
   */
  public boolean isLocationVector()
      throws UnmatchedDimensionException, IllegalParameterException;


  /**
   * Calculates the dot product between two vectors.
   *
   * @param y the vector to be interacted with
   * @return Returns the dot product
   */
  public double dotProduct(MyVector y) throws UnmatchedDimensionException;

  /**
   * Calculates the normalized vector. Divide the vector by its length.
   *
   * @return a new normalized Vector
   */
  public MyVector getNormalizedVector();

  /**
   * Calculates the vector length
   *
   * @return the vector amount
   */
  public double getLength();
}
