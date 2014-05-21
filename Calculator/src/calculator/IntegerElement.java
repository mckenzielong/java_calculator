/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

/**
 *
 * @author McKenzie Long
 */
public class IntegerElement extends BasicElement {

    private final Integer value;

    public IntegerElement(Integer scopeLevel, Integer knownValue) {
        super(scopeLevel);
        value = knownValue;
        
    }
    
    @Override
    public int evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }        

    @Override
    public boolean isValidSyntax() {
        return true;
    }
    
    
}
