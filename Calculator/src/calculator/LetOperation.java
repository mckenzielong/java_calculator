/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

/**
 *
 * @author McKenzie Long
 * 
 * */

public class LetOperation extends BasicElement {
    
    private static final String name = "let";
    private VariableElement variable;
    private BasicElement expression;

    public LetOperation(Integer scopeLevel) {
        super(scopeLevel);
    }
    
    @Override
    public int evaluate() {
        return 0;
    }
    
    public VariableElement getVariable() {
        return variable;
    }

    public void setVariable(VariableElement variable) {
        this.variable = variable;
    }
    
    public BasicElement getExpression() {
        return expression;
    }

    public void setExpression(BasicElement expression) {
        this.expression = expression;
    }
    
    
}
