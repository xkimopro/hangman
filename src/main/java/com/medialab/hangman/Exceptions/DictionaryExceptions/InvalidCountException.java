package com.medialab.hangman.Exceptions.DictionaryExceptions;

public class InvalidCountException extends DictionaryException {
    public InvalidCountException ()  {
        super("Dictionaries have unique words");
    }
} 