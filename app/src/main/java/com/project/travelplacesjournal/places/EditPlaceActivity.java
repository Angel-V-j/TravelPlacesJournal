package com.project.travelplacesjournal.places;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.*;
import com.project.travelplacesjournal.data.entities.Place;

public class EditPlaceActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private EditText etNotes;
    private EditText etVisitDate;
    private EditText etLatitude;
    private EditText etLongitude;
    private EditText etCategoryId;

    private RatingBar ratingBar;
    private CheckBox cbPublic;
    private Button btnSave;

    private AppDatabase db;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etNotes = findViewById(R.id.etNotes);
        etVisitDate = findViewById(R.id.etVisitDate);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etCategoryId = findViewById(R.id.etCategoryId);

        ratingBar = findViewById(R.id.ratingBar);
        cbPublic = findViewById(R.id.cbPublic);

        btnSave = findViewById(R.id.btnSave);

        db = DatabaseProvider.getDatabase(this);

        int placeId =
                getIntent().getIntExtra("PLACE_ID", -1);

        if (placeId == -1) {
            finish();
            return;
        }

        place = db.placeDao().getById(placeId);

        if (place == null) {
            finish();
            return;
        }

        loadPlace();

        btnSave.setOnClickListener(v -> updatePlace());
    }

    private void loadPlace() {

        etName.setText(place.getName());
        etDescription.setText(place.getDescription());
        etNotes.setText(place.getNotes());
        etVisitDate.setText(place.getVisitDate());

        etLatitude.setText(
                String.valueOf(place.getLatitude())
        );

        etLongitude.setText(
                String.valueOf(place.getLongitude())
        );

        etCategoryId.setText(
                String.valueOf(place.getCategoryId())
        );

        ratingBar.setRating(place.getRating());

        cbPublic.setChecked(place.isPublic());
    }

    private void updatePlace() {

        try {

            place.setName(
                    etName.getText().toString().trim()
            );

            place.setDescription(
                    etDescription.getText().toString().trim()
            );

            place.setNotes(
                    etNotes.getText().toString().trim()
            );

            place.setVisitDate(
                    etVisitDate.getText().toString().trim()
            );

            place.setLatitude(
                    Double.parseDouble(
                            etLatitude.getText().toString()
                    )
            );

            place.setLongitude(
                    Double.parseDouble(
                            etLongitude.getText().toString()
                    )
            );

            place.setCategoryId(
                    Integer.parseInt(
                            etCategoryId.getText().toString()
                    )
            );

            place.setRating(
                    (int) ratingBar.getRating()
            );

            place.setPublic(
                    cbPublic.isChecked()
            );

            db.placeDao().update(place);

            Toast.makeText(
                    this,
                    "Place updated",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (Exception ex) {

            Toast.makeText(
                    this,
                    ex.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}