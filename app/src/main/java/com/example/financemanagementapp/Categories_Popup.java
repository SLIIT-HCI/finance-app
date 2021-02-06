package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Categories_Popup extends AppCompatActivity {

    String itemValue;
    ListView list;

    //Intent intent = getIntent();
    //String trType = intent.getStringExtra("type").toString();

    String[] subtitle ={
            "Cash", "Investments", "Savings","Receivables", "Other",
            "Credit Card", "Loans", "Mortgages", "Payables", "Other",
            "Salary", "Bonus", "Other",
            "Car", "Car", "Car",
            "Household", "Household", "Household", "Household", "Household",
            //"Entertainment", "Entertainment", "Entertainment",
            //"Utilities",
            //"Other",
    };

    String[] maintitle ={
            "Sub Title 1","Sub Title 2", "Sub Title 3","Sub Title 4", "Sub Title 5",
            "Sub Title 1","Sub Title 2", "Sub Title 3","Sub Title 4", "Sub Title 5",
            "Sub Title 1","Sub Title 2", "Sub Title 3",
            "Fuel", "Maintenance", "Other",
            "Grocery", "Medicine", "School", "Clothing", "Other",
            //"Movies", "Shopping", "Other",
            //"Electricity", "Water", "TV", "Internet", "Other",
            //"Other",

    };

    String[] type ={
            "Asset", "Asset", "Asset", "Asset", "Asset",
            "Liability", "Liability", "Liability", "Liability", "Liability",
            "Income", "Income", "Income",
            "Expense", "Expense", "Expense",
            "Expense", "Expense", "Expense", "Expense", "Expense",
            //"Expense", "Expense", "Expense",
            //"Expense",
            //"Expense",
    };

    Integer[] imgid={
            R.drawable.icon_account,R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
            R.drawable.icon_account,R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
            R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
            R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
            R.drawable.icon_account,R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
            //R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
            //R.drawable.icon_reminders,
            //R.drawable.icon_reminders,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories__popup);

        /*
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1500;
        params.width = 900;
        params.y = -20;

        this.getWindow().setAttributes(params);

        // add flag
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 5.9f;
        */


        CategoriesList adapter = new CategoriesList(this, maintitle, subtitle, type, imgid);
        list=(ListView)findViewById(R.id.category_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    itemValue = (String) list.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
                    intent.putExtra("cat", itemValue);
                    startActivity(intent);
                    finish();
                }

                else if(position == 1) {
                    itemValue = (String) list.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),itemValue,Toast.LENGTH_SHORT).show();
                }

                else if(position == 2) {
                    itemValue = (String) list.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3) {
                    itemValue = (String) list.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
                }
                else if(position == 4) {
                    itemValue = (String) list.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}