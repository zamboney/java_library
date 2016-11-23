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

    private <T> List<T> getData(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return new ArrayList<T>();
            }
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            return (List<T>) ois.readObject();
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

    private <T>boolean saveData(List<T> list,T item, String fileName) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            list.add(item);
            oos.writeObject(list);
            return true;
        } catch (IOException ex) {
            throw ex;
        } finally {
            oos.close();
        }
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
        try {
            return this.saveData(this.GetBooks(),b, this.BOOK_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<BookReader> GetBookReader() {
         List<BookReader> items = new ArrayList<BookReader>();
        try {
            items = this.getData(this.BOOK_DAT);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    @Override
    public boolean SaveBookReader(BookReader br) {
        try {
            return this.saveData(this.GetBookReader(),br, this.BOOK_READER_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Rent> GetRents() {
         List<Rent> items = new ArrayList<Rent>();
        try {
            items = this.getData(this.RENT_DAT);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    @Override
    public boolean SaveRent(Rent r) {
        try {
            return this.saveData(this.GetRents(),r, this.BOOK_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
