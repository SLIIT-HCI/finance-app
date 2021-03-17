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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Closeable;

public class Split_Expenses extends AppCompatActivity {

    Button splitButton;
    EditText totalAmount, No_Members;
    TextView amountPerMember;
    //ImageView splitCapture, splitUpload;


   FloatingActionButton splitCapture, splitUpload, splitTick, splitCancel;

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
        splitCapture = (FloatingActionButton) findViewById(R.id.captureSplitReceipt);
        splitUpload = (FloatingActionButton) findViewById(R.id.uploadSplitReceipt);

        //Bottom buttons
        splitTick = (FloatingActionButton)findViewById(R.id.splitExTick);
        splitCancel = (FloatingActionButton) findViewById(R.id.splitAmountCancel);


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


//        //Once the user clicks on capture icon: direct to capture activity
        splitCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Split_Expenses.this, SmartReceiptCapture.class);
                startActivity(i);
            }
        });


//        //Selecting the image from the gallery: direct to recognize the text from the bill
        splitUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Split_Expenses.this, TextRecognitionActivity.class);
                startActivity(i);
            }
        });

        //if user need to cancel, move back to the previous activity
        splitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Split_Expenses.this, TransactionsFragment.class);
//                startActivity(i);
                    onBackPressed();

            }
        });

        //if clicks on tick button move to add transaction activity
        splitTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Split_Expenses.this, AddTransactionsActivity.class);
                startActivity(i);
            }
        });


    }

}