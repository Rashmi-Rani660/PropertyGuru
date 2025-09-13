package com.example.propertyguru;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicDetailsFragment extends Fragment {

    Button btnNext;
    String selectedPropertyType = "";
    String selectedBHK = "";
    String selectedFurnish = "";
    String selectedUnit = "";
    final String[] selectedRoom = {""};

    List<AppCompatButton> bhkButtons = new ArrayList<>();
    List<AppCompatButton> propertyButtons = new ArrayList<>();
    List<AppCompatButton> furnishButtons = new ArrayList<>();
    List<Button> roomButtons;

    LinearLayout layoutBHK, layoutBedrooms, layoutBathrooms, layoutBalconies, layoutRooms;
    TextView tvUnit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_details, container, false);

        btnNext = view.findViewById(R.id.btnNext);
        layoutBHK = view.findViewById(R.id.layoutBHK);
        layoutBedrooms = view.findViewById(R.id.layoutBedrooms);
        layoutBathrooms = view.findViewById(R.id.layoutBathrooms);
        layoutBalconies = view.findViewById(R.id.layoutBalconies);
        layoutRooms = view.findViewById(R.id.layoutRooms);

        Button btnRoom1 = view.findViewById(R.id.btnRoom1);
        Button btnRoom2 = view.findViewById(R.id.btnRoom2);
        Button btnRoom3 = view.findViewById(R.id.btnRoom3);
        Button btnRoom4 = view.findViewById(R.id.btnRoom4);

        roomButtons = Arrays.asList(btnRoom1, btnRoom2, btnRoom3, btnRoom4);

        // ---------------------- PROPERTY TYPE ------------------------
        AppCompatButton btnApartment = view.findViewById(R.id.btnApartment);
        AppCompatButton btnHouse = view.findViewById(R.id.btnHouse);
        AppCompatButton btnDuplex = view.findViewById(R.id.btnDuplex);
        AppCompatButton btnFloor = view.findViewById(R.id.btnFloor);
        AppCompatButton btnVilla = view.findViewById(R.id.btnVilla);
        AppCompatButton btnPenthouse = view.findViewById(R.id.btnPenthouse);
        AppCompatButton btnStudio = view.findViewById(R.id.btnStudio);
        AppCompatButton btnSocietyShop = view.findViewById(R.id.btnSocietyShop);

        propertyButtons = Arrays.asList(
                btnApartment, btnHouse, btnDuplex, btnFloor,
                btnVilla, btnPenthouse, btnStudio, btnSocietyShop
        );

        View.OnClickListener propertyClickListener = v -> {
            for (AppCompatButton b : propertyButtons) {
                if (b.getId() == v.getId()) {
                    b.setBackgroundResource(R.drawable.bg_property_selected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                    selectedPropertyType = b.getText().toString();
                } else {
                    b.setBackgroundResource(R.drawable.bg_property_unselected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
                }
            }

            if ("Society Shop".equals(selectedPropertyType)) {
                layoutBHK.setVisibility(View.GONE);
                layoutBedrooms.setVisibility(View.GONE);
                layoutBathrooms.setVisibility(View.GONE);
                layoutBalconies.setVisibility(View.GONE);
                layoutRooms.setVisibility(View.VISIBLE);
                selectedBHK = ""; // reset
            } else {
                layoutBHK.setVisibility(View.VISIBLE);
                layoutBedrooms.setVisibility(View.VISIBLE);
                layoutBathrooms.setVisibility(View.VISIBLE);
                layoutBalconies.setVisibility(View.VISIBLE);
                layoutRooms.setVisibility(View.GONE);
            }
        };

        for (AppCompatButton b : propertyButtons) {
            b.setOnClickListener(propertyClickListener);
        }

        // ---------------------- BHK BUTTONS ------------------------
        bhkButtons = Arrays.asList(
                view.findViewById(R.id.btn1RK),
                view.findViewById(R.id.btn1BHK),
                view.findViewById(R.id.btn1_5BHK),
                view.findViewById(R.id.btn2BHK),
                view.findViewById(R.id.btn2_5BHK),
                view.findViewById(R.id.btn3BHK),
                view.findViewById(R.id.btn3_5BHK),
                view.findViewById(R.id.btn4BHK),
                view.findViewById(R.id.btn4_5BHK),
                view.findViewById(R.id.btn5BHK),
                view.findViewById(R.id.btn6BHK),
                view.findViewById(R.id.btn7BHK),
                view.findViewById(R.id.btn8BHK),
                view.findViewById(R.id.btn9BHK),
                view.findViewById(R.id.btn10BHK)
        );

        View.OnClickListener bhkClickListener = v -> {
            for (AppCompatButton b : bhkButtons) {
                if (b.getId() == v.getId()) {
                    b.setBackgroundResource(R.drawable.bg_property_selected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                    selectedBHK = b.getText().toString();
                } else {
                    b.setBackgroundResource(R.drawable.bg_property_unselected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
                }
            }
        };

        for (AppCompatButton b : bhkButtons) {
            b.setOnClickListener(bhkClickListener);
        }

        // ---------------------- UNIT SELECTOR ------------------------
        LinearLayout unitSelector = view.findViewById(R.id.unitSelector);
        tvUnit = view.findViewById(R.id.tvUnit);
        unitSelector.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), v);
            popupMenu.getMenu().add("sq. ft.");
            popupMenu.getMenu().add("sq. m.");
            popupMenu.getMenu().add("acre");
            popupMenu.getMenu().add("hectare");

            popupMenu.setOnMenuItemClickListener(item -> {
                selectedUnit = item.getTitle().toString();
                tvUnit.setText(selectedUnit);
                return true;
            });

            popupMenu.show();
        });

        // ---------------------- FURNISH TYPE ------------------------
        AppCompatButton btnUnfurnished = view.findViewById(R.id.btnUnfurnished);
        AppCompatButton btnSemiFurnished = view.findViewById(R.id.btnSemiFurnished);
        AppCompatButton btnFullyFurnished = view.findViewById(R.id.btnFullyFurnished);

        furnishButtons = Arrays.asList(btnUnfurnished, btnSemiFurnished, btnFullyFurnished);

        View.OnClickListener furnishClickListener = v -> {
            for (AppCompatButton b : furnishButtons) {
                if (b.getId() == v.getId()) {
                    b.setBackgroundResource(R.drawable.bg_property_selected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                    selectedFurnish = b.getText().toString();
                } else {
                    b.setBackgroundResource(R.drawable.bg_property_unselected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
                }
            }
        };

        for (AppCompatButton b : furnishButtons) {
            b.setOnClickListener(furnishClickListener);
        }

        // ---------------------- ROOM DETAILS SELECTABLE GROUPS ------------------------
        setupRoomChips(layoutBedrooms);
        setupRoomChips(layoutBathrooms);
        setupRoomChips(layoutBalconies);

        // ---------------------- ROOM COUNT BUTTONS FOR SHOP ------------------------
        View.OnClickListener roomClickListener = view1 -> {
            for (Button b : roomButtons) {
                if (b.getId() == view1.getId()) {
                    b.setBackgroundResource(R.drawable.bg_property_selected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                    selectedRoom[0] = b.getText().toString();
                } else {
                    b.setBackgroundResource(R.drawable.bg_property_unselected);
                    b.setTextColor(ContextCompat.getColor(requireContext(), R.color.grayText));
                }
            }
        };

        for (Button roomBtn : roomButtons) {
            roomBtn.setOnClickListener(roomClickListener);
        }

        // ---------------------- AGENT CHECKBOX ------------------------
        CheckBox checkboxShareWithAgents = view.findViewById(R.id.checkboxShareWithAgents);

        // ---------------------- NEXT BUTTON ------------------------
        btnNext.setOnClickListener(v -> {
            boolean isChecked = checkboxShareWithAgents.isChecked();

            if (selectedPropertyType.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a property type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!"Society Shop".equals(selectedPropertyType) && selectedBHK.isEmpty()) {
                Toast.makeText(requireContext(), "Please select BHK", Toast.LENGTH_SHORT).show();
                return;
            }

            if ("Society Shop".equals(selectedPropertyType) && selectedRoom[0].isEmpty()) {
                Toast.makeText(requireContext(), "Please select room count", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedFurnish.isEmpty()) {
                Toast.makeText(requireContext(), "Please select furnish type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedUnit.isEmpty()) {
                Toast.makeText(requireContext(), "Please select area unit", Toast.LENGTH_SHORT).show();
                return;
            }

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.propertyDetailsContainer, new RentPriceDetailsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void setupRoomChips(ViewGroup layout) {
        Context context = layout.getContext();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            if (view instanceof TextView) {
                TextView chip = (TextView) view;
                chip.setSelected(false);
                chip.setTextColor(ContextCompat.getColor(context, R.color.grayTextRoom));
                chip.setOnClickListener(v -> {
                    for (int j = 0; j < layout.getChildCount(); j++) {
                        View other = layout.getChildAt(j);
                        if (other instanceof TextView) {
                            other.setSelected(false);
                            ((TextView) other).setTextColor(ContextCompat.getColor(context, R.color.grayTextRoom));
                        }
                    }
                    chip.setSelected(true);
                    chip.setTextColor(ContextCompat.getColor(context, R.color.indigo));
                });
            }
        }
    }
}
