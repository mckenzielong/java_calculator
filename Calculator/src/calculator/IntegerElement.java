package calculator;

/**
 * A wrapper around the integer value for the syntax tree. This node will always represent a leaf.
 *
 * @author McKenzie Long
 * @see BasicElement
 */
public class IntegerElement extends BasicElement {

   private final Integer value;

   /**
    * Set the default value of the node, and where the node falls in the syntax tree.
    *
    * @param scopeLevel level in the syntax tree.
    * @param knownValue the value that will be represented by the node.
    */
   public IntegerElement(Integer scopeLevel, Integer knownValue) {
      super(scopeLevel);
      value = knownValue;

   }

   @Override
   public int evaluate() {
      return value;
   }

   @Override
   public String toString() {
      return value.toString();
   }

   /**
    * The node represented by the IntegerElement is always valid.
    *
    * @return true
    */
   @Override
   public boolean isValidSyntax() {
      return true;
   }

}
