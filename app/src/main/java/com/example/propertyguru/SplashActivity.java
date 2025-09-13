package com.example.propertyguru;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 4000;
    private static final String FULL_TEXT = "PROPERTY GURU";
    private static final long CHAR_DELAY = 100;

    private TextView appNameText;
    private TextView taglineText;
    private Handler textHandler = new Handler();
    private int currentCharIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logoImage);
        appNameText = findViewById(R.id.appNameText);
        taglineText = findViewById(R.id.taglineText);
        View splashRoot = findViewById(R.id.splashRoot);
        ShimmerFrameLayout shimmerLayout = findViewById(R.id.shimmerLayout);

        shimmerLayout.startShimmer();

        // Animations
        logoImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
        appNameText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_fade));

        // Background animation
        int colorFrom = Color.WHITE;
        int colorTo = Color.parseColor("#f3e5f5");
        ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnim.setDuration(1500);
        colorAnim.addUpdateListener(anim -> splashRoot.setBackgroundColor((int) anim.getAnimatedValue()));
        colorAnim.start();

        // Typing animation
        startTypingAnimation();

        // Delayed transition
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }, SPLASH_DURATION);
    }

    private void startTypingAnimation() {
        currentCharIndex = 0;
        appNameText.setText("");
        textHandler.postDelayed(typeCharacter, CHAR_DELAY);
    }

    private final Runnable typeCharacter = new Runnable() {
        @Override
        public void run() {
            if (currentCharIndex <= FULL_TEXT.length()) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String currentText = FULL_TEXT.substring(0, currentCharIndex);

                if (currentCharIndex <= 9) {
                    SpannableString part1 = new SpannableString(currentText);
                    part1.setSpan(new ForegroundColorSpan(Color.parseColor("#4C1F7A")),
                            0, currentText.length(), 0);
                    builder.append(part1);
                } else {
                    SpannableString part1 = new SpannableString("PROPERTY ");
                    part1.setSpan(new ForegroundColorSpan(Color.parseColor("#4C1F7A")),
                            0, part1.length(), 0);
                    builder.append(part1);

                    SpannableString part2 = new SpannableString(currentText.substring(9));
                    part2.setSpan(new ForegroundColorSpan(Color.parseColor("#dc3545")),
                            0, part2.length(), 0);
                    builder.append(part2);
                }

                appNameText.setText(builder);
                currentCharIndex++;
                textHandler.postDelayed(this, CHAR_DELAY);
            } else {
                showTagline();
            }
        }
    };

    private void showTagline() {
        String tagline = "Find your perfect space";
        SpannableString coloredTagline = new SpannableString(tagline);
        coloredTagline.setSpan(new ForegroundColorSpan(Color.parseColor("#4C1F7A")),
                0, tagline.length(), 0);

        taglineText.setText(coloredTagline);

        Animation taglineAnim = AnimationUtils.loadAnimation(this, R.anim.tagline_fade_up);
        taglineText.setVisibility(View.VISIBLE);
        taglineText.startAnimation(taglineAnim);
    }

}
