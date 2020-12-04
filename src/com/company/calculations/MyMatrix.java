package com.company.calculations;

import exceptions.UnmatchedDimensionException;

public class MyMatrix implements MatrixOperationService {

  private final double[][] matrix;

  public MyMatrix(int col, int row, double... values) throws UnmatchedDimensionException {
    if (col * row != values.length) {
      throw new UnmatchedDimensionException(String
          .format(
              "The matrix needs %d values with the given dimensions but only %d values where given",
              col * row,
              values.length));
    }
    matrix = new double[row][col];
    int valueCount = 0;
    for (int i = 0; i < row; i++) {
      for (int n = 0; n < col; n++) {
        matrix[i][n] = values[valueCount];
        valueCount++;
      }
    }
  }

  

  @Override
  public String toString() {
    return "MyMatrix{" +
        "matrix=" + getPrintableMatrix(this) +
        '}';
  }

  private static int largestAmountOfDigits(MyMatrix matrix) {
    int result = 0;
    int max = 0;
    for (double[] row : matrix.matrix) {
      for (double col : row) {
        int count = 0;
        while ((int) col > 0) {
          col /= 10;
          ++count;
        }
        max = Math.max(count, max);
      }
    }
    return max;
  }

  private static String getPrintableMatrix(MyMatrix matrix) {
    StringBuilder sb = new StringBuilder();
    int maxDigits = largestAmountOfDigits(matrix);
    sb.append('\n');
    for (double[] row : matrix.matrix) {
      for (double col : row) {
        sb.append(String.format("%" + maxDigits + "d", (int) col));
      }
      sb.append('\n');
    }

    return sb.toString();
  }
}
