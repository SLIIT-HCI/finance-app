package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Split_Expenses extends AppCompatActivity {

    Button splitButton;
    EditText totalAmount, No_Members;
    TextView amountPerMember;
    ImageView splitCapture, splitUpload;
   // Button splitCapture, splitUpload;

    double split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split__expenses);

        splitButton = (Button) findViewById(R.id.split_button);
        totalAmount = (EditText) findViewById(R.id.splitExpenseAmountView);
        No_Members = (EditText) findViewById(R.id.noOfMembers);
        amountPerMember = (TextView) findViewById(R.id.perMemberTextView);

        //Capturing
        splitCapture = (ImageView) findViewById(R.id.captureSplitReceipt);
        splitUpload = (ImageView) findViewById(R.id.uploadSplitReceipt);

        //splitting the total amount among the members
        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float totAmount = Float.parseFloat(totalAmount.getText() + "");
                int nMembers = Integer.parseInt(No_Members.getText() + "");
                split =(double) totAmount / nMembers;
                amountPerMember.setText(split + "");

            }
        });


        //Once the user clicks on capture icon: direct to capture activity
        splitCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Split_Expenses.this, SmartReceiptCapture.class);
                startActivity(i);
            }
        });


        //Selecting the image from the gallery: direct to recognize the text from the bill
        splitUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Split_Expenses.this, TextRecognitionActivity.class);
                startActivity(i);
            }
        });

    }

}