package calculator;

/**
 * A LROperation consists of a left and right operand, and an operation to preform to combine the
 * left and right operand into a single value. ie: add
 *
 * @author McKenzie Long
 * @see BasicElement
 * @see Operation
 */
public abstract class LROperation extends Operation {

   private BasicElement leftOperand;
   private BasicElement rightOperand;

   /**
    * @return the name of the operation
    */
   public abstract String optName();

   /**
    * Evaluate the left and right operand and combine the two into a single value using the
    * operation the LROperation represents.
    *
    * @return the value from evaluating the left and right operand.
    */
   public abstract int operation();

   /**
    * Create the default item, setting syntax flags to false, and setting the scope of the node in
    * the syntax tree.
    *
    * @param scope location in the syntax tree.
    */
   public LROperation(Integer scope) {
      super(scope);
   }

   @Override
   public int evaluate() {
      return operation();
   }

   /**
    * @return the left operand currently set.
    */
   public BasicElement getLeftOpperand() {
      return leftOperand;
   }

   /**
    * @return the right operand currently set.
    */
   public BasicElement getRightOpperand() {
      return rightOperand;
   }

   /**
    * Set the given expression as the left operand.
    *
    * @param leftOpperand expression to be set.
    */
   public void setLeftOperand(BasicElement leftOpperand) {
      this.leftOperand = leftOpperand;
   }

   /**
    * Set the given expression as the right operand.
    *
    * @param rightOpperand expression to be set.
    */
   public void setRightOperand(BasicElement rightOpperand) {
      this.rightOperand = rightOpperand;
   }

   @Override
   public String toString() {
      return optName() + "(" + leftOperand.toString() + ","
              + rightOperand.toString() + ")";
   }

   /**
    * The LROperation is only valid if a left bracket, left expression, comma, right expression, and
    * right bracket is encountered.
    *
    * @return true if the LROperation is valid
    */
   @Override
   public boolean isValidSyntax() {
      return hasLeftBracket()
              && (leftOperand != null)
              && hasComma()
              && (rightOperand != null)
              && hasRightBracket();
   }
}
