/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import main.modals.* /**
         *
         * @author ritzhaki
         */

public class DemoDal implements BaseDal {

    @Override
    public Book[] getBooks() {
        Book[] books = new Book[]{
            new Book("book a", Condition.Poor),
            new Book("book b", Condition.Good),
            new Book("book c", Condition.NotInUse),
            new Book("book d", Condition.Good)
        };
        return books;
    }

    @Override
    public BookReader[] getBookReader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rent[] getRents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
