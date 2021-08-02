package gr.dimitra.mobiletest.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.dimitra.mobiletest.R;
import gr.dimitra.mobiletest.adapters.CustomMarkerInfoWindowView;
import gr.dimitra.mobiletest.calls.Meters;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Meters meters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (getIntent().hasExtra("meter")){
            meters = (Meters) getIntent().getSerializableExtra("meter");
            Log.d("dimimall",new Double(meters.getLat())+" "+new Double(meters.getLng()));
        }
        // Add a marker in Sydney and move the camera
        CustomMarkerInfoWindowView customMarkerInfoWindowView = new CustomMarkerInfoWindowView(MapsActivity.this);
        mMap.setInfoWindowAdapter(customMarkerInfoWindowView);

        LatLng sydney = new LatLng(new Double(meters.getLat()), new Double(meters.getLng()));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);

        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(meters);
        marker.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));

    }

}