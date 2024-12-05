//(Scherer)

package com.trashaway;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import data.ApiService;
import data.LocationData;
import data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddLocation extends AppCompatActivity {

    private EditText et_name;
    private TextView tv_latitude, tv_longitude;
    private Spinner sp_type, sp_icon;
    private Button btn_save;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude = 0.0;
    private double currentLongitude = 0.0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_location);

        //creates new RetrofitClient instance
        apiService = RetrofitClient.getApiService();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check location permission (Scherer)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        et_name = findViewById(R.id.et_name);
        tv_latitude = findViewById(R.id.tv_latitude);
        tv_longitude = findViewById(R.id.tv_longitude);
        sp_type = findViewById(R.id.spinner_type);
        sp_icon = findViewById(R.id.spinner_icon);
        btn_save = findViewById(R.id.btn_save);

        // Load types in spinner (Scherer)
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.type_choices, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_type.setAdapter(adapter1);

        // Load icons in spinner (Scherer)
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.icon_choices, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_icon.setAdapter(adapter2);

        // Button-Listener (Scherer)
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String latitudeStr = tv_latitude.getText().toString();
                String longitudeStr = tv_longitude.getText().toString();
                String typeChoice = sp_type.getSelectedItem().toString();
                String iconChoice = sp_icon.getSelectedItem().toString();

                if (name.isEmpty()) {
                    Toast.makeText(AddLocation.this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Return location data to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("latitude", currentLatitude);
                resultIntent.putExtra("longitude", currentLongitude);
                resultIntent.putExtra("type", typeChoice);
                resultIntent.putExtra("icon", iconChoice);
                setResult(Activity.RESULT_OK, resultIntent);

                LocationData locationDataPackage = new LocationData(name,Double.parseDouble(latitudeStr),Double.parseDouble(longitudeStr),iconChoice);
                createLocationUpdate(locationDataPackage);

                finish();
            }
        });
    }


    public void createLocationUpdate(LocationData locationDataPackage){

        apiService.addLocation(locationDataPackage).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Location added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error adding location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getCurrentLocation() { // (Scherer)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() { // (Scherer)
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();

                    // Update TextViews
                    tv_latitude.setText(String.format("%.6f", currentLatitude));
                    tv_longitude.setText(String.format("%.6f", currentLongitude));
                } else {
                    // Default in case of no location
                    Toast.makeText(AddLocation.this,
                            "Standort konnte nicht abgerufen werden. Bitte Standortdienste aktivieren.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}