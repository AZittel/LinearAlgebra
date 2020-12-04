package junit;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.calculations.Point;
import exceptions.UnmatchedDimensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointTest {


  @Test
  @DisplayName("Test if points are the origin")
  public void isOrigin_correct() throws UnmatchedDimensionException {
    Point p = new Point("test", 0.0,0.0,0.0,0.0,0.0);
    assertTrue(Point.isOrigin(p));
    Point p2 = new Point("test2", 0.0,-1.2);
    assertFalse(Point.isOrigin(p2));
  }

}
