package com.texnoera.socialmedia.utils;

import com.texnoera.socialmedia.model.constants.ApiConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordUtils {
    private static final Random RND = new Random();

    public static boolean isNotValidPassword(String password) {
        if (password == null || password.isEmpty() || password.trim().isEmpty()) {
            return true;
        }
        String trim = password.trim();
        if (trim.length() < ApiConstants.REQUIRED_MIN_PASSWORD_LENGTH) {
            return true;
        }
        int charactersNumber = ApiConstants.REQUIRED_MIN_CHARACTERS_NUMBER_IN_PASSWORD;
        int lettersUCaseNumber = ApiConstants.REQUIRED_MIN_LETTERS_NUMBER_EVERY_CASE_IN_PASSWORD;
        int lettersLCaseNumber = ApiConstants.REQUIRED_MIN_LETTERS_NUMBER_EVERY_CASE_IN_PASSWORD;
        int digitsNumber = ApiConstants.REQUIRED_MIN_DIGITS_NUMBER_IN_PASSWORD;
        for (int i = 0; i < trim.length(); i++) {
            String currentLetter = String.valueOf(trim.charAt(i));
            if (!ApiConstants.PASSWORD_ALL_CHARACTERS.contains(currentLetter)) {
                return true;
            }
            charactersNumber -= ApiConstants.PASSWORD_CHARACTERS.contains(currentLetter) ? 1 : 0;
            lettersUCaseNumber -= ApiConstants.PASSWORD_LETTERS_UPPER_CASE.contains(currentLetter) ? 1 : 0;
            lettersLCaseNumber -= ApiConstants.PASSWORD_LETTERS_LOWER_CASE.contains(currentLetter) ? 1 : 0;
            digitsNumber -= ApiConstants.PASSWORD_DIGITS.contains(currentLetter) ? 1 : 0;
        }
        return ((charactersNumber > 0) || (lettersUCaseNumber > 0) || (lettersLCaseNumber > 0) || (digitsNumber > 0));
    }
}