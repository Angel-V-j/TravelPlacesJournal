package com.project.travelplacesjournal.places;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.Place;
import com.project.travelplacesjournal.data.entities.PlaceImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddPlacesActivity extends AppCompatActivity {

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
    private Button btnGallery;
    private Button btnCamera;

    private ImageView imgPreview;
    private Bitmap capturedBitmap;
    private Uri cameraImageUri;

    private final List<Uri> selectedImages =
            new ArrayList<>();

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
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);

        btnSave.setOnClickListener(v -> savePlace());
        btnGallery.setOnClickListener(v -> {

            galleryLauncher.launch("image/*");
        });
        btnCamera.setOnClickListener(v -> {

            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED){

                openCamera();
            }
            else{

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        100
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if(requestCode == 100){

            if(grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED){

                openCamera();
            }
        }
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

            // TODO:
            // когато направите login система
            // тук ще идва реалното userId
            place.setUserId(1);

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

                image.setImagePath(
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
    private void openCamera() {

        try {

            File imageFile =
                    File.createTempFile(
                            "place_",
                            ".jpg",
                            getCacheDir()
                    );

            cameraImageUri =
                    FileProvider.getUriForFile(
                            this,
                            getPackageName()
                                    + ".provider",
                            imageFile
                    );

            cameraLauncher.launch(
                    cameraImageUri
            );

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetMultipleContents(),
                    uris -> {
                        if(uris != null){
                            selectedImages.clear();
                            selectedImages.addAll(uris);

                            if(!uris.isEmpty()){

                                imgPreview.setImageURI(
                                        uris.get(0)
                                );
                            }
                        }
                    }
            );

    private final ActivityResultLauncher<Uri>
            cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicture(),
                    success -> {

                        if(success){

                            selectedImages.add(
                                    cameraImageUri
                            );

                            imgPreview.setImageURI(
                                    cameraImageUri
                            );
                        }
                    }
            );
}