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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import modals.Book;
import modals.BookReader;
import modals.Rent;
import views.CheckList;
import views.*;

/**
 *
 * @author ritzhaki
 */
public class BookReaderController extends BaseController {

    BookReaderController(BaseDal dal) {
        super(dal);
    }



    public void Add() throws IOException, BackToHomeException {
        views.OutPut.ShowText("Enter Reader Name");
        String name = views.Input.GetWord();
        views.OutPut.ShowText("Enter Reader Email");
        String email = views.Input.GetWord();
        BookReader br = new BookReader(name, email);
        this._dal.SaveBookReader(br);
        views.OutPut.ShowText("New Book Reader Added :" + br.toString());

    }

    public BookReader PeekOne() throws BackToHomeException {
        while (true) {
            OutPut.ShowText("Enter Reader name");
            String name = Input.GetWord();
            List<BookReader> bookreaders = this._dal.GetBookReader()
                    .stream()
                    .filter((br) -> br.getName().contains(name))
                    .sorted((a, b) -> {
                        return a.getName().compareTo(b.getName());
                    })
                    .collect(Collectors.toList());
            if (bookreaders.isEmpty()) {
                OutPut.ShowText(String.format("\"%s\" isn't found", name));
                continue;
            }
            BookReader br = bookreaders.get(CheckList.Show("Result for: " + name,
                    bookreaders.stream().map((book) -> book.getName()).collect(Collectors.toList())));
            if (br.CanRent()) {
                return br;
            } else {
                OutPut.ShowText(br.getName() + " can't rent");
                
            }
        }
    }

}
