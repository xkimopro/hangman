package com.medialab.hangman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.medialab.hangman.Exceptions.GameExceptions.*;

class CharacterFrequencyComparator implements Comparator<Character> {
    Map<Character, ArrayList<String>> base;
    public CharacterFrequencyComparator(Map<Character, ArrayList<String>> base) {
        this.base = base;
    }
    public int compare(Character a, Character b) {
        if (base.get(a).size() >= base.get(b).size()) {
            return -1;
        } else {
            return 1;
        }
    }
}


public class Game {
    Dictionary dictionary;
    int score, wrong_choices, word_size, wrong_choices_limit, choices;
    String chosen_word;
    ArrayList<String> candidates;
    ArrayList<LinkedHashMap<Character, ArrayList<String>>> position_maps;
    ArrayList<Character> current_word;


    public int getChoices(){
        return this.choices;
    }
    public int getWrongChoices(){
        return this.wrong_choices;
    }
    public int getScore(){
        return this.score;
    }
    public ArrayList<Character> getCurrent_word(){
        return new ArrayList<Character>(current_word);
    }
    public int getWordsRemaining(){
        return candidates.size();
    }
    public String getChosenWord() { return chosen_word; }
    public Dictionary getDictionary() { return dictionary; }
    public LinkedHashMap<Character, ArrayList<String>> getPositionMaps(int index){
        return position_maps.get(index);
    }



    public void createPositionMaps() {
        position_maps = new ArrayList<LinkedHashMap<Character, ArrayList<String>>>();
        ArrayList<TreeMap<Character, ArrayList<String>>> tree_position_maps = new ArrayList<TreeMap<Character, ArrayList<String>>>();
        for (int pos = 0; pos < word_size; pos++) {
            HashMap<Character, ArrayList<String>> position_map = new HashMap<Character, ArrayList<String>>();
            CharacterFrequencyComparator cfc = new CharacterFrequencyComparator(position_map);
            TreeMap<Character, ArrayList<String>> position_map_sorted = new TreeMap<Character, ArrayList<String>>(cfc);
            for (String word : candidates) {
                Character c = word.charAt(pos);
                if (!position_map.containsKey(c)) { // if list doesnt exist at character c then create it and push word
                    ArrayList<String> tmp = new ArrayList<String>();
                    tmp.add(word);
                    position_map.put(c, tmp);
                } else { // Else just push new word to list
                    ArrayList<String> tmp = position_map.get(c);
                    tmp.add(word);
                }
            }
            position_map_sorted.putAll(position_map);
            tree_position_maps.add(position_map_sorted);
        }

        for (TreeMap<Character, ArrayList<String>> hm : tree_position_maps) {
            LinkedHashMap<Character, ArrayList<String>> lhm = new LinkedHashMap<Character, ArrayList<String>>(hm);
            position_maps.add(lhm);
        }
    }

    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.chosen_word = dictionary.pickWord();
        this.word_size = this.chosen_word.length();
        this.candidates = new ArrayList<String>(dictionary.getWords());
        this.score = 0;
        this.choices = 0;
        this.wrong_choices = 0;
        this.wrong_choices_limit = 6;
        current_word = new ArrayList<Character>();
        for (int i = 0; i < chosen_word.length(); i++)
            current_word.add('_');

        candidates.removeIf(e -> e.length() != word_size);
        createPositionMaps();
    }

    public Boolean pickLetter(int index, Character choice) throws ChoiceException, ArithmeticException {

        try {
            if (index < 0 || index >= word_size)
                throw new OutOfRangeException();
            LinkedHashMap<Character, ArrayList<String>> lhm = position_maps.get(index);
            for (Character letter : lhm.keySet()) {
                if (letter == choice) {
                    Character right_letter = chosen_word.charAt(index);
                    Character previous = current_word.get(index);
                    if (previous != '_')
                        throw new LetterAlreadyFound();

                    if (right_letter == choice) {
                        correctChoice(index, choice);
                        choices+=1;
                        return true;
                    }
                    else {
                        wrongChoice(index, choice);
                        choices+=1;
                        return false;
                    }
                }
            }
            throw new LetterException();
        } catch (Exception e) {
            throw e;
        }

    }

    public double calculatePropability(Character letter, LinkedHashMap<Character, ArrayList<String>> words_per_char) {
        double prop = 0.0;
        int total_words = 0;
        int found_words = 0;
        for (Character c : words_per_char.keySet()) {
            if (c == letter)
                found_words = words_per_char.get(c).size();
            total_words += words_per_char.get(c).size();
        }
        prop = (double) found_words / (double) total_words;
        return prop;
    }

    public int findScore(double propability) throws ArithmeticException {
        if (propability >= 0.6 && propability <= 1)
            return 5;
        else if (propability < 0.6 && propability >= 0.4)
            return 10;
        else if (propability < 0.4 && propability >= 0.25)
            return 15;
        else if (propability < 0.25 && propability >= 0.0)
            return 30;
        else
            throw new ArithmeticException("Incorrectly formatted propability");
    }

    public void correctChoice(int index, Character choice) throws ArithmeticException {
        try {
            LinkedHashMap<Character, ArrayList<String>> lhm = position_maps.get(index);
            double propability = calculatePropability(choice, lhm);
            int points = findScore(propability);
            score += points;
            ArrayList<String> bad_words = new ArrayList<String>();
            for (Character c : lhm.keySet()) {
                if (c != choice) {
                    bad_words.addAll(lhm.get(c));
                }
            }
            candidates.removeIf(e -> bad_words.contains(e));
            createPositionMaps();
            current_word.set(index, choice);

        } catch (ArithmeticException e) {
            throw e;
        }
        System.out.println("Correct choice!");
        System.out.println("Score " + score);

    }

    public void wrongChoice(int index, Character choice) {
        LinkedHashMap<Character, ArrayList<String>> lhm = position_maps.get(index);
        ArrayList<String> bad_words = lhm.get(choice);
        candidates.removeIf(e -> bad_words.contains(e));
        createPositionMaps();
        score -= 15;
        if (score < 0)
            score = 0;

        wrong_choices+=1;
        printSets();
        System.out.println(chosen_word);

        System.out.println("Wrong choice!");
        System.out.println("Score " + score);

    }


    public void printSets() {
        for (int pos = 0; pos < word_size; pos++) {
            System.out.println("Position " + (pos));
            LinkedHashMap<Character, ArrayList<String>> lhm = position_maps.get(pos);
            for (Character c : lhm.keySet()) {
                System.out.println(c + " " + lhm.get(c).toString());

            }

        }
    }
    public int gameStatus(){
        System.out.println(wrong_choices);
        if (wrong_choices == 6) {
            for (int i = 0; i < current_word.size(); i++) {
                if (current_word.get(i) == '_') return -1;
            }
        }
        else {
            for (int i=0; i<current_word.size(); i++) {
                if (current_word.get(i) == '_') return 0;
            }
        }
        return  1;
    }









}
