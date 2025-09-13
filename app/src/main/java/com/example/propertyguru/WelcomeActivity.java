package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WelcomeActivity extends AppCompatActivity {

    private CardView cardBuyHome, cardRent, cardSellRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        cardBuyHome = findViewById(R.id.cardBuyHome);
        cardRent = findViewById(R.id.cardRent);
        cardSellRent = findViewById(R.id.cardSellRent);

        cardBuyHome.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        });

        cardRent.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        });

        // Inside onCreate() of WelcomeActivity

        LinearLayout sellRentLayout = findViewById(R.id.sellRentLayout); // or Button/TextView depending on your layout

        sellRentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, RentSellActivity.class);
            startActivity(intent);
        });



    }
}