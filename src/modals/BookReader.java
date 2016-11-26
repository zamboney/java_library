/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class BookReader implements Serializable{
    private final UUID _id = UUID.randomUUID();
    private final String _name;
    private final Date _created;
    private final String _email;
    private boolean _canRent;
    private String RentId = null;
    private List<String> _RentIds;

    @Override
    public String toString() {
        return String.format("%s",this._name);
    }

    public UUID getId() {
        return _id;
    }

    public boolean CanRent() {
        return _canRent;
    }

    public void setCanRent(boolean _canRent) {
        this._canRent = _canRent;
    }

    public BookReader(String _name, String _email) {
        this._created = new Date();
        this._name = _name;
        this._email = _email;
        this._canRent = true;
        this._RentIds = new ArrayList<String>(){};
    }
    
    public List<String> getRentIds(){
        return this._RentIds;
    }
    public void AddRentId(UUID id){
        this._RentIds.add(id.toString());
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
