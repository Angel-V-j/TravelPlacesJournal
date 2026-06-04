package com.project.travelplacesjournal.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

public class ImageUtils {
    public static ImageView createImagePreview(Context context, Uri uri) {

        ImageView imageView =
                new ImageView(context);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        250,
                        250
                );

        params.setMargins(
                8,
                8,
                8,
                8
        );

        imageView.setLayoutParams(params);

        imageView.setScaleType(
                ImageView.ScaleType.CENTER_CROP
        );

        imageView.setImageURI(uri);

        return imageView;
    }

    public static LinearLayout createRemovableImagePreview(Context context, Uri uri,
                                                           List<Uri> selectedImages,
                                                           LinearLayout layoutImages) {

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        ImageView imageView = createImagePreview(context, uri);

        Button btnRemove = new Button(context);
        btnRemove.setText("Remove");
        btnRemove.setOnClickListener(v ->
                removeImage(uri, container, selectedImages, layoutImages));

        container.addView(imageView);
        container.addView(btnRemove);

        return container;
    }

    private static void removeImage(Uri uri, LinearLayout preview,
            List<Uri> selectedImages,
            LinearLayout layoutImages) {

        selectedImages.remove(uri);
        layoutImages.removeView(preview);
    }

    public static Uri getCamImgUri(Context context, Activity activity) {
        if(PermissionHelper.hasCameraPermission(context)){
            return createCamImgUri(context);
        }
        else{
            PermissionHelper.requestCameraPermission(activity);
        }

        return null;
    }

    public static Uri createCamImgUri(Context context) {
        try {
            File imageFile =
                    File.createTempFile(
                            "place_",
                            ".jpg",
                            context.getCacheDir()
                    );

            return FileProvider.getUriForFile(
                            context,
                            context.getPackageName() + ".provider",
                            imageFile
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
