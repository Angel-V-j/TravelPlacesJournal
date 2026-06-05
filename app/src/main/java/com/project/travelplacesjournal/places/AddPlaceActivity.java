package com.project.travelplacesjournal.places;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.data.entities.PlaceImage;
import com.project.travelplacesjournal.places.helpers.LocationHelper;
import com.project.travelplacesjournal.places.helpers.PlaceImageHelper;
import com.project.travelplacesjournal.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class AddPlaceActivity extends AppCompatActivity {
    private LinearLayout layoutImages;
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
    private Button btnCurrLocation;
    private Button btnGallery;
    private Button btnCamera;
    private PlaceImageHelper imgHelper;
    private LocationHelper locHelper;
    private final List<Uri> selectedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        layoutImages = findViewById(R.id.layoutImages);

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
        btnCurrLocation = findViewById(R.id.btnCurrentLocation);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);

        imgHelper = new PlaceImageHelper(this,
                layoutImages, selectedImages);
        locHelper = new LocationHelper(this);

        btnSave.setOnClickListener(v -> savePlace());
        btnCurrLocation.setOnClickListener(v ->
                locHelper.fillLocationFields(etLatitude,etLongitude));
        btnGallery.setOnClickListener(v -> imgHelper.openGallery());
        btnCamera.setOnClickListener(v -> imgHelper.openCamera());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        imgHelper.handlePermissionResult(requestCode, grantResults);
    }

    private void savePlace() {

        try {
            String name =
                    etName.getText().toString().trim();

            String description =
                    etDescription.getText().toString().trim();

            String notes =
                    etNotes.getText().toString().trim();

            String visitDate =
                    etVisitDate.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name is required");
                return;
            }

            int categoryId = 0;

            if (!etCategoryId.getText().toString().trim().isEmpty()) {
                categoryId =
                        Integer.parseInt(
                                etCategoryId.getText().toString().trim()
                        );
            }

            double latitude = 0;

            if (!etLatitude.getText().toString().trim().isEmpty()) {
                latitude =
                        Double.parseDouble(
                                etLatitude.getText().toString().trim()
                        );
            }

            double longitude = 0;

            if (!etLongitude.getText().toString().trim().isEmpty()) {
                longitude =
                        Double.parseDouble(
                                etLongitude.getText().toString().trim()
                        );
            }

            int rating = (int) ratingBar.getRating();

            boolean isPublic = cbPublic.isChecked();

            Place place = new Place();

            place.setName(name);
            place.setDescription(description);
            place.setNotes(notes);
            place.setVisitDate(visitDate);

            place.setLatitude(latitude);
            place.setLongitude(longitude);

            place.setCategoryId(categoryId);

            place.setRating(rating);

            place.setPublic(isPublic);

            place.setUserId(new SessionManager(this).getUserId());

            AppDatabase db =
                    DatabaseProvider.getDatabase(this);

            long placeId =
                    db.placeDao().insert(place);

            for(Uri uri : selectedImages) {
                PlaceImage image =
                        new PlaceImage();

                image.setPlaceId(
                        (int) placeId
                );

                image.setImageUri(
                        uri.toString()
                );

                db.placeImageDao().insert(image);
            }

            Toast.makeText(
                    this,
                    "Place saved successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (Exception ex) {

            Toast.makeText(
                    this,
                    "Error: " + ex.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}