package calculator;

/**
 * Basic node for building the syntax tree. All other nodes in the tree derive from this class.
 *
 * @author McKenzie Long
 */
public abstract class BasicElement {

   private final Integer scope;

   /**
    * Sets the scope level of where the element falls in the syntax tree.
    *
    * @param scopeLevel
    */
   public BasicElement(Integer scopeLevel) {
      scope = scopeLevel;
   }

   /**
    * Evaluate the current node as an integer.
    *
    * @return integer that is the value of the expression represented by the node.
    */
   public abstract int evaluate();

   /**
    * Check to see if the current node is complete and valid.
    *
    * @return true if the node is valid, false if the node is invalid.
    */
   public abstract boolean isValidSyntax();

   /**
    * @return the level, also known as scope of where the node falls in the syntax tree. Root
    * defaults to zero.
    */
   public Integer getScope() {
      return scope;
   }

}
