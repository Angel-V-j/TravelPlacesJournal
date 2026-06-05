package com.project.travelplacesjournal.places;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.*;
import com.project.travelplacesjournal.data.entities.Category;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.data.entities.PlaceImage;
import com.project.travelplacesjournal.places.helpers.LocationHelper;
import com.project.travelplacesjournal.places.helpers.PlaceImageHelper;
import com.project.travelplacesjournal.utils.ArrayAdapterUtils;

import java.util.ArrayList;
import java.util.List;

public class EditPlaceActivity extends AppCompatActivity {
    private LinearLayout layoutImages;
    private EditText etName;
    private EditText etDescription;
    private EditText etNotes;
    private EditText etVisitDate;
    private EditText etLatitude;
    private EditText etLongitude;
    private Spinner spCategory;
    private RatingBar ratingBar;
    private CheckBox cbPublic;
    private Button btnSave;
    private Button btnGallery;
    private Button btnCamera;
    private Button btnCurrentLocation;
    private AppDatabase db;
    private Place place;
    private PlaceImageHelper imgHelper;
    private LocationHelper locHelper;
    private final List<Uri> selectedImages = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();

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
        spCategory = findViewById(R.id.spCategory);

        ratingBar = findViewById(R.id.ratingBar);
        cbPublic = findViewById(R.id.cbPublic);

        btnSave = findViewById(R.id.btnSave);
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);
        imgHelper = new PlaceImageHelper(this,
                layoutImages, selectedImages);
        locHelper = new LocationHelper(this);
        db = DatabaseProvider.getDatabase(this);
        categories.addAll(db.categoryDao().getAll());
        int placeId = getIntent().getIntExtra("PLACE_ID", -1);
        if (placeId == -1) {
            finish();
            return;
        }

        place = db.placeDao().getById(placeId);
        if (place == null) {
            finish();
            return;
        }

        loadCategories();
        loadPlace();
        btnGallery.setOnClickListener(v -> imgHelper.openGallery());
        btnCurrentLocation.setOnClickListener(v ->
                locHelper.fillLocationFields(etLatitude, etLongitude));
        btnCamera.setOnClickListener(v -> imgHelper.openCamera());
        btnSave.setOnClickListener(v -> updatePlace());
    }

    private void loadPlace() {
        etName.setText(place.getName());
        etDescription.setText(place.getDescription());
        etNotes.setText(place.getNotes());
        etVisitDate.setText(place.getVisitDate());
        etLatitude.setText(String.valueOf(place.getLatitude()));
        etLongitude.setText(String.valueOf(place.getLongitude()));
        ratingBar.setRating(place.getRating());
        cbPublic.setChecked(place.isPublic());

        List<PlaceImage> images = db.placeImageDao()
                .getByPlaceId(place.getId());

        for(PlaceImage image : images) {
            Uri uri = Uri.parse(image.getImageUri());
            selectedImages.add(uri);
            imgHelper.addImagePreview(uri);
        }
    }

    private void updatePlace() {
        try {
            place.setName(etName.getText().toString().trim());
            place.setDescription(etDescription.getText().toString().trim());
            place.setNotes(etNotes.getText().toString().trim());
            place.setVisitDate(etVisitDate.getText().toString().trim());
            place.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
            place.setLongitude(Double.parseDouble(etLongitude.getText().toString()));
            place.setCategoryId(categories
                    .get(spCategory.getSelectedItemPosition())
                    .getId());

            place.setRating((int) ratingBar.getRating());
            place.setPublic(cbPublic.isChecked());
            db.placeDao().update(place);
            db.placeImageDao().deleteByPlaceId(place.getId());
            for(Uri uri : selectedImages){
                PlaceImage image = new PlaceImage();
                image.setPlaceId(place.getId());
                image.setImageUri(uri.toString());
                db.placeImageDao().insert(image);
            }

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        imgHelper.handlePermissionResult(requestCode, grantResults);
    }

    private void selectCurrentCategory() {
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId() == place.getCategoryId()) {
                spCategory.setSelection(i, true);
                break;
            }
        }
    }

    private void loadCategories() {
        categories.clear();
        categories.addAll(db.categoryDao().getAll());

        new Thread(() -> {
            runOnUiThread(() -> {
                spCategory.setAdapter(ArrayAdapterUtils
                        .createStrArrAdapter(categories, this));

                selectCurrentCategory();
            });

        }).start();
    }
}