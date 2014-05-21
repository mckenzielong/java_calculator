/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author McKenzie Long
 */
public class Calculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Here we will parse a string, making sure it works etc.
        //lazy meaning inside 
        //first combine all the arguments, incase they used spaces...
        StringBuilder allArgs = new StringBuilder();
        for (String arg : args) {
            allArgs.append(arg);
        }

        System.out.println(allArgs.toString());
        StringTokenizer tokens = new StringTokenizer(allArgs.toString(), "\\(|\\)|,", true);

        BasicElement treeRoot = buildSyntaxTree(tokens);
        System.out.println(treeRoot.toString());
        System.out.println(treeRoot.evaluate());

        while (tokens.hasMoreTokens()) {
            System.out.println(tokens.nextToken());
        }
        //idea here is as we parse, we will push the element on to the stack
        //once parsing is done, we will evaluate the stack, element by element

        //parse
        //operationStack.push(new Operation(new IntegerElement(4), new IntegerElement(2)));
    }

    private static BasicElement buildSyntaxTree(StringTokenizer tokens) {
        BasicElement root = null;
        Integer scopeLevel = 0;
        Stack<BasicElement> stackOfElements = new Stack();
        HashMap<String, VariableElement> activeVariables = new HashMap();
        //TODO limit to VAR

        if (tokens.hasMoreTokens()) {
            root = createNode(tokens.nextToken(), scopeLevel);
            stackOfElements.push(root);
        } else {
            //error, no tokens provided...
        }

        while (tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            BasicElement currentElement = createNode(currentToken, scopeLevel);

            if (!stackOfElements.empty()) {
                //behaves agnostic of ',' seperator
                //set correctly process the children of the parents
                System.out.println("current is: " + currentElement.getClass().toString());

                /* if the current token is a terminator, update the parent's 
                 syntax in terms of 'terminators'. 
  
                 Otherwise we have a var, const or Operation in hand, and we
                 need to update the parent.  Found it easier to structure code
                 this way since we are updating for syntax versus updating for
                 both syntax and evaluation */
                BasicElement parentElement = stackOfElements.pop();
                System.out.println("Parent is: " + parentElement.getClass().toString());

                if (Terminator.class.isInstance(currentElement)) {

                    parentElement = updateParentNodeTerminator(parentElement,
                            Terminator.class.cast(currentElement));
                    
                    stackOfElements.push(parentElement);

                    /* Once we have updated the parent for syntax, we need to 
                     check if any adjustments must be made to the scope level. 
                     In this grammar, left brackets should be a scope increase
                     and right brackets mean a decrease, and that our element 
                     is done being updated */
                    if (LeftBracket.class.isInstance(currentElement)) {
                        scopeLevel++;
                    } else if (RightBracket.class.isInstance(currentElement)) {
                        BasicElement finishedElement = stackOfElements.pop();
                        if (!finishedElement.isValidSyntax()) {
                            //error, bad syntax
                        }
                        scopeLevel--;
                    }

                } else {
                    parentElement = updateParentNodeExpression(parentElement,
                            currentElement);
                    
                    stackOfElements.push(parentElement);

                }

                

            }

            if (!Terminator.class.isInstance(currentElement)
                    && !IntegerElement.class.isInstance(currentElement)) {
                System.out.println("Pushing obj");
                stackOfElements.push(currentElement);
            }

            //we will push new opperand if not single opperand (int / var)
        }
        System.out.println("Open brackets is: " + scopeLevel.toString());
        return root;
    }

    /**
     *
     * @param token
     * @param scopeLevel
     * @return
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
                    System.out.println("Is Const");
                    returnElement = new IntegerElement(Integer.valueOf(token), scopeLevel);
                } else {
                    returnElement = new VariableElement(scopeLevel, token);
                    System.out.println("Is Var");
                }
        }
        return returnElement;
    }

    private static BasicElement updateParentNodeTerminator(
            BasicElement parentElement, Terminator currentElement) {
        //System.out.println("Parent is: " + parentElement.getClass().toString());
        if (Operation.class.isInstance(parentElement)) {
            Operation ops = LROperation.class.cast(parentElement);
            if (LeftBracket.class.isInstance(currentElement)) {
                if (!ops.hasLeftBracket()) {
                    ops.setLeftBracket();
                } else {
                    //error, opening bracket already set
                }
            } else if (RightBracket.class.isInstance(currentElement)) {
                if (!ops.hasRightBracket()) {
                    ops.setRightBracket();
                } else {
                    //error, closing bracket already set
                }
            } else if (Separator.class.isInstance(currentElement)) {
                if (!ops.hasComma()) {
                    ops.setComma();
                } else {
                    //Here we have the let operation to consider, it has two
                    if (LetOperation.class.isInstance(ops)) {
                        LetOperation letOp = LetOperation.class.cast(ops);
                        if (!letOp.hasSecondComma()) {
                            letOp.setSecondComma();
                        } else {
                            //error, second comma set
                        }
                    } else {
                        //comma is already set
                    }
                }
            } else {
                //terminator case not implemented           
            }
        } else {
            //parent case not implemented -- syntax error
        }

        return parentElement;
    }

    private static BasicElement updateParentNodeExpression(
            BasicElement parentElement, BasicElement currentElement) {
        
        if (LROperation.class.isInstance(parentElement)) {
            LROperation lrOpt = LROperation.class.cast(parentElement);
            //few cases here
            if (lrOpt.getLeftOpperand() == null) {
                lrOpt.setLeftOpperand(currentElement);
                System.out.println("Is set? Left " + LROperation.class.cast(parentElement).getLeftOpperand().getClass());
            } else if (lrOpt.getRightOpperand() == null && lrOpt.hasComma()) {
                lrOpt.setRightOpperand(currentElement);
                System.out.println("Is set? Right " + LROperation.class.cast(parentElement).getRightOpperand().getClass());
            } else {
                //syntax error
            }
            
            
        }

        return parentElement;

    }
}

/* seeing a ',' means a few things:
 1) we have "Operation" on stack, should process right next
 2) have let on stack, should process middle
 3) have let on stack, should process right
 4) bad formatting
                 
 if (nextItemIsElement) {
 //error
 }

 BasicElement workingElement = stackOfElements.pop();
 System.out.println("COMMA TOKEN: " + workingElement.getClass().toString());
 //check for cases opt(,val)
 if (Operation.class.isInstance(workingElement)) {
 Operation ops = Operation.class.cast(workingElement);
 if (ops.getLeftOpperand() == null) {
 System.out.println("WHOOPS no left element?");
 }
 }
 stackOfElements.push(workingElement);
 //TODO Case for LET(var, val, expr)

 //the next element should be an element
 nextItemIsElement = true;
 break;
 */
