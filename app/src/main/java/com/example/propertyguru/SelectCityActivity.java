package com.example.propertyguru;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectCityActivity extends AppCompatActivity {

    private ImageView backButton, micIcon, locationIcon;
    private EditText searchCityEditText;
    private TextView currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city); // Ensure your XML is named this

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        micIcon = findViewById(R.id.micIcon);
        locationIcon = findViewById(R.id.locationIcon);
        searchCityEditText = findViewById(R.id.searchCityEditText);
        currentLocation = findViewById(R.id.currentLocation);

        // Back Button Function
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // Mic icon click
        if (micIcon != null) {
            micIcon.setOnClickListener(v ->
                    Toast.makeText(this, "Voice Search Clicked", Toast.LENGTH_SHORT).show());
        }

        // Use My Current Location
        if (currentLocation != null) {
            currentLocation.setOnClickListener(v ->
                    Toast.makeText(this, "Fetching Current Location...", Toast.LENGTH_SHORT).show());
        }

        // Popular City Buttons
        int[] cityButtonIds = {
                R.id.delhiButton, R.id.mumbaiButton, R.id.bengaluruButton,
                R.id.noidaButton, R.id.gurgaonButton, R.id.hyderabadButton
        };

        for (int id : cityButtonIds) {
            Button cityButton = findViewById(id);
            if (cityButton != null) {
                cityButton.setOnClickListener(v -> {
                    String city = ((Button) v).getText().toString();
                    Toast.makeText(this, "Selected City: " + city, Toast.LENGTH_SHORT).show();

                    // TODO: You can use Intent or SharedPreferences to send this city value to other activities
                });
            }
        }

        // Optional: Search EditText
        if (searchCityEditText != null) {
            searchCityEditText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String query = searchCityEditText.getText().toString().trim();
                    if (!query.isEmpty()) {
                        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                        // TODO: Implement actual search/filter logic here
                    }
                    return true;
                }
                return false;
            });
        }
    }
}
