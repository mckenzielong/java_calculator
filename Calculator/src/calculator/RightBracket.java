package calculator;

/**
 * A wrapper class around the right bracket token. The left bracket basically represents returning a
 * level in our syntax tree. This is a terminator, meaning that it is always a leaf and has no
 * evaluate value.
 *
 * @author McKenzie Long
 * @see BasicElement
 * @see Terminator
 */
public class RightBracket extends Terminator {

   public RightBracket(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int evaluate() {
      throw new UnsupportedOperationException("Right bracket cannot be evaluated, check syntax.");
   }

}
