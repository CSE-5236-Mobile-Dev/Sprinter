package com.pearlsea.sprinter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class SummaryFragment extends Fragment  implements OnMapReadyCallback {

    List<RunningFragment.RunPoint> run;
    TextView distance;
    TextView time;

    public SummaryFragment(List<RunningFragment.RunPoint> run) {
        this.run = run;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        distance = rootView.findViewById(R.id.distanceTextEntry);
        time = rootView.findViewById(R.id.timeTextEntry);

        return rootView;
    }

    private PolylineOptions constructCurrentPolyline() {
        PolylineOptions options = new PolylineOptions()
                .clickable(false)
                .color(Color.parseColor("#ffa366"))
                .width(10);

        for (RunningFragment.RunPoint r : run) {
            options.add(r.position);
        }

        return options;
    }

    public static String getTimeDifference(Date date1, Date date2) {
        long timeDifferenceMillis = Math.abs(date1.getTime() - date2.getTime());
        long hours = timeDifferenceMillis / (60 * 60 * 1000);
        long minutes = (timeDifferenceMillis / (60 * 1000)) % 60;
        long seconds = (timeDifferenceMillis / 1000) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


    private GoogleMap mMap;
    private boolean mapReady;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mapReady = true;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        Polyline p = mMap.addPolyline(constructCurrentPolyline());
        double polylineLength = SphericalUtil.computeLength(p.getPoints());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(run.get(0).position, 18));

        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(polylineLength / 1609.344);
        distance.setText(formatted + " Miles");

        time.setText(getTimeDifference(run.get(0).time, run.get(run.toArray().length - 1).time));

    }
}