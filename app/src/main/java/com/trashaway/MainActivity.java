package com.trashaway;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.trashaway.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private TextView tv_score;
    private int score = 0;

    //List for saving locations (Scherer)
    private List<Location> locations;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;


    //Advanced class for location information (Scherer)
    static class Location implements Serializable {
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
        locations.add(new Location("Mülleimer an der Hochschule",47.715887,10.313561,"Restmüll",R.drawable.blue_icon));
        locations.add(new Location("Christophs Biotonne",47.718511,10.318771,"Biomüll",R.drawable.black_icon));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                // Check location permission (Scherer)

        checkForPermission();
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() { // (Scherer)
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    moveCameraToLocation(currentLatitude,currentLongitude);

                } else {
                    // Default in case of no location
                    Toast.makeText(MainActivity.this,
                            "Standort konnte nicht abgerufen werden. Bitte Standortdienste aktivieren.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateMap();

        mMap.setOnMarkerClickListener(marker -> { // (Scherer)
            // Extract data from the marker
            String name = marker.getTitle();
            double latitude = marker.getPosition().latitude;
            double longitude = marker.getPosition().longitude;

            Location selectedLocation = findLocationByMarker(marker);
            String type = selectedLocation.type;
            int iconResId = selectedLocation.iconResId;

            // Pass data to the CheckLocation activity (Scherer)
            Intent intent = new Intent(MainActivity.this, CheckLocation.class);
            intent.putExtra("name", name);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("type", type);
            intent.putExtra("icon", iconResId);
            startActivity(intent);

            return true; // Marks that the click has been processed
        });
    }

    // Process return from AddLocation (Scherer)
    private ActivityResultLauncher<Intent> addLocationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        double latitude = data.getDoubleExtra("latitude", 0);
                        double longitude = data.getDoubleExtra("longitude", 0);
                        String type = data.getStringExtra("type");
                        String icon_id = data.getStringExtra("icon");

                        int iconResId = getIconResource(icon_id);
                        locations.add(new Location(name, latitude, longitude, type, iconResId));
                        moveCameraToLocation(latitude,longitude);
                        updateMap();
                    }
                }
            }
    );

    // Update map with current locations (Scherer)
    private void updateMap() {
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

    // Move camera to newest location
    private void moveCameraToLocation(double latitude, double longitude){
        LatLng newLocation = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLocation,17));
    }

    // Function to select type based on name (Scherer)
    private String getTypeResource(String type) {
        switch (type) {
            case "Mülleimer": return "Mülleimer";
            case "Wertstoffhof": return "Wertstoffhof";
            case "Müllinsel": return "Müllinsel";
            default: return "Keine Angabe"; // Default, in case of no selection
        }
    }

    // Function to select icon based on name (Scherer)
    private int getIconResource(String icon_id) {
        switch (icon_id) {
            case "yellow_icon": return R.drawable.yellow_icon;
            case "blue_icon": return R.drawable.blue_icon;
            case "black_icon": return R.drawable.black_icon;
            default: return R.drawable.profile_icon; // Default, in case of no selection
        }
    }

    // Safe locations list (Scherer)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("locations", new ArrayList<>(locations));
    }
    // Restore locations list (Scherer)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            locations = (List<Location>) savedInstanceState.getSerializable("locations");
            updateMap();
        }
    }

    private Location findLocationByMarker(Marker marker) {
        for (Location location : locations) {
            if (marker.getTitle() != null && marker.getTitle().equals(location.name)) {
                return location;
            }
        }
        return null;
    }

    public void addLocationPressed(View view){ // (Scherer)
        Intent newIntent = new Intent(MainActivity.this, AddLocation.class);
        addLocationLauncher.launch(newIntent);
    }

    //Start Main Menu (Witzigmann)
    public void menuPressed(View v){
        //Launch Menu
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }

    private void checkForPermission(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
                enableLiveLocation();
        }

    }

    private void enableLiveLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    public void onCenterLocation(View view){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        enableLiveLocation();
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<android.location.Location>() { // (Scherer)
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    moveCameraToLocation(currentLatitude, currentLongitude);

                }

            }

        });

    }
}