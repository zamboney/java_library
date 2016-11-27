/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.util.ArrayList;
import views.BackToHomeException;

/**
 *
 * @author ritzhaki
 */
public class LibraryInit extends BaseController {

    public LibraryInit(BaseDal dal) {
        super(dal);
    }

    public void mainPage() throws IOException {
        while (true) {
            try {
                int input = views.Input.peekFromList(
                        "\n\n"
                        + "======================\n"
                        + "Welcome to the Library\n"
                        + "======================\n"
                        + "*Press \"H\" and Enter to Go Back Here."
                        + "\n",
                        new ArrayList<String>() {
                    {
                        add("Book - add new book, remove a book, view a list of all of the books");
                        add("Reader - create new reader, view a list of all of the readers");
                        add("Rent - rent a book, retrun a book, view a list of all of the renting of books");
                    }
                });
                switch (input) {
                    case 0:
                        this.Books();
                        break;
                    case 1:
                        this.Reader();
                        break;
                    case 2:
                        this.Rent();
                        break;

                }
            } catch (BackToHomeException ex) {
                continue;
            }
        }
    }

    private void Books() throws BackToHomeException, IOException {
        int input = views.Input.peekFromList("Books",
                new ArrayList<String>() {
            {
                add("Add New Book");
                add("Remove A Book");
                add("Show All Books");
            }
        });
        switch (input) {
            case 0:
                new BookController(this._dal).Add();
                break;
            case 1:
                new BookController(this._dal).Remove();
                break;
            case 2:
                new BookController(this._dal).ShowByName();
                break;
        }
    }

    private void Reader() throws BackToHomeException, IOException {
        int input = views.Input.peekFromList("Reader",
                new ArrayList<String>() {
            {
                add("Add New Reader");
                add("Show All Readers");
            }
        });
        switch (input) {
            case 0:
                new BookReaderController(this._dal).Add();
                break;
            case 1:
                new BookReaderController(this._dal).All();
                break;
        }
    }

    private void Rent() throws BackToHomeException {
        int input = views.Input.peekFromList("Rent",
                new ArrayList<String>() {
            {
                add("Open a Rent Ticket");
                add("Retren a Book");
                add("Show All Rents Tickets");
            }
        });
        switch (input) {
            case 0:
                new RentController(this._dal).Add();
                break;
            case 1:
                new RentController(this._dal).ReturnBook();
                break;
            case 2:
                new RentController(this._dal).ShowAll();
                break;
        }
    }

}
