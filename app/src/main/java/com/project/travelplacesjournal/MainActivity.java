package com.project.travelplacesjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.admin.AdminPanelActivity;
import com.project.travelplacesjournal.auth.ProfileActivity;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.User;
import com.project.travelplacesjournal.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToProfile, btnAdminPanel;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= DatabaseProvider.getDatabase(this);
        sessionManager= new SessionManager(this);

        btnGoToProfile=findViewById(R.id.btnGoToProfile);
        btnAdminPanel= findViewById(R.id.btnAdminPanel);

        checkUserPermissions();

        btnGoToProfile.setOnClickListener(v ->{
            Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        btnAdminPanel.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this, AdminPanelActivity.class);
            startActivity(intent);
        });

    }

    private void checkUserPermissions() {

        int userId= sessionManager.getUserId();

        new Thread(()->{
            User currentUser= db.userDao().getById(userId);

            if (currentUser!=null){
                runOnUiThread(()->{
                    if (currentUser.isAdmin()){
                        btnAdminPanel.setVisibility(View.VISIBLE);
                    }else {
                        btnAdminPanel.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
}