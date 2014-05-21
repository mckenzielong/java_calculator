package calculator;

/**
 * An error that represents a failure while building the syntax tree. Just a simple wrapper around
 * the exception class. This represents a fatal failure.
 *
 * @author McKenzie Long
 */
public class SyntaxTreeException extends Exception {

   public SyntaxTreeException(String message) {
      super(message);
   }

   public SyntaxTreeException(String message, Throwable throwable) {
      super(message, throwable);
   }

}
