package com.example.propertyguru;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RentSellActivity extends AppCompatActivity {

    TextView tabOwner, tabBroker, tvLoginHere;
    public String selectedUserType = "owner"; // ✅ Default user type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_sell);

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        tabOwner = findViewById(R.id.tabOwner);
        tabBroker = findViewById(R.id.tabBroker);
        tvLoginHere = findViewById(R.id.tv_login);

        // Default tab: Owner
        loadFragment(new OwnerFragment());
        selectedUserType = "owner"; // ✅
        tabOwner.setBackgroundResource(R.drawable.tab_selected);
        tabOwner.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        tabBroker.setBackgroundResource(R.drawable.tab_unselected);
        tabBroker.setTextColor(ContextCompat.getColor(this, R.color.black));

        // ✅ Owner tab click
        tabOwner.setOnClickListener(v -> {
            loadFragment(new OwnerFragment());
            selectedUserType = "owner"; // ✅ Set type
            tabOwner.setBackgroundResource(R.drawable.tab_selected);
            tabOwner.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            tabBroker.setBackgroundResource(R.drawable.tab_unselected);
            tabBroker.setTextColor(ContextCompat.getColor(this, R.color.black));
        });

        // ✅ Broker tab click
        tabBroker.setOnClickListener(v -> {
            loadFragment(new BrokerBuilderFragment());
            selectedUserType = "broker"; // ✅ Set type
            tabBroker.setBackgroundResource(R.drawable.tab_selected);
            tabBroker.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            tabOwner.setBackgroundResource(R.drawable.tab_unselected);
            tabOwner.setTextColor(ContextCompat.getColor(this, R.color.black));
        });

        // ✅ Login bottom sheet
        if (tvLoginHere != null) {
            tvLoginHere.setOnClickListener(v -> showLoginBottomSheet());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void showLoginBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_login, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

        ImageView backArrow = sheetView.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> bottomSheetDialog.dismiss());

        Button truecallerBtn = sheetView.findViewById(R.id.btn_truecaller);
        Button manualVerifyBtn = sheetView.findViewById(R.id.btn_verify_manual);

        truecallerBtn.setOnClickListener(v -> {
            VerifyPhoneBottomSheet bottomSheet = new VerifyPhoneBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        manualVerifyBtn.setOnClickListener(v -> {
            VerifyPhoneBottomSheet bottomSheet = new VerifyPhoneBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });
    }
}
