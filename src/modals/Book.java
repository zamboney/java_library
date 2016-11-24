/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class Book implements Serializable{
    private final UUID _id = UUID.randomUUID();
    private final String _name;
    private Condition _condition;
    private final Genre _genre;
    private int _dateToRent = 2;
    private String RentId = null;

    public void setCondition(Condition _condition) {
        this._condition = _condition;
    }

    public String getRentId() {
        return RentId;
    }

    public void setRentId(String RentId) {
        this.RentId = RentId;
    }

    public int getDateToRent() {
        return _dateToRent;
    }

    public UUID getId() {
        return _id;
    }

    public void setDateToRent(int _dateToRent) {
        this._dateToRent = _dateToRent;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" - %s",this._name, this._condition);
    }
    
    
    public Book(String _name, Condition _condition, Genre _genre) {
        this._name = _name;
        this._genre = _genre;
        this._condition = _condition;
    }

    public Condition getCondition() {
        return _condition;
    }

    
    public String getName() {
        return _name;
    }

    public Genre getGenre() {
        return _genre;
    }
    
    
    
}
