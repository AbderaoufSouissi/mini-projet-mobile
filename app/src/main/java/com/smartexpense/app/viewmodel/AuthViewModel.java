package com.smartexpense.app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.smartexpense.app.model.User;
import com.smartexpense.app.repository.AuthRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private ExecutorService executorService;

    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();

    private MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private MutableLiveData<String> registerError = new MutableLiveData<>();

    private MutableLiveData<User> loggedInUser = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    // Login
    public void login(String email, String password) {
        executorService.execute(() -> {
            User user = authRepository.loginUser(email, password);
            if (user != null) {
                loggedInUser.postValue(user);
                loginSuccess.postValue(true);
            } else {
                loginError.postValue("Invalid email or password");
                loginSuccess.postValue(false);
            }
        });
    }

    // Register
    public void register(User user) {
        executorService.execute(() -> {
            long result = authRepository.registerUser(user);
            if (result > 0) {
                registerSuccess.postValue(true);
            } else {
                registerError.postValue("Email already exists or registration failed");
                registerSuccess.postValue(false);
            }
        });
    }

    // LiveData getters
    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public LiveData<String> getRegisterError() {
        return registerError;
    }

    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}

