package com.project.travelplacesjournal.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.adapters.CategoryAdapter;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Category;

import java.util.ArrayList;
import java.util.List;


public class ManageCategoriesActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    private RecyclerView rvCategories;
    private Button btnCreateCategory;
    private ImageButton btnBack;
    private AppDatabase db;
    private CategoryAdapter adapter;
    private List<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        db = DatabaseProvider.getDatabase(this);
        rvCategories = findViewById(R.id.rvCategories);
        btnCreateCategory = findViewById(R.id.btnCreateCategory);
        btnBack = findViewById(R.id.btnCategoryBack);

        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(categoryList, this);
        rvCategories.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());
        btnCreateCategory.setOnClickListener(v -> showCategoryDialog(null));

        loadCategories();
    }

    private void loadCategories() {
        new Thread(()->{
            List<Category> categories = db.categoryDao().getAll();
            runOnUiThread(()-> adapter.updateList(categories));
        }).start();
    }

    private void showCategoryDialog(Category categoryToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setPadding(40, 40, 40, 40);

        if(categoryToEdit != null){
            builder.setTitle("Редактирай категория");
            input.setText(categoryToEdit.getName());
        } else {
            builder.setTitle("Нова категория");
            input.setHint("Име");
        }
        builder.setView(input);

        builder.setPositiveButton("Запази", (dialog, which) -> {
            String name = input.getText().toString().trim();
            if (name.isEmpty()) return;

            new Thread(() -> {
                if (categoryToEdit != null) {
                    categoryToEdit.setName(name);
                    db.categoryDao().update(categoryToEdit);
                } else {
                    db.categoryDao().insert(new Category(name));
                }
                loadCategories();
            }).start();
        });
        builder.setNegativeButton("Отказ", null);
        builder.show();
    }


    @Override
    public void onEditClick(Category category) {
        showCategoryDialog(category);
    }

    @Override
    public void onDeleteClick(Category category) {
        new AlertDialog.Builder(this)
                .setTitle("Изтриване")
                .setMessage("Сигурни ли сте, че искате да изтриете категория \"" + category.getName() + "\"?")
                .setPositiveButton("Да", (dialog, which) -> {
                    new Thread(() -> {
                        db.categoryDao().delete(category);
                        loadCategories();
                    }).start();
                })
                .setNegativeButton("Не", null)
                .show();
    }
}