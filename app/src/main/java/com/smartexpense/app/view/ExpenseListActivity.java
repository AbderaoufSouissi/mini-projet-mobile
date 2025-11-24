package com.smartexpense.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.smartexpense.app.R;
import com.smartexpense.app.adapter.ExpenseAdapter;
import com.smartexpense.app.model.Expense;
import com.smartexpense.app.utils.SessionManager;
import com.smartexpense.app.viewmodel.ExpenseViewModel;

public class ExpenseListActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    private RecyclerView expensesRecyclerView;
    private LinearLayout emptyStateLayout;
    private FloatingActionButton addExpenseFab;
    private TextInputEditText searchEditText;
    private AutoCompleteTextView filterCategoryDropdown;

    private ExpenseAdapter expenseAdapter;
    private ExpenseViewModel expenseViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        initViews();
        initViewModel();
        setupRecyclerView();
        setupFilters();
        setupListeners();
        loadExpenses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpenses();
    }

    private void initViews() {
        expensesRecyclerView = findViewById(R.id.expensesRecyclerView);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);
        addExpenseFab = findViewById(R.id.addExpenseFab);
        searchEditText = findViewById(R.id.searchEditText);
        filterCategoryDropdown = findViewById(R.id.filterCategoryDropdown);

        sessionManager = new SessionManager(this);
    }

    private void initViewModel() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }

    private void setupRecyclerView() {
        expenseAdapter = new ExpenseAdapter(this);
        expensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expensesRecyclerView.setAdapter(expenseAdapter);
    }

    private void setupFilters() {
        String[] categories = {
            getString(R.string.all_categories),
            getString(R.string.food),
            getString(R.string.transport),
            getString(R.string.shopping),
            getString(R.string.bills),
            getString(R.string.other)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categories
        );
        filterCategoryDropdown.setAdapter(adapter);
        filterCategoryDropdown.setText(categories[0], false);
    }

    private void setupListeners() {
        addExpenseFab.setOnClickListener(v -> {
            startActivity(new Intent(this, AddExpenseActivity.class));
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchExpenses(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        filterCategoryDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String category = filterCategoryDropdown.getText().toString();
            filterByCategory(category);
        });
    }

    private void loadExpenses() {
        int userId = sessionManager.getUserId();
        expenseViewModel.loadExpenses(userId);
        observeViewModel();
    }

    private void observeViewModel() {
        expenseViewModel.getExpensesList().observe(this, expenses -> {
            if (expenses != null) {
                expenseAdapter.setExpenses(expenses);

                if (expenses.isEmpty()) {
                    expensesRecyclerView.setVisibility(View.GONE);
                    emptyStateLayout.setVisibility(View.VISIBLE);
                } else {
                    expensesRecyclerView.setVisibility(View.VISIBLE);
                    emptyStateLayout.setVisibility(View.GONE);
                }
            }
        });

        expenseViewModel.getOperationSuccess().observe(this, success -> {
            if (success != null && success) {
                loadExpenses();
            }
        });
    }

    private void searchExpenses(String query) {
        if (query.isEmpty()) {
            loadExpenses();
        } else {
            int userId = sessionManager.getUserId();
            expenseViewModel.searchExpenses(userId, query);
        }
    }

    private void filterByCategory(String category) {
        int userId = sessionManager.getUserId();

        if (category.equals(getString(R.string.all_categories))) {
            expenseViewModel.loadExpenses(userId);
        } else {
            expenseViewModel.filterByCategory(userId, category);
        }
    }

    @Override
    public void onExpenseClick(Expense expense) {
        // Optional: Show expense details
    }

    @Override
    public void onEditClick(Expense expense) {
        Intent intent = new Intent(this, AddExpenseActivity.class);
        intent.putExtra("expense_id", expense.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Expense expense) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete)
            .setMessage(R.string.confirm_delete_message)
            .setPositiveButton(R.string.yes, (dialog, which) -> {
                expenseViewModel.deleteExpense(expense.getId());
                Toast.makeText(this, R.string.expense_deleted, Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton(R.string.no, null)
            .show();
    }
}

