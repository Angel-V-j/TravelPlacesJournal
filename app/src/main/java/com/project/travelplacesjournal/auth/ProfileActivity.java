package com.project.travelplacesjournal.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.project.travelplacesjournal.R;
import com.project.travelplacesjournal.data.database.AppDatabase;
import com.project.travelplacesjournal.data.database.DatabaseProvider;
import com.project.travelplacesjournal.data.entities.User;
import com.project.travelplacesjournal.utils.SessionManager;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivAvatar;
    private TextView tvEmail, tvRole;
    private EditText etFirstName, etLastName, etBio;
    private Button btnSave, btnLogout;

    private AppDatabase db;
    private SessionManager sessionManager;
    private User currentUser;
    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent>cameraLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode()== RESULT_OK && result.getData()!=null){
                    Bundle extras =result.getData().getExtras();
                    Bitmap imageBitmap=(Bitmap) extras.get("data");
                    ivAvatar.setImageBitmap(imageBitmap);

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Avatar_" + System.currentTimeMillis(), null);
                    if (path != null) {
                        selectedImageUri = Uri.parse(path);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> galleryLauncher= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result->{
                if (result.getResultCode()== RESULT_OK && result.getData() != null){
                    selectedImageUri=result.getData().getData();
                    ivAvatar.setImageURI(selectedImageUri);
                }
    }
    );

    private final ActivityResultLauncher<String>requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted->{
                if(isGranted){
                    openCamera();
                }else {
                    Toast.makeText(this, "Разрешението за камера е отхвърлено!",Toast.LENGTH_LONG).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = DatabaseProvider.getDatabase(this);
        sessionManager = new SessionManager(this);

        ivAvatar = findViewById(R.id.ivProfileAvatar);
        tvEmail = findViewById(R.id.tvProfileEmail);
        etFirstName = findViewById(R.id.etProfileFirstName);
        etLastName = findViewById(R.id.etProfileLastName);
        etBio = findViewById(R.id.etProfileBio);
        tvRole = findViewById(R.id.tvProfileRole);
        btnSave = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnProfileLogout);

        loadUserProfile();
        ivAvatar.setOnClickListener((v->showImageSourceDialog()));
        btnSave.setOnClickListener(v->saveProfileChanges());
        btnLogout.setOnClickListener(v->{
            sessionManager.logoutUser();
            Intent intent= new Intent(ProfileActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void saveProfileChanges() {
        if (currentUser==null){
            return;
        }

        String firstName= etFirstName.getText().toString().trim();
        String lastName=etLastName.getText().toString().trim();
        String bioText= etBio.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty()){
            Toast.makeText(this, "Полетата Име и Фамилия са задължителни!", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setBio(bioText);
        if (selectedImageUri!= null){
            currentUser.setAvatarPath(selectedImageUri.toString());
        }

        new Thread(()->{
            db.userDao().update(currentUser);
            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Профилът бе обновен успешно!", Toast.LENGTH_SHORT).show());
        }).start();
    }

    private void showImageSourceDialog() {
        String[]options ={"Снимай с Камера", "Избери от Галерия"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Избери аватар от:");
        builder.setItems(options,(dialog, which)->{
            if (which==0){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            } else if (which == 1) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });
        builder.show();

    }

    private void loadUserProfile() {
        int userId = sessionManager.getUserId();

        new Thread(() -> {
            currentUser = db.userDao().getById(userId);

            if (currentUser != null) {
                runOnUiThread(() -> {
                    tvEmail.setText("Имейл: " + currentUser.getEmail());
                    etFirstName.setText(currentUser.getFirstName());
                    etLastName.setText(currentUser.getLastName());
                    etBio.setText(currentUser.getBio());

                    if (currentUser.getAvatarPath() != null) {
                        ivAvatar.setImageURI(Uri.parse(currentUser.getAvatarPath()));
                    }

                    if (currentUser.isAdmin()) {
                        tvRole.setText("Роля: Админ");
                        tvRole.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                    } else {
                        tvRole.setText("Роля: Потребител ");
                    }
                });
            }
        }).start();
    }

    private void openCamera() {
        Intent takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            cameraLauncher.launch(takePictureIntent);
        }
    }
}
