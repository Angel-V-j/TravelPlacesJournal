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
import com.project.travelplacesjournal.data.entities.Ratings;
import com.project.travelplacesjournal.places.adapters.PlaceAdapter;
import com.project.travelplacesjournal.utils.ArrayAdapterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceListActivity extends AppCompatActivity {

    private EditText etSearch;
    private Spinner spCategoryFilter;
    private Spinner spRatingFilter;
    private Button btnFilter;
    private RecyclerView recyclerViewPlaces;
    private Button btnAddPlace;
    private AppDatabase db;
    private PlaceAdapter adapter;
    private List<Place> places;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        db = DatabaseProvider.getDatabase(this);
        etSearch = findViewById(R.id.etSearch);
        spCategoryFilter = findViewById(R.id.spCategoryFilter);
        spRatingFilter = findViewById(R.id.spRatingFilter);
        recyclerViewPlaces = findViewById(R.id.recyclerViewPlaces);
        btnFilter = findViewById(R.id.btnFilter);
        btnAddPlace = findViewById(R.id.btnAddPlace);
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));

        loadCategories();
        createRatingSpinner();
        loadPlaces();
        btnFilter.setOnClickListener(v -> applyFilters());
        btnAddPlace.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PlaceListActivity.this, AddPlaceActivity.class);
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
                ArrayAdapterUtils.createStrArrAdapter(categories, this)
        );
    }

    private void createRatingSpinner() {
        spRatingFilter.setAdapter(ArrayAdapterUtils
                .createStrArrAdapter(Arrays.asList(Ratings.values()),this));
    }

    private void applyFilters() {
        String search = etSearch.getText()
                .toString()
                .trim();

        Category category = categories.get(
                        spCategoryFilter.getSelectedItemPosition());

        if(category.getId() == -1){
            addSearch(search);
        } else {
            addCategoryFilter(category.getId());
            if(!search.isEmpty())
                places.removeIf(place ->
                        !place.getName()
                                .toLowerCase()
                                .contains(search.toLowerCase())
                                &&
                                !place.getDescription()
                                        .toLowerCase()
                                        .contains(search.toLowerCase())
                );

        }

        addRatingFilter();
        adapter = new PlaceAdapter(this, places, categories);
        recyclerViewPlaces.setAdapter(adapter);
    }

    private void addSearch(String search) {
        if(!search.isEmpty())
            places = db.placeDao().search(search);
        else
            places = db.placeDao().getAll();
    }
    private void addCategoryFilter(int cId) {
        places = db.placeDao().filterByCategory(cId);
    }
    private void addRatingFilter() {
        int position = spRatingFilter.getSelectedItemPosition();

        Ratings rating = Ratings.values()[position];

        int minRating = rating.getMinRating();
        if(minRating > 0) {
            places.removeIf(place ->
                    place.getRating() < minRating
            );
        }
    }
}