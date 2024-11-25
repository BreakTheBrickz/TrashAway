// (Scherer)

package com.trashaway;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageView;
import android.widget.TextView;

public class CheckLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location);

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
}