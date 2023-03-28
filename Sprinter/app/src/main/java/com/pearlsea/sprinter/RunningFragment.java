package com.pearlsea.sprinter;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.maps.Style;
import com.mapbox.maps.MapView;

import java.util.List;

public class RunningFragment extends Fragment implements PermissionsListener {

    MapView mapView = null;
    private boolean locationGranted = false;
    PermissionsManager permissionsManager;

    public RunningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_running, container, false);

        mapView = rootView.findViewById(R.id.runningMap);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

        /* Setup Location Permissions */
        // Initialize the PermissionsManager
        permissionsManager = new PermissionsManager(this);

        // Check if location permissions are granted
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            // Permission-sensitive logic goes here
            Log.d("RunningFragment", "Permissions Already Granted");
            activateLocationComponent();
        } else {
            // Request location permissions if they are not granted
            Log.d("RunningFragment", "Requesting Location Permissions");
            permissionsManager.requestLocationPermissions(getActivity());
        }

        return rootView;
    }

    //region Map Logic
    private void getUserLocation() {
        // Create a new instance of the FusedLocationProviderClient
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        // Request the user's last known location
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("RunningFragment", "Location Doesn't Have Permission in getUserLocation function");
            return;
        }
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(location -> {
                // Check if the location is null
                if (location == null) {
                    // Location is null, handle the error
                    return;
                }

                // Log the location to the console
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("RunningFragment", "Location Gathered: " + latitude + " " + longitude);
                updateMapBox(latitude, longitude);
            })
            .addOnFailureListener(e -> {
                // Handle the error
                Log.e("RunningFragment", "Failed to Get Location");
            });

    }

    private void updateMapBox(double latitude, double longitude) {

    }

    //endregion

    //region Mapbox Default Required Functions
    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }
    //endregion

    //region Location Permissions
    // Implement the PermissionsListener interface to handle the result of the permission request
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        // Build an alert dialog to explain why the app needs location permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Location Permissions");
        builder.setMessage("This app needs location permissions to show your location on the map.");

        // Add a button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Request the permissions again after the user dismisses the dialog
                permissionsManager.requestLocationPermissions(getActivity());
            }
        });

        // Show the dialog
        builder.show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        Log.d("RunningFragment", "Permission Result Triggered");
        if (granted && mapView != null) {
            // Get User Location
            this.getUserLocation();
        }
    }

    private void activateLocationComponent() {
        // Activate the Maps SDK's LocationComponent to show the device's location
        this.locationGranted = true;

        Log.d("RunningFragment", "Location Component Activated");

        this.getUserLocation();
    }
    //endregion
}