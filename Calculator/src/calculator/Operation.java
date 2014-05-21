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

public abstract class Operation extends BasicElement{
    
    private BasicElement leftOpperand;
    private BasicElement rightOpperand;
    private boolean leftBracket;
    private boolean rightBracket;
    private boolean comma;
       
    public Operation(Integer scope) {
        super(scope);
        leftBracket = false;
        rightBracket = false;
        comma = false;
    }
    
    public abstract String optName();
    public abstract int operation();
    
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
    
    public boolean hasLeftBracket() {
        return leftBracket;
    }

    public void setLeftBracket() {
        this.leftBracket = true;
    }

    public boolean hasRightBracket() {
        return rightBracket;
    }

    public void setRightBracket() {
        this.rightBracket = true;
    }

    public boolean hasComma() {
        return comma;
    }

    public void setComma() {
        this.comma = true;
    }  
    
    @Override
    public boolean isValidSyntax() {
        return leftBracket
                && (leftOpperand != null)
                && comma
                && (rightOpperand != null)
                && rightBracket;        
    }
}
