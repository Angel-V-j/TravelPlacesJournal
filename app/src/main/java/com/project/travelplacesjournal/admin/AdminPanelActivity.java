package com.project.travelplacesjournal.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.R;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        ImageButton btnBack = findViewById(R.id.btnAdminBack);
        Button btnCategories = findViewById(R.id.btnManageCategories);
        Button btnUsers = findViewById(R.id.btnManageUsers);
        Button btnPlaces = findViewById(R.id.btnManagePublicPlaces);

        btnBack.setOnClickListener(v-> finish());

        btnCategories.setOnClickListener(v-> {
            Intent intent = new Intent(AdminPanelActivity.this, ManageCategoriesActivity.class);
            startActivity(intent);
        });

        btnUsers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPanelActivity.this, ManageUsersActivity.class);
            startActivity(intent);
        });
        btnPlaces.setOnClickListener(v -> {
            Toast.makeText(this, "места", Toast.LENGTH_SHORT).show();
        });
    }
}
