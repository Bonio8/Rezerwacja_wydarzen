package com.example.rezerwacja_wydarzen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    private TextView textViewSummary;
    private Button buttonConfirm, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewSummary = findViewById(R.id.textViewSummary);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonCancel = findViewById(R.id.buttonCancel);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String email = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        String phone = intent.getStringExtra(MainActivity.EXTRA_PHONE);
        String event = intent.getStringExtra(MainActivity.EXTRA_EVENT);
        boolean sms = intent.getBooleanExtra(MainActivity.EXTRA_SMS, false);

        String summaryText = "Imię: " + name +
                "\nEmail: " + email +
                "\nTelefon: " + phone +
                "\nWydarzenie: " + event +
                "\nPrzypomnienie SMS: " + (sms ? "Tak" : "Nie");
        textViewSummary.setText(summaryText);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}