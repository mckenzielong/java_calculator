package calculator;

/**
 * Addition representation for the syntax tree. Adds the left and right operand together.
 *
 * @author McKenzie Long
 */
public class Add extends LROperation {

   private static final String name = "add";

   public Add(Integer scopeLevel) {
      super(scopeLevel);
   }

   @Override
   public int operation() {
      return getLeftOpperand().evaluate() + getRightOpperand().evaluate();
   }

   @Override
   public String optName() {
      return name;
   }
}
