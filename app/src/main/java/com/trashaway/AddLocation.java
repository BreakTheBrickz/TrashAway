//(Scherer)

package com.trashaway;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddLocation extends AppCompatActivity {

    private EditText et_name, et_latitude, et_longitude;
    private Spinner sp_icon;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_location);

        et_name = findViewById(R.id.et_name);
        et_latitude = findViewById(R.id.et_latitude);
        et_longitude = findViewById(R.id.et_longitude);
        sp_icon = findViewById(R.id.spinner_icon);
        btn_save = findViewById(R.id.btn_save);

        // Load icons in spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.icon_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_icon.setAdapter(adapter);

        // Button-Listener
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String latitudeStr = et_latitude.getText().toString();
                String longitudeStr = et_longitude.getText().toString();
                String iconChoice = sp_icon.getSelectedItem().toString();

                if (name.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
                    Toast.makeText(AddLocation.this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Return location data to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("latitude", Double.parseDouble(latitudeStr));
                resultIntent.putExtra("longitude", Double.parseDouble(longitudeStr));
                resultIntent.putExtra("icon", iconChoice);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}