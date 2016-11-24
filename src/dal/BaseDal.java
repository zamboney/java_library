/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.util.*;
import modals.Rent;
import modals.BookReader;
import modals.Book;

/**
 *
 * @author ritzhaki
 */
public interface BaseDal {
    List<Book> GetBooks();
    boolean SaveBook(Book b);
    List<BookReader> GetBookReader();
    boolean SaveBookReader(BookReader br);
    List<Rent> GetRents();
    boolean SaveRent(Rent r);
    int GetDelayDays();

    public boolean RemoveBook(UUID GetUUID);
    public boolean RemoveBookReader(UUID GetUUID);
    
    public void UpDateRent(Rent rent);

    public BookReader GetBookReaderById(UUID readerId);
    public Book GetBookById(UUID readerId);

    public void UpDateBook(Book book);
    
}
