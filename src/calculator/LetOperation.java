package calculator;

/**
 * The assignment node for the syntax tree. Expected syntax is let(variable, value, expression).
 *
 * @see BasicElement
 * @see Operation
 * @author McKenzie Long
 *
 *
 */
public class LetOperation extends Operation {

   private static final String name = "let";
   private boolean secondComma;
   private VariableElement variable;
   private BasicElement expression;

   /**
    * Construct the node, and set the syntax flags to false, making the object invalid. Node level
    * in the syntax tree is set.
    *
    * @param scopeLevel level in the syntax tree.
    */
   public LetOperation(Integer scopeLevel) {
      super(scopeLevel);
      secondComma = false;
   }

   /**
    * Evaluate the let's expression as an integer with respect to the given variable.
    *
    * @return the integer value of the expression.
    */
   @Override
   public int evaluate() {
      return expression.evaluate();
   }

   /**
    * @return true if the second Separator has been encountered for this operation.
    */
   public boolean hasSecondComma() {
      return secondComma;
   }

   /**
    * Sets the second comma flag. This will be set once the second Separator token is read in for
    * the let operation.
    */
   public void setSecondComma() {
      this.secondComma = true;
   }

   /**
    * @return the variable stored and used by the operation.
    */
   public VariableElement getVariable() {
      return variable;
   }

   /**
    * Set the variable that might be referenced in the let operation expression.
    *
    * @param variable variable be set
    */
   public void setVariable(VariableElement variable) {
      this.variable = variable;
   }

   public BasicElement getExpression() {
      return expression;
   }

   public void setExpression(BasicElement expression) {
      this.expression = expression;
   }

   /**
    * The operation is considered valid only once the left bracket, variable, first and second
    * comma, expression and right bracket have been encountered. ie: let(name, value, expression)
    *
    * @return true if the operation is valid, false otherwise.
    */
   @Override
   public boolean isValidSyntax() {
      return hasLeftBracket()
              && (variable != null
              && variable.getValue() != null)
              && hasComma()
              && hasSecondComma()
              && (expression != null)
              && hasRightBracket();
   }
}
