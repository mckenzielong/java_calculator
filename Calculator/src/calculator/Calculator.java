package calculator;

import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Calculator produces an output to console when evaluating a string of expressions. A syntax tree
 * is built and evaluated. Output is printed to the standard out.
 *
 * @author McKenzie Long
 */
public class Calculator {

   /**
    * <p>
    * Evaluate a string of Integer operations and print the result out to the console. Operations
    * are evaluated from leftmost to rightmost. Variables can get set using the let operation. Valid
    * variables names are: strings of characters, where each character is one of a­z, A­Z.
    * </p>
    * <p>
    * Accepted expressions are:
    *
    * <ul>
    * <li>add(expression, expression)</li>
    * <li>sub(expression, expression)</li>
    * <li>mul(expression, expression)</li>
    * <li>div(expression, expression)</li>
    * <li>let(name, expression, expression)</li>
    * </ul>
    *
    * </p>
    * <p>
    * If an error is encountered, it will be printed to console.
    * </p>
    *
    * @param args the command line arguments that will try to be read and evaluated
    */
   public static void main(String[] args) {

      /* first combine all the arguments, incase they used spaces.  Use string builder since strings
       in java are not mutable, next strip out any remaining whitespace and tokenize*/
      StringBuilder allArgs = new StringBuilder();
      for (String arg : args) {
         allArgs.append(arg);
      }

      String vettedArgs = allArgs.toString().replaceAll("\\s+", "");

      //simple tokenization using built in tokenizer from here we get a (,) or var, int or opt
      StringTokenizer tokens = new StringTokenizer(vettedArgs, "\\(|\\)|,", true);

      //try to build and evaulate the syntax tree we created
      BasicElement treeRoot = buildSyntaxTree(tokens);
      System.out.println(treeRoot.toString());
      System.out.println(treeRoot.evaluate());

   }

