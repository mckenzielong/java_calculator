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
public class Add extends Operation {
        
    private static final String name = "ADD";
    
    public Add(Integer scopeLevel) {
        super(scopeLevel);
    }
    
    @Override
    public int operate() {
        return getLeftOpperand().evaluate() + getRightOpperand().evaluate();      
    }

    @Override
    public String optName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
    
   
}
