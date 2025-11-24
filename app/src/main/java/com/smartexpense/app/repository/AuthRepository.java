package com.smartexpense.app.repository;

import android.content.Context;

import com.smartexpense.app.database.DatabaseHelper;
import com.smartexpense.app.database.UserDao;
import com.smartexpense.app.model.User;

public class AuthRepository {

    private UserDao userDao;

    public AuthRepository(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        userDao = new UserDao(dbHelper);
    }

    // Register new user
    public long registerUser(User user) {
        // Check if email already exists
        if (userDao.isEmailExists(user.getEmail())) {
            return -1; // Email already exists
        }
        return userDao.createUser(user);
    }

    // Login user
    public User loginUser(String email, String password) {
        return userDao.validateLogin(email, password);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    // Validate security answer
    public boolean validateSecurityAnswer(String email, String answer) {
        return userDao.validateSecurityAnswer(email, answer);
    }

    // Reset password
    public boolean resetPassword(String email, String newPassword) {
        return userDao.updatePassword(email, newPassword);
    }
}

