package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Split_Expenses extends AppCompatActivity {

    Button splitButton;
    EditText totalAmount, No_Members;
    TextView amountPerMember;

    double split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split__expenses);

        splitButton = (Button) findViewById(R.id.split_button);
        totalAmount = (EditText) findViewById(R.id.splitExpenseAmountView);
        No_Members = (EditText) findViewById(R.id.noOfMembers);
        amountPerMember = (TextView) findViewById(R.id.perMemberTextView);

        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float totAmount = Float.parseFloat(totalAmount.getText() + "");
                int nMembers = Integer.parseInt(No_Members.getText() + "");
                split = totAmount / nMembers;
                amountPerMember.setText(split + "");

            }
        });

    }
}