/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class Rent implements Serializable{

    private final UUID _id;
    private final BookReader _reader;
    private final Book _book;
    private final Date _start;
    private Date _end;
    private final Date _doto;
    private final Condition _onRentCondition;
    private Condition _onRetrunCondition;

    public Rent(BookReader _reader, Book _book, Date _doto) {
        this._id = UUID.randomUUID();
        this._reader = _reader;
        this._doto = _doto;
        this._book = _book;
        this._start = new Date();
        this._onRentCondition = _book.getCondition();
    }

    public void Retrun(Condition retCond){
        this._onRetrunCondition = retCond;
    }
    public Date getDoto() {
        return _doto;
    }

    public BookReader getReader() {
        return _reader;
    }

    public Book getBook() {
        return _book;
    }

    public Date getStart() {
        return _start;
    }

    public Date getEnd() {
        return _end;
    }

    public void setEnd(Date _end) {
        this._end = _end;
    }

}
