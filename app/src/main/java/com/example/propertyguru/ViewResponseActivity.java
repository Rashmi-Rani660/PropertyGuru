package com.example.propertyguru;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewResponseActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_response);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewResponsePagerAdapter adapter = new ViewResponsePagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("All Responses");
            else tab.setText("All Respondents");
        }).attach();

        // ✅ Handle back navigation from LeadDetailsFragment
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (fragment == null) {
                // Show correct RecyclerView again
                RecyclerView recyclerResponses = findViewById(R.id.recyclerResponses);
                RecyclerView recyclerRespondents = findViewById(R.id.recyclerRespondents);

                if (recyclerResponses != null) {
                    recyclerResponses.setVisibility(View.VISIBLE);
                }
                if (recyclerRespondents != null) {
                    recyclerRespondents.setVisibility(View.VISIBLE);
                }

                // Hide the fragment container again
                View container = findViewById(R.id.fragmentContainer);
                if (container != null) {
                    container.setVisibility(View.GONE);
                }
            }
        });
    }
}
