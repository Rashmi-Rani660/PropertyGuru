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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BrokerBuilderFragment extends Fragment {

    private EditText etPhone;
    private Button btnContinue, btnTruecaller;

    private String selectedRole = "broker"; // Change to "builder" if needed

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_broker_builder, container, false);

        etPhone = view.findViewById(R.id.etPhone);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnTruecaller = view.findViewById(R.id.btnTruecaller);

        // Initially disable continue
        btnContinue.setEnabled(false);
        btnContinue.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_disabled));

        // Validate phone input
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePhoneInput(s.toString().trim());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Manual OTP Verification
        btnContinue.setOnClickListener(v -> showOTPVerificationSheet());

        // Truecaller simulation flow
        btnTruecaller.setOnClickListener(v -> showTruecallerSimulationSheet());

        return view;
    }

    private void validatePhoneInput(String phone) {
        boolean isValid = phone.matches("^[6-9]\\d{9}$");
        btnContinue.setEnabled(isValid);
        btnContinue.setBackground(ContextCompat.getDrawable(requireContext(),
                isValid ? R.drawable.bg_button_enabled : R.drawable.bg_button_disabled));
    }

    private void showOTPVerificationSheet() {
        View sheetView = LayoutInflater.from(requireContext())
                .inflate(R.layout.bottom_sheet_verify_phone, null);
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(sheetView);
        dialog.show();

        Button btnVerifyOTP = sheetView.findViewById(R.id.btnVerifyOTP);
        btnVerifyOTP.setOnClickListener(v -> {
            dialog.dismiss();
            launchHomeWithDashboard();
        });
    }

    private void showTruecallerSimulationSheet() {
        View sheetView = LayoutInflater.from(requireContext())
                .inflate(R.layout.bottom_sheet_truecaller_verify, null); // ✅ create this layout
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(sheetView);
        dialog.show();

        Button btnAllow = sheetView.findViewById(R.id.btnAllowTruecaller);
        Button btnDeny = sheetView.findViewById(R.id.btnDenyTruecaller);

        btnAllow.setOnClickListener(v -> {
            dialog.dismiss();
            launchHomeWithDashboard(); // success → go to home
        });

        btnDeny.setOnClickListener(v -> dialog.dismiss()); // Just close dialog
    }

    private void launchHomeWithDashboard() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("role", selectedRole); // "broker" or "builder"
        startActivity(intent);
        requireActivity().finish();
    }
}
