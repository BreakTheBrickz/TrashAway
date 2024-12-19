package com.trashaway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrashABC_Overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trash_abc_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void altkleiderPressed(View view) {
       Intent i = new Intent(TrashABC_Overview.this,Altkleider.class);
       startActivity(i);
    }

    public void restmüllPressed(View view){

        Intent i = new Intent(TrashABC_Overview.this, Restmuell.class);
        startActivity(i);

    }

    public void backPressed(View view){

        Intent i = new Intent(TrashABC_Overview.this, MainMenu.class);
        startActivity(i);

    }



}