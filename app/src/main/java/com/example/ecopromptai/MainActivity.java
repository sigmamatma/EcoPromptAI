package com.example.ecopromptai;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText inputPrompt;
    TextView aiResponsePreview;
    TextView btnShowFullResponse;
    TextView charCount;
    Button btnOptimize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFF2E7D32);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("EcoPrompt AI");
        }

        inputPrompt = findViewById(R.id.inputPrompt);
        aiResponsePreview = findViewById(R.id.aiResponsePreview);
        btnOptimize = findViewById(R.id.btnOptimize);
        btnShowFullResponse = findViewById(R.id.btnShowFullResponse);
        charCount = findViewById(R.id.charCount);

        inputPrompt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCount.setText(s.length() + " znaków");
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        btnOptimize.setOnClickListener(v -> {
            String text = inputPrompt.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(this, "Wpisz zapytanie", Toast.LENGTH_SHORT).show();
                return;
            }

            aiResponsePreview.setText("Przykładowa odpowiedź AI");
        });

        View.OnClickListener openDetail = v -> {
            String response = aiResponsePreview.getText().toString();
            if (response.trim().isEmpty()) return;

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("response", response);
            startActivity(intent);
        };

        aiResponsePreview.setOnClickListener(openDetail);
        btnShowFullResponse.setOnClickListener(openDetail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        }

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}