package com.medialab.hangman;

import com.medialab.hangman.Messages.LoadDictionaryOp;
import com.medialab.hangman.Messages.NewDictionaryOp;

import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;

import java.util.ArrayList;

public class FileIO {

    static String dictionaries_path = "./src/main/java/com/medialab/hangman/Dictionaries/";

    public static NewDictionaryOp createDictionaryFile(String dict_id, String open_library_id) {

        int error_code = 0;
        String filename = "hangman_" + dict_id + ".txt";
        String msg = "File " + filename + " created successfully";
        Dictionary d = new Dictionary();

        NewDictionaryOp ndop = new NewDictionaryOp(error_code, msg, d);

        try {
            d.createFromOpenLibraryId(open_library_id);
            ArrayList<String> words = d.getWords();

            FileWriter myWriter = new FileWriter(dictionaries_path + filename);
            for (String w : words) {
                myWriter.write(w + "\n");
            }

            myWriter.close();

        } catch (Exception e) {
            ndop.setStatusAndMsg(1, e.getMessage() , null);
        }
        return ndop;
    }

    public static LoadDictionaryOp loadNewDictionaryFile(String dict_id) {

        int error_code = 0;
        String filename = "hangman_" + dict_id + ".txt";
        String msg = "File " + filename + " loaded successfully";
        Dictionary d = new Dictionary();

        LoadDictionaryOp ldop = new LoadDictionaryOp(error_code, msg, d);
        File dict_file = new File(dictionaries_path + filename);
        Scanner dict_reader = null;
        try {
            dict_reader = new Scanner(dict_file); // throws FileNotFoundException
            ArrayList<String> words = new ArrayList<String>();
            while (dict_reader.hasNextLine()) {
                String w = dict_reader.nextLine();
                words.add(w);
            }
            d.loadFromWordList(words);
        } catch (Exception e) {
            ldop.setStatusAndMsg(1, e.getMessage(), null);
        }
        dict_reader.close();

        return ldop;

        // try {
        // d.createFromOpenLibraryId(open_library_id);
        // ArrayList<String> words = d.getWords();

        // FileWriter myWriter = new FileWriter(dictionaries_path + filename);
        // for (String w : words) {
        // myWriter.write(w + "\n");
        // }

        // myWriter.close();

        // } catch (Exception e) {
        // ldop.setStatusAndMsg(1, e.getMessage());
        // }
        // return ldop;
    }
}
