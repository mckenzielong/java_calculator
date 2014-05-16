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
public class Separator extends Terminator {

    public Separator(Integer scopeLevel) {
        super(scopeLevel);
    }

    @Override
    public int evaluate() {
        throw new UnsupportedOperationException("");
    }

}
