/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.modals.*;

/**
 *
 * @author ritzhaki
 */
public class Library {

    public static Book[] books;
    public static BookReader[] readers;
    public static Rent[] rents;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        getData();
        System.out.println(
                "Hello and welcome to the Library\n"
                + "[1] rent a book\n"
                + "[2] return a book\n"
                + "[3] add new reader");
        char input;
        input = '0';
        while ((input = getInput()) != '0') {
            System.out.print(input);
            System.out.println(" is not a vaild input");
        }
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
