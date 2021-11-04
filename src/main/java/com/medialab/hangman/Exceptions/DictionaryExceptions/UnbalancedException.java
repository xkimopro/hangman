package com.medialab.hangman.Exceptions.DictionaryExceptions;

public class UnbalancedException extends DictionaryException {
    public UnbalancedException ()  {
        super("Dictionaries must have at least 20% of their words with 9 or more letters");
    }
} 