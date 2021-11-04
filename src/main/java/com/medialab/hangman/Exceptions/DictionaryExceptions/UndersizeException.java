package com.medialab.hangman.Exceptions.DictionaryExceptions;

public class UndersizeException extends DictionaryException {
    public UndersizeException ()  {
        super("Dictionaries must have at least 20 words");
    }
} 
