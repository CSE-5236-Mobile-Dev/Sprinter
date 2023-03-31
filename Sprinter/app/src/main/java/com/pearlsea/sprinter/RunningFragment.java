package com.pearlsea.sprinter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.internal.zzag;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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

    //region Map Helpers
    BitmapDescriptor locationIcon;

    BitmapDescriptor getLocationIcon() {
        if (locationIcon != null) {
            return locationIcon;
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mapmarker);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
            locationIcon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);
            return locationIcon;
        }
    }

    public class RunPoint {
        public LatLng position;
        public Date time;

        public RunPoint(LatLng position, Date time) {
            this.position = position;
            this.time = time;
        }
    }
    List<RunPoint> currentRun = new ArrayList<RunPoint>();

    private void populateSampleRun() {
        // Start position
        LatLng startPosition = new LatLng(37.7749, -122.4194); // San Francisco, CA
        Date startTime = new Date();

        // Populate the list with sample data
        for (int i = 0; i < 10; i++) {
            // Generate a random offset for the position
            double latOffset = Math.random() * 0.001 - 0.0005;
            double lngOffset = Math.random() * 0.001 - 0.0005;

            // Calculate the new position
            LatLng newPosition = new LatLng(
                    startPosition.latitude + latOffset,
                    startPosition.longitude + lngOffset
            );

            // Calculate the new time
            Date newTime = new Date(startTime.getTime() + i * 5000);

            // Create a new RunPoint and add it to the list
            currentRun.add(new RunPoint(newPosition, newTime));
        }
    }

    private boolean useSampleData = true;
    private RunActivity parentActivity;
    private int sampleIndex = 0;
    private RunPoint retrieveLocation() {
        if (useSampleData) {
            /* Send out a stream of sample data for easier debugging */
            if (currentRun.size() == 0) populateSampleRun();
            if (sampleIndex < currentRun.size()) {
                RunPoint current = currentRun.get(sampleIndex);
                sampleIndex++;
                return current;
            }

        }
        else {
            /* Use Device Location for Real Simulation */

            parentActivity = (RunActivity) getActivity();
            if (parentActivity != null) {
                parentActivity.getLastLocation();
                double latitude = parentActivity.latitude;
                double longitude = parentActivity.longitude;
                LatLng latestPosition = new LatLng(latitude, longitude);

                RunPoint location = new RunPoint(latestPosition, Calendar.getInstance().getTime());

                /* Add Location to the Run */
                currentRun.add(location);

                return location;
            }
        }

        // out of places or error - send 0,0
        // TODO: end run if this point is reached
        return new RunPoint(new LatLng(0,0), Calendar.getInstance().getTime());
    }

    private void createPolyLine() {
    }

    //endregion

    //region Handle Map Updates

    private Handler handler = new Handler();

    /* Grab Location Data Every 5 Seconds */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            /* Retrieve Location */
            RunPoint location = retrieveLocation();

            /* Log Location */
            Log.d("RunningFragment", location.position.latitude + " " + location.position.longitude);

            /* Move Camera to Latest Position and Create a Marker */
            MarkerOptions m = new MarkerOptions();
            m.icon(getLocationIcon()); // Needs Bitmap Descriptor
            m.position(location.position);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.position, 18));

            /* Clear Map then Update Current Position and Polyline */
            mMap.clear();
            mMap.addMarker(m);

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
        } else {
            startLocationGathering();
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