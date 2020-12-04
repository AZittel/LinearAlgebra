package exceptions;

public class UnmatchedDimensionException extends Exception{

  /**
   * Constructs a new exception if two vectors have different dimensions
   * @param message
   */
  public UnmatchedDimensionException(final String message){
    super(message);
  }

  public UnmatchedDimensionException(){
    super();
  }
}
