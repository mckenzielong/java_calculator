package calculator;

/**
 * A wrapper class around the comma token. The comma is used to help process the location in another
 * expression. This is a terminator, meaning that it is always a leaf and has no evaluate value.
 *
 * @author McKenzie Long
 * @see BasicElement
 * @see Terminator
 */
public class Separator extends Terminator {

   public Separator(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int evaluate() {
      throw new UnsupportedOperationException("A comma cannot be evaluated, check syntax.");
   }

}
