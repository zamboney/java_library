/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dal.BaseDal;
import java.io.IOException;
import java.util.HashMap;
import views.CheckList;
import views.List;

/**
 *
 * @author ritzhaki
 */
public class LibraryInit {

    private final int NUMBER_OF_DAY_LIMIT = 20;
    private final BaseDal _dal;
    private final BookController _bookController;
    private final BookReaderController _bookReaderController;

    public LibraryInit(BaseDal dal) {
        this._dal = dal;
        this._bookController = new BookController(this._dal);
        this._bookReaderController = new BookReaderController(this._dal);
    }

    public void mainPage() throws IOException {
        while (true) {
            String input = CheckList.Show("Welcome to the Library",
                    new HashMap<String, String>() {
                {
                    put("1", "Rent A Book");
                    put("2", "Add New Reader");
                    put("3", "Add New Book");
                    put("4", "Show Setting");
                    put("Q", "Press \"Q\" to Quit");
                }
            });
            switch (input) {
                case "Q":
                    return;
                case "1":
                    this._bookReaderController.Rent();
                    break;
                case "2":
                    this._bookReaderController.Add();
                    break;
                case "3":
                    this._bookController.Add();
                    break;
                case "4":
                    this.showSetting();
                    break;

            }
        }
    }

    private void showSetting() {
        List.ShowList("Setting:", new HashMap<String, String>() {
            {
                put("Number Of Delay Day's Allow ", Integer.toString(NUMBER_OF_DAY_LIMIT));
            }
        });
    }

}
