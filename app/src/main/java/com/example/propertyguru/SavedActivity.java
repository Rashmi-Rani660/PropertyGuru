package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SavedActivity extends AppCompatActivity {

    private LinearLayout tabSaved, tabSeen, tabContacted, tabRecent;
    private ImageView backButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved); // your layout file name

        // Initialize views
        tabSaved = findViewById(R.id.tabSaved);
        tabSeen = findViewById(R.id.tabSeen);
        tabContacted = findViewById(R.id.tabContacted);
        tabRecent = findViewById(R.id.tabRecent);
        backButton = findViewById(R.id.backButton);
        searchButton = findViewById(R.id.searchButton);

        // Set tab click listeners
        tabSaved.setOnClickListener(v -> setSelectedTab(tabSaved));
        tabSeen.setOnClickListener(v -> setSelectedTab(tabSeen));
        tabContacted.setOnClickListener(v -> setSelectedTab(tabContacted));
        tabRecent.setOnClickListener(v -> setSelectedTab(tabRecent));

        // Back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Start new search button
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(SavedActivity.this, SelectCityActivity.class);
            startActivity(intent);
        });

        // Set default selected tab
        setSelectedTab(tabSaved);
    }

    private void setSelectedTab(LinearLayout selectedTab) {
        tabSaved.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unselected_tab));
        tabSeen.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unselected_tab));
        tabContacted.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unselected_tab));
        tabRecent.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unselected_tab));

        if (selectedTab != null) {
            selectedTab.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_selected_tab));
        }
    }
}
