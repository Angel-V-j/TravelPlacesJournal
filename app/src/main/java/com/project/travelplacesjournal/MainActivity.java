package com.project.travelplacesjournal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.places.AddPlaceActivity;
import com.project.travelplacesjournal.places.PlaceListActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnPlaces;
    private Button btnAddPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnPlaces = findViewById(R.id.btnPlaces);
        btnAddPlace = findViewById(R.id.btnAddPlace);

        btnPlaces.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PlaceListActivity.class
                    );

            startActivity(intent);
        });

        btnAddPlace.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AddPlaceActivity.class
                    );

            startActivity(intent);
        });
    }
}