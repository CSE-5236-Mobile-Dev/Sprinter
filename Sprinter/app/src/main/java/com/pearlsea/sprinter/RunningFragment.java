package com.pearlsea.sprinter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

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

//    public RunPoint generateSampleRunPoint() {
//        // Generate a random position within a small radius
//        double lat = 37.7749 + (Math.random() * 0.01 - 0.005);
//        double lng = -122.4194 + (Math.random() * 0.01 - 0.005);
//        LatLng position = new LatLng(lat, lng);
//
//        // Generate a random time within the last hour
//        Date time = new Date(System.currentTimeMillis() - (long) (Math.random() * 60 * 60 * 1000));
//
//        // Create and return a new RunPoint
//        return new RunPoint(position, time);
//    }

    private LatLng lastPosition = new LatLng(37.7749, -122.4194);
    private Date lastTime = Calendar.getInstance().getTime();

    public RunPoint generateSampleRunPoint() {
        // Generate a new position 25 meters from the last position
        double lat = lastPosition.latitude + (Math.random() * 0.0006 - 0.0003);
        double lng = lastPosition.longitude + (Math.random() * 0.0006 - 0.0003);
        LatLng position = new LatLng(lat, lng);

        // Generate a new time 5 seconds after the last time
        Date time = new Date(lastTime.getTime() + 5000);

        // Update the last position and time
        lastPosition = position;
        lastTime = time;

        // Create and return a new RunPoint
        return new RunPoint(position, time);
    }

    private boolean useSampleData = true;
    private RunActivity parentActivity;
    private RunPoint retrieveLocation() {
        if (useSampleData) {
            /* Send out a stream of sample data for easier debugging */
            RunPoint r = generateSampleRunPoint();
            currentRun.add(r);
            return r;

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

    private PolylineOptions constructCurrentPolyline() {
        PolylineOptions options = new PolylineOptions()
                .clickable(false)
                .color(Color.parseColor("#ffa366"))
                .width(10);

        for (RunPoint r : currentRun) {
            options.add(r.position);
        }

        return options;
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
            Polyline p = mMap.addPolyline(constructCurrentPolyline());
            double polylineLength = SphericalUtil.computeLength(p.getPoints());
            Log.d("RunningFragment", "Traveled: " + polylineLength + " meters.");
            Log.d("RunningFragment", "Traveled: " + polylineLength / 1609.344 + " miles.");

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