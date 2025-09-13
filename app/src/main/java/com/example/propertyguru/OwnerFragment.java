package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

public class OwnerFragment extends Fragment {

    Button btnResidential, btnCommercial, btnRent, btnSell, btnNext;
    EditText etPhone, etName, etCity;

    private Button btnSocietyShop;
    private LinearLayout layoutSocietyShop;
    private TextView tvYouAreLooking;

    private String selectedCommercialSubtype = "";
    String selectedPropertyType = "";
    String selectedListingType = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner, container, false);

        btnResidential = view.findViewById(R.id.btnResidential);
        btnCommercial = view.findViewById(R.id.btnCommercial);
        btnRent = view.findViewById(R.id.btnRent);
        btnSell = view.findViewById(R.id.btnSell);
        btnNext = view.findViewById(R.id.btnNext);
        btnSocietyShop = view.findViewById(R.id.btnSocietyShop);
        layoutSocietyShop = view.findViewById(R.id.layoutSocietyShop);
        tvYouAreLooking = view.findViewById(R.id.tvYouAreLooking);

        etPhone = view.findViewById(R.id.etPhone);
        etName = view.findViewById(R.id.etName);
        etCity = view.findViewById(R.id.etCity);

        // Property Type selection
        btnResidential.setOnClickListener(v -> {
            selectedPropertyType = "Residential";

            btnResidential.setBackgroundResource(R.drawable.bg_property_selected);
            btnResidential.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            btnCommercial.setBackgroundResource(R.drawable.bg_property_unselected);
            btnCommercial.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));

            layoutSocietyShop.setVisibility(View.GONE);
            tvYouAreLooking.setVisibility(View.VISIBLE);
            btnRent.setVisibility(View.VISIBLE);
            btnSell.setVisibility(View.VISIBLE);

            selectedCommercialSubtype = "";
            selectedListingType = "";
            validateInputs();
        });

        btnCommercial.setOnClickListener(v -> {
            selectedPropertyType = "Commercial";

            btnCommercial.setBackgroundResource(R.drawable.bg_property_selected);
            btnCommercial.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            btnResidential.setBackgroundResource(R.drawable.bg_property_unselected);
            btnResidential.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));

            layoutSocietyShop.setVisibility(View.VISIBLE);
            tvYouAreLooking.setVisibility(View.GONE);
            btnRent.setVisibility(View.GONE);
            btnSell.setVisibility(View.GONE);

            selectedListingType = "";
            validateInputs();
        });

        btnSocietyShop.setOnClickListener(v -> {
            selectedCommercialSubtype = "Society Shop";

            btnSocietyShop.setBackgroundResource(R.drawable.bg_property_selected);
            btnSocietyShop.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
            validateInputs();
        });

        setupToggle(btnRent, btnSell);
        setupToggle(btnSell, btnRent);
        setupValidation();

        btnNext.setOnClickListener(v -> {
            if (btnNext.isEnabled()) {
                Intent intent = new Intent(requireContext(), PostPropertyActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupToggle(Button selected, Button... others) {
        selected.setOnClickListener(v -> {
            if (selected == btnRent || selected == btnSell) {
                selectedListingType = selected.getText().toString();
            }

            selected.setBackgroundResource(R.drawable.bg_property_selected);
            selected.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

            for (Button b : others) {
                b.setBackgroundResource(R.drawable.bg_property_unselected);
                b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
            }

            validateInputs();
        });
    }

    private void setupValidation() {
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs();
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        etPhone.addTextChangedListener(watcher);
        etName.addTextChangedListener(watcher);
        etCity.addTextChangedListener(watcher);
    }

    private void validateInputs() {
        String phone = etPhone.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String city = etCity.getText().toString().trim();

        boolean isPhoneValid = Pattern.matches("^[6-9]\\d{9}$", phone);
        boolean isNameValid = Pattern.matches("^[a-zA-Z\\s]{2,}$", name);
        boolean isCityValid = city.length() >= 2;
        boolean isTypeSelected = !selectedPropertyType.isEmpty();

        boolean isValid = false;

        // ✔ Logic for Residential ➝ Require Rent/Sell selected
        if (selectedPropertyType.equals("Residential")) {
            isValid = isPhoneValid && isNameValid && isCityValid
                    && isTypeSelected && !selectedListingType.isEmpty();
        }

        // ✔ Logic for Commercial ➝ Require Society Shop selected
        else if (selectedPropertyType.equals("Commercial")) {
            isValid = isPhoneValid && isNameValid && isCityValid
                    && isTypeSelected && !selectedCommercialSubtype.isEmpty();
        }

        btnNext.setEnabled(isValid);
        if (isValid) {
            btnNext.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.purpleHeader));
        } else {
            btnNext.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.disabledGray));
        }
    }
}
