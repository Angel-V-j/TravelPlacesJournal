package com.project.travelplacesjournal.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.User;
import com.project.travelplacesjournal.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName,etLastName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister, btnGoToLogin;

    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = DatabaseProvider.getDatabase(this);
        sessionManager = new SessionManager(this);

        etFirstName= findViewById(R.id.etRegisterFirstName);
        etLastName= findViewById(R.id.etRegisterLastName);
        etEmail= findViewById(R.id.etRegisterEmail);
        etPassword=findViewById(R.id.etRegisterPassword);
        etConfirmPassword= findViewById(R.id.etRegisterConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        btnGoToLogin=findViewById(R.id.btnGoToLogin);


        btnRegister.setOnClickListener(v-> handleRegistration());
        btnGoToLogin.setOnClickListener(v->{
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        });
    }

    private void handleRegistration() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Моля, попълнете всички полета!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Въведете валиден имейл адрес!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Паролата трябва да е поне 6 символа!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Паролите не съвпадат!", Toast.LENGTH_SHORT).show();
            return;
        }
        User existingUser = db.userDao().getByEmail(email);

        if (existingUser != null) {
            Toast.makeText(this, "Този имейл вече е регистриран!", Toast.LENGTH_LONG).show();
        }else {
            User newUser = new User(email, password, firstName, lastName, null, null, false);
            long generatedId = db.userDao().insert(newUser);

            sessionManager.createLoginSession((int) generatedId, false);

            Toast.makeText(this, "Регистрацията е успешна!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
