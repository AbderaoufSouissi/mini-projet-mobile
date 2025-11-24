package com.smartexpense.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartexpense.app.model.CategoryTotal;
import com.smartexpense.app.model.Expense;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpenseDao {

    private DatabaseHelper dbHelper;

    public ExpenseDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Create expense
    public long createExpense(Expense expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXPENSE_USER_ID, expense.getUserId());
        values.put(DatabaseHelper.COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COLUMN_EXPENSE_CATEGORY, expense.getCategory());
        values.put(DatabaseHelper.COLUMN_EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(DatabaseHelper.COLUMN_EXPENSE_DATE, expense.getDate());

        long id = db.insert(DatabaseHelper.TABLE_EXPENSES, null, values);
        db.close();

        return id;
    }

    // Get all expenses for a user
    public List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_EXPENSES,
            null,
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ?",
            new String[]{String.valueOf(userId)},
            null, null,
            DatabaseHelper.COLUMN_EXPENSE_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                expenses.add(cursorToExpense(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return expenses;
    }

    // Get expense by ID
    public Expense getExpenseById(int expenseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Expense expense = null;

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_EXPENSES,
            null,
            DatabaseHelper.COLUMN_EXPENSE_ID + " = ?",
            new String[]{String.valueOf(expenseId)},
            null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            expense = cursorToExpense(cursor);
            cursor.close();
        }

        db.close();
        return expense;
    }

    // Update expense
    public boolean updateExpense(Expense expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COLUMN_EXPENSE_CATEGORY, expense.getCategory());
        values.put(DatabaseHelper.COLUMN_EXPENSE_DESCRIPTION, expense.getDescription());
        values.put(DatabaseHelper.COLUMN_EXPENSE_DATE, expense.getDate());

        int rows = db.update(
            DatabaseHelper.TABLE_EXPENSES,
            values,
            DatabaseHelper.COLUMN_EXPENSE_ID + " = ?",
            new String[]{String.valueOf(expense.getId())}
        );

        db.close();
        return rows > 0;
    }

    // Delete expense
    public boolean deleteExpense(int expenseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rows = db.delete(
            DatabaseHelper.TABLE_EXPENSES,
            DatabaseHelper.COLUMN_EXPENSE_ID + " = ?",
            new String[]{String.valueOf(expenseId)}
        );

        db.close();
        return rows > 0;
    }

    // Search expenses by description
    public List<Expense> searchExpenses(int userId, String query) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_EXPENSES,
            null,
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ? AND " +
            DatabaseHelper.COLUMN_EXPENSE_DESCRIPTION + " LIKE ?",
            new String[]{String.valueOf(userId), "%" + query + "%"},
            null, null,
            DatabaseHelper.COLUMN_EXPENSE_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                expenses.add(cursorToExpense(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return expenses;
    }

    // Filter by category
    public List<Expense> getExpensesByCategory(int userId, String category) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_EXPENSES,
            null,
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ? AND " +
            DatabaseHelper.COLUMN_EXPENSE_CATEGORY + " = ?",
            new String[]{String.valueOf(userId), category},
            null, null,
            DatabaseHelper.COLUMN_EXPENSE_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                expenses.add(cursorToExpense(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return expenses;
    }

    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(int userId, long startDate, long endDate) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
            DatabaseHelper.TABLE_EXPENSES,
            null,
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ? AND " +
            DatabaseHelper.COLUMN_EXPENSE_DATE + " BETWEEN ? AND ?",
            new String[]{String.valueOf(userId), String.valueOf(startDate), String.valueOf(endDate)},
            null, null,
            DatabaseHelper.COLUMN_EXPENSE_DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                expenses.add(cursorToExpense(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return expenses;
    }

    // Get total for today
    public double getTodayTotal(int userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfDay = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long endOfDay = calendar.getTimeInMillis();

        return getTotalByDateRange(userId, startOfDay, endOfDay);
    }

    // Get total for this week
    public double getWeekTotal(int userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfWeek = calendar.getTimeInMillis();

        long now = System.currentTimeMillis();

        return getTotalByDateRange(userId, startOfWeek, now);
    }

    // Get total for this month
    public double getMonthTotal(int userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfMonth = calendar.getTimeInMillis();

        long now = System.currentTimeMillis();

        return getTotalByDateRange(userId, startOfMonth, now);
    }

    // Get total by date range
    private double getTotalByDateRange(int userId, long startDate, long endDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double total = 0;

        Cursor cursor = db.rawQuery(
            "SELECT SUM(" + DatabaseHelper.COLUMN_EXPENSE_AMOUNT + ") as total FROM " +
            DatabaseHelper.TABLE_EXPENSES + " WHERE " +
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ? AND " +
            DatabaseHelper.COLUMN_EXPENSE_DATE + " BETWEEN ? AND ?",
            new String[]{String.valueOf(userId), String.valueOf(startDate), String.valueOf(endDate)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
            cursor.close();
        }

        db.close();
        return total;
    }

    // Get category totals
    public List<CategoryTotal> getCategoryTotals(int userId) {
        List<CategoryTotal> categoryTotals = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
            "SELECT " + DatabaseHelper.COLUMN_EXPENSE_CATEGORY + ", " +
            "SUM(" + DatabaseHelper.COLUMN_EXPENSE_AMOUNT + ") as total FROM " +
            DatabaseHelper.TABLE_EXPENSES + " WHERE " +
            DatabaseHelper.COLUMN_EXPENSE_USER_ID + " = ? GROUP BY " +
            DatabaseHelper.COLUMN_EXPENSE_CATEGORY,
            new String[]{String.valueOf(userId)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_CATEGORY));
                double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
                categoryTotals.add(new CategoryTotal(category, total));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return categoryTotals;
    }

    // Helper method to convert cursor to Expense object
    private Expense cursorToExpense(Cursor cursor) {
        return new Expense(
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_ID)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_USER_ID)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_AMOUNT)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_CATEGORY)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_DESCRIPTION)),
            cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_DATE))
        );
    }
}

