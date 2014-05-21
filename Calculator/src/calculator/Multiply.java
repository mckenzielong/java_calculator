package calculator;

/**
 * Multiplication representation in the syntax tree. Multiplies the left operand by the right
 * operand.
 *
 * @author McKenzie Long
 */
public class Multiply extends LROperation {

   private static final String name = "mul";

   public Multiply(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int operation() {
      return getLeftOpperand().evaluate() * getRightOpperand().evaluate();
   }

   @Override
   public String optName() {
      return name;
   }
}
