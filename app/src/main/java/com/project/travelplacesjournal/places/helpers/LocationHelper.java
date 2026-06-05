package com.project.travelplacesjournal.places.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Consumer;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.project.travelplacesjournal.utils.PermissionHelper;

public class LocationHelper {
    private final AppCompatActivity activity;
    public LocationHelper(AppCompatActivity activity) {
        this.activity = activity;
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation(Consumer<Location> onSuccess) {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(activity);

        if(PermissionHelper.hasLocationPermission(activity)) {
            client.getLastLocation()
                    .addOnSuccessListener(location -> {
                                if(location != null)
                                    onSuccess.accept(location);
                    });
        } else {
            PermissionHelper.requestLocationPermission(activity);
        }
    }

    public void fillLocationFields(EditText etLatitude, EditText etLongitude) {
        getCurrentLocation(location -> {
            etLatitude.setText(String.valueOf(location.getLatitude()));
            etLongitude.setText(String.valueOf(location.getLongitude()));
        });
    }
}
