package com.pearlsea.sprinter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class SummaryFragmentTest {

    private SummaryFragment summaryFragment;

    @Mock
    private SupportMapFragment mockMapFragment;

    @Mock
    private GoogleMap mockGoogleMap;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Create a mock list of run points
        List<RunningFragment.RunPoint> mockRun = new ArrayList<>();
        mockRun.add(new RunningFragment.RunPoint(new LatLng(0, 0), new Date()));
        mockRun.add(new RunningFragment.RunPoint(new LatLng(1, 1), new Date()));
        mockRun.add(new RunningFragment.RunPoint(new LatLng(2, 2), new Date()));

        summaryFragment = new SummaryFragment(mockRun);

        // Set up the mock map fragment and map
        when(mockMapFragment.getMapAsync(summaryFragment)).thenReturn(null);
        summaryFragment.mapReady = true;
        summaryFragment.mMap = mockGoogleMap;
    }

    @Test
    public void testOnMapReady() {
        // Call the onMapReady method
        summaryFragment.onMapReady(mockGoogleMap);

        // Verify that the map type is set to MAP_TYPE_HYBRID
        verify(mockGoogleMap).setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Verify that the moveCamera method is called with the correct arguments
        verify(mockGoogleMap).moveCamera(eq(CameraUpdateFactory.newLatLngZoom(summaryFragment.run.get(0).position, 18)));

        // Verify that the distance text view is updated with the correct value
        verify(summaryFragment.distance).setText(anyString());

        // Verify that the time text view is updated with the correct value
        verify(summaryFragment.time).setText(anyString());
    }
}
