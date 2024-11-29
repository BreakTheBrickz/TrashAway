// (Scherer)

package com.trashaway;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CheckLocation extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tv_status;
    private Button btn_throw_away;
    private static final double NEARBY_RADIUS = 100; // setting radius
    private double targetLatitude, targetLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location);

        tv_status = findViewById(R.id.tv_status);
        btn_throw_away = findViewById(R.id.btn_throw_away);

        targetLatitude = getIntent().getDoubleExtra("latitude",0.0);
        targetLongitude = getIntent().getDoubleExtra("longitude",0.0);

        // Button initially invisible
        btn_throw_away.setVisibility(View.GONE);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            checkProximityToTarget(targetLatitude, targetLongitude, btn_throw_away);
        }

        // Button-Listener for btn_throw_away
        btn_throw_away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return the score to MainActivity and close the activity
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent); // Successful answer
                finish(); // Close activity
            }
        });

        // Get the passed data
        String name = getIntent().getStringExtra("name");
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);
        String type = getIntent().getStringExtra("type");
        int iconResId = getIntent().getIntExtra("iconResId", R.drawable.profile_icon);

        // Reference the views
        TextView nameTextView = findViewById(R.id.location_name);
        TextView coordinatesTextView = findViewById(R.id.location_coordinates);
        TextView typeTextView = findViewById(R.id.location_type);
        ImageView iconImageView = findViewById(R.id.location_icon);

        // Put the data into the views
        nameTextView.setText("Name: " + name);
        coordinatesTextView.setText("Koordinaten: " + latitude + ", " + longitude);
        typeTextView.setText("Typ: " + type);
        iconImageView.setImageResource(iconResId);
    }

    private void checkProximityToTarget(double targetLatitude, double targetLongitude, Button btn_throw_away) {
        // Get current location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();

                    // Calculate distance
                    float distance = calculateDistance(currentLatitude, currentLongitude,
                            targetLatitude, targetLongitude);

                    // Distance verification
                    if (distance <= NEARBY_RADIUS) {
                        tv_status.setText("Du bist in der Nähe des Standorts!");
                        btn_throw_away.setVisibility(View.VISIBLE);
                    } else {
                        tv_status.setText("Du bist zu weit entfernt. Entfernung: " + distance + " Meter");
                        btn_throw_away.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(CheckLocation.this,
                            "Standort konnte nicht abgerufen werden.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Calculating the distance between two coordinates (in meters)
    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] result = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, result);
        return result[0];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkProximityToTarget(targetLatitude, targetLongitude, btn_throw_away);
            } else {
                Toast.makeText(this, "Standortberechtigung erforderlich, um die Nähe zu überprüfen.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}