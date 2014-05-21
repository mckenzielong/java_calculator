package calculator;

/**
 * Representation of the divide operation in the syntax tree creation. Divides the left operand by
 * the right operand.
 *
 * @author McKenzie Long
 */
public class Divide extends LROperation {

   private static final String name = "div";

   public Divide(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int operation() {
      return getLeftOpperand().evaluate() / getRightOpperand().evaluate();
   }

   @Override
   public String optName() {
      return name;
   }
}
