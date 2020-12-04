package exceptions;

public class IllegalParameterException extends Exception{

  /**
   * Constructs a new exception if a parameter does not fit the requirements.
   * @param message
   */
  public IllegalParameterException(final String message){
    super(message);
  }

  public IllegalParameterException(){
    super();
  }
}
