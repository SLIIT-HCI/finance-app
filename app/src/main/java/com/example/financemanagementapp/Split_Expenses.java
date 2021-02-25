package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Split_Expenses extends AppCompatActivity {

    //Global variables
    Button splitButton;
    EditText totalAmount, No_Members;
    TextView amountPerMember;

    int Nmembers;
    double total, pPerson;

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

                float TotAmount = Float.parseFloat(totalAmount.getText() + "");
                int noOfMembers = Integer.parseInt(No_Members.getText() + "");
                float result = ((float) TotAmount / noOfMembers);
                amountPerMember.setText((int) result);


//                String TotAmount = totalAmount.getText().toString();
//                String noOfMembers = No_Members.getText().toString();
//                double result = ((double) TotAmount/noOfMembers);
//                amountPerMember.setText(result);

//                buttonDivision.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mValueOne = Float.parseFloat(calcAns.getText() + "");
//                        division = true;
//                        calcAns.setText(null);
//                    }
//                });
//                if (division == true) {
//                    calcAns.setText(mValueOne / mValueTwo + "");
//                    division = false;
//                }
            }
        });
    }
}