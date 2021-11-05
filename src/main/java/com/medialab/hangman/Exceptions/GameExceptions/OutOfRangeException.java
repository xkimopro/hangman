package com.medialab.hangman.Exceptions.GameExceptions;

public class OutOfRangeException extends ChoiceException {
    public OutOfRangeException (){
        super("Chosen letter position is outside of allowed range");
    }
}







