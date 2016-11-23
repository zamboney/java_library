/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import modals.Book;

/**
 *
 * @author ritzhaki
 */
public class BookReaderController {

    BaseDal _dal;

    BookReaderController(BaseDal dal) {
        this._dal = dal;
    }

    void Rent() {
        String bookName = views.Input.getWord("Enter Book Name");
        HashMap<String,String> books = new HashMap<String,String>(){};
        this._dal.GetBooks()
                .stream()
                .filter((book)-> book.getName().contains(bookName))
                .collect(Collectors.toList());
        
        
        
        
        
    }

    void Add() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
