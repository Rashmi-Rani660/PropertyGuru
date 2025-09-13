package com.example.propertyguru;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TermsOfUseActivity extends AppCompatActivity {

    private LinearLayout btnPrivacy, btnAbout;
    private View btnRateApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        btnPrivacy = findViewById(R.id.btnPrivacy);
        btnAbout = findViewById(R.id.btnAbout);
        btnRateApp = findViewById(R.id.btnRateApp);

        // Show rate dialog
        btnRateApp.setOnClickListener(v -> showRatingDialog());

        btnPrivacy.setOnClickListener(v -> {
            Intent intent = new Intent(TermsOfUseActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(TermsOfUseActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

    }


    // Custom rate app dialog
    private void showRatingDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rate_app, null);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        new AlertDialog.Builder(this)
                .setTitle("Rate Our App")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    float rating = ratingBar.getRating();
                    Toast.makeText(this, "Thanks for rating: " + rating + " stars", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
