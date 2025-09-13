package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShortlistFragment extends Fragment {

    private RadioGroup radioGroup;
    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private ArrayList<String> shortlistedProperties;
    private ShortlistAdapter adapter;

    public ShortlistFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shortlist, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        recyclerView = view.findViewById(R.id.recyclerViewShortlist);
        emptyLayout = view.findViewById(R.id.emptyLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data (simulate empty and non-empty list for testing)
        shortlistedProperties = new ArrayList<>();
        // shortlistedProperties.add("2BHK Flat in Mumbai"); // Uncomment to simulate shortlist

        adapter = new ShortlistAdapter(shortlistedProperties);
        recyclerView.setAdapter(adapter);

        updateUI();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // You can change data source based on selection
            updateUI(); // For now, no data switching for Projects/Localities
        });

        return view;
    }

    private void updateUI() {
        if (shortlistedProperties.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }
}
