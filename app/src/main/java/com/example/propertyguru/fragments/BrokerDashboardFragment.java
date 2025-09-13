package com.example.propertyguru.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.propertyguru.*;

public class BrokerDashboardFragment extends Fragment {

    private View cardPostProperty, cardSearchProperties;
    private View itemViewResponses, itemEditListings, itemHomepage;
    private View itemShareFeedback, itemTermsOfUse, itemRateApp, itemManageProfile, itemCustomerService;

    public BrokerDashboardFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_dashboard, container, false); // same layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔄 Change heading text
        TextView heading = view.findViewById(R.id.tv_manage_profile);
        if (heading != null) {
            heading.setText("Broker Profile • Manage Profile");
        }

        cardPostProperty = view.findViewById(R.id.card_post_property);
        cardSearchProperties = view.findViewById(R.id.card_search_properties);
        itemViewResponses = view.findViewById(R.id.item_manage_view_responses);
        itemEditListings = view.findViewById(R.id.item_manage_edit_listings);
        itemHomepage = view.findViewById(R.id.item_manage_homepage);
        itemShareFeedback = view.findViewById(R.id.item_share_feedback);
        itemTermsOfUse = view.findViewById(R.id.item_terms_of_use);
        itemRateApp = view.findViewById(R.id.item_rate_app);
        itemManageProfile = view.findViewById(R.id.tv_manage_profile);
        itemCustomerService = view.findViewById(R.id.item_customer_service);

        if (cardPostProperty != null) {
            cardPostProperty.setOnClickListener(v -> startActivity(new Intent(getActivity(), PostPropertyActivity.class)));
        }

        if (cardSearchProperties != null) {
            cardSearchProperties.setOnClickListener(v -> startActivity(new Intent(getActivity(), SelectCityActivity.class)));
        }

        if (itemViewResponses != null) {
            itemViewResponses.setOnClickListener(v -> startActivity(new Intent(getActivity(), ViewResponseActivity.class)));
        }

        if (itemEditListings != null) {
            itemEditListings.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditListingActivity.class)));
        }

        if (itemHomepage != null) {
            itemHomepage.setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeActivity.class)));
        }

        if (itemShareFeedback != null) {
            itemShareFeedback.setOnClickListener(v -> startActivity(new Intent(getActivity(), ShareFeedbackActivity.class)));
        }

        if (itemTermsOfUse != null) {
            itemTermsOfUse.setOnClickListener(v -> startActivity(new Intent(getActivity(), TermsOfUseActivity.class)));
        }

        if (itemManageProfile != null) {
            itemManageProfile.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProfileActivity.class)));
        }

        if (itemCustomerService != null) {
            itemCustomerService.setOnClickListener(v -> startActivity(new Intent(getActivity(), CustomerServiceActivity.class)));
        }

        if (itemRateApp != null) {
            itemRateApp.setOnClickListener(v -> showRateAppDialog());
        }
    }

    private void showRateAppDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.rate_app_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show();

        LinearLayout btnPositive = dialogView.findViewById(R.id.btnPositive);
        LinearLayout btnNegative = dialogView.findViewById(R.id.btnNegative);
        TextView tvRemindLater = dialogView.findViewById(R.id.tvRemindLater);

        btnPositive.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().getPackageName()));
            startActivity(intent);
        });

        btnNegative.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(getActivity(), ShareFeedbackActivity.class));
        });

        tvRemindLater.setOnClickListener(v -> dialog.dismiss());
    }
}
