/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ritzhaki
 */
public class CheckList {

    private static String getLine() throws IOException {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public static String Show(String title, HashMap<String, String> options) throws IOException {
        String input;
        while (true) {
            List.ShowList(title, options);
            input = CheckList.getLine();
            if (options.containsKey(input)) {
                break;
            }
            System.out.println(input + " is not a vaild input");
        }
        return input;
    }
}
