package com.smartexpense.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartexpense.app.R;
import com.smartexpense.app.model.User;
import com.smartexpense.app.repository.AuthRepository;
import com.smartexpense.app.utils.ValidationUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout;
    private TextInputLayout securityAnswerInputLayout;
    private TextInputLayout newPasswordInputLayout;

    private TextInputEditText emailEditText;
    private TextInputEditText securityAnswerEditText;
    private TextInputEditText newPasswordEditText;

    private TextView securityQuestionText;
    private MaterialButton checkEmailButton;
    private MaterialButton verifyButton;
    private MaterialButton resetButton;

    private AuthRepository authRepository;
    private ExecutorService executorService;

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initViews();
        setupListeners();

        authRepository = new AuthRepository(this);
        executorService = Executors.newSingleThreadExecutor();
    }

    private void initViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout);
        securityAnswerInputLayout = findViewById(R.id.securityAnswerInputLayout);
        newPasswordInputLayout = findViewById(R.id.newPasswordInputLayout);

        emailEditText = findViewById(R.id.emailEditText);
        securityAnswerEditText = findViewById(R.id.securityAnswerEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);

        securityQuestionText = findViewById(R.id.securityQuestionText);
        checkEmailButton = findViewById(R.id.checkEmailButton);
        verifyButton = findViewById(R.id.verifyButton);
        resetButton = findViewById(R.id.resetButton);
    }

    private void setupListeners() {
        checkEmailButton.setOnClickListener(v -> checkEmail());
        verifyButton.setOnClickListener(v -> verifyAnswer());
        resetButton.setOnClickListener(v -> resetPassword());

        findViewById(R.id.backToLoginButton).setOnClickListener(v -> finish());
    }

    private void checkEmail() {
        emailInputLayout.setError(null);
        String email = emailEditText.getText().toString().trim();

        if (!ValidationUtils.isValidEmail(email)) {
            emailInputLayout.setError(getString(R.string.error_invalid_email));
            return;
        }

        executorService.execute(() -> {
            User user = authRepository.getUserByEmail(email);

            runOnUiThread(() -> {
                if (user != null) {
                    userEmail = email;
                    securityQuestionText.setText(user.getSecurityQuestion());
                    securityQuestionText.setVisibility(View.VISIBLE);
                    securityAnswerInputLayout.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.VISIBLE);
                    checkEmailButton.setVisibility(View.GONE);
                    emailEditText.setEnabled(false);
                } else {
                    Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void verifyAnswer() {
        securityAnswerInputLayout.setError(null);
        String answer = securityAnswerEditText.getText().toString().trim();

        if (!ValidationUtils.isNotEmpty(answer)) {
            securityAnswerInputLayout.setError(getString(R.string.error_security_answer_required));
            return;
        }

        executorService.execute(() -> {
            boolean isValid = authRepository.validateSecurityAnswer(userEmail, answer);

            runOnUiThread(() -> {
                if (isValid) {
                    newPasswordInputLayout.setVisibility(View.VISIBLE);
                    resetButton.setVisibility(View.VISIBLE);
                    verifyButton.setVisibility(View.GONE);
                    securityAnswerEditText.setEnabled(false);
                } else {
                    Toast.makeText(this, R.string.incorrect_security_answer, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void resetPassword() {
        newPasswordInputLayout.setError(null);
        String newPassword = newPasswordEditText.getText().toString().trim();

        if (!ValidationUtils.isValidPassword(newPassword)) {
            newPasswordInputLayout.setError(getString(R.string.error_password_too_short));
            return;
        }

        executorService.execute(() -> {
            boolean success = authRepository.resetPassword(userEmail, newPassword);

            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, R.string.password_reset_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}

