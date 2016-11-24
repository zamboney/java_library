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
    private final String _readerId;
    private final String _bookId;
    private final Date _start;
    private Date _end;
    private final Date _doto;
    private final Condition _onRentCondition;
    private Condition _onRetrunCondition;

    public Rent(BookReader _reader, Book _book, Date _doto) {
        this._id = UUID.randomUUID();
        this._readerId = _reader.getId().toString();
        this._doto = _doto;
        this._bookId = _book.getId().toString();
        this._start = new Date();
        this._onRentCondition = _book.getCondition();
    }

    public void Retrun(Condition retCond){
        this._onRetrunCondition = retCond;
    }
    public Date getDoto() {
        return _doto;
    }

    public UUID getReaderId() {
        return UUID.fromString(_readerId);
    }

    public UUID getBookId() {
        return UUID.fromString(_bookId);
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

    public UUID getId() {
        return this._id;
    }

}
