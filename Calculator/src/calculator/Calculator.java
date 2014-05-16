/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

import java.util.ArrayList;
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
        for (int i = 0 ; i < args.length; i++) {
            allArgs.append(args[i]);
        }
        
        System.out.println(allArgs.toString());
        StringTokenizer tokens = new StringTokenizer(allArgs.toString(),"\\(|\\)|," , true);
        
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
    
    private static BasicElement buildTree (StringTokenizer tokens) {
        BasicElement root = null;
        Integer scopeLevel = 0;
        Stack<BasicElement> stackOfElements = new Stack();
        ArrayList<BasicElement> activeVariables = new ArrayList();
        boolean nextItemIsElement = true;
        //TODO limit to VAR
        
        while (tokens.hasMoreTokens()) {
            BasicElement currentElement = null; 
            String currentToken = tokens.nextToken();
            
            
            //System.out.println(currentToken);
            //TODO: move this switch to own function
            //have to add LB, RB, COMMA
            switch (currentToken) {
                case "add":
                    currentElement = new Add(scopeLevel);
                    break;
                
                case "div":
                    currentElement = new Divide(scopeLevel);
                    break;

                case "mul":
                    currentElement = new Multiply(scopeLevel);
                    break;

                case "sub":
                    currentElement = new Subtract(scopeLevel);
                    break;
                    
                case "(":
                    //this means we increase a scope level
                    if (nextItemIsElement) {
                        //error
                    }
                    scopeLevel++;
                    nextItemIsElement = true;
                    break;
                
                case ")":
                    //pop an element and move down a scope level
                    scopeLevel--;
                    //should try, else throw formatting error
                    stackOfElements.pop();
                    break;
                    
                case ",":
                    /* seeing a ',' means a few things:
                    1) we have "Operation" on stack, should process right next
                    2) have let on stack, should process middle
                    3) have let on stack, should process right
                    4) bad formatting
                    */
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
                
                default:
                    //Looking for var or integer
                    currentElement = new IntegerElement(Integer.valueOf(currentToken), scopeLevel);
                    nextItemIsElement = false;
                         
            }
            
            if (root == null) {
                root = currentElement;
            } 
            
            if (currentElement != null) {
                nextItemIsElement = false;
                if (!stackOfElements.empty()) {
                    //behaves agnostic of ',' seperator
                    //set correctly process the children of the parents
                    BasicElement parentElement = stackOfElements.pop();
                    System.out.println("Parent is: " + parentElement.getClass().toString());
                    System.out.println(currentElement.getClass().toString());

                    if (Operation.class.isInstance(parentElement)) {
                        //if parent has left and right operands, check to see what
                        Operation ops = Operation.class.cast(parentElement);
                        if (ops.getLeftOpperand() == null) {
                            System.out.println("SET LEFT LEMENT");
                            ops.setLeftOpperand(currentElement);
                        } else if (ops.getRightOpperand() == null) {
                            System.out.println("SET RIGHT LEMENT");
                            ops.setRightOpperand(currentElement);
                        } else {
                            //error!
                        }
                    }
                    stackOfElements.push(parentElement);
                }
                
                if (!IntegerElement.class.isInstance(currentElement)) {
                    System.out.println("Pushing obj");
                    stackOfElements.push(currentElement);
                }
                
            } else {
                System.out.println("TOKEN IS: " + currentToken);
            }
            
            //we will push new opperand if not single opperand (int / var)
         
        }
        System.out.println("Open brackets is: " + scopeLevel.toString());
        return root;
    }
    
}
