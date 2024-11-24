package com.trashaway;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trashaway.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int ADD_LOCATION_REQUEST = 1;

    //List for saving locations (Scherer)
    private static List<Location> locations;


    //Advanced class for location information (Scherer)
    static class Location {
        String name;
        double latitude;
        double longitude;
        String type;
        int iconResId;

        Location(String name, double latitude, double longitude, String type, int iconResId) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.type = type;
            this.iconResId = iconResId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Add locations (with name, coordinates, type, icon) (Scherer)
        locations = new ArrayList<>();
        locations.add(new Location("Trashbin Dave", 47.7245, 10.3146, "Weizenabfall", R.drawable.yellow_icon));
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
        updateMap();

        //Add locations as marker on map (Scherer)
        for (Location location : locations) {
            LatLng latLng = new LatLng(location.latitude, location.longitude);
        }
    }

    // Update map with current locations (Scherer)
    public static void updateMap() {
        if (mMap == null) return;

        mMap.clear();
        for (Location location : locations) {
            LatLng latLng = new LatLng(location.latitude, location.longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(location.name)
                    .snippet(location.type)
                    .icon(BitmapDescriptorFactory.fromResource(location.iconResId)));
        }
    }

    // Process return from AddLocation (Scherer)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_LOCATION_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String icon_id = data.getStringExtra("icon");

            // Select icon
            int iconResId = getIconResource(icon_id);

            // Add new location and update map
            locations.add(new Location(name, latitude, longitude, "Benutzerdefiniert", iconResId));
            updateMap();
        }
    }

    // Function to select icon based on name (Scherer)
    private int getIconResource(String icon_id) {
        switch (icon_id) {
            case "yellow_icon": return R.drawable.yellow_icon;
            case "blue_icon": return R.drawable.blue_icon;
            case "black_icon": return R.drawable.black_icon;
            default: return R.drawable.throw_away_icon; // Default, in case of no selection
        }
    }

    public void addLocationPressed(View view){
        Intent newIntent = new Intent(MainActivity.this, AddLocation.class);
        MainActivity.this.startActivity(newIntent);
    }
}