/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.BackToHomeException;
import views.CheckList;
import views.ViewList;

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
                int input = CheckList.Show("Welcome to the Library",
                        new ArrayList<String>() {
                    {
                        add("Book");
                        add("Reader");
                        add("Rent");
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
                        

                }
            } catch (BackToHomeException ex) {
                continue;
            }
        }
    }

    private void Books() throws BackToHomeException, IOException {
        int input = CheckList.Show("Books",
                new ArrayList<String>() {
            {
                add("Add");
                add("Remove");
                add("Show All");
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
        int input = CheckList.Show("Reader",
                new ArrayList<String>() {
            {
                add("Add");
                add("Show All");
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
       int input = CheckList.Show("Rent",
                new ArrayList<String>() {
            {
                add("Rent");
                add("Retren");
                add("Show All");
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
