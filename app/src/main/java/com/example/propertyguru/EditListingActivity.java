package com.example.propertyguru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditListingActivity extends AppCompatActivity {

    private TextView sortByText, titleText;
    private Button btnBHK, btnPropertyType, btnListingType;
    private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);

        // Initialize views AFTER setContentView
        sortByText = findViewById(R.id.sortByText);
        titleText = findViewById(R.id.titleText); // ✅ moved here
        btnBHK = findViewById(R.id.btnBHK);
        btnPropertyType = findViewById(R.id.btnPropertyType);
        btnListingType = findViewById(R.id.btnListingType);
        backIcon = findViewById(R.id.backIcon);

        View searchBar = findViewById(R.id.searchBar);
        Button btnYourListings = findViewById(R.id.btnYourListings);
        FrameLayout listingFragmentContainer = findViewById(R.id.listingFragmentContainer);
        View mainContent = findViewById(R.id.mainContentLayout);

        btnYourListings.setOnClickListener(v -> {
            // Hide current UI
            mainContent.setVisibility(View.GONE);
            listingFragmentContainer.setVisibility(View.VISIBLE);

            // Load the ListingFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listingFragmentContainer, new com.example.propertyguru.ListingFragment())
                    .commit();
        });

        // Back navigation
        backIcon.setOnClickListener(v -> finish());

        // Popup menus
        sortByText.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_sort_by));
        btnBHK.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_bhk));
        btnPropertyType.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_property_type));
        btnListingType.setOnClickListener(v -> showPopupMenu(v, R.menu.menu_listing_type));

        // Search bar opens SelectCityActivity
        searchBar.setOnClickListener(v -> {
            Intent intent = new Intent(EditListingActivity.this, SelectCityActivity.class);
            startActivity(intent);
        });
    }

    private void showPopupMenu(View anchor, int menuResId) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(menuResId, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            String selected = item.getTitle().toString();

            if (anchor instanceof TextView) {
                ((TextView) anchor).setText(selected);
            } else if (anchor instanceof Button) {
                ((Button) anchor).setText(selected);
            }

            Toast.makeText(this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
            return true;
        });

        popupMenu.show();
    }
}
