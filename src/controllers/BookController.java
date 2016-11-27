/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import modals.Book;
import modals.Condition;
import modals.Genre;
import views.BackToHomeException;
import views.Input;
import views.OutPut;

/**
 *
 * @author ritzhaki
 */
public class BookController extends BaseController {

    /**
     * Conditions enum as a list of strings.
     */
    private final List<String> _bookConditions;

    /**
     * Genres enum as a list of strings.
     */
    private final List<String> _bookGenres;

    /**
     * create the book controller. initilazed the list of book conditions and
     * genres;
     *
     * @param dal
     */
    public BookController(BaseDal dal) {
        super(dal);
        _bookConditions = Arrays.asList(Arrays.stream(Condition.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
        _bookGenres = Arrays.asList(Arrays.stream(Genre.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
    }

    /**
     * get a Books if there name contains the "bookName" and the genre is
     * "BookGenre"
     *
     * @param bookName - books name that contains the the string.
     * @param bookGenre - the genre of the books.
     * @return filter Books base on the prams.
     * @throws BackToHomeException - get back to home.
     */
    private List<Book> FilterBooks(String bookName, Genre bookGenre) throws BackToHomeException {
        while (true) {
            List<Book> books
                    = this._dal.GetBooks()
                            .stream()
                            .filter((book) -> "".equals(book.getRentId()))
                            .filter((book) -> book.getGenre().equals(bookGenre))
                            .filter((book) -> book.getName().contains(bookName))
                            .sorted((a, b) -> {
                                return a.getName().compareTo(b.getName());
                            })
                            .collect(Collectors.toList());
            if (books.isEmpty()) {
                OutPut.ShowText(String.format("\"%s\" isn't found", bookName));
                throw new BackToHomeException();
            }
            return books;
        }
    }

    /**
     * Open Check to peek a single book.
     *
     * @return the book that been peeked from the check list.
     * @throws BackToHomeException - go back home.
     */
    public Book PeekOne() throws BackToHomeException {
        String bookName = views.Input.getWordWithText("Enter book name");
        Genre bookGenre = this.peekAGenare();
        List<Book> books = this.FilterBooks(bookName, bookGenre);
        return books.get(views.Input.peekFromList("Result:",
                books.stream().map((book) -> book.getName()).collect(Collectors.toList())));
    }

    /**
     * Add a new Books Get the genre, book name, condition and number of books
     *
     * @throws BackToHomeException
     */
    void Add() throws BackToHomeException {

        Genre bookGenre = this.peekAGenare();

        String bookName = views.Input.getWordWithText("Enter book name");

        Condition bookCondition = this.peekACondition();

        int bookUnits = views.Input.GetInt("Number of Books");
        List<Book> books = new ArrayList<Book>() {
        };
        for (int i = 0; i < bookUnits; i++) {
            Book newBook = new Book(bookName, bookCondition, bookGenre);
            this._dal.SaveBook(newBook);
            books.add(newBook);
        }
        showBookList(books);

    }

    /**
     * show a table of books.
     * @param books - book to convert to rows on the table.
     */
    private void showBookList(List<Book> books) {
        List<List<String>> rows
                = books
                        .stream()
                        .map((book) -> new ArrayList<String>() {
                    {
                        add(book.getId().toString());
                        add(book.getName());
                        add(book.getCondition().toString());
                        add(book.getGenre().toString());
                    }
                }).collect(Collectors.toList());

        List<String> headers = new ArrayList<String>() {
            {
                add("Id");
                add("Name");
                add("Condition");
                add("Genre");

            }
        };
        views.OutPut.ShowTable("%-37s|%-20s|%-10s|%-10s", headers, rows);
        views.OutPut.ShowText("Total item -> " + rows.size());
        views.OutPut.ShowText("");
    }

    /**
     * show book base on name, and status (rented or unrented).
     * @throws BackToHomeException 
     */
    void ShowByName() throws BackToHomeException {
        String bookName = views.Input.getWordWithText("Enter book name");
        Boolean getRentsBook = views.Input.peekFromList("Get Which book", new ArrayList<String>() {
            {
                add("Rent's Books");
                add("Not Rent's");
            }
        }) == 0;
        showBookList(this._dal.GetBooks()
                .stream()
                .filter((book) -> book.getName().contains(bookName))
                .filter((book) -> getRentsBook ? !book.getRentId().equals("") : book.getRentId().equals("")).collect(Collectors.toList()));
    }

    /**
     * Remove a Book base on his UUID.
     * @throws BackToHomeException 
     */
    void Remove() throws BackToHomeException {
        views.OutPut.ShowText("enter UUID of a Book");
        UUID id = views.Input.GetUUID();
        if (this._dal.GetBooks().stream().filter((item) -> item.getId().equals(id)).findFirst() == null) {
            views.OutPut.ShowText(id + " not found.");
        } else {
            this._dal.RemoveBook(id);
            views.OutPut.ShowText(id + " deleted.");

        }

    }

    /**
     * Peak a book Condition from a list.
     *
     * @return the Condition that been peeked.
     * @throws BackToHomeException
     */
    public Condition peekACondition() throws BackToHomeException {
        return Condition.valueOf(this._bookConditions.get(views.Input.peekFromList("What is the Book Condition", this._bookConditions)));
    }

    /**
     * Peak a book Genre from a list.
     *
     * @return the Genre that been peeked.
     * @throws BackToHomeException
     */
    public Genre peekAGenare() throws BackToHomeException {
        return Genre.valueOf(this._bookGenres.get(views.Input.peekFromList("What is the Book Genare", this._bookGenres)));

    }

    /**
     * Peek a book base on his UUID.
     * @return the Book that bean peeked.
     * @throws BackToHomeException 
     */
    Book PeekOneById() throws BackToHomeException {
        UUID id = Input.GetUUID();
        try {
            return this._dal.GetBooks()
                    .stream()
                    .filter((b) -> b.getId().equals(id))
                    .findFirst().get();
        } catch (java.util.NoSuchElementException exc) {
            views.OutPut.ShowText("this book isn't exist");
            throw new views.BackToHomeException();
        }
    }
}
