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

        BasicElement treeRoot = buildTree(tokens);
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

    private static BasicElement buildTree(StringTokenizer tokens) {
        BasicElement root = null;
        Integer scopeLevel = 0;
        Stack<BasicElement> stackOfElements = new Stack();
        HashMap<String, VariableElement> activeVariables = new HashMap();
        boolean nextItemIsElement = true;
        //TODO limit to VAR
        
        while (tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            BasicElement currentElement = processToken(currentToken, scopeLevel);

            //We now have and object representing the current token in hand
            //move this?
            if (root == null) {
                root = currentElement;
            }

            //TODO: error
            if (currentElement == null) {
                return null;
            }

            nextItemIsElement = false;
            if (!stackOfElements.empty()) {
                //behaves agnostic of ',' seperator
                //set correctly process the children of the parents
                System.out.println("current is: " + currentElement.getClass().toString());

                //parent has left and right operands (sub, add, etc)
                //TODO: currently duped code, think of how to fix
                //if current is an LROpt, let, var or int, we need to look at parent
                if (Operation.class.isInstance(currentElement)
                        || LetOperation.class.isInstance(currentElement)
                        || IntegerElement.class.isInstance(currentElement)) {
                    
                    BasicElement parentElement = stackOfElements.pop();
                    System.out.println("Parent is: " + parentElement.getClass().toString());
                    if (Operation.class.isInstance(parentElement)) {
                        Operation ops = Operation.class.cast(parentElement);
                        if (ops.getLeftOpperand() == null) {
                            System.out.println("SET LEFT LEMENT " + currentElement.getClass().toString());
                            ops.setLeftOpperand(currentElement);
                        } else if (ops.getRightOpperand() == null) {
                            System.out.println("SET RIGHT LEMENT " + currentElement.getClass().toString());
                            ops.setRightOpperand(currentElement);
                        } else {
                            System.out.println("Syntax Error: opperand is loaded");
                            //error!
                        }
                    } else if (LetOperation.class.isInstance(parentElement)) {
                        LetOperation letOps = LetOperation.class.cast(parentElement);
                        if (letOps.getVariable() == null) {
                            //System.out.println("SET LEFT LEMENT");
                            //letOps.setVariable(currentElement);
                        } else if (letOps.getExpression() == null) {
                            //System.out.println("SET RIGHT LEMENT");
                            letOps.setExpression(currentElement);
                        } else {
                            //error!
                        }
                    } else if (VariableElement.class.isInstance(parentElement)) {
                        VariableElement varOp = VariableElement.class.cast(parentElement);
                        if (varOp.getValue() == null) {
                            varOp.setValue(currentElement);
                        } else {
                            System.out.println("Syntax Error: Variable already has value set");
                        }
                    }
                    stackOfElements.push(parentElement);
                
                } else if (VariableElement.class.isInstance(currentElement)) {
                    BasicElement parentElement = stackOfElements.pop();
                    //if this is a var declaration, add it to the stack, next should be value
                    if (LetOperation.class.isInstance(parentElement)
                            && (LetOperation.class.cast(parentElement).getVariable() == null)) {
                        //add it to the stack, but the variable isn't active yet
                    }
                    
                } else if (LeftBracket.class.isInstance(currentElement)) {
                    scopeLevel++;
                } else if (RightBracket.class.isInstance(currentElement)) {
                    BasicElement parentElement = stackOfElements.pop();
                    scopeLevel--;
                } else if (Separator.class.isInstance(currentElement)) {
                    BasicElement parentElement = stackOfElements.pop();
                    System.out.println("COMMA TOKEN: " + parentElement.getClass().toString());
                    //check for cases opt(,val)
                    if (Operation.class.isInstance(parentElement)) {
                        Operation ops = Operation.class.cast(parentElement);
                        if (ops.getLeftOpperand() == null) {
                            //Error...
                            System.out.println("Syntax Error: no left element");
                        } else if (ops.getLeftOpperand() != null && ops.getRightOpperand() != null) {
                            System.out.println("Syntax Error: unexpected comma");
                        }
                    } else if (LetOperation.class.isInstance(parentElement)) {
                        LetOperation letOpt = LetOperation.class.cast(parentElement);
                        /*three cases here:
                        1) var name just set
                        2) value for var just set
                        3) syntax error
                        */
                        if (letOpt.getVariable() == null) {
                            System.out.println("Syntax Error: variable name not declared");
                        } else if (letOpt.getVariable().getValue() == null) {
                            
                        }
                    }
                    stackOfElements.push(parentElement);
                }
                
            }

            if (!Terminator.class.isInstance(currentElement)) {
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
    private static BasicElement processToken(String token, Integer scopeLevel) {
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
                //Looking for var or integer
                try {
                    returnElement = new IntegerElement(Integer.valueOf(token), scopeLevel);
                } catch (NumberFormatException e) {
                    returnElement = new VariableElement(scopeLevel, token);
                }          
        }
        return returnElement;
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
