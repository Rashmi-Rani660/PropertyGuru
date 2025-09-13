package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.propertyguru.fragments.BrokerDashboardFragment;
import com.example.propertyguru.fragments.OwnerDashboardFragment;
import com.example.propertyguru.fragments.BuilderDashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ImageView searchBar, menuIcon, bellIcon;
    private TextView postProperty;
    private BottomNavigationView bottomNav;

    private boolean isOwnerTabSelected = true; // default to Owner tab
    private String selectedRole = "owner";     // default role

    private View header; // reference to header view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // View bindings
        searchBar = findViewById(R.id.searchBar);
        menuIcon = findViewById(R.id.menuIcon);
        bellIcon = findViewById(R.id.bellIcon);
        postProperty = findViewById(R.id.postPropertyText);
        bottomNav = findViewById(R.id.bottomNav);
        header = findViewById(R.id.header_home); // header layout

        // Receive role from intent (optional)
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("role")) {
            selectedRole = intent.getStringExtra("role");
        }

        boolean openOnlyDashboard = intent.getBooleanExtra("openOnlyDashboard", false);

        if (openOnlyDashboard) {
            if (header != null) header.setVisibility(View.GONE);

            Fragment fragment;

            switch (selectedRole.toLowerCase()) {
                case "broker":
                    fragment = new BrokerDashboardFragment();
                    break;
                case "builder":
                    fragment = new BuilderDashboardFragment();
                    break;
                case "owner":
                default:
                    fragment = new OwnerDashboardFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFragmentContainer, fragment)
                    .commit();
        }


        // Menu icon click → Load dashboard fragment
        menuIcon.setOnClickListener(v -> {
            // Hide header view
            if (header != null) {
                header.setVisibility(View.GONE);
            }

            Fragment fragment;

            switch (selectedRole.toLowerCase()) {
                case "broker":
                    fragment = new BrokerDashboardFragment();
                    break;
                case "builder":
                    fragment = new BuilderDashboardFragment();
                    break;
                case "owner":
                default:
                    fragment = new OwnerDashboardFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        // Bell icon click placeholder
        bellIcon.setOnClickListener(v -> {
            // You can launch NotificationActivity here
        });

        // Post Property Button
        postProperty.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, PostPropertyActivity.class);
            i.putExtra("role", selectedRole);
            startActivity(i);
        });

        // Search Bar → City selection
        searchBar.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, SelectCityActivity.class);
            startActivity(i);
        });

        // Bottom Navigation Logic
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Show header again
                if (header != null) {
                    header.setVisibility(View.VISIBLE);
                }
                // If needed, you can load a home fragment here too
                return true;

            } else if (itemId == R.id.nav_responses) {
                startActivity(new Intent(this, ViewResponseActivity.class));
                return true;

            } else if (itemId == R.id.nav_activity) {
                startActivity(new Intent(this, RecentActivity.class));
                return true;

            } else if (itemId == R.id.nav_profile) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                i.putExtra("role", selectedRole);
                startActivity(i);
                return true;
            }

            return false;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Show header when returning from fragment
        if (header != null) {
            header.setVisibility(View.VISIBLE);
        }
    }
}
