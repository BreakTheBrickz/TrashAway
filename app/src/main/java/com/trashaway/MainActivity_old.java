package com.trashaway;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity_old extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button openTrashABCButton = findViewById(R.id.openTrashABCButton);
        openTrashABCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity_old.this, TrashABC.class);
                startActivity(intent);
            }
        });


    }

    //Mein Name Sebi

    /*-----------------------Stefan&Marcel-----------------------
    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //Hooks
    DrawerLayout = findViewById(R.id.drawer_Layout);
    NavigationView = findViewById(R.id.nav_view);
    toolbar = findViewById(R.id.toolbar);

    //Tool Bar
    setSupportActionBar(toolbar);

    //Navigation Drawer Menu
    ActionbarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
     */

}