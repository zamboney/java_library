/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dal.FileSystemDal;
import modals.Rent;
import modals.BookReader;
import modals.Book;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modals.Condition;
import views.*;

/**
 *
 * @author ritzhaki
 */
public class Library {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        FileSystemDal fsd = new FileSystemDal();
        new controllers.LibraryInit(fsd).mainPage();
    }

}
