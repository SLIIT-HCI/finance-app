package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateSMSList extends AppCompatActivity {

    EditText updameFinName, updateContact;
    String id, name, contact;
    Button updateSMS, cancelSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_s_m_s_list);

        updameFinName = (EditText) findViewById(R.id.updameFinName);
        updateContact = (EditText) findViewById(R.id.updateContact);
        updateSMS = (Button) findViewById(R.id.updateSMS);
        cancelSMS = (Button) findViewById(R.id.cancelSMS);

        // Getting data to update
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        updameFinName.setText(intent.getStringExtra("name"));
        updateContact.setText(intent.getStringExtra("contact"));

        updateSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSMSProviderDetails(id);
            }
        });

        cancelSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void updateSMSProviderDetails(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("SMSProviders").child(id);

        //getting the  values to update
        name = updameFinName.getText().toString().trim();
        contact = updateContact.getText().toString().trim();

        //updating transaction
        SMSProvider smsProvider = new SMSProvider(id, name, contact);
        dR.setValue(smsProvider);

        Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }


}