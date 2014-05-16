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
public abstract class BasicElement {
    
    private BasicElement parent;
    private Integer scope;

    public BasicElement (Integer scopeLevel) {
        scope = scopeLevel;
    }

    public abstract int evaluate();

    public void setParent(BasicElement parent) {
        this.parent = parent;
    }

    public BasicElement getParent() {
        return parent;
    }
    
    public Integer getScope() {
        return scope;
    }
   
    
}
