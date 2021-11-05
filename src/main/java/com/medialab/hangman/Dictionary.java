package com.medialab.hangman;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.stream.Collectors;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.medialab.hangman.Exceptions.ApiExceptions.ApiException;
import com.medialab.hangman.Exceptions.DictionaryExceptions.*;
import com.medialab.hangman.Utilities.Functions;

public class Dictionary {

    ArrayList<String> words = new ArrayList<String>();

    public void formatWords() {

        for (int i = 0; i < words.size(); i++) {
            String cap_word = words.get(i).toUpperCase();
            String filt_word = cap_word.replaceAll("[^a-zA-Z0-9]", "");
            words.set(i, filt_word);
        }
        words.removeIf(e -> e.length() < 6);
        List<String> unique_words = words.stream().distinct().collect(Collectors.toList());
        words = new ArrayList<String>(unique_words);

    }

    public void validateWords() throws DictionaryException {

        HashMap<String, Integer> word_count = new HashMap<String, Integer>();

        int nine_lettered = 0;
        int total_words = words.size();

        if (total_words < 20)
            throw new UndersizeException();

        for (String w : words)
            word_count.put(w, 0);
        for (String w : words) {
            if (!Functions.isStringUpperCase(w))
                throw new WordLetterException();
            if (w.length() < 6)
                throw new InvalidRangeException();
            if (w.length() >= 9)
                nine_lettered += 1;
            word_count.put(w, word_count.get(w) + 1);

        }
        for (String i : word_count.keySet()) {
            if (word_count.get(i) != 1)
                throw new InvalidCountException();
        }

        double nine_lettered_ratio = (double) nine_lettered / (double) total_words;

        if (nine_lettered_ratio < 0.2)
            throw new UnbalancedException();

    }

    public void createFromOpenLibraryId(String open_library_id)
            throws DictionaryException, ApiException, UnirestException {
        try {
            ApiConsumer.fetchData(open_library_id);
            String description = ApiConsumer.getDescription();
            words = new ArrayList<String>(Arrays.asList(description.split("\\s+")));
            formatWords();
            validateWords(); // Throws dictionary exception
        } catch (Exception e) {
            throw e;
        }

    }

    public void loadFromWordList(ArrayList<String> word_list) throws DictionaryException {

        try {
            words = new ArrayList<String>(word_list);
            validateWords();
        } catch (Exception e) {
            throw e;
        }

    }

    public String pickWord() {
        int min = 0;
        int max = words.size()-1;
        int random_index = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return words.get(random_index);

    }

    public ArrayList<String> getWords() {
        return this.words;
    }
}
