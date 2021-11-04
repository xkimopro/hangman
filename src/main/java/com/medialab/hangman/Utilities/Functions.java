package com.medialab.hangman.Utilities;

public class Functions {
    public static boolean isStringUpperCase(String str) {

        // convert String to char array
        char[] charArray = str.toCharArray();

        for (int i = 0; i < charArray.length; i++) {

            // if any character is not in upper case, return false
            if (!Character.isUpperCase(charArray[i]))
                return false;
        }

        return true;
    }
}
