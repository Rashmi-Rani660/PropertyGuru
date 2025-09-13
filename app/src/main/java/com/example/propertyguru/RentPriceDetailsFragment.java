package com.example.propertyguru;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RentPriceDetailsFragment extends Fragment {

    private EditText etMonthlyRent, etMaintenanceAmount;
    private TextView tvAvailableFrom;
    private AppCompatButton btnNone, btn1Month, btn2Month, btnCustom;
    private Button btnPostProperty;
    private String selectedDepositOption = "";
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent_price_details, container, false);

        // Monthly Rent field
        etMonthlyRent = view.findViewById(R.id.etMonthlyRent);
        etMonthlyRent.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMonthlyRent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        // Maintenance Amount field
        etMaintenanceAmount = view.findViewById(R.id.etMaintenanceAmount);
        etMaintenanceAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        etMaintenanceAmount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        // Available From Calendar
        tvAvailableFrom = view.findViewById(R.id.tvAvailableFrom);
        calendar = Calendar.getInstance();
        view.findViewById(R.id.ivCalendar).setOnClickListener(v -> openDatePicker());

        // Deposit Option Buttons
        btnNone = view.findViewById(R.id.btnNone);
        btn1Month = view.findViewById(R.id.btn1Month);
        btn2Month = view.findViewById(R.id.btn2Month);
        btnCustom = view.findViewById(R.id.btnCustom);

        setDepositButtonListener();

        // Post Property Button
        btnPostProperty = view.findViewById(R.id.btnPostProperty);
        btnPostProperty.setOnClickListener(v -> validateAndPost());

        return view;
    }

    private void openDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, // Lighter theme
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    tvAvailableFrom.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Optional: Transparent background
        dialog.show();
    }

    private void setDepositButtonListener() {
        AppCompatButton[] buttons = {btnNone, btn1Month, btn2Month, btnCustom};

        for (AppCompatButton button : buttons) {
            button.setOnClickListener(v -> {
                // Reset all buttons to default style
                for (AppCompatButton b : buttons) {
                    b.setBackgroundResource(R.drawable.bg_property_unselected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
                }

                // Set selected style
                button.setBackgroundResource(R.drawable.bg_property_selected);
                button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));

                // Store selected option
                selectedDepositOption = button.getText().toString();
            });
        }
    }


    private void validateAndPost() {
        String rent = etMonthlyRent.getText().toString().trim();
        String maintenance = etMaintenanceAmount.getText().toString().trim();
        String availableFrom = tvAvailableFrom.getText().toString().trim();

        if (rent.isEmpty()) {
            Toast.makeText(getContext(), "Please enter monthly rent", Toast.LENGTH_SHORT).show();
            return;
        }

        if (availableFrom.isEmpty() || availableFrom.equals("Available From")) {
            Toast.makeText(getContext(), "Please select availability date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDepositOption.isEmpty()) {
            Toast.makeText(getContext(), "Please select security deposit option", Toast.LENGTH_SHORT).show();
            return;
        }

        // Optional: maintenance charges
        if (!maintenance.isEmpty()) {
            try {
                Integer.parseInt(maintenance);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid maintenance amount", Toast.LENGTH_SHORT).show();
                return;
            }
        }


// Open PhotosFragment
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.propertyDetailsContainer, new PhotosFragment())
                .addToBackStack(null)
                .commit();
    }
}
