package com.example.propertyguru;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nex3z.flowlayout.FlowLayout;

import java.util.Arrays;
import java.util.List;

public class AmenitiesFragment extends Fragment {

    private final List<String> amenities = Arrays.asList(
            "Maintenance Staff", "Water Storage", "Security / Fire Alarm",
            "Visitor Parking", "Park", "Lift(s)"
    );

    private final List<String> waterSources = Arrays.asList(
            "Municipal corporation", "Borewell/Tank", "24*7 Water"
    );

    private final List<String> overlookingOptions = Arrays.asList(
            "Pool", "Park/Garden", "Club", "Main Road", "Others", "Sea Facing"
    );

    private final String[] propertyFacings = {
            "North", "South", "East", "West",
            "North-East", "North-West", "South-East", "South-West"
    };

    private final String[] flooringTypes = {
            "Select", "Marble", "Vitrified", "Wooden", "Granite", "Others"
    };

    private final String[] locationAdvantages = {
            "Close to Metro Station", "Close to School", "Close to Hospital",
            "Close to Market", "Close to Railway Station", "Close to Airport",
            "Close to Mall", "Close to Highway"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities, container, false);

        // Layouts
        FlowLayout layoutAmenities = view.findViewById(R.id.layoutAmenities);
        FlowLayout layoutWaterSources = view.findViewById(R.id.layoutWaterSources);
        FlowLayout layoutOverlooking = view.findViewById(R.id.layoutOverlooking);
        FlowLayout layoutPropertyFacing = view.findViewById(R.id.layoutPropertyFacing);
        FlowLayout layoutLocationAdvantages = view.findViewById(R.id.layoutLocationAdvantages);

        Spinner spinnerFlooring = view.findViewById(R.id.spinnerFlooringType);
        Spinner spinnerRoadUnit = view.findViewById(R.id.spinnerFacingRoadUnit);

        Button btnSaveContinue = view.findViewById(R.id.btnSaveContinue);
        TextView tvAddMore = view.findViewById(R.id.tvAddMoreAmenities);

        // Set up chips
        for (String item : amenities)
            layoutAmenities.addView(createSelectableChip(item));

        for (String item : waterSources)
            layoutWaterSources.addView(createSelectableChip(item));

        for (String item : overlookingOptions)
            layoutOverlooking.addView(createSelectableChip(item));

        for (String facing : propertyFacings)
            layoutPropertyFacing.addView(createSelectableChip(facing));

        for (String loc : locationAdvantages)
            layoutLocationAdvantages.addView(createSelectableChip(loc));

        // Spinners
        ArrayAdapter<String> flooringAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, flooringTypes);
        flooringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFlooring.setAdapter(flooringAdapter);

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.unit_array, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoadUnit.setAdapter(unitAdapter);

        // Add More Dialog
        tvAddMore.setOnClickListener(v -> showAddAmenityDialog(layoutAmenities));

        // ✅ Save Button Logic
        btnSaveContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomeActivity.class);
            intent.putExtra("role", "owner"); // or "broker", "builder"
            startActivity(intent);
            requireActivity().finish();


        });


        return view;
    }

    private AppCompatTextView createSelectableChip(String label) {
        AppCompatTextView chip = new AppCompatTextView(requireContext());
        chip.setText("+  " + label);
        chip.setTextSize(14);
        chip.setPadding(32, 20, 32, 20);
        chip.setBackgroundResource(R.drawable.bg_chip_selector);
        chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        chip.setClickable(true);
        chip.setFocusable(true);
        chip.setAllCaps(false);
        chip.setLayoutParams(new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        ((ViewGroup.MarginLayoutParams) chip.getLayoutParams()).setMargins(16, 16, 0, 0);

        chip.setOnClickListener(v -> {
            chip.setSelected(!chip.isSelected());
            chip.setTextColor(chip.isSelected()
                    ? ContextCompat.getColor(requireContext(), android.R.color.white)
                    : ContextCompat.getColor(requireContext(), R.color.black));
        });

        return chip;
    }

    private void showAddAmenityDialog(FlowLayout layoutAmenities) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Amenity");

        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter amenity name");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newAmenity = input.getText().toString().trim();
            if (!newAmenity.isEmpty()) {
                layoutAmenities.addView(createSelectableChip(newAmenity));
            } else {
                Toast.makeText(getContext(), "Amenity cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
