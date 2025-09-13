package com.example.propertyguru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;

import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, emailTextView, phoneTextView;
    private TextView savedCount, seenCount, contactedCount, recentCount;
    private Button editButton, searchButton, postPropertyButton;
    private ImageView profileImage, illustrationImage;
    private LinearLayout optionsContainer;
    private TextView profileInitial;

    private LinearLayout tabSaved, tabSeen, tabContacted, tabRecent;
    private List<LinearLayout> allTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bind Views
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        savedCount = findViewById(R.id.savedCount);
        seenCount = findViewById(R.id.seenCount);
        contactedCount = findViewById(R.id.contactedCount);
        recentCount = findViewById(R.id.recentCount);
        editButton = findViewById(R.id.editButton);
        searchButton = findViewById(R.id.searchButton);
        postPropertyButton = findViewById(R.id.postPropertyButton);
        profileImage = findViewById(R.id.profileimage);
        illustrationImage = findViewById(R.id.illustrationImage);
        optionsContainer = findViewById(R.id.optionsContainer);
        profileInitial = findViewById(R.id.profileInitial);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        String picture = prefs.getString("picture", "");

        Log.d("ProfileData", "Name: " + name);
        Log.d("ProfileData", "Email: " + email);
        Log.d("ProfileData", "Picture: " + picture);

        // Set text data
        nameTextView.setText(name);
        emailTextView.setText(email);

        // Load profile image or show initial placeholder
        if (!picture.isEmpty()) {
            Glide.with(this)
                    .load(picture)
                    .circleCrop()
                    .error(R.drawable.profileimage)
                    .listener(new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target,
                                                    boolean isFirstResource) {
                            // Show initial letter placeholder if image loading fails
                            showProfileInitial(name);
                            return false; // allow Glide to handle error drawable as well
                        }

                        @Override
                        public boolean onResourceReady(android.graphics.drawable.Drawable resource, Object model,
                                                       com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target,
                                                       com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            // Hide initial letter when image loaded successfully
                            profileInitial.setVisibility(View.GONE);
                            profileImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(profileImage);

            // Initially hide initial letter and show image
            profileInitial.setVisibility(View.GONE);
            profileImage.setVisibility(View.VISIBLE);

        } else {
            // No image URL, show initial letter
            showProfileInitial(name);
        }

        // Dummy counts
        savedCount.setText("00");
        seenCount.setText("00");
        contactedCount.setText("00");
        recentCount.setText("00");

        // Button click listeners
        editButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class)));

        searchButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, SelectCityActivity.class)));

        postPropertyButton.setOnClickListener(view -> startActivity(new Intent(ProfileActivity.this, PostPropertyActivity.class)));

        // Options menu setup
        String[] options = {
                "Communication Settings",
                "Share Feedback",
                "Logout"
        };

        int[] optionIcons = {
                R.drawable.communication,
                R.drawable.feedback,
                R.drawable.logout
        };

        for (int i = 0; i < options.length; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.profile_option_item, null);

            ImageView iconImage = itemView.findViewById(R.id.iconImage);
            TextView optionText = itemView.findViewById(R.id.optionText);

            iconImage.setImageResource(optionIcons[i]);
            optionText.setText(options[i]);

            int finalI = i;
            itemView.setOnClickListener(v -> {
                switch (options[finalI]) {
                    case "Communication Settings":
                        startActivity(new Intent(ProfileActivity.this, CommunicationSettingsActivity.class));
                        break;
                    case "Share Feedback":
                        startActivity(new Intent(ProfileActivity.this, ShareFeedbackActivity.class));
                        break;
                    case "Logout":
                        showLogoutDialog();
                        break;
                    default:
                        Toast.makeText(ProfileActivity.this, options[finalI] + " clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            });

            optionsContainer.addView(itemView);

            if (i != options.length - 1) {
                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(Color.parseColor("#CCCCCC"));
                optionsContainer.addView(divider);
            }
        }

        // Tabs setup
        tabSaved = findViewById(R.id.tabSaved);
        tabSeen = findViewById(R.id.tabSeen);
        tabContacted = findViewById(R.id.tabContacted);
        tabRecent = findViewById(R.id.tabRecent);
        allTabs = Arrays.asList(tabSaved, tabSeen, tabContacted, tabRecent);

        setSelectedTab(tabSaved); // default tab

        tabSaved.setOnClickListener(v -> setSelectedTab(tabSaved));
        tabSeen.setOnClickListener(v -> setSelectedTab(tabSeen));
        tabContacted.setOnClickListener(v -> setSelectedTab(tabContacted));
        tabRecent.setOnClickListener(v -> setSelectedTab(tabRecent));
    }

    private void showProfileInitial(String name) {
        profileImage.setVisibility(View.GONE);
        profileInitial.setVisibility(View.VISIBLE);

        if (name != null && !name.isEmpty()) {
            profileInitial.setText(name.substring(0, 1).toUpperCase());
        } else {
            profileInitial.setText("?");  // fallback character
        }
    }

    private void setSelectedTab(LinearLayout selectedTab) {
        for (LinearLayout tab : allTabs) {
            if (tab == selectedTab) {
                tab.setBackgroundResource(R.drawable.bg_selected_tab);
                setTabColor(tab, "#A34DE3");
            } else {
                tab.setBackgroundResource(R.drawable.bg_unselected_tab);
                setTabColor(tab, "#888888");
            }
        }
    }

    private void setTabColor(LinearLayout tab, String color) {
        for (int i = 0; i < tab.getChildCount(); i++) {
            View child = tab.getChildAt(i);
            if (child instanceof ImageView) {
                ((ImageView) child).setColorFilter(Color.parseColor(color));
            } else if (child instanceof TextView) {
                ((TextView) child).setTextColor(Color.parseColor(color));
            }
        }
    }

    private void showLogoutDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("YES", (dialog, which) -> {
                    SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
