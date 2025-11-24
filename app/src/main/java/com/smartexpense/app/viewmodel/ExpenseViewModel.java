package com.smartexpense.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.smartexpense.app.model.CategoryTotal;
import com.smartexpense.app.model.Expense;
import com.smartexpense.app.repository.ExpenseRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository expenseRepository;
    private ExecutorService executorService;

    private MutableLiveData<List<Expense>> expensesList = new MutableLiveData<>();
    private MutableLiveData<Double> todayTotal = new MutableLiveData<>();
    private MutableLiveData<Double> weekTotal = new MutableLiveData<>();
    private MutableLiveData<Double> monthTotal = new MutableLiveData<>();
    private MutableLiveData<List<CategoryTotal>> categoryTotals = new MutableLiveData<>();

    private MutableLiveData<Boolean> operationSuccess = new MutableLiveData<>();
    private MutableLiveData<String> operationError = new MutableLiveData<>();

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        expenseRepository = new ExpenseRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    // Load all expenses
    public void loadExpenses(int userId) {
        executorService.execute(() -> {
            List<Expense> expenses = expenseRepository.getAllExpenses(userId);
            expensesList.postValue(expenses);
        });
    }

    // Load totals
    public void loadTotals(int userId) {
        executorService.execute(() -> {
            double today = expenseRepository.getTodayTotal(userId);
            double week = expenseRepository.getWeekTotal(userId);
            double month = expenseRepository.getMonthTotal(userId);

            todayTotal.postValue(today);
            weekTotal.postValue(week);
            monthTotal.postValue(month);
        });
    }

    // Load category totals
    public void loadCategoryTotals(int userId) {
        executorService.execute(() -> {
            List<CategoryTotal> totals = expenseRepository.getCategoryTotals(userId);
            categoryTotals.postValue(totals);
        });
    }

    // Add expense
    public void addExpense(Expense expense) {
        executorService.execute(() -> {
            long result = expenseRepository.addExpense(expense);
            if (result > 0) {
                operationSuccess.postValue(true);
            } else {
                operationError.postValue("Failed to add expense");
                operationSuccess.postValue(false);
            }
        });
    }

    // Update expense
    public void updateExpense(Expense expense) {
        executorService.execute(() -> {
            boolean result = expenseRepository.updateExpense(expense);
            if (result) {
                operationSuccess.postValue(true);
            } else {
                operationError.postValue("Failed to update expense");
                operationSuccess.postValue(false);
            }
        });
    }

    // Delete expense
    public void deleteExpense(int expenseId) {
        executorService.execute(() -> {
            boolean result = expenseRepository.deleteExpense(expenseId);
            if (result) {
                operationSuccess.postValue(true);
            } else {
                operationError.postValue("Failed to delete expense");
                operationSuccess.postValue(false);
            }
        });
    }

    // Search expenses
    public void searchExpenses(int userId, String query) {
        executorService.execute(() -> {
            List<Expense> expenses = expenseRepository.searchExpenses(userId, query);
            expensesList.postValue(expenses);
        });
    }

    // Filter by category
    public void filterByCategory(int userId, String category) {
        executorService.execute(() -> {
            List<Expense> expenses = expenseRepository.getExpensesByCategory(userId, category);
            expensesList.postValue(expenses);
        });
    }

    // LiveData getters
    public LiveData<List<Expense>> getExpensesList() {
        return expensesList;
    }

    public LiveData<Double> getTodayTotal() {
        return todayTotal;
    }

    public LiveData<Double> getWeekTotal() {
        return weekTotal;
    }

    public LiveData<Double> getMonthTotal() {
        return monthTotal;
    }

    public LiveData<List<CategoryTotal>> getCategoryTotals() {
        return categoryTotals;
    }

    public LiveData<Boolean> getOperationSuccess() {
        return operationSuccess;
    }

    public LiveData<String> getOperationError() {
        return operationError;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}

