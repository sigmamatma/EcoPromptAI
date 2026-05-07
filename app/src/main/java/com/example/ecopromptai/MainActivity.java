package com.example.ecopromptai;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.ecopromptai.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class MainActivity extends AppCompatActivity {

    EditText inputPrompt;
    TextView aiResponsePreview;
    TextView optimizedPrompt;
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
        optimizedPrompt = findViewById(R.id.optimizedPrompt);
        btnOptimize = findViewById(R.id.btnOptimize);
        btnShowFullResponse = findViewById(R.id.btnShowFullResponse);
        charCount = findViewById(R.id.charCount);

        GenerativeModel gm = new GenerativeModel("gemini-3-flash-preview", BuildConfig.GEMINI_API_KEY);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

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
            // UI: Pokazujemy stan ładowania
            optimizedPrompt.setText("Generowanie optymalizacji... Proszę czekać.");
            btnOptimize.setEnabled(false); // Blokada przycisku (anti-spam)

// 2. Przygotuj zapytanie (prompt)
            Content content = new Content.Builder()
                    .addText("Jesteś ekspertem od prompt engineeringu. Zoptymalizuj poniższy prompt, aby był precyzyjny, zwięzły i najlepiej streszczał zarys tego, co użytkownik potencjalnie chce w prostych słowach: " + text +" W odpowiedzi wypisz tylko streszczony przez ciebie prompt. Postaraj sie przedewszystkim ograniczyć ilość wykorzystanych słów do wymaganego minimum dla czytelności użytkownika")
                    .build();

// 3. Wyślij zapytanie
            ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

// 4. Odbierz odpowiedź (asynchronicznie)
            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    String tekstOdAi = result.getText();
                    runOnUiThread(() -> {
                        optimizedPrompt.setText(tekstOdAi);
                        btnOptimize.setEnabled(true);
                        Log.d("GEMINI_AI", "Sukces: " + tekstOdAi);
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    runOnUiThread(() -> {
                        optimizedPrompt.setText("Błąd komunikacji z AI. Sprawdź połączenie lub klucz API.");
                        btnOptimize.setEnabled(true);
                        Log.e("GEMINI_AI", "Błąd: " + t.getMessage());
                    });
                }
            }, ContextCompat.getMainExecutor(this));


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