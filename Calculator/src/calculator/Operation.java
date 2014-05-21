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
public abstract class Operation extends BasicElement {
    
    private boolean leftBracket;
    private boolean rightBracket;
    private boolean comma;
       
    public Operation(Integer scope) {
        super(scope);
        leftBracket = false;
        rightBracket = false;
        comma = false;
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
    
}
