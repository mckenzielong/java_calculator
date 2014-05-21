package calculator;

/**
 * Subtraction representation for the syntax tree. Subtracts the right operand from the left
 * operand.
 *
 * @author McKenzie Long
 */
public class Subtract extends LROperation {

   private static final String name = "sub";

   public Subtract(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int operation() {
      return getLeftOpperand().evaluate() - getRightOpperand().evaluate();
   }

   @Override
   public String optName() {
      return name;
   }
}
