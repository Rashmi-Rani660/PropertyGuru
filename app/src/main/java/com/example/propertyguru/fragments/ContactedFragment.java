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

public class ContactedFragment extends Fragment {

    private RadioGroup radioGroup;
    private RecyclerView recyclerView;
    private LinearLayout emptyLayout;
    private ArrayList<String> contactedList;
    private ContactedAdapter adapter;

    public ContactedFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacted, container, false);

        radioGroup = view.findViewById(R.id.radioGroupContacted);
        recyclerView = view.findViewById(R.id.recyclerViewContacted);
        emptyLayout = view.findViewById(R.id.emptyContactedLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data - start empty
        contactedList = new ArrayList<>();
        // contactedList.add("2BHK Flat, Mumbai"); // Uncomment to test list showing

        adapter = new ContactedAdapter(contactedList);
        recyclerView.setAdapter(adapter);

        updateUI();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Optional: Load different data for properties/projects
            updateUI();
        });

        return view;
    }

    private void updateUI() {
        if (contactedList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
    }
}
