/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

/**
 *
 * @author Ken
 */
public class VariableElement extends BasicElement {
    
    private static final String VARIABLE_NAME_FORMAT = "[a-zA-Z]{1}";
    private BasicElement value;
    private String name;
    
    public VariableElement(Integer scopeLevel, String name) throws IllegalArgumentException {
        super(scopeLevel);
        
        //check if variable name is legal
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Variable name is not properly formatted: " + VARIABLE_NAME_FORMAT);
        }
    }

    @Override
    public int evaluate() {
        return value.evaluate();
    }

    public BasicElement getValue() {
        return value;
    }

    public void setValue(BasicElement value) {
        this.value = value;
    }
    
    private boolean isValidName(String name) {
        //current rule is only 1 letter
        return name.matches(VARIABLE_NAME_FORMAT);
    } 
    
}
