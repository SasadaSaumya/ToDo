package com.sasadev.todo.utils;

public class InputValidator {

    public static boolean isValidUsername(String username) {
        return username.length() >= 3;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    public static boolean isMobileValid(String mobile) {
        return mobile != null && mobile.length() == 10 && mobile.matches("^0(70|71|72|74|75|76|77|78|79)\\d{7}$");
    }
}
