/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author ritzhaki
 */
public class CheckList {

    public static int Show(String title, List<String> options) throws BackToHomeException {
        int input = 0;
        while (true) {
            ViewList.ShowList(title, options);
            String str = Input.GetWord();
            try {
                input = Integer.parseInt(str);
                if (input != 0 && input <= options.size()) {
                    break;
                }
            } catch (NumberFormatException e) {
            }

            OutPut.ShowText(str + " is not a vaild input");
        }
        return input - 1;
    }
}
