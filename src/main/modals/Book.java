/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.modals;

import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class Book {
    private final UUID _id = UUID.randomUUID();
    private final String _name;
    private Condition _condition;

    public Book(String _name, Condition _condition) {
        this._name = _name;
        this._condition = _condition;
    }

    public Condition getCondition() {
        return _condition;
    }

    public void setCondition(Condition _condition) {
        this._condition = _condition;
    }
    
}
