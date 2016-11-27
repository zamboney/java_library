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
public class OutPut {

    public static void ShowText(String text) {
        System.out.println(text);
    }

    public static void ShowTable(String headerFormat, List<String> headers, List<List<String>> rows) {
        String str = String.format(headerFormat, headers.toArray());
        OutPut.ShowText(String.format(str, headers));
        OutPut.ShowText(new String(new char[str.length()]).replace("\0", "="));
        rows.forEach((row) -> OutPut.ShowText(String.format(headerFormat, row.toArray())));
        OutPut.ShowText(new String(new char[str.length()]).replace("\0", "="));
        OutPut.ShowText(new String(new char[str.length()]).replace("\0", " "));

    }
    
        public static void ShowList(String title, List<String> options) {
        OutPut.ShowText(title);
        for (int i = 0; i < options.size(); i++) {
            OutPut.ShowText(String.format("[%s] %s",Integer.toString(i + 1), options.get(i)));
        }
    }
        
        

}
