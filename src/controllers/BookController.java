/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import modals.Book;
import modals.Condition;
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

    public BookController(BaseDal dal) {
        super(dal);
        _bookConditions = Arrays.asList(Arrays.stream(Condition.class.getEnumConstants()).map(Enum::name).toArray(String[]::new));
    }

    private List<Book> FilterBooks() throws BackToHomeException {
        while (true) {
            OutPut.ShowText("Enter book name");
            String bookName = Input.GetWord();
            List<Book> books = this._dal.GetBooks()
                    .stream()
                    .filter((book) -> book.getName().contains(bookName))
                    .sorted((a, b) -> {
                        return a.getName().compareTo(b.getName());
                    })
                    .collect(Collectors.toList());
            if (books.size() == 0) {
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
        views.OutPut.ShowText("Enter Book Name");
        String bookName = views.Input.GetWord();

        int peekCondition = views.CheckList.Show("What is the Book Condition", this._bookConditions);
        Condition bookCondition = Condition.valueOf(this._bookConditions.get(peekCondition));

        views.OutPut.ShowText("Number of Books");
        int bookUnits = views.Input.GetInt();
        for (int i = 0; i < bookUnits; i++) {
            Book newBook = new Book(bookName, bookCondition);
            this._dal.SaveBook(newBook);
        }

        views.OutPut.ShowText("New book: " + bookName + " Added " + bookUnits + " times.");
    }

    void ShowByName() throws BackToHomeException {
        List<Book> books = this.FilterBooks();
        views.OutPut.ShowText(String.format("%-37s|%-20s|%-10s", "Id", "Name", "Condition"));
        views.OutPut.ShowText(String.format("===================================================================="));
        books.forEach((book) -> {
            views.OutPut.ShowText(String.format("%-37s|%-20s|%-10s",
                    book.getId(),
                    book.getName(),
                    book.getCondition()
            ));
        });
        views.OutPut.ShowText(String.format("===================================================================="));
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
}
