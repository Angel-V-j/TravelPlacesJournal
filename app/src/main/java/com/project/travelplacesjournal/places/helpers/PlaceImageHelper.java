package com.project.travelplacesjournal.places.helpers;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.project.travelplacesjournal.utils.ImageUtils;
import com.project.travelplacesjournal.utils.PermissionHelper;

import java.util.List;

public class PlaceImageHelper {
    private final AppCompatActivity activity;

    private final LinearLayout layoutImages;

    private final List<Uri> selectedImages;

    private Uri cameraImageUri;

    private final ActivityResultLauncher<String> galleryLauncher;

    private final ActivityResultLauncher<Uri> cameraLauncher;

    public PlaceImageHelper(AppCompatActivity activity,
            LinearLayout layoutImages,
            List<Uri> selectedImages) {

        this.activity = activity;
        this.layoutImages = layoutImages;
        this.selectedImages = selectedImages;

        galleryLauncher = activity.registerForActivityResult(
                        new ActivityResultContracts.GetMultipleContents(),
                        this::handleGalleryImages);

        cameraLauncher = activity.registerForActivityResult(
                        new ActivityResultContracts.TakePicture(),
                        this::handleCameraResult);
    }

    public void openGallery() {
        galleryLauncher.launch("image/*");
    }

    public void openCamera() {
        if(PermissionHelper.hasCameraPermission(activity)) {
            cameraImageUri = ImageUtils.createCamImgUri(activity);
            if(cameraImageUri != null) {
                cameraLauncher.launch(cameraImageUri);
            }
        } else {
            PermissionHelper.requestCameraPermission(activity);
        }
    }

    private void handleGalleryImages(List<Uri> uris) {
        if(uris == null)
            return;

        for(Uri uri : uris){
            Uri locUri = ImageUtils.copyImgToAppStr(activity, uri);
            if (locUri != null) {
                selectedImages.add(locUri);
                addImagePreview(locUri);
            }
        }
    }

    private void handleCameraResult(Boolean success) {
        if(Boolean.TRUE.equals(success)){
            selectedImages.add(cameraImageUri);
            addImagePreview(cameraImageUri);
        }
    }

    public void addImagePreview(Uri uri) {
        LinearLayout preview = ImageUtils.createRemovableImagePreview(
                activity,
                uri,
                selectedImages,
                layoutImages);

        layoutImages.addView(preview);
    }
    public void handlePermissionResult(int requestCode, int[] grantResults) {
        if(requestCode == PermissionHelper.CAMERA_PERMISSION_REQUEST){
            if(grantResults.length > 0 && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
        }
    }
}
