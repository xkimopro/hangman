package com.medialab.hangman.Exceptions.GameExceptions;

public class LetterAlreadyFound extends ChoiceException {
    public LetterAlreadyFound (){
        super("Chosen letter is already found for position");
    }
}