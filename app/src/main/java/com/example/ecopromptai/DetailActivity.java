package com.example.ecopromptai;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pełna odpowiedź");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setTitleTextColor(0xFF2E7D32);

        View navIcon = toolbar.getChildAt(1);
        if (navIcon != null) {
            navIcon.setClickable(true);
            navIcon.setOnClickListener(v -> finish());
        }

        TextView fullAiResponse = findViewById(R.id.fullAiResponse);

        String response = getIntent().getStringExtra("response");

        if (response != null && !response.isEmpty()) {
            fullAiResponse.setText(response);
        } else {
            fullAiResponse.setText("Brak danych");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}