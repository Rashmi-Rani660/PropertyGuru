package com.example.propertyguru;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecentActivity extends AppCompatActivity {

    TextView tabSearches, tabViews, tabShortlist, tabContacted;
    TextView[] tabArray;
    View defaultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        tabSearches = findViewById(R.id.tabSearches);
        tabViews = findViewById(R.id.tabViews);
        tabShortlist = findViewById(R.id.tabShortlist);
        tabContacted = findViewById(R.id.tabContacted);
        defaultLayout = findViewById(R.id.defaultLayout);

        tabArray = new TextView[]{tabSearches, tabViews, tabShortlist, tabContacted};

        setTabClickListeners();

        tabSearches.performClick(); // Default open
    }

    private void setTabClickListeners() {
        for (TextView tab : tabArray) {
            tab.setOnClickListener(v -> {
                updateTabStyles((TextView) v);

                int id = v.getId();
                if (id == R.id.tabViews) {
                    openViewsFragment();
                } else if (id == R.id.tabShortlist) {
                    openShortlistFragment();
                } else if (id == R.id.tabContacted) {
                    openContactedFragment();
                } else {
                    showSearchContent();
                }
            });
        }
    }

    private void openViewsFragment() {
        defaultLayout.setVisibility(View.GONE);
        findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new com.example.propertyguru.ViewsFragment())
                .commit();
    }

    private void openShortlistFragment() {
        defaultLayout.setVisibility(View.GONE);
        findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new com.example.propertyguru.ShortlistFragment())
                .commit();
    }

    private void openContactedFragment() {
        defaultLayout.setVisibility(View.GONE);
        findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new com.example.propertyguru.ContactedFragment())
                .commit();
    }

    private void showSearchContent() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.fragmentContainer))
                    .commit();
        }

        defaultLayout.setVisibility(View.VISIBLE);
        findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
    }

    private void updateTabStyles(TextView selectedTab) {
        for (TextView tab : tabArray) {
            if (tab == selectedTab) {
                tab.setBackgroundResource(R.drawable.tab_selected_bg);
                tab.setTextColor(Color.parseColor("#003366"));
            } else {
                tab.setBackgroundResource(R.drawable.tab_unselected_bg);
                tab.setTextColor(Color.parseColor("#000000"));
            }
        }
    }
}
