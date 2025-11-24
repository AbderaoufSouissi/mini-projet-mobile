package com.smartexpense.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartexpense.app.model.User;

public class UserDao {

    private DatabaseHelper dbHelper;

    public UserDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Create user
    public long createUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, user.getPassword());
        values.put(DatabaseHelper.COLUMN_USER_SECURITY_QUESTION, user.getSecurityQuestion());
        values.put(DatabaseHelper.COLUMN_USER_SECURITY_ANSWER, user.getSecurityAnswer());

        long id = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();

        return id;
    }

    // Get user by email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            DatabaseHelper.COLUMN_USER_EMAIL + " = ?",
            new String[]{email},
            null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }

        db.close();
        return user;
    }

    // Get user by ID
    public User getUserById(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            DatabaseHelper.COLUMN_USER_ID + " = ?",
            new String[]{String.valueOf(userId)},
            null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }

        db.close();
        return user;
    }

    // Check if email exists
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            new String[]{DatabaseHelper.COLUMN_USER_ID},
            DatabaseHelper.COLUMN_USER_EMAIL + " = ?",
            new String[]{email},
            null, null, null
        );

        boolean exists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return exists;
    }

    // Validate login
    public User validateLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            null,
            DatabaseHelper.COLUMN_USER_EMAIL + " = ? AND " +
            DatabaseHelper.COLUMN_USER_PASSWORD + " = ?",
            new String[]{email, password},
            null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user = cursorToUser(cursor);
            cursor.close();
        }

        db.close();
        return user;
    }

    // Validate security answer
    public boolean validateSecurityAnswer(String email, String answer) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            new String[]{DatabaseHelper.COLUMN_USER_ID},
            DatabaseHelper.COLUMN_USER_EMAIL + " = ? AND " +
            DatabaseHelper.COLUMN_USER_SECURITY_ANSWER + " = ?",
            new String[]{email, answer},
            null, null, null
        );

        boolean valid = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return valid;
    }

    // Update password
    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, newPassword);

        int rows = db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            DatabaseHelper.COLUMN_USER_EMAIL + " = ?",
            new String[]{email}
        );

        db.close();
        return rows > 0;
    }

    // Helper method to convert cursor to User object
    private User cursorToUser(Cursor cursor) {
        return new User(
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_USERNAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_EMAIL)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_PASSWORD)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_SECURITY_QUESTION)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_SECURITY_ANSWER))
        );
    }
}

