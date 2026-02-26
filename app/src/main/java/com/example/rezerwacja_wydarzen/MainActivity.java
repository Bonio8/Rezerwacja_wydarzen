package com.example.rezerwacja_wydarzen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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

        String[] events = {"Koncert", "Festiwal muzyczny", "Kino", "Mecz piłki nożnej", "Stand-up"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, events);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvent.setAdapter(adapter);

        buttonSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String event = spinnerEvent.getSelectedItem() != null ? spinnerEvent.getSelectedItem().toString() : "";
                boolean smsChecked = checkBoxSMS.isChecked();

                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                intent.putExtra(EXTRA_NAME, name);
                intent.putExtra(EXTRA_EMAIL, email);
                intent.putExtra(EXTRA_PHONE, phone);
                intent.putExtra(EXTRA_EVENT, event);
                intent.putExtra(EXTRA_SMS, smsChecked);

                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, "Rejestracja zakończona sukcesem", Toast.LENGTH_SHORT).show();
            } else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Rejestracja anulowana", Toast.LENGTH_SHORT).show();
            }
        }
    }
}