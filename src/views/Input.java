/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.Scanner;
import java.util.UUID;

/**
 *
 * @author ritzhaki
 */
public class Input {

    public static String GetWord() throws BackToHomeException {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        if (input.equals("Q")) {
            System.exit(0);
        }
        if (input.equals("H")){
            throw new BackToHomeException();
        }
        return input;
    }

    public static int GetInt(String text) throws BackToHomeException {
        while (true) {
            views.OutPut.ShowText(text);
            String input = Input.GetWord();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                OutPut.ShowText(input + " is not a valid number");
            }

        }
    }

    public static UUID GetUUID() throws BackToHomeException {
        while (true) {
            String input = Input.GetWord();
            try {
                return UUID.fromString(input);
            } catch (java.lang.IllegalArgumentException nfe) {
                OutPut.ShowText(input + " is not a valid UUID");
            }
        }
    }
    
    public static String getWordWithText(String text) throws BackToHomeException{
        views.OutPut.ShowText(text);
        return views.Input.GetWord();
    }

}
