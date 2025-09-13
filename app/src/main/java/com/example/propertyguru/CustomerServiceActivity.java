package com.example.propertyguru;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerServiceActivity extends AppCompatActivity {

    private LinearLayout faqContainer;
    private EditText searchInput;

    // Class-level lists
    private List<String> questions;
    private List<String> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        faqContainer = findViewById(R.id.faqContainer);
        searchInput = findViewById(R.id.searchInput);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Initialize all FAQs
        initializeFAQs();

        // Load all FAQs initially
        loadFAQs(questions, answers);

        // Add search functionality
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFAQs(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initializeFAQs() {
        questions = new ArrayList<>(Arrays.asList(
                "Is an online Rental Agreement valid or legal?",
                "How much stamp duty should I pay on my rental agreement?",
                "How can I check the validity of E-stamped Rental Agreements?",
                "What are the important points to validate in a rental agreement?",
                "I am yet to find a tenant for my property, why should I pre-purchase the Rental Agreement Service?",
                "What are the types of tenancy agreements in India?",
                "What is a Rental Agreement?",
                "How to change my company name?",
                "How can Property Guru be accessed?",
                "How can I receive property alerts based on my buying/renting preference?",
                "How can I buy a paid property advertisement?",
                "How can a relationship manager help me?",
                "How to search for properties if I am a buyer/tenant?",
                "How do I search for properties posted by owner/dealer/builder?",
                "How to know the right market value of my property?",
                "Which cities does Property Guru offer its services?",
                "What type of properties are available?",
                "How can Property Guru help me?"
        ));

        answers = new ArrayList<>(Arrays.asList(
                "Online rental agreements executed on e-stamp and signed by both parties are valid. For more help, contact our support.",
                "Typically, Rs. 100 for e-stamp paper. You can select higher denominations too depending on your state rules.",
                "You can visit your state's e-stamping website and enter the document number to verify the agreement.",
                "Include full details of all parties, rent amount, duration, terms, notice period, and responsibilities clearly in the agreement.",
                "The agreement service is valid for 12 months. You can use it anytime once you get a suitable tenant.",
                "1. Lease Agreement: Governed by rent control laws.\n2. Leave & License Agreement: More flexible terms.",
                "A rental agreement is a legal document between landlord and tenant ensuring legal protection to both parties.",
                "Submit valid proof documents based on your company classification to request a name change.",
                "You can use our app on Android, iOS, or access it via desktop browser.",
                "Enable notifications or opt-in during property searches to get alerts based on your preferences.",
                "Choose from banners, microsites, top visibility, or digital packages via our Paid Services section.",
                "We assign a dedicated relationship manager to guide you with finding buyers or tenants quickly.",
                "Use the search bar to enter location, budget, and property type for smart results.",
                "Apply filters in the search section to choose listings by owner, dealer, or builder.",
                "Use our 'Know Your Property Value' tool and market comparisons to evaluate your asset.",
                "Property Guru is live in 600+ cities and also allows listing of international properties.",
                "Apartments, independent houses, floors, shops, offices, and land are available on our platform.",
                "We help you buy, sell or rent through listings, verified contacts, lead management, and support tools."
        ));
    }

    private void loadFAQs(List<String> qList, List<String> aList) {
        faqContainer.removeAllViews(); // clear previous
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < qList.size(); i++) {
            View faqView = inflater.inflate(R.layout.item_faq_question, faqContainer, false);

            TextView questionText = faqView.findViewById(R.id.questionText);
            TextView answerText = faqView.findViewById(R.id.answerText);

            questionText.setText(qList.get(i));
            answerText.setText(aList.get(i));
            answerText.setVisibility(View.GONE);

            faqView.setOnClickListener(v -> {
                if (answerText.getVisibility() == View.GONE) {
                    answerText.setVisibility(View.VISIBLE);
                } else {
                    answerText.setVisibility(View.GONE);
                }
            });

            faqContainer.addView(faqView);
        }
    }

    private void filterFAQs(String keyword) {
        keyword = keyword.toLowerCase();
        List<String> filteredQ = new ArrayList<>();
        List<String> filteredA = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            String q = questions.get(i).toLowerCase();
            String a = answers.get(i).toLowerCase();
            if (q.contains(keyword) || a.contains(keyword)) {
                filteredQ.add(questions.get(i));
                filteredA.add(answers.get(i));
            }
        }

        loadFAQs(filteredQ, filteredA);
    }
}
