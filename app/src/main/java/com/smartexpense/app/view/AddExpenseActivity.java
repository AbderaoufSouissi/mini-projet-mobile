package com.smartexpense.app.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartexpense.app.R;
import com.smartexpense.app.model.Expense;
import com.smartexpense.app.utils.FormatUtils;
import com.smartexpense.app.utils.SessionManager;
import com.smartexpense.app.utils.ValidationUtils;
import com.smartexpense.app.viewmodel.ExpenseViewModel;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private TextInputLayout amountInputLayout;
    private TextInputLayout descriptionInputLayout;

    private TextInputEditText amountEditText;
    private TextInputEditText dateEditText;
    private TextInputEditText descriptionEditText;
    private AutoCompleteTextView categoryDropdown;

    private MaterialButton saveButton;
    private MaterialButton cancelButton;

    private ExpenseViewModel expenseViewModel;
    private SessionManager sessionManager;

    private long selectedDate;
    private int expenseId = -1; // -1 means new expense

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        initViews();
        initViewModel();
        setupCategories();
        setupListeners();
        observeViewModel();

        // Check if editing existing expense
        expenseId = getIntent().getIntExtra("expense_id", -1);
        if (expenseId != -1) {
            loadExpense();
        } else {
            selectedDate = System.currentTimeMillis();
            dateEditText.setText(FormatUtils.formatDate(selectedDate));
        }
    }

    private void initViews() {
        amountInputLayout = findViewById(R.id.amountInputLayout);
        descriptionInputLayout = findViewById(R.id.descriptionInputLayout);

        amountEditText = findViewById(R.id.amountEditText);
        dateEditText = findViewById(R.id.dateEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categoryDropdown = findViewById(R.id.categoryDropdown);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        sessionManager = new SessionManager(this);
    }

    private void initViewModel() {
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
    }

    private void setupCategories() {
        String[] categories = {
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
        categoryDropdown.setAdapter(adapter);
    }

    private void setupListeners() {
        dateEditText.setOnClickListener(v -> showDatePicker());

        saveButton.setOnClickListener(v -> saveExpense());

        cancelButton.setOnClickListener(v -> finish());
    }

    private void observeViewModel() {
        expenseViewModel.getOperationSuccess().observe(this, success -> {
            if (success != null && success) {
                String message = expenseId == -1 ?
                    getString(R.string.expense_added) :
                    getString(R.string.expense_updated);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        expenseViewModel.getOperationError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDate);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                Calendar selected = Calendar.getInstance();
                selected.set(year, month, dayOfMonth);
                selectedDate = selected.getTimeInMillis();
                dateEditText.setText(FormatUtils.formatDate(selectedDate));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void saveExpense() {
        // Clear previous errors
        amountInputLayout.setError(null);
        descriptionInputLayout.setError(null);

        String amountStr = amountEditText.getText().toString().trim();
        String category = categoryDropdown.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        // Validate
        boolean isValid = true;

        if (!ValidationUtils.isValidAmount(amountStr)) {
            amountInputLayout.setError(getString(R.string.error_invalid_amount));
            isValid = false;
        }

        if (!ValidationUtils.isNotEmpty(category)) {
            Toast.makeText(this, R.string.error_category_required, Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!ValidationUtils.isNotEmpty(description)) {
            descriptionInputLayout.setError(getString(R.string.error_description_required));
            isValid = false;
        }

        if (isValid) {
            double amount = Double.parseDouble(amountStr);
            int userId = sessionManager.getUserId();

            if (expenseId == -1) {
                // Add new expense
                Expense expense = new Expense(userId, amount, category, description, selectedDate);
                expenseViewModel.addExpense(expense);
            } else {
                // Update existing expense
                Expense expense = new Expense(expenseId, userId, amount, category, description, selectedDate);
                expenseViewModel.updateExpense(expense);
            }
        }
    }

    private void loadExpense() {
        // This would load expense details if editing
        // For now, we'll leave it empty as we need to pass the full expense object
        // through intent or load it from repository
    }
}

