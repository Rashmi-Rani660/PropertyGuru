package com.example.propertyguru;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListingsActivity extends AppCompatActivity {

    RecyclerView listingsRecycler;
    ListingAdapter adapter;
    List<ListingModel> propertyList = new ArrayList<>();

    LinearLayout tabAll, tabProjects, tabVerified, tabLaunches, tabReady, tabOwner, tabChoice;
    ImageView iconAll, iconProjects, iconVerified, iconLaunches, iconReady, iconOwner, iconChoice;
    TextView textAll, textProjects, textVerified, textLaunches, textReady, textOwner, textChoice;
    View underlineAll, underlineProjects, underlineVerified, underlineLaunches, underlineReady, underlineOwner, underlineChoice;
    LinearLayout navProfile, navSaved, navTopLocalities;
    Button filterButton;
    EditText searchBar;
    ProgressBar progressBar;
    CircleImageView profileImageBottom;
    TextView navProfileInitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        listingsRecycler = findViewById(R.id.listingsRecycler);
        listingsRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListingAdapter(this, propertyList, ListingAdapter.BannerType.NONE);
        listingsRecycler.setAdapter(adapter);

        initializeViews();
        setupProfileUI();

        progressBar.setVisibility(View.GONE);

        searchBar.setOnClickListener(v -> startActivity(new Intent(this, SearchLocalitiesActivity.class)));
        filterButton.setOnClickListener(v -> showFilterBottomSheet());

        navProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        navSaved.setOnClickListener(v -> startActivity(new Intent(this, SavedActivity.class)));
        navTopLocalities.setOnClickListener(v -> startActivity(new Intent(this, TopLocalitiesActivity.class)));

        fetchPropertiesFromApi(); // Initial load
        setTabSelected(tabAll, iconAll, textAll, underlineAll);

        tabAll.setOnClickListener(v -> {
            fetchPropertiesFromApi();
            setTabSelected(tabAll, iconAll, textAll, underlineAll);
        });

        tabProjects.setOnClickListener(v -> {
            loadFilteredData("project", ListingAdapter.BannerType.PROJECTS);
            setTabSelected(tabProjects, iconProjects, textProjects, underlineProjects);
        });

        tabVerified.setOnClickListener(v -> {
            loadFilteredData("verified", ListingAdapter.BannerType.VERIFIED);
            setTabSelected(tabVerified, iconVerified, textVerified, underlineVerified);
        });

        tabLaunches.setOnClickListener(v -> {
            loadFilteredData("launch", ListingAdapter.BannerType.NEW_LAUNCHES);
            setTabSelected(tabLaunches, iconLaunches, textLaunches, underlineLaunches);
        });

        tabReady.setOnClickListener(v -> {
            loadFilteredData("ready", ListingAdapter.BannerType.READY_TO_MOVE);
            setTabSelected(tabReady, iconReady, textReady, underlineReady);
        });

        tabOwner.setOnClickListener(v -> {
            loadFilteredData("owner", ListingAdapter.BannerType.OWNER);
            setTabSelected(tabOwner, iconOwner, textOwner, underlineOwner);
        });

        tabChoice.setOnClickListener(v -> {
            loadFilteredData("choice", ListingAdapter.BannerType.YOUR_CHOICE);
            setTabSelected(tabChoice, iconChoice, textChoice, underlineChoice);
        });
    }

    private void initializeViews() {
        tabAll = findViewById(R.id.tabAll);
        tabProjects = findViewById(R.id.tabProjects);
        tabVerified = findViewById(R.id.tabVerified);
        tabLaunches = findViewById(R.id.tabLaunches);
        tabReady = findViewById(R.id.tabReady);
        tabOwner = findViewById(R.id.tabOwner);
        tabChoice = findViewById(R.id.tabChoice);

        iconAll = findViewById(R.id.iconAll);
        iconProjects = findViewById(R.id.iconProjects);
        iconVerified = findViewById(R.id.iconVerified);
        iconLaunches = findViewById(R.id.iconLaunches);
        iconReady = findViewById(R.id.iconReady);
        iconOwner = findViewById(R.id.iconOwner);
        iconChoice = findViewById(R.id.iconChoice);

        textAll = findViewById(R.id.textAll);
        textProjects = findViewById(R.id.textProjects);
        textVerified = findViewById(R.id.textVerified);
        textLaunches = findViewById(R.id.textLaunches);
        textReady = findViewById(R.id.textReady);
        textOwner = findViewById(R.id.textOwner);
        textChoice = findViewById(R.id.textChoice);

        underlineAll = findViewById(R.id.underlineAll);
        underlineProjects = findViewById(R.id.underlineProjects);
        underlineVerified = findViewById(R.id.underlineVerified);
        underlineLaunches = findViewById(R.id.underlineLaunches);
        underlineReady = findViewById(R.id.underlineReady);
        underlineOwner = findViewById(R.id.underlineOwner);
        underlineChoice = findViewById(R.id.underlineChoice);

        navProfile = findViewById(R.id.navProfile);
        navSaved = findViewById(R.id.navSaved);
        navTopLocalities = findViewById(R.id.navTopLocalities);

        profileImageBottom = findViewById(R.id.profileImageBottom);
        navProfileInitial = findViewById(R.id.navProfileInitial);
        progressBar = findViewById(R.id.progressBar);
        searchBar = findViewById(R.id.searchBar);
        filterButton = findViewById(R.id.filterButton);
    }

    private void setupProfileUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // ✅ Step 1: Set default name if missing (only once)
            if (user.getDisplayName() == null || user.getDisplayName().trim().isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName("Rashmi") // 👈 You can change this as per actual user input
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Profile", "User profile updated.");
                            }
                        });
            }

            // ✅ Step 2: Prepare image and name display
            String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            String fallbackLetter = "U";

            // Try from displayName first, else email
            if (displayName != null && !displayName.trim().isEmpty()) {
                fallbackLetter = displayName.trim().substring(0, 1).toUpperCase();
            } else if (email != null && !email.trim().isEmpty()) {
                fallbackLetter = email.trim().substring(0, 1).toUpperCase();
            }

            // Set image or fallback letter
            if (photoUrl != null && !photoUrl.isEmpty()) {
                Glide.with(this).load(photoUrl).into(profileImageBottom);
                profileImageBottom.setVisibility(View.VISIBLE);
                navProfileInitial.setVisibility(View.GONE);
            } else {
                navProfileInitial.setText(fallbackLetter);
                navProfileInitial.setVisibility(View.VISIBLE);
                profileImageBottom.setVisibility(View.GONE);
            }
        } else {
            Log.d("ProfileUI", "User is null");
        }
    }


    private void fetchPropertiesFromApi() {
        loadFilteredData("", ListingAdapter.BannerType.NONE);
    }

    private void loadFilteredData(String filterType, ListingAdapter.BannerType bannerType) {
        String url = "https://propertyguru-u9zr.onrender.com/api/property";
        if (!filterType.isEmpty()) {
            url += "?type=" + filterType;
        }

        Log.d("API_CALL", "URL: " + url);
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);

        // ✅ Use JsonObjectRequest here
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    propertyList.clear();

                     try {
                        JSONArray dataArray = response.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject obj = dataArray.getJSONObject(i);

                            ListingModel model = new ListingModel();
                            model.setTitle(obj.optString("title"));
                            model.setLocation(obj.optString("location"));
                            model.setBuilderName(obj.optString("builderName"));
                            model.setPossessionDetails(obj.optString("possessionDetails"));
                            model.setBhk2Price(obj.optJSONObject("bhkPricing").optJSONObject("2BHK") != null
                                    ? obj.optJSONObject("bhkPricing").optJSONObject("2BHK").optString("price")
                                    : "N/A");
                            model.setBhk3Price(obj.optJSONObject("bhkPricing").optJSONObject("3BHK") != null
                                    ? obj.optJSONObject("bhkPricing").optJSONObject("3BHK").optString("price")
                                    : "N/A");

                            // Handle image array if needed
                            JSONArray images = obj.optJSONArray("image");
                            List<String> imageList = new ArrayList<>();
                            if (images != null) {
                                for (int j = 0; j < images.length(); j++) {
                                    String imageUrl = "https://propertyguru-u9zr.onrender.com/uploads/property" + images.getString(j);
                                    Log.d("IMAGE_URL", imageUrl);  // 👈 Add this
                                    imageList.add(imageUrl);
                                }
                            }
                            model.setImages(imageList);


                            model.setDateAdded(parseDate(obj.optString("createdAt")));
                            model.setRelevanceScore(0); // or get from backend if available

                            propertyList.add(model);
                        }

                        adapter = new ListingAdapter(this, propertyList, bannerType);
                        listingsRecycler.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        Log.e("API_PARSE", "JSON parsing error: " + e.getMessage());
                        Toast.makeText(this, "Parsing failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                },
                error -> {
                    Log.e("API_ERROR", "Volley error: " + error.toString());
                    Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });

        queue.add(request);
    }


    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private void showFilterBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.filter_bottom_sheet, null);
        dialog.setContentView(view);

        view.findViewById(R.id.btnRelevance).setOnClickListener(v -> { applySorting("relevance"); dialog.dismiss(); });
        view.findViewById(R.id.btnPriceLowHigh).setOnClickListener(v -> { applySorting("price_low_high"); dialog.dismiss(); });
        view.findViewById(R.id.btnPriceHighLow).setOnClickListener(v -> { applySorting("price_high_low"); dialog.dismiss(); });
        view.findViewById(R.id.btnPriceSqftLowHigh).setOnClickListener(v -> { applySorting("price_sqft_low_high"); dialog.dismiss(); });
        view.findViewById(R.id.btnPriceSqftHighLow).setOnClickListener(v -> { applySorting("price_sqft_high_low"); dialog.dismiss(); });
        view.findViewById(R.id.btnDateAdded).setOnClickListener(v -> { applySorting("date_added"); dialog.dismiss(); });

        dialog.show();
    }

    private void applySorting(String type) {
        switch (type) {
            case "relevance": propertyList.sort((a, b) -> b.getRelevanceScore() - a.getRelevanceScore()); break;
            case "price_low_high": propertyList.sort(Comparator.comparingInt(ListingModel::getPriceValue)); break;
            case "price_high_low": propertyList.sort((a, b) -> b.getPriceValue() - a.getPriceValue()); break;
            case "price_sqft_low_high": propertyList.sort(Comparator.comparingDouble(ListingModel::getPricePerSqft)); break;
            case "price_sqft_high_low": propertyList.sort((a, b) -> Double.compare(b.getPricePerSqft(), a.getPricePerSqft())); break;
            case "date_added": propertyList.sort((a, b) -> {
                Date d1 = a.getDateAdded(), d2 = b.getDateAdded();
                if (d1 == null && d2 == null) return 0;
                if (d1 == null) return 1;
                if (d2 == null) return -1;
                return d2.compareTo(d1);
            }); break;
        }
        adapter.notifyDataSetChanged();
    }

    private void setTabSelected(LinearLayout selectedTab, ImageView icon, TextView label, View underline) {
        resetAllTabs();

        selectedTab.setSelected(true);
        int fromColor = getResources().getColor(R.color.tab_unselected);
        int toColor = getResources().getColor(R.color.tab_selected);

        ValueAnimator anim1 = ValueAnimator.ofArgb(fromColor, toColor);
        anim1.addUpdateListener(a -> label.setTextColor((int) a.getAnimatedValue()));
        anim1.setDuration(250).start();

        ValueAnimator anim2 = ValueAnimator.ofArgb(fromColor, toColor);
        anim2.addUpdateListener(a -> icon.setColorFilter((int) a.getAnimatedValue()));
        anim2.setDuration(250).start();

        label.setTypeface(null, Typeface.BOLD);
        underline.setVisibility(View.VISIBLE);
        underline.setScaleX(0f);
        underline.animate().scaleX(1f).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        selectedTab.animate().scaleX(1.05f).scaleY(1.05f).setDuration(200).start();
    }

    private void resetAllTabs() {
        LinearLayout[] tabs = {tabAll, tabProjects, tabVerified, tabLaunches, tabReady, tabOwner, tabChoice};
        ImageView[] icons = {iconAll, iconProjects, iconVerified, iconLaunches, iconReady, iconOwner, iconChoice};
        TextView[] texts = {textAll, textProjects, textVerified, textLaunches, textReady, textOwner, textChoice};
        View[] underlines = {underlineAll, underlineProjects, underlineVerified, underlineLaunches, underlineReady, underlineOwner, underlineChoice};

        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setSelected(false);
            texts[i].setTextColor(getResources().getColor(R.color.tab_unselected));
            texts[i].setTypeface(null, Typeface.NORMAL);
            icons[i].setColorFilter(getResources().getColor(R.color.tab_unselected));
            underlines[i].setVisibility(View.GONE);
            tabs[i].setScaleX(1f);
            tabs[i].setScaleY(1f);
        }
    }
}
