package com.example.propertyguru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private EditText etPhone;
    private Button btnSendOtp, btnLoginWhatsapp, btnEmailLogin;
    private TextView tvSkipLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    // Google Sign-In client
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.et_phone);
        btnSendOtp = findViewById(R.id.btn_send_otp);
        btnLoginWhatsapp = findViewById(R.id.btn_login_whatsapp);
        tvSkipLogin = findViewById(R.id.tv_skip_login);
        progressBar = findViewById(R.id.progressBar);

        // New button for Email login (make sure to add this button in your XML layout)
        btnEmailLogin = findViewById(R.id.btn_login_email);

        mAuth = FirebaseAuth.getInstance();

        btnSendOtp.setEnabled(false);
        etPhone.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSendOtp.setEnabled(s.length() == 10);
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        btnSendOtp.setOnClickListener(view -> sendWhatsAppLoginAPI());

        tvSkipLogin.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ListingsActivity.class));
            finish();
        });

        btnLoginWhatsapp.setOnClickListener(v -> sendWhatsAppLoginAPI());

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // ✅ this line is required
                .requestEmail()
                .requestProfile()  // Add this line to request profile info (including photo)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnEmailLogin.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        // Force sign out first to always show the account picker dialog
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                String email = account.getEmail();
                String name = account.getDisplayName();
                Uri photoUrl = account.getPhotoUrl();
                String id = account.getId();
                String idToken = account.getIdToken(); // ✅ Add this

                Log.d(TAG, "Google Sign-In success:");
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Email: " + email);
                Log.d(TAG, "ID: " + id);
                Log.d(TAG, "Token: " + idToken);
                Log.d(TAG, "Picture URL: " + (photoUrl != null ? photoUrl.toString() : "N/A"));

                Toast.makeText(this, "Welcome, " + name, Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email);
                editor.putString("name", name);
                editor.putString("picture", photoUrl != null ? photoUrl.toString() : "");
                editor.apply();

                // 🔗 Pass idToken to backend
                callBackendLoginApi(email, name, photoUrl != null ? photoUrl.toString() : "", idToken);

            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callBackendLoginApi(String email, String name, String photoUrl, String idToken) {
        progressBar.setVisibility(View.VISIBLE);

        try {
            String baseUrl = "https://propertyguru-u9zr.onrender.com/auth/google";

            String url = baseUrl
                    + "?email=" + Uri.encode(email)
                    + "&name=" + Uri.encode(name)
                    + "&picture=" + Uri.encode(photoUrl)
                    + "&token=" + Uri.encode(idToken);

            Log.d("LOGIN_API_URL", url);

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        Log.d("LOGIN_API_RESPONSE", response);

                        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("email", email);
                        editor.putString("name", name);
                        editor.putString("picture", photoUrl);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, ListingsActivity.class);
                        intent.putExtra("user_email", email);
                        intent.putExtra("user_name", name);
                        intent.putExtra("user_picture", photoUrl);
                        startActivity(intent);
                        finish();
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        if (error.networkResponse != null) {
                            String body = new String(error.networkResponse.data);
                            Log.e("LOGIN_API_ERROR", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("LOGIN_API_ERROR", "Response Body: " + body);
                        } else {
                            Log.e("LOGIN_API_ERROR", "No network response: " + error.toString());
                        }
                        Toast.makeText(LoginActivity.this, "Backend login failed", Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(6000, 2, 2.0f));
            Volley.newRequestQueue(this).add(request);

        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Log.e("LOGIN_API_EXCEPTION", "Exception: " + e.getMessage());
            Toast.makeText(this, "Login error", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendWhatsAppLoginAPI() {
        String rawPhone = etPhone.getText().toString().trim();
        String phone = rawPhone.replaceAll("[^0-9]", "");

        if (phone.length() != 10) {
            Toast.makeText(this, "Enter valid 10-digit phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        String fullApiPhone = "91" + phone;
        String fullDisplayPhone = "+91" + phone;
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        Log.d("OTP", "Generated OTP: " + otp);

        String instanceId = "cmcd68hvt2zey148rp3h37gxy";
        String message = "\uFE0FProperty Guru Verification\n\n" +
                "Your OTP is: *" + otp + "*\n\n" +
                "This OTP is valid for 10 minutes.\n" +
                "Please *do not share* th  is code with anyone.\n\n" +
                "Need help? Contact us at info@propertygru.in";

        String url = "https://transapi.bluwaves.in/api/sendText?token=" + instanceId
                + "&phone=" + fullApiPhone
                + "&message=" + Uri.encode(message);

        Log.d("WHATSAPP_URL", url);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    Log.d("WHATSAPP_RESPONSE", response);
                    Toast.makeText(this, "OTP sent to WhatsApp", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, VerifyOtpActivity.class);
                    intent.putExtra("phone", fullDisplayPhone);
                    intent.putExtra("otp", otp);
                    startActivity(intent);
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Log.e("WHATSAPP_ERROR", "Error: " + error.toString());
                    Toast.makeText(this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                });

        request.setRetryPolicy(new DefaultRetryPolicy(6000, 2, 2.0f));
        Volley.newRequestQueue(this).add(request);
    }
}
