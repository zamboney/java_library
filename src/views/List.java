/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.HashMap;

/**
 *
 * @author ritzhaki
 */
public class List {

    public static void ShowList(String title, HashMap<String, String> options) {
        System.out.println(title);
        options.forEach((k, v) -> {
            System.out.println(String.format("[%s] %s", k, v));
        });
    }
}
