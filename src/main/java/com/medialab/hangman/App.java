package com.medialab.hangman;

import com.medialab.hangman.Messages.*;

public class App {

    public static void main(String[] args) {
        // String open_library_id = "OL31390631M";
        LoadDictionaryOp ldop = FileIO.loadNewDictionaryFile("A2");

        int error_code = ldop.getStatus();
        // String msg = ldop.getMsg();
        if (error_code == 0) {
            Dictionary d = ldop.getDict();
            Game g = new Game(d);

            try {
                g.startGame();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {

        }

    }
}
