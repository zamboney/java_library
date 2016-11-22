/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class BookReader {
    private final UUID _id = UUID.randomUUID();
    private final String _name;
    private final Date _created;
    private final String _email;
    private boolean _canRent;

    public boolean isCanRent() {
        return _canRent;
    }

    public void setCanRent(boolean _canRent) {
        this._canRent = _canRent;
    }

    public BookReader(String _name, String _email) {
        this._created = new Date();
        this._name = _name;
        this._email = _email;
    }

    public String getName() {
        return _name;
    }

    public Date getCreated() {
        return _created;
    }

    public String getEmail() {
        return _email;
    }
    
}
