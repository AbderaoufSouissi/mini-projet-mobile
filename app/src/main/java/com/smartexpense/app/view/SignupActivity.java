package com.smartexpense.app.view;

import android.content.Intent;
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
import com.smartexpense.app.model.User;
import com.smartexpense.app.utils.ValidationUtils;
import com.smartexpense.app.viewmodel.AuthViewModel;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout usernameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private TextInputLayout confirmPasswordInputLayout;
    private TextInputLayout securityAnswerInputLayout;

    private TextInputEditText usernameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private TextInputEditText securityAnswerEditText;
    private AutoCompleteTextView securityQuestionDropdown;

    private MaterialButton signupButton;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        initViewModel();
        setupSecurityQuestions();
        setupListeners();
        observeViewModel();
    }

    private void initViews() {
        usernameInputLayout = findViewById(R.id.usernameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        securityAnswerInputLayout = findViewById(R.id.securityAnswerInputLayout);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        securityAnswerEditText = findViewById(R.id.securityAnswerEditText);
        securityQuestionDropdown = findViewById(R.id.securityQuestionDropdown);

        signupButton = findViewById(R.id.signupButton);
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void setupSecurityQuestions() {
        String[] questions = {
            getString(R.string.sq_pet_name),
            getString(R.string.sq_birth_city),
            getString(R.string.sq_favorite_food),
            getString(R.string.sq_mother_maiden_name),
            getString(R.string.sq_favorite_color)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            questions
        );
        securityQuestionDropdown.setAdapter(adapter);
    }

    private void setupListeners() {
        signupButton.setOnClickListener(v -> attemptSignup());

        findViewById(R.id.loginText).setOnClickListener(v -> {
            finish();
        });
    }

    private void observeViewModel() {
        authViewModel.getRegisterSuccess().observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, R.string.registration_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        authViewModel.getRegisterError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptSignup() {
        // Clear previous errors
        usernameInputLayout.setError(null);
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
        confirmPasswordInputLayout.setError(null);
        securityAnswerInputLayout.setError(null);

        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String securityQuestion = securityQuestionDropdown.getText().toString().trim();
        String securityAnswer = securityAnswerEditText.getText().toString().trim();

        // Validate
        boolean isValid = true;

        if (!ValidationUtils.isNotEmpty(username)) {
            usernameInputLayout.setError(getString(R.string.error_username_required));
            isValid = false;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            emailInputLayout.setError(getString(R.string.error_invalid_email));
            isValid = false;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            passwordInputLayout.setError(getString(R.string.error_password_too_short));
            isValid = false;
        }

        if (!ValidationUtils.doPasswordsMatch(password, confirmPassword)) {
            confirmPasswordInputLayout.setError(getString(R.string.error_passwords_dont_match));
            isValid = false;
        }

        if (!ValidationUtils.isNotEmpty(securityQuestion)) {
            Toast.makeText(this, R.string.error_security_question_required, Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!ValidationUtils.isNotEmpty(securityAnswer)) {
            securityAnswerInputLayout.setError(getString(R.string.error_security_answer_required));
            isValid = false;
        }

        if (isValid) {
            User user = new User(username, email, password, securityQuestion, securityAnswer);
            authViewModel.register(user);
        }
    }
}

