package com.smartexpense.app.repository;

import android.content.Context;

import com.smartexpense.app.database.DatabaseHelper;
import com.smartexpense.app.database.ExpenseDao;
import com.smartexpense.app.model.CategoryTotal;
import com.smartexpense.app.model.Expense;

import java.util.List;

public class ExpenseRepository {

    private ExpenseDao expenseDao;

    public ExpenseRepository(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        expenseDao = new ExpenseDao(dbHelper);
    }

    // Add expense
    public long addExpense(Expense expense) {
        return expenseDao.createExpense(expense);
    }

    // Get all expenses
    public List<Expense> getAllExpenses(int userId) {
        return expenseDao.getAllExpenses(userId);
    }

    // Get expense by ID
    public Expense getExpenseById(int expenseId) {
        return expenseDao.getExpenseById(expenseId);
    }

    // Update expense
    public boolean updateExpense(Expense expense) {
        return expenseDao.updateExpense(expense);
    }

    // Delete expense
    public boolean deleteExpense(int expenseId) {
        return expenseDao.deleteExpense(expenseId);
    }

    // Search expenses
    public List<Expense> searchExpenses(int userId, String query) {
        return expenseDao.searchExpenses(userId, query);
    }

    // Filter by category
    public List<Expense> getExpensesByCategory(int userId, String category) {
        return expenseDao.getExpensesByCategory(userId, category);
    }

    // Get expenses by date range
    public List<Expense> getExpensesByDateRange(int userId, long startDate, long endDate) {
        return expenseDao.getExpensesByDateRange(userId, startDate, endDate);
    }

    // Get today's total
    public double getTodayTotal(int userId) {
        return expenseDao.getTodayTotal(userId);
    }

    // Get week total
    public double getWeekTotal(int userId) {
        return expenseDao.getWeekTotal(userId);
    }

    // Get month total
    public double getMonthTotal(int userId) {
        return expenseDao.getMonthTotal(userId);
    }

    // Get category totals
    public List<CategoryTotal> getCategoryTotals(int userId) {
        return expenseDao.getCategoryTotals(userId);
    }
}

