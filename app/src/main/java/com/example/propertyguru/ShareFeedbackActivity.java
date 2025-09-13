package com.example.propertyguru;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ShareFeedbackActivity extends AppCompatActivity {

    private Spinner feedbackTypeSpinner;
    private EditText feedbackEditText, emailEditText, nameEditText, mobileEditText;
    private Button submitButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_feedback);

        feedbackTypeSpinner = findViewById(R.id.feedbackTypeSpinner);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        emailEditText = findViewById(R.id.emailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);

        String[] feedbackTypes = {"Select Feedback", "General Feedback", "Complaint", "Suggestion", "Query", "Appreciation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, feedbackTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackTypeSpinner.setAdapter(adapter);

        submitButton.setOnClickListener(v -> {
            submitFeedback();
        });

        backButton.setOnClickListener(v -> finish());
    }

    private void submitFeedback() {
        String feedbackType = feedbackTypeSpinner.getSelectedItem().toString();
        String feedbackMsg = feedbackEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String mobile = mobileEditText.getText().toString().trim();

        if (feedbackType.equals("Select Feedback") || feedbackMsg.isEmpty() || email.isEmpty() || name.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(this, "Please fill all the details!", Toast.LENGTH_SHORT).show();
        } else {
            // Show Thank You Popup
            showThankYouDialog();

            // Clear fields after submission
            feedbackTypeSpinner.setSelection(0);
            feedbackEditText.setText("");
            emailEditText.setText("");
            nameEditText.setText("");
            mobileEditText.setText("");
        }
    }

    private void showThankYouDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thank You!")
                .setMessage("Thank you for your feedback! We appreciate your input and will review it shortly.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
