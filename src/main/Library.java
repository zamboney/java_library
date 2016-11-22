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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modals.Condition;

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
        //fsd.SaveBook(new Book("book a", Condition.Good));
        fsd.GetBooks().forEach((b) -> {
            System.out.print(b);
        });

//        System.out.println(
//                "Hello and welcome to the Library\n"
//                + "[1] rent a book\n"
//                + "[2] return a book\n"
//                + "[3] add new reader");
//        char input;
//        input = '0';
//        while ((input = getInput()) != '0') {
//            System.out.print(input);
//            System.out.println(" is not a vaild input");
//        }
    }

    public static char getInput() throws IOException {
        char input = (char) System.in.read();
        Matcher m = Pattern.compile(Character.toString(input)).matcher("/^(1|2|3|)$");
        return m.matches() ? input : '0';
    }

    private static void getData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
