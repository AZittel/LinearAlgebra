package com.company;

import com.company.calculations.MyVector;
import exceptions.UnmatchedDimensionException;

public class SystemCalculator {
  static double drawX, drawY;

  static double calculatePositionX(MyVector viewFrom, MyVector viewTo, double x, double y,
                                   double z) throws UnmatchedDimensionException {

    setStuff(viewFrom, viewTo, x, y, z);
    return drawX;
  }

  static double calculatePositionY(MyVector viewFrom, MyVector viewTo, double x, double y,
                                   double z) throws UnmatchedDimensionException {

    setStuff(viewFrom, viewTo, x, y, z);
    return drawY;
  }

  static void setStuff(MyVector viewFrom, MyVector viewTo, double x, double y,
                       double z) throws UnmatchedDimensionException {

    MyVector viewVector = viewTo.vectorSubtraction(viewFrom);
    MyVector directionVector = new MyVector(1, 1, 1);
    MyVector planeVector1 = viewVector.crossProduct(directionVector);
    MyVector planeVector2 = viewVector.crossProduct(planeVector1);

    MyVector viewToPoint = new MyVector((viewVector.getValues()[0] ));

    double t = (viewVector.getX() * viewTo.getX() + viewVector.getY()*viewTo.getY() + viewVector.getZ()*viewTo.getZ()
      -  (viewVector.getX() * viewTo.getX()  + viewVector.getY()*viewTo.getY() + viewVector.getZ()*viewTo.getZ()))
      /  (viewVector.getX() * viewToPoint.getX() + viewVector.getY()*viewToPoint.getY() + viewVector.getZ()*viewToPoint.getZ());

    x = viewFrom.getX() + viewToPoint.getX() * t;
    y = viewFrom.getY()  + viewToPoint.getY() * t;
    z = viewFrom.getZ() + viewToPoint.getZ() * t;

    if(t > 0)
    {
      drawX = planeVector2.getX() * x + planeVector2.getY() * y + planeVector2.getZ() * z;
      drawY = planeVector1.getX() * x + planeVector1.getY() * y + planeVector1.getZ() * z;
    }
  }

}
