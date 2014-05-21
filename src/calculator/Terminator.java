package calculator;

/**
 * This is a terminator, meaning that it is always a leaf and has no evaluate value.
 *
 * @author McKenzie Long
 * @see BasicElement
 */
public abstract class Terminator extends BasicElement {

   public Terminator(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public boolean isValidSyntax() {
      return false;
   }

   @Override
   public int evaluate() {
      throw new UnsupportedOperationException("A terminator cannot be evaluated, check syntax.");
   }
}
