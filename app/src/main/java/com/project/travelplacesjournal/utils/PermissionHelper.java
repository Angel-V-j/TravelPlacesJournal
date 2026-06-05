package com.project.travelplacesjournal.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {
    public static final int CAMERA_PERMISSION_REQUEST = 100;
    public static final int LOCATION_PERMISSION_REQUEST = 200;
    public static boolean hasCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[] {
                        Manifest.permission.CAMERA
                },
                CAMERA_PERMISSION_REQUEST);
    }
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public static boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[] {Manifest.permission
                        .ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
    }
}
