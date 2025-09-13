package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LeadDetailsFragment extends Fragment {

    public LeadDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lead_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // You can pass data using setArguments + getArguments if needed
        TextView leadName = view.findViewById(R.id.textLeadName);
        TextView leadContact = view.findViewById(R.id.textContact);
        TextView leadScore = view.findViewById(R.id.textScore);
        TextView leadSource = view.findViewById(R.id.textSource);
        TextView leadInterest = view.findViewById(R.id.textAlsoInterested);
        TextView leadFocus = view.findViewById(R.id.textFocus);
        TextView viewsCount = view.findViewById(R.id.textViews);
        TextView interestSent = view.findViewById(R.id.textInterestSent);
        TextView siteVisit = view.findViewById(R.id.textSiteVisit);
        TextView buyWithin = view.findViewById(R.id.textBuyWithin);

        // Dummy data (replace with dynamic from arguments if needed)
        leadName.setText("Rahul Kr");
        leadContact.setText("+91-7973419869 | rahulbharath202@gmail.com");
        leadScore.setText("2.0 ★★☆☆☆");
        leadSource.setText("C2V");
        leadInterest.setText("N.A.");
        leadFocus.setText("2BHK, Rs. 50 Lacs to 60 Lacs, Sector 49 Noida");
        viewsCount.setText("3202");
        interestSent.setText("NA");
        siteVisit.setText("NA");
        buyWithin.setText("NA");
    }
}
