/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modals.Book;
import modals.BookReader;
import modals.Rent;

/**
 *
 * @author rani
 */
public class FileSystemDal implements BaseDal {

    public final String BOOK_DAT;
    public final String BOOK_READER_DAT;
    public final String RENT_DAT;

    public FileSystemDal() {
        this.BOOK_DAT = "books.dat";
        this.BOOK_READER_DAT = "book_readers.dat";
        this.RENT_DAT = "rets.dat";
    }

    private List<Book> getData(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return new ArrayList<Book>();
            }
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            return (List<Book>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public List<Book> GetBooks() {
        List<Book> books = new ArrayList<Book>();
        try {
            books = this.getData(this.BOOK_DAT);

        } catch (IOException ex) {

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return books;
    }

    @Override
    public boolean SaveBook(Book b) {
        List<Book> books = this.GetBooks();
        books.add(b);
        try {
            return this.saveData(books, this.BOOK_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<BookReader> GetBookReader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean SaveBookReader(BookReader br) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rent> GetRents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean SaveRent(Rent r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean saveData(List<Book> list, String fileName) throws IOException, ClassNotFoundException {
        List<Book> arr;
        File file = new File(fileName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                arr = (List<Book>) ois.readObject();
            } catch (IOException ex) {
                throw ex;
            } finally {
                ois.close();
            }
        } 
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
           
            oos.writeObject(list);
        } catch (IOException ex) {
            throw ex;
        } finally {

            oos.close();
        }
        return true;
    }

}
