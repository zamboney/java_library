/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import modals.Book;
import modals.BookReader;
import modals.Condition;
import modals.Genre;
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
    private HashMap<UUID, Book> bookHash = new HashMap<UUID, Book>() {
    };

    @Override
    public List<Book> GetBooks() {
        List<Book> books = new ArrayList<Book>();

        try {
            books = this.getData(this.BOOK_DAT);
            books.forEach((b) -> bookHash.put(b.getId(), b));
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
    private HashMap<UUID, BookReader> hashBookReader = new HashMap<UUID, BookReader>();

    @Override
    public List<BookReader> GetBookReader() {
        List<BookReader> items = new ArrayList<BookReader>();
        List<Rent> rents = this.GetRents();
        try {

            items = this.getData(this.BOOK_READER_DAT);

            items.forEach((reader) -> {
                int delay = rents.stream()
                        .filter(r -> r.getEnd() != null && r.getReaderId().equals(reader.getId()))
                        .mapToInt((r) -> {
                            Double gap = ((r.getDoto().getTime() - r.getEnd().getTime()) / 8.64e+7);
                            return gap.intValue() > 0 ? gap.intValue() : 0;
                        }).sum();
                reader.setCanRent(delay < this.GetDelayDays());
                hashBookReader.put(reader.getId(), reader);
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
        Book book = books.stream().filter((b) -> b.getId().equals(GetUUID)).findFirst().get();
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
        BookReader br = readers.stream().filter((item) -> item.getId() == GetUUID).findFirst().get();
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

    @Override
    public void UpDateRent(Rent rent) {
        List<Rent> rents = this.GetRents();
        Rent rr = rents.stream().filter((r) -> r.getId().equals(rent.getId())).findFirst().get();
        rents.remove(rr);
        rents.add(rent);
        try {
            this.saveData(rents, RENT_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BookReader GetBookReaderById(UUID readerId) {
        this.GetBookReader();
        return hashBookReader.get(readerId);

    }

    @Override
    public Book GetBookById(UUID readerId) {
        this.GetBooks();
        return bookHash.get(readerId);
    }

    @Override
    public void UpDateBook(Book book) {
        List<Book> books = this.GetBooks();
        Book rr = books.stream().filter((r) -> r.getId().equals(r.getId())).findFirst().get();
        books.remove(rr);
        books.add(book);
        try {
            this.saveData(books, BOOK_DAT);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
