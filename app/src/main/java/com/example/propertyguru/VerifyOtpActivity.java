package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyOtpActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerify;
    private String correctOtp;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        etOtp = findViewById(R.id.et_otp);
        btnVerify = findViewById(R.id.btn_verify);
        TextView tvPhoneInfo = findViewById(R.id.tv_phone_info);

        // Get the phone and OTP passed from LoginActivity
        correctOtp = getIntent().getStringExtra("otp");
        phone = getIntent().getStringExtra("phone");

        tvPhoneInfo.setText("OTP sent to " + phone);


        btnVerify.setOnClickListener(view -> {
            String enteredOtp = etOtp.getText().toString().trim();
            if (enteredOtp.equals(correctOtp)) {
                Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                // Login complete - go to main screen
                Intent intent = new Intent(VerifyOtpActivity.this, ListingsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
