/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

/**
 * A node that represents a variable in the syntax tree.
 *
 * @see BasicElement
 * @author McKenzie Long
 */
public class VariableElement extends BasicElement {

   private static final String VARIABLE_NAME_FORMAT = "[a-zA-Z]+";
   private BasicElement value;
   private String name;

   /**
    * Builds a variable object with name as the name for the variable.
    *
    * @param scopeLevel scope level in the syntax tree, 0 is the root element.
    * @param name name of variable.
    * @throws IllegalArgumentException name of the variable is not valid.
    */
   public VariableElement(Integer scopeLevel, String name) throws IllegalArgumentException {
      super(scopeLevel);

      //check if variable name is legal
      if (!isValidName(name)) {
         throw new IllegalArgumentException("Variable \"" + name + "\" is not properly formatted: " + VARIABLE_NAME_FORMAT);
      } else {
         this.name = name;
      }
   }

   @Override
   public int evaluate() {
      return value.evaluate();
   }

   /**
    * @return the expression that the variable is referencing.
    */
   public BasicElement getValue() {
      return value;
   }

   /**
    * Sets the expression that the variable will represent.
    *
    * @param value the expression that this variable will reference.
    */
   public void setValue(BasicElement value) {
      this.value = value;
   }

   /**
    * @return the valid name of the variable.
    */
   public String getName() {
      return name;
   }

   /**
    * Variables: strings of characters, where each character is one of a­z, A­Z. To me this means
    * that variables are many characters in length, but I am also making the assumption of no name
    * space collisions. Though this is easy to avoid using scope + name.
    *
    * @param name proposed variable name
    * @return true if name is valid, false if not valid
    */
   private boolean isValidName(String name) {
      //current rule is only letters, easy to swap that rule out here though 
      return name.matches(VARIABLE_NAME_FORMAT);
   }

   @Override
   public boolean isValidSyntax() {
      return (value != null);
   }
}
