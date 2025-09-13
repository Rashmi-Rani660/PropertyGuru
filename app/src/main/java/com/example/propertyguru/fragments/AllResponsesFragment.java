package com.example.propertyguru.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.propertyguru.R;
import com.example.propertyguru.ResponseAdapter;
import com.example.propertyguru.ResponseModel;

import java.util.ArrayList;
import java.util.List;

public class AllResponsesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_responses, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerResponses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ✅ Declare and initialize the list
        List<ResponseModel> responseList = new ArrayList<>();

        // ✅ Add sample data
        responseList.add(new ResponseModel(
                "Rahul Kr",
                "June 21, 2025",
                "2.0",
                "Individual",
                "Rs50 Lac, 2 Bed , Flat/Apartment for Sale in Yamaha Vihar, Sector 49 Noida",
                "Plain Listing\nDeleted"
        ));

        // ✅ Set adapter
        recyclerView.setAdapter(new ResponseAdapter(responseList));

        return view;
    }
}
