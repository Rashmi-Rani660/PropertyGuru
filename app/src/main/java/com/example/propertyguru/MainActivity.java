package com.example.propertyguru;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    Button tabAll, tabProjects, tabHousingVerified, tabNewLaunches, tabReadyToMove, tabOwnerProperties, tabYouChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabAll = findViewById(R.id.tabAll);
        tabProjects = findViewById(R.id.tabProjects);
        tabHousingVerified = findViewById(R.id.tabHousingVerified);
        tabNewLaunches = findViewById(R.id.tabNewLaunches);
        tabReadyToMove = findViewById(R.id.tabReadyToMove);
        tabOwnerProperties = findViewById(R.id.tabOwnerProperties);
        tabYouChoice = findViewById(R.id.tabYouChoice);

        tabAll.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "All tab selected", Toast.LENGTH_SHORT).show());

        tabProjects.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Projects tab selected", Toast.LENGTH_SHORT).show());

        tabHousingVerified.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Housing Verified tab selected", Toast.LENGTH_SHORT).show());

        tabNewLaunches.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "New Launches tab selected", Toast.LENGTH_SHORT).show());

        tabReadyToMove.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Ready to Move tab selected", Toast.LENGTH_SHORT).show());

        tabOwnerProperties.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Owner Properties tab selected", Toast.LENGTH_SHORT).show());

        tabYouChoice.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Your Choice tab selected", Toast.LENGTH_SHORT).show());
    }
}
