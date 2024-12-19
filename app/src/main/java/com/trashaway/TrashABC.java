package com.trashaway;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;


//Reichart

public class TrashABC extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_abc); // Verknüpfung mit dem Layout
    }
}


/*

public class TrashABC extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_abc_altkleider);

        // TextView für OKL-Liste
        TextView oklPointsList = findViewById(R.id.dos_altkleider_schuhe_list);

        // String aus der Ressourcen-Datei laden
        String altkleiderString = getString(R.string.dos_list_altkleider_und_schuhe);

        // String in einzelne Punkte aufteilen
        String[] points = altkleiderString.split(";");

        // Neuen String mit Zeilenumbrüchen und Strichen erstellen
        StringBuilder formattedPoints = new StringBuilder();
        for (String point : points) {
            formattedPoints.append("• ").append(point.trim()).append("\n");
        }

        // Formatierten Text in die TextView setzen
        oklPointsList.setText(formattedPoints.toString().trim());
    }
}


/*

public class TrashABC extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trash_abc);

        // TextViews für Do's und Don'ts
        TextView dos_list_altkleider_und_schuhe = findViewById(R.id.dos_list_altkleider_und_schuhe);
        TextView donts_List = findViewById(R.id.donts_list_restmuell);

        // Strings aus strings.xml laden
        String dosString = getString(R.string.dos_list_altkleider_und_schuhe);
        String dontsString = getString(R.string.donts_list_altkleider_und_schuhe);

        // String in Zeilen umwandeln
        String formattedDos = formatStringToBulletList(dosString);
        String formattedDonts = formatStringToBulletList(dontsString);

        // Formatierten Text in TextViews setzen
        dosList.setText(formattedDos);
        donts_List.setText(formattedDonts);
    }

    // Methode, um den Text basierend auf dem Trennzeichen zu formatieren
    private String formatStringToBulletList(String input) {
        String[] items = input.split(";"); // Aufteilen basierend auf Semikolon
        StringBuilder formattedList = new StringBuilder();
        for (String item : items) {
            formattedList.append("• ").append(item.trim()).append("\n"); // Punkt hinzufügen
        }
        return formattedList.toString().trim(); // Unnötige Leerzeichen entfernen
    }
}


 */