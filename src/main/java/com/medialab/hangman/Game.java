package com.medialab.hangman;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    Dictionary dictionary;
    String chosen_word;
    int word_size;
    ArrayList<String> candidates;
    ArrayList< HashMap< Character, ArrayList<String> > > position_maps;

    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.chosen_word = dictionary.pickWord();
        this.word_size = this.chosen_word.length();
        this.candidates = new ArrayList<String>(dictionary.getWords());
        candidates.removeIf(e -> e.length() != word_size);



        ArrayList< HashMap< Character, ArrayList<String> > > position_maps = new ArrayList< HashMap< Character, ArrayList<String> > >();

        for (int pos = 0; pos < word_size; pos++) {

            HashMap<Character, ArrayList<String>> position_map = new HashMap<Character, ArrayList<String>>();
            System.out.println("CHANGING index " + pos);

            for (String word : candidates) {

                Character c = word.charAt(pos);
                // System.out.println(c);

                if (!position_map.containsKey(c)) { // if list doesnt exist at character c then create it and push first
                                                    // word
                    ArrayList<String> tmp = new ArrayList<String>();
                    tmp.add(word);
                    position_map.put(c, tmp);
                } else { // Else just push new word to list
                    ArrayList<String> tmp = position_map.get(c);
                    tmp.add(word);
                }
            }
            
            position_maps.add(position_map);
        }

        // for (HashMap<Character, ArrayList<String>> hm : position_maps ) {
        //     for (Character c : hm.keySet()) {
        //         ArrayList<String> tmp = hm.get(c);
        //         System.out.println("Character " + c + " " + tmp.toString());
        //     }
        // }

    }

    public void findCandidates() {
    }

}
