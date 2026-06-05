package com.project.travelplacesjournal.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {
    public static ArrayAdapter<String> createCategoryAdapter(List<Category> categories,
                                                      Activity activity) {
        List<String> names = new ArrayList<>();
        for(Category category : categories)
            names.add(category.getName());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout
                .simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        return adapter;
    }
}
