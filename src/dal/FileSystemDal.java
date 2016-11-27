/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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

    /**
     * Get data from file base on the type.
     *
     * @param <T> - the list type in the file.
     * @param fileName - the file name
     * @return a list of type <T>
     * @throws IOException - when IO failed
     * @throws ClassNotFoundException - when the reading of the object failed
     */
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

    /**
     * add new item to the file, base on <T> type
     *
     * @param <T> the type of the data
     * @param list a list of the type <T>
     * @param item the item that been added
     * @param fileName the file location
     * @return true if succeeded
     * @throws IOException - the file IO problem
     * @throws ClassNotFoundException - the deserialize failed.
     */
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

    /**
     * force to update the list (good to update a item in the list)
     *
     * @param <T> the type of the data
     * @param list a list of the type <T>
     * @param fileName the file location
     * @return true if succeeded
     * @throws IOException - the file IO problem
     * @throws ClassNotFoundException - the deserialize failed.
     */
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
        List<Book> books = new ArrayList<>();
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
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private HashMap<UUID, BookReader> hashBookReader;

    @Override
    public List<BookReader> GetBookReader() {
        hashBookReader = new HashMap<>();
        List<BookReader> items = new ArrayList<>();

        try {
            items = this.getData(this.BOOK_READER_DAT);
            items = checkDelays(this.getData(this.BOOK_READER_DAT));
            items.forEach((reader) -> {

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
    private HashMap<UUID, Rent> rentHash;

    @Override
    public List<Rent> GetRents() {
        rentHash = new HashMap<>();
        List<Rent> items = new ArrayList<>();
        try {
            items = this.getData(this.RENT_DAT);
            items.forEach((r) -> rentHash.put(r.getId(), r));
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
    public Rent GetRentById(UUID rentId) {
        this.GetRents();
        return rentHash.get(rentId);
    }

    @Override
    public void UpDateBook(Book book) {
        List<Book> books = this.GetBooks();
        try {
            Book rr = books.stream().filter((r) -> r.getId().equals(r.getId())).findFirst().get();
            books.remove(rr);
            books.add(book);
            this.saveData(books, BOOK_DAT);
        } catch (NoSuchElementException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void UpDateBookReader(BookReader reader) {
        List<BookReader> rrs = this.GetBookReader();
        try {
            BookReader rr = rrs.stream().filter((r) -> r.getId().equals(r.getId())).findFirst().get();
            rrs.remove(rr);
            rrs.add(reader);
            this.saveData(rrs, BOOK_READER_DAT);
        } catch (NoSuchElementException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileSystemDal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Check the reader delay days
     *
     * check the difference between the end date and todo date, and if end date
     * not exist check the now time.
     *
     * @param data list of bookreaders;
     * @return update the delay date and the can rent property.
     */
    private List<BookReader> checkDelays(List<BookReader> data) {
        // this is to init the rent hash table.
        this.GetRents();
        long now = new Date().getTime();
        return data.stream().map((br) -> {
            int delay = br.getRentIds().stream()
                    .mapToInt((str) -> {
                        Rent r = this.GetRentById(UUID.fromString(str));
                        if (r.getEnd() != null) {
                            Double d = (r.getDoto().getTime() - (r.getEnd() != null ? r.getEnd().getTime() : now)) / 1.15741e-8;
                            return d.intValue() > 0 ? d.intValue() : 0;
                        }
                        return 0;
                    }).sum();
            if (delay > this.GetDelayDays()) {
                br.setCanRent(false);
            }
            return br;
        }).collect(Collectors.toList());
    }
}
