/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.List;


/**
 *
 * @author ritzhaki
 */
public class ViewList {
    
    public static void ShowList(String title, List<String> options) {
        OutPut.ShowText(title);
        for (int i = 0; i < options.size(); i++) {
            OutPut.ShowText(String.format("[%s] %s",Integer.toString(i + 1), options.get(i)));
        }
    }
}
