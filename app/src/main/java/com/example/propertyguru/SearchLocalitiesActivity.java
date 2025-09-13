package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchLocalitiesActivity extends AppCompatActivity {

    LinearLayout cardSector73, cardSector150;
    Button btnLandmarkAce, btnLandmarkMedanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_localities);

        TextView tvChangeCity = findViewById(R.id.tvChangeCity);

        tvChangeCity.setOnClickListener(v -> {
            startActivity(new Intent(SearchLocalitiesActivity.this, SelectCityActivity.class));
        });


        // Locality cards
        cardSector73 = findViewById(R.id.cardSector73);
        cardSector150 = findViewById(R.id.cardSector150);

        cardSector73.setOnClickListener(v -> {
            Toast.makeText(this, "Sector 73 selected", Toast.LENGTH_SHORT).show();
        });

        cardSector150.setOnClickListener(v -> {
            Toast.makeText(this, "Sector 150 selected", Toast.LENGTH_SHORT).show();
        });

        // Landmark buttons
        btnLandmarkAce = findViewById(R.id.btnLandmarkAce);
        btnLandmarkMedanta = findViewById(R.id.btnLandmarkMedanta);

        btnLandmarkAce.setOnClickListener(v -> {
            Toast.makeText(this, "Ace City Square", Toast.LENGTH_SHORT).show();
        });

        btnLandmarkMedanta.setOnClickListener(v -> {
            Toast.makeText(this, "Medanta Hospital Site", Toast.LENGTH_SHORT).show();
        });
    }
}
