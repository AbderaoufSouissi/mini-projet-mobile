package com.smartexpense.app.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    // Validate email
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validate password (minimum 6 characters)
    public static boolean isValidPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    // Check if passwords match
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return !TextUtils.isEmpty(password) && password.equals(confirmPassword);
    }

    // Validate amount
    public static boolean isValidAmount(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return false;
        }
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate non-empty field
    public static boolean isNotEmpty(String text) {
        return !TextUtils.isEmpty(text) && !text.trim().isEmpty();
    }
}

