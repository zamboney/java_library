/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
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

    /**
     * create new Rent ticket
     *
     * 1) Peak a book. 2) peak a reader. 3) Create toDo Date and add the book
     * date to return. 4) Set the book Rent id 5) Add the rent ticket to the
     * Reader 6) Update the Book and the BookReader entities and create the new
     * RentTicket
     *
     * @throws BackToHomeException
     */
    void Add() throws BackToHomeException {
        Book book = new BookController(this._dal).PeekOne();
        BookReader reader = new BookReaderController(this._dal).PeekOne();
        if (!reader.CanRent()) {
            views.OutPut.ShowText(reader.getName() + " Can't Rent a Book;");
            throw new BackToHomeException();
        }
        Date toDo = new Date();
        toDo.setTime((long) (toDo.getTime() + 1.15741e-8 * book.getDateToRent()));
        Rent rent = new Rent(reader, book, toDo);
        book.setRentId(rent.getId());
        reader.AddRentId(rent.getId());
        this._dal.UpDateBook(book);
        this._dal.SaveRent(rent);
        this._dal.UpDateBookReader(reader);
    }

    /**
     * Return a book that been rented
     *
     * 1) get the book id which been rented 2) get the return condition of the
     * book. 3) set the rent id of the book to null (the book now can be rented
     * again). 4) Up date the rent end to now. 5) Update the book and rents on
     * the DAL layer.
     *
     * @throws BackToHomeException
     */
    void ReturnBook() throws BackToHomeException {
        views.OutPut.ShowText("Enter Book Id:");
        Rent rent;
        try {
            UUID id = views.Input.GetUUID();
            rent = this._dal.GetRents().stream().filter((r) -> r.getBookId().equals(id)).findFirst().get();
        } catch (NoSuchElementException e) {
            views.OutPut.ShowText("Book UUID didn't found");
            throw new BackToHomeException();
        }

        Book book = this._dal.GetBookById(rent.getBookId());
        book.setCondition(new BookController(this._dal).peekACondition());
        book.setRentId(null);
        rent.setEnd(new Date());
        this._dal.UpDateBook(book);
        this._dal.UpDateRent(rent);

    }

    /**
     * Show all of the rents base on reader ID or Name
     *
     * @throws BackToHomeException
     */
    void ShowAll() throws BackToHomeException {
        int res = views.Input.peekFromList("Search by", new ArrayList() {
            {
                add("ID");
                add("Name");
            }
        });
        List<Rent> rows = this._dal.GetRents();
        switch (res) {
            case 0:
                views.OutPut.ShowText("Enter Reader UUID");
                UUID id = views.Input.GetUUID();
                rows = rows.stream().filter((r) -> r.getId().equals(id)).collect(Collectors.toList());
                break;
            case 1:
                views.OutPut.ShowText("Enter Reader Name");
                String name = views.Input.GetWord();
                rows = rows.stream()
                        .filter((r)
                                -> this._dal.GetBookReaderById(r.getReaderId()).getName().contains(name)
                        ).collect(Collectors.toList());
                break;
        }
        this.showRentList(rows);
    }

    /**
     * show list of all of the rents.
     *
     * @param rents rows.
     */
    private void showRentList(List<Rent> rents) {
        List<List<String>> rows
                = rents
                        .stream()
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
}
