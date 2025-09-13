package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class VerifyPhoneBottomSheet extends BottomSheetDialogFragment {

    private EditText editPhone;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_verify_phone, container, false);

        editPhone = view.findViewById(R.id.editPhone);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String phone = editPhone.getText().toString().trim();
            if (!phone.isEmpty()) {
                Toast.makeText(getContext(), "Phone submitted: " + phone, Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                editPhone.setError("Enter valid phone");
            }
        });

        return view;
    }
}
