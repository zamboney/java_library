/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import modals.Book;
import modals.Condition;
import modals.Genre;
import views.BackToHomeException;
import views.CheckList;
import views.Input;
import views.OutPut;

/**
 *
 * @author ritzhaki
 */
public class BookController extends BaseController {

    private final List<String> _bookConditions;
    private final List<String> _bookGenres;

    public BookController(BaseDal dal) {
        super(dal);
        _bookConditions = Arrays.asList(Arrays.stream(Condition.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
        _bookGenres = Arrays.asList(Arrays.stream(Genre.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
    }

    private List<Book> FilterBooks() throws BackToHomeException {
        while (true) {

            Genre bookGenre = this.peekAGenare();
            OutPut.ShowText("Enter book name");
            String bookName = Input.GetWord();
            List<Book> books = this._dal.GetBooks()
                    .stream()
                    .filter((book)-> book.getRentId() == null)
                    .filter((book) -> book.getGenre().equals(bookGenre))
                    .filter((book) -> book.getName().contains(bookName))
                    .sorted((a, b) -> {
                        return a.getName().compareTo(b.getName());
                    })
                    .collect(Collectors.toList());
            if (books.isEmpty()) {
                OutPut.ShowText(String.format("\"%s\" isn't found", bookName));
                continue;
            }
            return books;
        }
    }

    public Book PeekOne() throws BackToHomeException {

        List<Book> books = this.FilterBooks();
        return books.get(CheckList.Show("Result:",
                books.stream().map((book) -> book.getName()).collect(Collectors.toList())));
    }

    void Add() throws IOException, BackToHomeException {

        Genre bookGenre = this.peekAGenare();

        views.OutPut.ShowText("Enter Book Name");
        String bookName = views.Input.GetWord();

        Condition bookCondition = this.peekACondition();

        views.OutPut.ShowText("Number of Books");
        int bookUnits = views.Input.GetInt();
        for (int i = 0; i < bookUnits; i++) {
            Book newBook = new Book(bookName, bookCondition, bookGenre);
            this._dal.SaveBook(newBook);
        }

        views.OutPut.ShowText("New book: " + bookName + " Added " + bookUnits + " times.");
    }

    void ShowByName() throws BackToHomeException {
        List<List<String>> rows
                = this.FilterBooks()
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

    public Condition peekACondition() throws BackToHomeException {
        return Condition.valueOf(this._bookConditions.get(views.CheckList.Show("What is the Book Condition", this._bookConditions)));
    }

    public Genre peekAGenare() throws BackToHomeException {
        return Genre.valueOf(this._bookGenres.get(views.CheckList.Show("What is the Book Genare", this._bookGenres)));

    }

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
