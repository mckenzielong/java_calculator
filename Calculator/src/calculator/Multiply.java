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
public class Multiply extends LROperation {
        
    private static final String name = "mul";
    
    public Multiply(Integer scopeLevel) {
        super(scopeLevel);
    }
    
    @Override
    public int operation() {
        return getLeftOpperand().evaluate() * getRightOpperand().evaluate();      
    }

    @Override
    public String optName() {
        return name;
    }   
}

