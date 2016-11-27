/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modals.BookReader;
import views.*;

/**
 *
 * @author ritzhaki
 */
public class BookReaderController extends BaseController {

    BookReaderController(BaseDal dal) {
        super(dal);
    }

    /**
     * Add a new reader
     *
     * @throws BackToHomeException
     */
    public void Add() throws BackToHomeException {
        views.OutPut.ShowText("Enter Reader Name");
        String name = views.Input.GetWord();
        views.OutPut.ShowText("Enter Reader Email");
        String email = views.Input.GetWord();
        BookReader br = new BookReader(name, email);
        this._dal.SaveBookReader(br);
        views.OutPut.ShowText("New Book Reader Added :" + br.toString());

    }

    /**
     * Print a list of all of the Reader
     *
     */
    public void PrintList(List<BookReader> list) {
        views.OutPut.ShowTable("%-37s|%-20s|%-9s",
                new ArrayList<String>() {
            {
                add("Id");
                add("Name");
                add("Can Rent");
            }
        },
                list
                        .stream()
                        .map(item -> new ArrayList<String>() {
                    {
                        add(item.getId().toString());
                        add(item.getName());
                        add(item.CanRent() ? " " : "    *    ");
                    }
                }).collect(Collectors.toList()));
    }

    /**
     * Show a list of reader base on there name.
     *
     * @throws BackToHomeException
     */
    public void All() throws BackToHomeException {
        views.OutPut.ShowText("Enter Reader Name");
        String name = views.Input.GetWord();
        PrintList(this._dal.GetBookReader()
                .stream()
                .filter(item -> item.getName().contains(name)).collect(Collectors.toList()));
    }

    /**
     * peek a reader base on his name.
     *
     * @return the peeked reader.
     * @throws BackToHomeException - if the reader can't rent.
     */
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
            BookReader br = bookreaders.get(views.Input.peekFromList("Result for: " + name,
                    bookreaders.stream().map((book) -> book.getName()).collect(Collectors.toList())));
            if (br.CanRent()) {
                return br;
            } else {
                OutPut.ShowText(br.getName() + " can't rent");

            }
        }
    }

}
