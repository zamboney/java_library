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
                                add("Rent A Book");
                                add("Add New Reader");
                                add("Add New Book");
                                add("Delete a Book");
                                add("Show All Rents");
                                add("Show All Book");
                            }
                        });
                switch (input) {
                    case 0:
                        new RentController(this._dal).Add();
                        break;
                    case 1:
                        new BookReaderController(this._dal).Add();
                        break;
                    case 2:
                        new BookController(this._dal).Add();
                        break;
                    case 3:
                        new BookController(this._dal).Remove();
                        break;
                    case 4:
                        new RentController(this._dal).ShowAll();
                        break;
                    case 5:
                        new BookController(this._dal).ShowByName();
                        break;
                        
                }
            } catch (BackToHomeException ex) {
               continue;
            }
        }
    }

}
