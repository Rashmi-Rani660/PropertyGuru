package com.example.propertyguru;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editPhone, editEmail;
    Button btnSave;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Bind views
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        editEmail = findViewById(R.id.edit_email);
        btnSave = findViewById(R.id.btn_save);
        backArrow = findViewById(R.id.back_arrow);

        // Dummy data prefill
        editName.setText("Rashmi Rani");
        editPhone.setText("9719175684");
        editEmail.setText("rashmirani25012003@gmail.com");

        // Back button click listener
        backArrow.setOnClickListener(v -> finish());

        // TextWatcher to enable/disable save button
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        };

        editName.addTextChangedListener(textWatcher);
        editPhone.addTextChangedListener(textWatcher);
        editEmail.addTextChangedListener(textWatcher);

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            Toast.makeText(EditProfileActivity.this, "Your details saved Successfully", Toast.LENGTH_SHORT).show();
        });

        // Initially check once (in case fields are already filled)
        checkFields();
    }

    private void checkFields() {
        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        boolean isValid = !name.isEmpty() && !phone.isEmpty() && !email.isEmpty();
        btnSave.setEnabled(isValid);

        if (isValid) {
            btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_dark));
        } else {
            btnSave.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
        }
    }
}
