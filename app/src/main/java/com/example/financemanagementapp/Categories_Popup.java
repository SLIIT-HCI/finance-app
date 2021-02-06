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

    private static String value;

    ListView list;
    String[] subtitle;
    String[] maintitle;
    String[] type;
    Integer[] imgid;

    //database
    String trType;
    String amount, date, time, account, schedule, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories__popup);

        amount = getIntent().getStringExtra("getAmount");
        trType = getIntent().getStringExtra("getType");
        date = getIntent().getStringExtra("getDate");
        time = getIntent().getStringExtra("getTime");
        account = getIntent().getStringExtra("getAccount");
        schedule = getIntent().getStringExtra("getSchedule");
        notes = getIntent().getStringExtra("getNotes");
        value = trType;


        if (trType.equals("Asset")) {
            subtitle = new String[]{
                    "Cash", "Investments", "Savings", "Receivables", "Other",
            };

            maintitle = new String[]{
                    "Sub Title 1", "Sub Title 2", "Sub Title 3", "Sub Title 4", "Sub Title 5",
            };

            type = new String[]{
                    "Asset", "Asset", "Asset", "Asset", "Asset",
            };

            imgid= new Integer[]{
                    R.drawable.icon_account, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
            };

        }

        else if (trType.equals("Liability")){

            subtitle = new String[]{
                    "Credit Card", "Loans", "Mortgages", "Payables", "Other",
            };

            maintitle = new String[]{
                    "Sub Title 1", "Sub Title 2", "Sub Title 3", "Sub Title 4", "Sub Title 5",
            };

            type = new String[]{
                    "Liability", "Liability", "Liability", "Liability", "Liability",
            };

            imgid = new Integer[]{
                    R.drawable.icon_account, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
            };
        }

        else if (trType.equals("Income")){

            subtitle = new String[]{
                    "Salary", "Bonus", "Other",
            };

            maintitle = new String[]{
                    "Sub Title 1", "Sub Title 2", "Sub Title 3",
            };

            type = new String[]{
                    "Income", "Income", "Income",
            };

            imgid = new Integer[]{
                    R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
            };
        }

        else if (trType.equals("Expense")){

            subtitle = new String[]{
                    "Car", "Car", "Car",
                    "Household", "Household", "Household", "Household", "Household",
                    "Entertainment", "Entertainment", "Entertainment",
                    "Utilities", "Utilities", "Utilities", "Utilities", "Utilities",
                    "Other",
            };

            maintitle = new String[]{
                    "Fuel", "Maintenance", "Other",
                    "Grocery", "Medicine", "School", "Clothing", "Other",
                    "Movies", "Shopping", "Other",
                    "Electricity", "Water", "TV", "Internet", "Other",
                    "Other",

            };

            type = new String[]{
                    "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense", "Expense", "Expense",
                    "Expense",
            };

            imgid = new Integer[]{
                    R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
                    R.drawable.icon_account, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
                    R.drawable.icon_budget, R.drawable.icon_appearance, R.drawable.icon_appearance,
                    R.drawable.icon_account, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm, R.drawable.icon_alarm,
                    R.drawable.icon_reminders,
            };
        }

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

                    itemValue = (String) list.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
                    intent.putExtra("getAmount", amount);
                    intent.putExtra("getType", trType);
                    intent.putExtra("getDate", date);
                    intent.putExtra("getTime", time);
                    intent.putExtra("getCategory", itemValue);
                    intent.putExtra("getAccount", account);
                    intent.putExtra("getSchedule", schedule);
                    intent.putExtra("getNotes", notes);
                    startActivity(intent);
                    finish();
            }
        });
    }

    public static String getValue() {
        return value;
    }

}