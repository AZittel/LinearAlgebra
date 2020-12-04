package junit;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.calculations.MyVector;
import com.company.calculations.Point;
import exceptions.IllegalParameterException;
import exceptions.UnmatchedDimensionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MyVectorTest {

  @AfterEach
  public void cleanUp() {
    MyVector.allMyVectors.clear();
  }

  @Test
  @DisplayName("Test vector creation with two points")
  public void constructor_twoPoints()
    throws UnmatchedDimensionException, IllegalParameterException {
    Point a = new Point(1, 2, 3);
    Point b = new Point(0, 0, 0);
    MyVector tmp = new MyVector("test1", a, b);

    assertEquals(3, tmp.getValues().length);
    assertEquals(a, tmp.getStart(), "Did not find the correct start point");
    assertEquals(b, tmp.getEnd(), "Did not find the correct end point");
  }

  @Test
  @DisplayName("Test if Multidimensional Vector creation is correct")
  public void constructor_multiDimension() {
    assertEquals(0, MyVector.allMyVectors.size());
    MyVector myVector = new MyVector("test", -4, -3, -2, -1, 0, 1, 2, 3, 4);
    assertEquals(1, MyVector.allMyVectors.size());
    assertArrayEquals(myVector.getValues(), new double[]{-4, -3.0, -2, -1, 0, 1, 2, 3, 4});
  }


  @Test
  @DisplayName("Test if the start point is correctly found")
  public void findStartWithEndPoint_legalParameter()
    throws UnmatchedDimensionException, IllegalParameterException {
    Point start = new Point(1, 2, 3);
    Point end = new Point(1, 2, 3);
    MyVector tmp = new MyVector("test1", 0, 0, 0);

    assertEquals(start, tmp.findStartWithEndPoint(end));
    assertEquals(end, tmp.findEndWithStartPoint(start));
  }

  @Test
  @DisplayName("Test if method works with null point parameter")
  public void findStartWithEndPoint_illegalParameter()
    throws UnmatchedDimensionException, IllegalParameterException {
    MyVector tmp = new MyVector("test1", 0, 0, 0);
    assertThrows(IllegalParameterException.class, () -> tmp.findStartWithEndPoint(null));
  }

  @Test
  @DisplayName("Test scaling a vector")
  public void scalingMultiplication() {
    MyVector vector = new MyVector(-1, 2, 3, 0);
    double scalar = 2;
    MyVector scaledVector = vector.scalarMultiplication(scalar);
    assertArrayEquals(new double[]{-2, 4, 6, 0}, scaledVector.getValues());
    scaledVector = scaledVector.scalarMultiplication(scalar);
    assertArrayEquals(new double[]{-4, 8, 12, 0}, scaledVector.getValues());

  }

  @Test
  @DisplayName("Test subtracting vectors")
  public void vectorSubtraction() throws UnmatchedDimensionException {
    MyVector vector = new MyVector(2, 2, 2, 2, 2);
    MyVector vector2 = new MyVector(-2, -2, -2, -2, -2);
    MyVector vector3 = new MyVector(0, 0, 0, 0, 0);
    double[] expectedValues = {0, 0, 0, 0, 0};
    double[] expectedValues2 = {-4, -4, -4, -4, -4};
    assertAll(
      () -> assertArrayEquals(expectedValues, vector.vectorSubtraction(vector).getValues(),
        " 2 - -  2 = 4"),
      () -> assertArrayEquals(expectedValues2, vector2.vectorSubtraction(vector).getValues(),
        "-2 -2 = -4"),
      () -> assertArrayEquals(vector.getValues(), vector.vectorSubtraction(vector3).getValues(),
        "2 - 0 = 2")
    );
  }

  @Test
  @DisplayName("Test adding vector")
  public void vectorAddition() throws UnmatchedDimensionException {
    MyVector vector = new MyVector(2, 2, 2, 2, 2);
    MyVector vector2 = new MyVector(-2, -2, -2, -2, -2);
    MyVector vector3 = new MyVector(0, 0, 0, 0, 0);
    double[] expectedValues = {0, 0, 0, 0, 0};
    double[] expectedValues2 = {-4, -4, -4, -4, -4};
    assertAll(
      () -> assertArrayEquals(expectedValues, vector.vectorAddition(vector2).getValues(),
        " 2 - 2 = 2"),
      () -> assertArrayEquals(expectedValues, vector2.vectorAddition(vector).getValues(),
        "-2 -2 = -4"),
      () -> assertArrayEquals(expectedValues2, vector2.vectorAddition(vector2).getValues(),
        "-2 -2 = -4"),
      () -> assertArrayEquals(vector.getValues(), vector.vectorAddition(vector3).getValues(),
        "2 - 0 = 2")
    );
  }

  @Test
  @DisplayName("Test vector amount")
  public void getVectorAmount() {
    MyVector vector = new MyVector(2, 2, 2, 2, 2);
    assertEquals(2 * Math.sqrt(5), vector.getLength(), "Expected to be 4,472135");
  }

  @Test
  @DisplayName("Test if a vector is a location vector")
  public void isLocationVector()
    throws UnmatchedDimensionException, IllegalParameterException {
    MyVector vector = new MyVector(0, 0, 0);
    MyVector vector2 = new MyVector(new Point(0, 0), new Point(5, 6));
    MyVector vector3 = new MyVector(new Point(1, 1), new Point(5, 6));

    assertTrue(vector.isLocationVector(), "Should originate from the center");
    assertTrue(vector2.isLocationVector(), "Should originate from the center");
    assertFalse(vector3.isLocationVector(), "Should originate from the center");

  }

  @Test
  @DisplayName("Test getting the location vector from a vector")
  public void getLocationVector() {
    MyVector vector = new MyVector(0, 0, 0);
    double[] expectedValues = {0, 0, 0};
    //    assertArrayEquals(expectedValues, , );
  }


  @Test
  @DisplayName("Test the crossProduct calculation")
  public void crossProduct_threeDimensional() throws UnmatchedDimensionException {
    MyVector vector = new MyVector(1, 2, 3);
    MyVector vector2 = new MyVector(1, -1, 2);
    MyVector expectedResult = new MyVector(7, 1, -3);

    assertEquals(expectedResult, vector.crossProduct(vector2),
      "The crossProduct was not calculated correctly");
  }

}
