package com.trashaway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        constraintLayout = findViewById(R.id.main_menu);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    //Toast.makeText(MainMenu.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainMenu.this, MainActivity.class);
                    startActivity(i);
                }
                else if (item.getItemId() == R.id.settings) {
                    Toast.makeText(MainMenu.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(MainMenu.this, Settings.class);
                    //startActivity(i);
                }
                else if (item.getItemId() == R.id.trash_abc) {
                    //Toast.makeText(MainMenu.this, "Trash ABC Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainMenu.this, TrashABC_Overview.class);
                    startActivity(i);
                }
                else if (item.getItemId() == R.id.login) {
                    Toast.makeText(MainMenu.this, "Login Selected", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(MainMenu.this, Login.class);
                    //startActivity(i);
                }
                else if (item.getItemId() == R.id.logout) {
                    Toast.makeText(MainMenu.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(MainMenu.this, Logout.class);
                    //startActivity(i);
                }
                else if (item.getItemId() == R.id.profile) {
                    Toast.makeText(MainMenu.this, "Your Profile Selected", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(MainMenu.this, Profile.class);
                    //startActivity(i);
                }
                else if (item.getItemId() == R.id.throw_away) {
                    Toast.makeText(MainMenu.this, "Throw away Selected", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(MainMenu.this, CheckLocation.class); <-- ?
                    //startActivity(i);
                }
                else if (item.getItemId() == R.id.add_bin) {
                    //Toast.makeText(MainMenu.this, "Add Bin Selected", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainMenu.this, AddLocation.class);
                    startActivity(i);
                }
                return false;
            }
        });
    }
}