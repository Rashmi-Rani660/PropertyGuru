package com.example.propertyguru;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PostPropertyActivity extends AppCompatActivity {

    // UI for property form tabs
    private TextView tabPropertyDetails, tabPriceDetails;
    private ScrollView propertyDetailsCard, priceDetailsCard;

    // Dashboard tab UI
    private TabLayout tabLayoutDashboard;
    private ViewPager2 viewPagerDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

        // 👉 Init Views
        tabPropertyDetails = findViewById(R.id.tabPropertyDetails);
        tabPriceDetails = findViewById(R.id.tabPriceDetails);
        propertyDetailsCard = findViewById(R.id.propertyDetailsCard);
        priceDetailsCard = findViewById(R.id.priceDetailsCard);
        viewPagerDashboard = findViewById(R.id.viewPagerDashboard);
        tabLayoutDashboard = findViewById(R.id.tabLayoutDashboard);
        View backArrow = findViewById(R.id.backArrow);

        // 👉 Back Arrow Logic
        backArrow.setOnClickListener(view -> {
            if (priceDetailsCard.getVisibility() == View.VISIBLE) {
                switchToPropertyDetails();
            } else {
                finish(); // Exit Activity
            }
        });

        // 👉 Setup ViewPager2 + TabLayout for Dashboard


        // 👉 Check if Dashboard should be shown
        boolean showDashboard = getIntent().getBooleanExtra("showDashboard", false);
        String role = getIntent().getStringExtra("role");

        if (showDashboard) {
            // Show Dashboard Only
            propertyDetailsCard.setVisibility(View.GONE);
            priceDetailsCard.setVisibility(View.GONE);
            viewPagerDashboard.setVisibility(View.VISIBLE);
            tabLayoutDashboard.setVisibility(View.VISIBLE);

            if (role != null) {
                switch (role) {
                    case "owner":
                        viewPagerDashboard.setCurrentItem(0, false);
                        break;
                    case "broker":
                        viewPagerDashboard.setCurrentItem(1, false);
                        break;
                    case "builder":
                        viewPagerDashboard.setCurrentItem(2, false);
                        break;
                }
            }
        } else {
            // 🟣 Show Property Details Flow
            viewPagerDashboard.setVisibility(View.GONE);
            tabLayoutDashboard.setVisibility(View.GONE);
            switchToPropertyDetails();

            // Load first fragment (Basic Details)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.propertyDetailsContainer, new BasicDetailsFragment())
                    .commit();
        }

        // 👉 Tab Click Listeners
        tabPropertyDetails.setOnClickListener(view -> switchToPropertyDetails());
        tabPriceDetails.setOnClickListener(view -> {
            Toast.makeText(this, "Please fill property details first", Toast.LENGTH_SHORT).show();
        });
    }

    // ✅ Show Property Details Fragment
    private void switchToPropertyDetails() {
        propertyDetailsCard.setVisibility(View.VISIBLE);
        priceDetailsCard.setVisibility(View.GONE);

        tabPropertyDetails.setBackgroundResource(R.drawable.tab_selected);
        tabPropertyDetails.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        tabPriceDetails.setBackgroundResource(R.drawable.tab_unselected);
        tabPriceDetails.setTextColor(ContextCompat.getColor(this, R.color.grayText));
    }

    // ✅ Show Price Details Fragment
    public void switchToPriceDetails() {
        propertyDetailsCard.setVisibility(View.GONE);
        priceDetailsCard.setVisibility(View.VISIBLE);

        tabPriceDetails.setBackgroundResource(R.drawable.tab_selected);
        tabPriceDetails.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        tabPropertyDetails.setBackgroundResource(R.drawable.tab_unselected);
        tabPropertyDetails.setTextColor(ContextCompat.getColor(this, R.color.grayText));

        Toast.makeText(this, "Now fill Price Details", Toast.LENGTH_SHORT).show();
    }
}
