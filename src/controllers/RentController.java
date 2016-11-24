/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import modals.Book;
import modals.BookReader;
import modals.Rent;
import views.BackToHomeException;

/**
 *
 * @author ritzhaki
 */
public class RentController extends BaseController {

    RentController(BaseDal _dal) {
        super(_dal);
    }

    void Add() throws BackToHomeException {
        Book book = new BookController(this._dal).PeekOne();
        BookReader reader = new BookReaderController(this._dal).PeekOne();
        Date toDo = new Date();
        toDo.setTime((long) (toDo.getTime() + 1.15741e-8 * book.getDateToRent()));
        Rent rent = new Rent(reader, book, toDo);
        book.setRentId(rent.toString());
        this._dal.UpDateBook(book);
        this._dal.SaveRent(rent);
    }

    void ReturnBook() throws BackToHomeException {
        Rent rent = this.GetByBookUUID();
        Book book = this._dal.GetBookById(rent.getBookId());
        book.setCondition(new BookController(this._dal).peekACondition());
        book.setRentId(rent.toString());
        this._dal.UpDateBook(book);
        this._dal.UpDateRent(rent);

    }

    void ShowAll() throws BackToHomeException {
        views.OutPut.ShowText("Enter Reader Name");
        String name = views.Input.GetWord();
        List<List<String>> rows = this._dal.GetRents()
                .stream()
                .filter((rent)->this._dal.GetBookReaderById(rent.getReaderId()).getName().contains(name))
                .map((rent) -> new ArrayList<String>() {
            {
                add(_dal.GetBookById(rent.getBookId()).getId().toString());
                add(_dal.GetBookById(rent.getBookId()).getName());
                add(_dal.GetBookById(rent.getBookId()).getCondition().toString());
                add(_dal.GetBookReaderById(rent.getReaderId()).getName());
            }
        }).collect(Collectors.toList());

        List<String> headers = new ArrayList<String>() {
            {
                add("Book Id");
                add("Book");
                add("Condition");
                add("Reader");

            }
        };
        views.OutPut.ShowTable("%-37s|%-20s|%-10s|%-20s", headers, rows);

    }

    private Rent GetByBookUUID() throws BackToHomeException {
        Book book = new BookController(_dal).PeekOneById();
        try {
            return this._dal.GetRents().stream()
                    .filter((r) -> r.getEnd() == null && _dal.GetBookById(r.getBookId()).getId().equals(book.getId()))
                    .findFirst().get();
        } catch (java.util.NoSuchElementException exc) {
            views.OutPut.ShowText("this book isn't rented");
            throw new views.BackToHomeException();
        }

    }

}
