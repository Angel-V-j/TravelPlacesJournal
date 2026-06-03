package com.project.travelplacesjournal.places;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.places.adapters.PlaceAdapter;

import java.util.List;

public class PlaceListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPlaces;
    private Button btnAddPlace;

    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        recyclerViewPlaces =
                findViewById(R.id.recyclerViewPlaces);

        btnAddPlace =
                findViewById(R.id.btnAddPlace);

        recyclerViewPlaces.setLayoutManager(
                new LinearLayoutManager(this)
        );

        btnAddPlace.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            PlaceListActivity.this,
                            AddPlacesActivity.class
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

        AppDatabase db =
                DatabaseProvider.getDatabase(this);

        List<Place> places =
                db.placeDao().getAll();

        adapter = new PlaceAdapter(places);

        recyclerViewPlaces.setAdapter(adapter);
    }
}