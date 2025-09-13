package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewsFragment extends Fragment {

    RadioButton rbProperties, rbProjects;
    RecyclerView recyclerView;
    TextView tvEmpty;
    ViewAdapter adapter;
    List<ViewItem> allItems = new ArrayList<>();
    List<ViewItem> filteredItems = new ArrayList<>();

    public ViewsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_views, container, false);

        rbProperties = view.findViewById(R.id.rbProperties);
        rbProjects = view.findViewById(R.id.rbProjects);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvEmpty = view.findViewById(R.id.tvEmpty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ViewAdapter(filteredItems);
        recyclerView.setAdapter(adapter);

        loadDummyData(); // load example data

        rbProperties.setOnClickListener(v -> filterData("property"));
        rbProjects.setOnClickListener(v -> filterData("project"));

        rbProperties.setChecked(true);
        filterData("property");

        return view;
    }

    private void loadDummyData() {
        allItems.add(new ViewItem("4 Bedroom House", "Sector 128, Noida", "property"));
        allItems.add(new ViewItem("Godrej Habitat", "Sector 107, Noida", "project"));
        allItems.add(new ViewItem("2BHK Apartment", "Noida Expressway", "property"));
        // Add more as needed
    }

    private void filterData(String type) {
        filteredItems.clear();
        for (ViewItem item : allItems) {
            if (item.type.equals(type)) {
                filteredItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();

        if (filteredItems.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
