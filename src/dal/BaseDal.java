/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import main.modals.*;

/**
 *
 * @author ritzhaki
 */
public interface BaseDal {
    Book[] getBooks();
    BookReader[] getBookReader();
    Rent[] getRents();
    
}
