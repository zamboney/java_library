/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        this._dal.SaveRent(new Rent(reader, book, toDo));
    }

    void ShowAll() {
        views.OutPut.ShowText(String.format("%-20s|%-20s|%-10s", "Reader" ,"Book","Return Date"));
        views.OutPut.ShowText(String.format("====================================================="));
        this._dal.GetRents().forEach((rent)->{
            views.OutPut.ShowText(String.format("%-20s|%-20s|%-10s", 
                    rent.getBook().getName(),
                    rent.getReader().getName(),
                    new SimpleDateFormat("yyyy-MM-dd").format(rent.getDoto().getTime())));
        });
    }

}
