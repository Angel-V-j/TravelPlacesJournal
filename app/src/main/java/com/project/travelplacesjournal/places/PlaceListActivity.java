package com.project.travelplacesjournal.places;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Category;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.places.adapters.PlaceAdapter;
import com.project.travelplacesjournal.utils.CategoryUtils;

import java.util.List;

public class PlaceListActivity extends AppCompatActivity {

    private EditText etSearch;
    private Spinner spCategoryFilter;
    private Button btnFilter;
    private RecyclerView recyclerViewPlaces;
    private Button btnAddPlace;
    private AppDatabase db;
    private PlaceAdapter adapter;
    List<Place> places;
    List<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        db = DatabaseProvider.getDatabase(this);
        etSearch = findViewById(R.id.etSearch);
        spCategoryFilter = findViewById(R.id.spCategoryFilter);
        recyclerViewPlaces = findViewById(R.id.recyclerViewPlaces);
        btnFilter = findViewById(R.id.btnFilter);
        btnAddPlace = findViewById(R.id.btnAddPlace);
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));
        loadCategories();
        loadPlaces();
        btnFilter.setOnClickListener(v -> applyFilters());
        btnAddPlace.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PlaceListActivity.this,
                    AddPlaceActivity.class
            );

            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPlaces();
    }

    private void loadPlaces() {
        places = db.placeDao().getAll();
        adapter = new PlaceAdapter(this, places, categories);
        recyclerViewPlaces.setAdapter(adapter);
    }

    private void loadCategories() {
        categories = db.categoryDao().getAll();
        Category allCategory = new Category();
        allCategory.setId(-1);
        allCategory.setName("All");
        categories.add(0, allCategory);
        spCategoryFilter.setAdapter(
                CategoryUtils.createCategoryAdapter(
                        categories,
                        this
                )
        );
    }

    private void applyFilters() {
        String search = etSearch.getText()
                        .toString()
                        .trim();

        Category category = categories.get(
                        spCategoryFilter.getSelectedItemPosition());

        if(category.getId() == -1){
            if(!search.isEmpty())
                places = db.placeDao().search(search);
            else
                places = db.placeDao().getAll();
        } else {
            places = db.placeDao().filterByCategory(category.getId());

            if(!search.isEmpty())
                places.removeIf(place -> !place
                                                .getName()
                                                .toLowerCase()
                                                .contains(search.toLowerCase()) && !place
                                                                    .getDescription()
                                                                    .toLowerCase()
                                                                    .contains(search.toLowerCase()));

        }

        adapter = new PlaceAdapter(this, places, categories);
        recyclerViewPlaces.setAdapter(adapter);
    }
}