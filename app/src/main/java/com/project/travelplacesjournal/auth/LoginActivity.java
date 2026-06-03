package com.project.travelplacesjournal.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.MainActivity;
import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.User;
import com.project.travelplacesjournal.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoToRegister;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        db= DatabaseProvider.getDatabase(this);
        sessionManager=new SessionManager(this);
        
        if(sessionManager.isLoggedIn()){
            navigateToMainActivity();
            return;
        }
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(v -> handleLogin());

        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Моля, попълнете всички полета!", Toast.LENGTH_SHORT).show();
            return;
        }


        User user = db.userDao().getByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            if (user.isBlocked()) {
                Toast.makeText(this, "Вашият профил е блокиран от администратор!", Toast.LENGTH_LONG).show();
                return;
            }

            sessionManager.createLoginSession(user.getId(), user.isAdmin());
            Toast.makeText(this, "Добре дошли, " + user.getFirstName() + "!", Toast.LENGTH_SHORT).show();
            navigateToMainActivity();

        } else {
            Toast.makeText(this, "Грешен имейл или парола!", Toast.LENGTH_LONG).show();
        }
    }


    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
