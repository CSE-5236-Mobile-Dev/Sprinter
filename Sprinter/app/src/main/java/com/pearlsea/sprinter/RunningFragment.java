package com.pearlsea.sprinter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import android.Manifest.permission;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RunningFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in {@link
     * #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    boolean mapReady = false;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        startLocationGathering();
    }

    //region Handle Map Updates
    private RunActivity parentActivity;
    private Handler handler = new Handler();

    /* Grab Location Data Every 5 Seconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Set the Parent Activity
            parentActivity = (RunActivity) getActivity();

            if (parentActivity != null) {
                parentActivity.getLastLocation();
                double latitude = parentActivity.latitude;
                double longitude = parentActivity.longitude;

                Log.d("RunningFragment", latitude + " " + longitude);
            }

            handler.postDelayed(this, 5000); // Call this runnable again after 5 seconds
        }
    };

    private boolean isRunning = false; // Flag to track the state of the location gathering

    /* Resume Fragment - Resume Data Collection */
    @Override
    public void onResume() {
        super.onResume();
        if (isRunning) {
            handler.postDelayed(runnable, 5000); // Call the runnable for the first time
        }
    }

    /* Stop Location Gathering when the Fragment is Paused */
    @Override
    public void onPause() {
        super.onPause();
        stopLocationGathering(); // Stop the runnable when the fragment is paused or stopped
    }

    /* Stop Location Gathering when the Fragment is Stopped */
    @Override
    public void onStop() {
        super.onStop();
        stopLocationGathering();
    }

    /* Start Control for Gathering Run Data */
    public void startLocationGathering() {
        if (!isRunning) {
            handler.postDelayed(runnable, 5000); // Call the runnable for the first time
            isRunning = true;
        }
    }

    /* Stop Control for Gathering Run Data */
    public void stopLocationGathering() {
        handler.removeCallbacks(runnable); // Stop the runnable
        isRunning = false;
    }
    //endregion
}