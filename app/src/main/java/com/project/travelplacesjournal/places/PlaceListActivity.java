package com.project.travelplacesjournal.places;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Category;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.places.adapters.PlaceAdapter;

import java.util.List;

public class PlaceListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPlaces;
    private Button btnAddPlace;

    private AppDatabase db;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        db = DatabaseProvider.getDatabase(this);

        recyclerViewPlaces = findViewById(R.id.recyclerViewPlaces);

        btnAddPlace = findViewById(R.id.btnAddPlace);

        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));

        btnAddPlace.setOnClickListener(v -> {

            Intent intent = new Intent(
                    PlaceListActivity.this,
                    AddPlaceActivity.class
            );

            startActivity(intent);
        });

        loadPlaces();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPlaces();
    }

    private void loadPlaces() {
        List<Place> places = db.placeDao().getAll();
        List<Category> categories = db.categoryDao().getAll();
        adapter = new PlaceAdapter(this, places, categories);
        recyclerViewPlaces.setAdapter(adapter);
    }
}