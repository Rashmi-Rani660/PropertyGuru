package com.example.propertyguru;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CommunicationSettingsActivity extends AppCompatActivity {

    private Switch switchPromotional, switchPush;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_settings);

        switchPromotional = findViewById(R.id.switchPromotional);
        switchPush = findViewById(R.id.switchPush);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        switchPromotional.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                Toast.makeText(this, "Promotional content ON", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Promotional content OFF", Toast.LENGTH_SHORT).show();
        });

        switchPush.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                Toast.makeText(this, "Push notifications ON", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Push notifications OFF", Toast.LENGTH_SHORT).show();
        });
    }
}
