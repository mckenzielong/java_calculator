/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

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
        
        //System.out.println(allArgs.toString());
        StringTokenizer tokens = new StringTokenizer(allArgs.toString(),"\\(|\\)" , true);
        
        BasicElement treeRoot = buildTree(tokens);
        System.out.println(treeRoot.toString());
       
        //idea here is as we parse, we will push the element on to the stack
        //once parsing is done, we will evaluate the stack, element by element
        
        //parse
        //operationStack.push(new Operation(new IntegerElement(4), new IntegerElement(2)));
                
    }
    
    private static BasicElement buildTree (StringTokenizer tokens) {
        BasicElement root = null;
        Integer scopeLevel = 0;
        String nextDesiredElement = ""; //eww, we can do better...
        //Should be doing this on type... ie we would expect an single opt or
        //a two or three operation...
        
        while (tokens.hasMoreTokens()) {
            if (scopeLevel < 0) {
                //error
            }
            BasicElement currentElement = null; 
            String currentToken = tokens.nextToken();
            
            /* if the next element isn't our desired token, and the current
            desired next token has some sort of restriction, we have a format
            error... */
            if (! (nextDesiredElement.equalsIgnoreCase(currentToken)
                    || nextDesiredElement.equalsIgnoreCase(""))) {
                //error
            } 
            
            //System.out.println(currentToken);
            switch (currentToken) {
                case "add":
                    currentElement = new Add(scopeLevel);
                    nextDesiredElement = "(";
                    break;
                    
                case "(":
                        scopeLevel++;
                        nextDesiredElement = "";
                    break;
                    
                case ")":
                    scopeLevel--;
                    nextDesiredElement = "";
                    break;
                    
                default:
                    //currentElement = new IntegerElement(Integer.parseInt(currentToken));
                         
            }
            if (root == null) {
                root = currentElement;
            }
            
        }
        System.out.println("Open brackets is: " + scopeLevel.toString());
        return root;
    }
    
}