   private static BasicElement buildSyntaxTree(StringTokenizer tokens)
           throws SyntaxTreeException, IllegalArgumentException, NumberFormatException {
      BasicElement root = null;
      Integer scopeLevel = 0;
      Stack<BasicElement> stackOfElements = new Stack();

      HashMap<String, VariableElement> activeVariables = new HashMap();
      /* Hashmap is an easy way to avoid namespace collisions.  What I could have done is appended
       the scope level to the variable name (x -> x#), then when looking up variable loop back
       from the current scope level until we reach 0, or find the variable. */

      if (tokens.hasMoreTokens()) {
         root = createNode(tokens.nextToken(), scopeLevel);
         stackOfElements.push(root);
      } else {
         throw new SyntaxTreeException("No tokens were given to be evaluated");
      }

      /* We begin by walking the tokens.  Since everthing is evaluated left from right, we will
       simply build each node in our tree from left to right.  We will consider two things,
       'terminators' and everything else.  We then add Operations to the stack, which helps us
       from using recursion.  This means we should avoid blowing the callstack up.
      
       This was a logical split to me.  Terminators all more or less behaved the same, and with 
       the exception of variables, other expressions were very similar. */
      while (tokens.hasMoreTokens()) {
         String currentToken = tokens.nextToken();
         BasicElement currentElement = createNode(currentToken, scopeLevel);

         if (!stackOfElements.empty()) {
            System.out.println("current is: " + currentElement.getClass().toString());

            /* if the current token is a terminator, update the parent's syntax in terms of 
             'terminators'. 
  
             Otherwise we have a var, const or Operation in hand, and we need to update the parent.  
             Found it easier to structure code this way since we are updating for syntax versus 
             updating for both syntax and evaluation */
            BasicElement parentElement = stackOfElements.pop();
            System.out.println("Parent is: " + parentElement.getClass().toString());

            if (Terminator.class.isInstance(currentElement)) {

               parentElement = updateParentNodeTerminator(parentElement,
                       Terminator.class.cast(currentElement));

               stackOfElements.push(parentElement);

               /* Once we have updated the parent for syntax, we need to check if any adjustments 
                must be made to the scope level.  In this grammar, left brackets should be a scope
                increase and right brackets mean a decrease, and that our element is done being 
                updated */
               if (LeftBracket.class.isInstance(currentElement)) {
                  scopeLevel++;
               } else if (RightBracket.class.isInstance(currentElement)) {
                  BasicElement finishedElement = stackOfElements.pop();

                  //we expect the finished element to be valid
                  if (!finishedElement.isValidSyntax()) {
                     throw new SyntaxTreeException(finishedElement.toString()
                             + " is not valid at scope level: " + finishedElement.getScope());
                  }

                  //if we just finished a let operation, clean up variables
                  if (LetOperation.class.isInstance(finishedElement)) {
                     VariableElement variableObject
                             = LetOperation.class.cast(finishedElement).getVariable();
                     activeVariables.remove(variableObject.getName());
                  }
                  scopeLevel--;
               }

            } else {
               //We have a special case when dealing with variables
               if (VariableElement.class.isInstance(currentElement)) {
                  VariableElement variableCast
                          = VariableElement.class.cast(currentElement);
                  /* Is a not variable definition, meaning that we have not set the second comma. 
                   This particular if check is kind of ugly, but if the isInstance fails then the
                   cast will not be evaluated. */
                  if (LetOperation.class.isInstance(parentElement)
                          && !LetOperation.class.cast(parentElement).hasSecondComma()) {

                     //try to add a new variable with name, other wise except
                     if (activeVariables.put(variableCast.getName(), variableCast) != null) {
                        throw new SyntaxTreeException("Variable name: " + variableCast.getName()
                                + " has already been declared at scope: 0-"
                                + variableCast.getScope());
                     }

                  } else {
                     //not variable declaration, try to find referenced variable
                     if (activeVariables.containsKey(variableCast.getName())) {
                        currentElement = activeVariables.get(variableCast.getName());
                     } else {
                        throw new SyntaxTreeException("Variable name: " + variableCast.getName()
                                + " has not been declared at scope: 0-"
                                + variableCast.getScope());
                     }
                  }
               }

               parentElement = updateParentNodeExpression(parentElement,
                       currentElement);

               stackOfElements.push(parentElement);
            }
         }

         /* We only want to push the expression on the stack if it is an operation.  This is because
          we need to revisit these once more of their syntax is encountered (ie: , and ) ).
          Integers and terminators do not need further processesing.  Variables are only valid
          after the value is set by the let operation. */
         if (Operation.class.isInstance(currentElement)) {
            System.out.println("Pushing obj");
            stackOfElements.push(currentElement);
         }

      }
      System.out.println("Open brackets is: " + scopeLevel.toString());
      return root;
   }

   /**
    * Read the token, and create the matching BasicElement object.
    *
    * @param token string to be converted into a BasicElement
    * @param scopeLevel the level of scope that the element is being created at.
    * @return a BasicElement based on what the token type, or null if the token is invalid.
    * @throws NumberFormatException token is Integer, but cannot be converted.
    * @throws IllegalArgumentException token is an invalid variable.
    */
   private static BasicElement createNode(String token, Integer scopeLevel)
           throws NumberFormatException, IllegalArgumentException {
      BasicElement returnElement;
      switch (token) {
         case "add":
            returnElement = new Add(scopeLevel);
            break;

         case "div":
            returnElement = new Divide(scopeLevel);
            break;

         case "mul":
            returnElement = new Multiply(scopeLevel);
            break;

         case "sub":
            returnElement = new Subtract(scopeLevel);
            break;

         case "let":
            returnElement = new LetOperation(scopeLevel);
            break;

         case "(":
            returnElement = new LeftBracket(scopeLevel);
            break;

         case ")":
            returnElement = new RightBracket(scopeLevel);
            break;

         case ",":
            returnElement = new Separator(scopeLevel);
            break;

         default:
            //Use regex to see if the token is a number or variable
            if (token.matches("\\d+")) {
               returnElement = new IntegerElement(scopeLevel, Integer.valueOf(token));
            } else {
               returnElement = new VariableElement(scopeLevel, token);
            }
      }
      return returnElement;
   }

