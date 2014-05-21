package calculator;

/**
 * A wrapper class around the left bracket token. The left bracket basically represents creating a
 * new level in our syntax tree.  This is a terminator, meaning that it is always a leaf and has no
 * evaluate value.
 *
 * @author McKenzie Long
 * @see BasicElement
 * @see Terminator
 */
public class LeftBracket extends Terminator {

   public LeftBracket(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int evaluate() {
      throw new UnsupportedOperationException("Left bracket cannot be evaluated, check syntax.");
   }

}
