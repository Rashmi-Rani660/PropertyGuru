package com.example.propertyguru;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllActivity extends AppCompatActivity {

    RecyclerView listingsRecycler;
    AllActivityAdapter adapter;
    List<AllActivityModel> propertyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        listingsRecycler = findViewById(R.id.allRecycler);
        listingsRecycler.setLayoutManager(new LinearLayoutManager(this));

        propertyList = new ArrayList<>();
        propertyList.add(new AllActivityModel("2BHK Apartment", "Mumbai", "Apartment", "₹80 Lakh", "Tata Housing", R.drawable.house1));
        propertyList.add(new AllActivityModel("3BHK Villa", "Delhi", "Villa", "₹1.5 Cr", "DLF Homes", R.drawable.house2));
        propertyList.add(new AllActivityModel("1BHK Flat", "Pune", "Flat", "₹40 Lakh", "Godrej Properties", R.drawable.house3));
        propertyList.add(new AllActivityModel("2BHK Flat", "Mumbai", "Flat", "₹90 Lakh", "Properties", R.drawable.house4));
        propertyList.add(new AllActivityModel("1BHK Flat", "Greater Noida", "Flat", "₹80 Lakh", "Godrej Properties", R.drawable.house5));
        propertyList.add(new AllActivityModel("3BHK Villa", "Delhi", "Villa", "₹1.5 Cr", "DLF Homes", R.drawable.house6));
        propertyList.add(new AllActivityModel("3BHK Villa", "Gurgaon", "Villa", "₹1.5 Cr", "TATA Homes", R.drawable.house7));
        propertyList.add(new AllActivityModel("3BHK Villa", "Shimla", "Villa", "₹1.5 Cr", "DLF Homes", R.drawable.house8));

        adapter = new AllActivityAdapter(this, propertyList);
        listingsRecycler.setAdapter(adapter);
    }
}