   private static BasicElement updateParentNodeTerminator(
           BasicElement parentElement, Terminator currentElement) throws SyntaxTreeException {
      //System.out.println("Parent is: " + parentElement.getClass().toString());
      if (Operation.class.isInstance(parentElement)) {
         Operation ops = Operation.class.cast(parentElement);
         if (LeftBracket.class.isInstance(currentElement)) {
            if (!ops.hasLeftBracket()) {
               ops.setLeftBracket();
            } else {
               throw new SyntaxTreeException("Left bracket already set for " + ops.getClass().toString()
                       + " at scope level: " + ops.getScope());
            }
         } else if (RightBracket.class.isInstance(currentElement)) {
            if (!ops.hasRightBracket()) {
               ops.setRightBracket();
            } else {
               throw new SyntaxTreeException("Right bracket already set for " + ops.getClass().toString()
                       + " at scope level: " + ops.getScope());
            }
         } else if (Separator.class.isInstance(currentElement)) {
            if (!ops.hasComma()) {
               ops.setComma();
            } else {
               //Here we have the let operation to consider, it has two
               if (LetOperation.class.isInstance(ops)) {
                  LetOperation letOp = LetOperation.class.cast(ops);
                  if (!letOp.hasSecondComma()) {
                     System.out.println("Setting second comma");
                     letOp.setSecondComma();
                  } else {
                     throw new SyntaxTreeException("Second comma separator already set for "
                             + letOp.getClass().toString() + " at scope level: "
                             + letOp.getScope());
                  }
               } else {
                  throw new SyntaxTreeException("Comma separator already set for "
                          + ops.getClass().toString() + " at scope level: " + ops.getScope());
               }
            }
         } else {
            throw new SyntaxTreeException("Unknown terminator case encountered at scope level: "
                    + currentElement.getScope());
         }
      } else {
         throw new SyntaxTreeException("Unexpected parent case encountered at scope level: "
                 + parentElement.getScope());
      }
      return parentElement;
   }

   private static BasicElement updateParentNodeExpression(
           BasicElement parentElement, BasicElement currentElement) throws SyntaxTreeException {

      if (LROperation.class.isInstance(parentElement)) {
         LROperation lrOpt = LROperation.class.cast(parentElement);
         //few cases here
         if (lrOpt.getLeftOpperand() == null) {
            lrOpt.setLeftOperand(currentElement);
            System.out.println("Is set? Left " + LROperation.class.cast(parentElement).getLeftOpperand().getClass());
         } else if (lrOpt.getRightOpperand() == null && lrOpt.hasComma()) {
            lrOpt.setRightOperand(currentElement);
            System.out.println("Is set? Right " + LROperation.class.cast(parentElement).getRightOpperand().getClass());
         } else {
            throw new SyntaxTreeException("Operands for " + lrOpt.getClass().toString() + " at scope: "
                    + parentElement.getScope() + " are not correctly formatted");
         }
      } else if (LetOperation.class.isInstance(parentElement)) {
         LetOperation letOpt = LetOperation.class.cast(parentElement);
         if (letOpt.getVariable() == null
                 && VariableElement.class.isInstance(currentElement)
                 && !letOpt.hasComma()
                 && !letOpt.hasSecondComma()) {
            //Here we don't check first comma, as variable is created before
            System.out.println("Set variable");
            letOpt.setVariable(VariableElement.class.cast(currentElement));
         } else if (letOpt.hasComma() && !letOpt.hasSecondComma()) {
            //Here we have the second item in the let, the value expression
            if (letOpt.getVariable().getValue() == null) {
               System.out.println("Set variable's value");
               letOpt.getVariable().setValue(currentElement);
            } else {
               throw new SyntaxTreeException("Variable for " + letOpt.getClass().toString()
                       + " at scope: " + parentElement.getScope() + " has already been set.");
            }
         } else if (letOpt.hasComma() && letOpt.hasSecondComma()) {
            letOpt.setExpression(currentElement);
         } else {
            throw new SyntaxTreeException(letOpt.getClass().toString() + " at scope: "
                    + parentElement.getScope() + " is not correctly formatted.");
         }

      } else {
         throw new SyntaxTreeException("Unexpected parent case encountered at scope level: "
                 + parentElement.getScope());
      }

      return parentElement;

   }
}
