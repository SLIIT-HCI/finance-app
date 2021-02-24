package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Split_Expenses extends AppCompatActivity {

    Button splitButton;
    EditText totalAmount, No_Members;
    TextView amountPerMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split__expenses);

        splitButton = (Button) findViewById(R.id.split_button);
        totalAmount = (EditText) findViewById(R.id.splitExpenseAmountView);
        No_Members = (EditText) findViewById(R.id.noOfMembers);
        amountPerMember = (TextView) findViewById(R.id.perMemberTextView);

//        String totAmount, noMemb, amountPMemeb;

    }
}