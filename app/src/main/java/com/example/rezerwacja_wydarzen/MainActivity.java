package com.example.rezerwacja_wydarzen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_EMAIL = "extra_email";
    public static final String EXTRA_PHONE = "extra_phone";
    public static final String EXTRA_EVENT = "extra_event";
    public static final String EXTRA_SMS = "extra_sms";

    private EditText editTextName, editTextEmail, editTextPhone;
    private Spinner spinnerEvent;
    private CheckBox checkBoxSMS;
    private Button buttonSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        spinnerEvent = findViewById(R.id.spinnerEvent);
        checkBoxSMS = findViewById(R.id.checkBoxSMS);
        buttonSummary = findViewById(R.id.buttonSummary);

        String[] events = {"Wybierz wydarzenie", "Koncert", "Festiwal muzyczny", "Kino", "Mecz piłki nożnej", "Stand-up"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, events) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvent.setAdapter(adapter);
        buttonSummary.setOnClickListener(v -> {

            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            int eventPosition = spinnerEvent.getSelectedItemPosition();
            boolean smsChecked = checkBoxSMS.isChecked();

            if (name.isEmpty()) {
                editTextName.setError("Wpisz imię i nazwisko");
                editTextName.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                editTextEmail.setError("Wpisz email");
                editTextEmail.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Niepoprawny format email");
                editTextEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                editTextPhone.setError("Wpisz numer telefonu");
                editTextPhone.requestFocus();
                return;
            }

            if (!phone.matches("\\d{9,15}")) {
                editTextPhone.setError("Niepoprawny numer telefonu");
                editTextPhone.requestFocus();
                return;
            }

            if (eventPosition == 0) {
                Toast.makeText(MainActivity.this, "Wybierz wydarzenie", Toast.LENGTH_SHORT).show();
                return;
            }

            String event = spinnerEvent.getSelectedItem().toString();

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            intent.putExtra(EXTRA_EMAIL, email);
            intent.putExtra(EXTRA_PHONE, phone);
            intent.putExtra(EXTRA_EVENT, event);
            intent.putExtra(EXTRA_SMS, smsChecked);

            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Rejestracja zakończona sukcesem", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Rejestracja anulowana", Toast.LENGTH_SHORT).show();
            }
        }
    }
}