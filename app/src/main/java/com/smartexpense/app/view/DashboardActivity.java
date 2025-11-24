package com.smartexpense.app.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.button.MaterialButton;
import com.smartexpense.app.R;
import com.smartexpense.app.model.CategoryTotal;
import com.smartexpense.app.utils.FormatUtils;
import com.smartexpense.app.utils.SessionManager;
import com.smartexpense.app.viewmodel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView welcomeText;
    private TextView todayTotalText;
    private TextView weekTotalText;
    private TextView monthTotalText;
    private PieChart pieChart;
    private MaterialButton addExpenseButton;
    private MaterialButton viewAllButton;
    private MaterialButton statisticsButton;

    private ExpenseViewModel expenseViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initViews();
        initViewModel();
        setupListeners();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void initViews() {
        welcomeText = findViewById(R.id.welcomeText);
        todayTotalText = findViewById(R.id.todayTotalText);
        weekTotalText = findViewById(R.id.weekTotalText);
        monthTotalText = findViewById(R.id.monthTotalText);
        pieChart = findViewById(R.id.pieChart);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        viewAllButton = findViewById(R.id.viewAllButton);
        statisticsButton = findViewById(R.id.statisticsButton);

        sessionManager = new SessionManager(this);

        String userName = sessionManager.getUserName();
        welcomeText.setText(getString(R.string.welcome, userName));
    }

    private void initViewModel() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }

    private void setupListeners() {
        findViewById(R.id.logoutButton).setOnClickListener(v -> showLogoutDialog());

        addExpenseButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddExpenseActivity.class));
        });

        viewAllButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ExpenseListActivity.class));
        });

        statisticsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, StatisticsActivity.class));
        });
    }

    private void loadData() {
        int userId = sessionManager.getUserId();

        // Load totals
        expenseViewModel.loadTotals(userId);
        expenseViewModel.loadCategoryTotals(userId);

        observeViewModel();
    }

    private void observeViewModel() {
        expenseViewModel.getTodayTotal().observe(this, total -> {
            if (total != null) {
                todayTotalText.setText(FormatUtils.formatCurrency(total));
            }
        });

        expenseViewModel.getWeekTotal().observe(this, total -> {
            if (total != null) {
                weekTotalText.setText(FormatUtils.formatCurrency(total));
            }
        });

        expenseViewModel.getMonthTotal().observe(this, total -> {
            if (total != null) {
                monthTotalText.setText(FormatUtils.formatCurrency(total));
            }
        });

        expenseViewModel.getCategoryTotals().observe(this, categoryTotals -> {
            if (categoryTotals != null && !categoryTotals.isEmpty()) {
                setupPieChart(categoryTotals);
            } else {
                setupEmptyPieChart();
            }
        });
    }

    private void setupPieChart(List<CategoryTotal> categoryTotals) {
        List<PieEntry> entries = new ArrayList<>();

        for (CategoryTotal categoryTotal : categoryTotals) {
            entries.add(new PieEntry((float) categoryTotal.getTotal(), categoryTotal.getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        // Set colors
        int[] colors = {
            Color.parseColor("#FF6384"),
            Color.parseColor("#36A2EB"),
            Color.parseColor("#FFCE56"),
            Color.parseColor("#4BC0C0"),
            Color.parseColor("#9966FF")
        };
        dataSet.setColors(colors);

        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(58f);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void setupEmptyPieChart() {
        pieChart.clear();
        pieChart.setNoDataText("No expenses yet");
        pieChart.invalidate();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.confirm_logout)
            .setMessage(R.string.confirm_logout_message)
            .setPositiveButton(R.string.yes, (dialog, which) -> logout())
            .setNegativeButton(R.string.no, null)
            .show();
    }

    private void logout() {
        sessionManager.logout();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

