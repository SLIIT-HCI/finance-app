package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView language, currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        language = (TextView) findViewById(R.id.language);
        currency = (TextView) findViewById(R.id.currency);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent language = new Intent(getApplicationContext(), );
                //startActivity(language);
            }
        });

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent currency = new Intent(getApplicationContext(), CurrencySettingsActivity.class);
                startActivity(currency);
            }
        });


    }



}