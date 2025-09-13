package com.example.propertyguru;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.propertyguru.fragments.BrokerDashboardFragment;
import com.example.propertyguru.fragments.BuilderDashboardFragment;
import com.example.propertyguru.fragments.OwnerDashboardFragment;

public class DashboardActivity extends AppCompatActivity {

    public static final String USER_TYPE_KEY = "USER_TYPE";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get the user type from intent
        String userType = getIntent().getStringExtra("USER_TYPE");

        Fragment selectedFragment;

        if ("owner".equalsIgnoreCase(userType)) {
            selectedFragment = new OwnerDashboardFragment();
        } else if ("broker".equalsIgnoreCase(userType)) {
            selectedFragment = new BrokerDashboardFragment();
        } else if ("builder".equalsIgnoreCase(userType)) {
            selectedFragment = new BuilderDashboardFragment();
        } else {
            selectedFragment = new OwnerDashboardFragment(); // Default fallback
        }

        // Show selected fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dashboardContainer, selectedFragment)
                .commit();
    }
}
