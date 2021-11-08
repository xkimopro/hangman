package com.medialab.hangman;

import com.medialab.hangman.Messages.LoadDictionaryOp;
import com.medialab.hangman.Messages.LoadStatsOp;
import com.medialab.hangman.Messages.NewDictionaryOp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;
import java.io.File;

import java.util.ArrayList;

public class FileIO {

    static String dictionaries_path = "./src/main/java/com/medialab/hangman/Dictionaries/";
    static String statsFile = "./src/main/java/com/medialab/hangman/Utilities/stats.txt";


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
            dict_reader.close();
        } catch (Exception e) {
            ldop.setStatusAndMsg(1, e.getMessage(), null);
        }

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

    public static void appendToStatsFile(String newLine) throws Exception{

        try {
            Writer output;
            output = new BufferedWriter(new FileWriter(statsFile, true));
            output.append(newLine + "\n");
            output.close();
        }
        catch (Exception e){
            throw e;
        }

    }

    public static LoadStatsOp readStatsFile(){

        int error_code = 0;
        String msg = "Stats File read and parsed successfully";
        ArrayList<String> rows = new ArrayList<String>();

        LoadStatsOp lsop = new LoadStatsOp(error_code, msg, rows);

        File stats_file = new File(statsFile);
        Scanner stats_reader = null;
        try {
            stats_reader = new Scanner(stats_file); // throws FileNotFoundException
            while (stats_reader.hasNextLine()) {
                String line = stats_reader.nextLine();
                rows.add(line);
            }

        } catch (Exception e) {
            lsop.setStatusAndMsg(1, e.getMessage(), null);
        }
        stats_reader.close();
        return lsop;

    }



}
