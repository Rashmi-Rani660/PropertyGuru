package com.example.propertyguru;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TopLocalitiesActivity extends AppCompatActivity {

    private LinearLayout localityContainer;
    private ImageView backButton;

    private String[][] topLocalities = {
            {"Sector 73", "₹3.5K/sq.ft.", "10K searches"},
            {"Sector 150", "₹12K/sq.ft.", "7K searches"},
            {"Sector 137", "₹9.2K/sq.ft.", "6.5K searches"},
            {"Sector 76", "₹7.8K/sq.ft.", "4.2K searches"},
            {"Noida Extension", "₹4.1K/sq.ft.", "12K searches"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_localities);

        localityContainer = findViewById(R.id.localityContainer);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        LayoutInflater inflater = LayoutInflater.from(this);

        for (String[] locality : topLocalities) {
            // ✅ Cast it as CardView instead of LinearLayout
            CardView card = (CardView) inflater.inflate(R.layout.locality_item, localityContainer, false);

            TextView name = card.findViewById(R.id.localityName);
            TextView rate = card.findViewById(R.id.localityRate);
            TextView searchCount = card.findViewById(R.id.localitySearchCount);

            name.setText(locality[0]);
            rate.setText(locality[1]);
            searchCount.setText(locality[2]);

            card.setOnClickListener(v -> {
                Toast.makeText(this, locality[0] + " selected", Toast.LENGTH_SHORT).show();
                // You can start another activity here like:
                // Intent intent = new Intent(this, LocalityDetailsActivity.class);
                // intent.putExtra("locality", locality[0]);
                // startActivity(intent);
            });

            localityContainer.addView(card);
        }
    }
}
