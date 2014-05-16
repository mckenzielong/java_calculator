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
public abstract class Operation extends BasicElement{
    
    private BasicElement leftOpperand;
    private BasicElement rightOpperand;
       
    public Operation(Integer scope) {
        super(scope);
    }
    
    public abstract String optName();
    public abstract int operate();
    
    @Override
    public int evaluate() {
        return operate();
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
        System.out.println(optName());
        System.out.println(leftOpperand.toString() );
        System.out.println(rightOpperand.toString() );
        return optName() + "( " + leftOpperand.toString() + " "
                + rightOpperand.toString() + " )";
    }
    
    
    
    
}
