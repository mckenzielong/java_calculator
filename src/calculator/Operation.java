package calculator;

/**
 * An abstract on operations. Each operation has similar syntax, where there is one left and right
 * bracket, and at least one comma. By default these syntax elements are represented by flags, and
 * are set to false by default. The operation is valid only if those flags are set to true.
 *
 * @author McKenzie Long
 * @see BasicElement
 */
public abstract class Operation extends BasicElement {

   private boolean leftBracket;
   private boolean rightBracket;
   private boolean comma;

   /**
    * Set the syntax flags to false, making the object invalid. Each operation has a left and right
    * bracket, and at least one comma.
    *
    * @param scope the depth in the syntax tree.
    */
   public Operation(Integer scope) {
      super(scope);
      leftBracket = false;
      rightBracket = false;
      comma = false;
   }

   /**
    * @return true if the left bracket has been encountered.
    */
   public boolean hasLeftBracket() {
      return leftBracket;
   }

   /**
    * The left bracket has been encountered for this node.
    */
   public void setLeftBracket() {
      this.leftBracket = true;
   }

   /**
    * @return true if the right bracket has been encountered.
    */
   public boolean hasRightBracket() {
      return rightBracket;
   }

   /**
    * The right bracket has been encountered for this node.
    */
   public void setRightBracket() {
      this.rightBracket = true;
   }

   /**
    * @return true if the comma has been encountered for this node.
    */
   public boolean hasComma() {
      return comma;
   }

   /**
    * The comma has been encountered for this node.
    */
   public void setComma() {
      this.comma = true;
   }

}
