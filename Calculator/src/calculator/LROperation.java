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

public abstract class LROperation extends Operation{
    
    private BasicElement leftOpperand;
    private BasicElement rightOpperand;

    public abstract String optName();
    public abstract int operation();
    
    public LROperation(Integer scope) {
        super(scope);
    }
    
    @Override
    public int evaluate() {
        return operation();
    }

    public BasicElement getLeftOpperand() {
        return leftOpperand;
    }

    public BasicElement getRightOpperand() {
        return rightOpperand;
    }
    
    public void setLeftOpperand(BasicElement leftOpperand) {
        this.leftOpperand = leftOpperand;
    }

    public void setRightOpperand(BasicElement rightOpperand) {
        this.rightOpperand = rightOpperand;
    }

    @Override
    public String toString() {
        return optName() + "(" + leftOpperand.toString() + ","
                + rightOpperand.toString() + ")";
    }
        
    @Override
    public boolean isValidSyntax() {
        return hasLeftBracket()
                && (leftOpperand != null)
                && hasComma()
                && (rightOpperand != null)
                && hasRightBracket();        
    }
}
