/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import modals.Book;
import modals.BookReader;
import modals.Condition;
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

    private <T> boolean saveData(List<T> list, T item, String fileName) throws IOException, ClassNotFoundException {
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
    private <T> boolean saveData(List<T> list, String fileName) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
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
            if (books.size() == 0) {
                books.add(new Book("book a", Condition.Good));
                books.add(new Book("book b", Condition.Good));
                books.add(new Book("book c", Condition.Good));
                books.add(new Book("book d", Condition.Good));
                this.saveData(books, new Book("book e", Condition.Good), this.BOOK_DAT);
                books = this.getData(this.BOOK_DAT);
            }
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    @Override
    public boolean SaveBook(Book b) {
        try {
            return this.saveData(this.GetBooks(), b, this.BOOK_DAT);
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

            items = this.getData(this.BOOK_READER_DAT);
            List<Rent> rents = this.GetRents();
            items.forEach((reader) -> {
                int delayDays = rents.stream()
                        .filter((rent) -> rent.getReader().equals(reader))
                        .mapToInt((rent) -> {
                            Double d = ((rent.getDoto().getTime() - rent.getEnd().getTime())/ 1.15741e-8);
                            return d > 0 ? d.intValue() : 0;
                        }).sum();
                reader.setCanRent(delayDays <= _getDelayDays);
            });
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    @Override
    public boolean SaveBookReader(BookReader br) {
        try {
            return this.saveData(this.GetBookReader(), br, this.BOOK_READER_DAT);
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
            return this.saveData(this.GetRents(), r, this.RENT_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private int _getDelayDays = 2;
    @Override
    public int GetDelayDays() {
        return this._getDelayDays;
    }

    @Override
    public boolean RemoveBook(UUID GetUUID) {
        List<Book> books = this.GetBooks();
        Book book = books.stream().filter((b)->b.getId().equals(GetUUID)).findFirst().get();
        books.remove(book);
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
    public boolean RemoveBookReader(UUID GetUUID) {
        List<BookReader> readers = this.GetBookReader();
        BookReader br = readers.stream().filter((item)->item.getId() == GetUUID).findFirst().get();
        readers.remove(br);
        try {
            return this.saveData(readers, this.BOOK_READER_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
