package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends AppCompatActivity {


    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, button10, buttonC, buttonEqual, buttonok;
    EditText calcAns;

    float mValueOne, mValueTwo;

    boolean addition, subtraction, multiplication, division;

    String ids, amount, date, time, category, trType, account, schedule, notes, function;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        ids = getIntent().getStringExtra("getID");
        //Toast.makeText(getApplicationContext(), function+"toast", Toast.LENGTH_SHORT).show();
        amount = getIntent().getStringExtra("getAmount");
        trType = getIntent().getStringExtra("getType");
        date = getIntent().getStringExtra("getDate");
        time = getIntent().getStringExtra("getTime");
        category = getIntent().getStringExtra("getCategory");
        account = getIntent().getStringExtra("getAccount");
        schedule = getIntent().getStringExtra("getSchedule");
        notes = getIntent().getStringExtra("getNotes");


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 1300;
        params.width = 800;
        params.y = 0;

        this.getWindow().setAttributes(params);

        // add flag
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 1.9f;


/*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 10;
        params.y = 20;
        getWindow().setAttributes(params);
*/

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        buttonMul = (Button) findViewById(R.id.buttonmul);
        buttonDivision = (Button) findViewById(R.id.buttondiv);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonEqual = (Button) findViewById(R.id.buttoneql);
        buttonok = (Button) findViewById(R.id.buttonok);
        calcAns = (EditText) findViewById(R.id.calcAns);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + "0");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (calcAns == null) {
                    calcAns.setText("");
                } else {
                    mValueOne = Float.parseFloat(calcAns.getText() + "");
                    addition = true;
                    calcAns.setText(null);
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(calcAns.getText() + "");
                subtraction = true;
                calcAns.setText(null);
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(calcAns.getText() + "");
                multiplication = true;
                calcAns.setText(null);
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueOne = Float.parseFloat(calcAns.getText() + "");
                division = true;
                calcAns.setText(null);
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mValueTwo = Float.parseFloat(calcAns.getText() + "");

                if (addition == true) {
                    calcAns.setText(mValueOne + mValueTwo + "");
                    addition = false;
                }

                if (subtraction == true) {
                    calcAns.setText(mValueOne - mValueTwo + "");
                    subtraction = false;
                }

                if (multiplication == true) {
                    calcAns.setText(mValueOne * mValueTwo + "");
                    multiplication = false;
                }

                if (division == true) {
                    calcAns.setText(mValueOne / mValueTwo + "");
                    division = false;
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText("");
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcAns.setText(calcAns.getText() + ".");
            }
        });

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
                intent.putExtra("num", calcAns.getText().toString());
                intent.putExtra("getAmount", amount);
                intent.putExtra("getType", trType);
                intent.putExtra("getDate", date);
                intent.putExtra("getTime", time);
                intent.putExtra("getCategory", category);
                intent.putExtra("getAccount", account);
                intent.putExtra("getSchedule", schedule);
                intent.putExtra("getNotes", notes);
                startActivity(intent);
                finish();
            }
        });

    }

}