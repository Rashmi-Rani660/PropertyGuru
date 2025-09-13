package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class BuilderDashboardFragment extends Fragment {

    private RecyclerView builderProjectRecycler;

    public BuilderDashboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder_dashboard, container, false);

        builderProjectRecycler = view.findViewById(R.id.builderProjectRecycler);

        setupProjectList();
        return view;
    }

    private void setupProjectList() {
        List<String> projectList = Arrays.asList("Skyline Towers", "Green Residency", "Urban Villas");
        builderProjectRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        builderProjectRecycler.setAdapter(new SimpleTextAdapter(projectList));
    }
}
