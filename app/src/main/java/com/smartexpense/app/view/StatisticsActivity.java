package com.smartexpense.app.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.smartexpense.app.R;
import com.smartexpense.app.model.CategoryTotal;
import com.smartexpense.app.model.Expense;
import com.smartexpense.app.utils.SessionManager;
import com.smartexpense.app.viewmodel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    private PieChart categoryPieChart;
    private BarChart spendingBarChart;

    private ExpenseViewModel expenseViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();
        initViewModel();
        loadData();
    }

    private void initViews() {
        categoryPieChart = findViewById(R.id.categoryPieChart);
        spendingBarChart = findViewById(R.id.spendingBarChart);

        sessionManager = new SessionManager(this);
    }

    private void initViewModel() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }

    private void loadData() {
        int userId = sessionManager.getUserId();

        expenseViewModel.loadCategoryTotals(userId);
        expenseViewModel.loadExpenses(userId);

        observeViewModel();
    }

    private void observeViewModel() {
        expenseViewModel.getCategoryTotals().observe(this, categoryTotals -> {
            if (categoryTotals != null && !categoryTotals.isEmpty()) {
                setupCategoryPieChart(categoryTotals);
            }
        });

        expenseViewModel.getExpensesList().observe(this, expenses -> {
            if (expenses != null && !expenses.isEmpty()) {
                setupSpendingBarChart(expenses);
            }
        });
    }

    private void setupCategoryPieChart(List<CategoryTotal> categoryTotals) {
        List<PieEntry> entries = new ArrayList<>();

        for (CategoryTotal categoryTotal : categoryTotals) {
            entries.add(new PieEntry((float) categoryTotal.getTotal(), categoryTotal.getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        int[] colors = {
            Color.parseColor("#FF6384"),
            Color.parseColor("#36A2EB"),
            Color.parseColor("#FFCE56"),
            Color.parseColor("#4BC0C0"),
            Color.parseColor("#9966FF")
        };
        dataSet.setColors(colors);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);

        categoryPieChart.setData(data);
        categoryPieChart.getDescription().setEnabled(false);
        categoryPieChart.setDrawHoleEnabled(true);
        categoryPieChart.setHoleColor(Color.WHITE);
        categoryPieChart.setTransparentCircleRadius(58f);
        categoryPieChart.setEntryLabelColor(Color.BLACK);
        categoryPieChart.setEntryLabelTextSize(12f);
        categoryPieChart.animateY(1000);
        categoryPieChart.invalidate();
    }

    private void setupSpendingBarChart(List<Expense> expenses) {
        // Group expenses by day for the last 7 days
        Map<String, Float> dailyTotals = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        // Initialize last 7 days
        List<String> days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            String day = getDayLabel(calendar);
            days.add(day);
            dailyTotals.put(day, 0f);
        }

        // Sum up expenses by day
        for (Expense expense : expenses) {
            calendar.setTimeInMillis(expense.getDate());
            String day = getDayLabel(calendar);

            if (dailyTotals.containsKey(day)) {
                float current = dailyTotals.get(day);
                dailyTotals.put(day, current + (float) expense.getAmount());
            }
        }

        // Create bar entries
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            String day = days.get(i);
            float total = dailyTotals.get(day);
            entries.add(new BarEntry(i, total));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Daily Spending");
        dataSet.setColor(Color.parseColor("#4CAF50"));
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);

        spendingBarChart.setData(data);
        spendingBarChart.getDescription().setEnabled(false);

        // X-axis configuration
        XAxis xAxis = spendingBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        spendingBarChart.animateY(1000);
        spendingBarChart.invalidate();
    }

    private String getDayLabel(Calendar calendar) {
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }
}

