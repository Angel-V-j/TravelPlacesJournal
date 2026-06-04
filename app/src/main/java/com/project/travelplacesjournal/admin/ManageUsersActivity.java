package com.project.travelplacesjournal.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.adapters.UserAdapter;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity implements UserAdapter.OnUserBlockClickListener {
    private AppDatabase db;
    private UserAdapter adapter;
    private final List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        db = DatabaseProvider.getDatabase(this);
        RecyclerView rvUsers = findViewById(R.id.rvUsers);
        ImageButton btnBack = findViewById(R.id.btnUserBack);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userList, this);
        rvUsers.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        loadUsers();
    }

    private void loadUsers() {
        new Thread(()->{
            List<User>users=db.userDao().getAllUsers();
            runOnUiThread(()->adapter.updateList(users));
        }).start();
    }

    @Override
    public void onBlockClick(User user){
        String message= user.isBlocked() ? "Искате ли да отблокирате " : "Сигурни ли сте, че искате да блокирате ";
        new AlertDialog.Builder(this)
                .setTitle("Промяна на статус")
                .setMessage(message + user.getFirstName() + " " + user.getLastName() + "?")
                .setPositiveButton("Да", (dialog, which) -> new Thread(() -> {
                    user.setBlocked(!user.isBlocked());
                    db.userDao().update(user);
                    loadUsers();
                }).start())
                .setNegativeButton("Не", null)
                .show();
    }
}
