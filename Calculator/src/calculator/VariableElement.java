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
    
    private BasicElement value;
    private String name;
    
    public VariableElement(Integer scopeLevel, String name) {
        super(scopeLevel);
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
    
}
