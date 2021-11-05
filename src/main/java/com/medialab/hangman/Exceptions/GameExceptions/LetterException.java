package com.medialab.hangman.Exceptions.GameExceptions;

public class LetterException extends ChoiceException {
    public LetterException (){
        super("Chosen letter is not present in position's letter subset");
    }
}







