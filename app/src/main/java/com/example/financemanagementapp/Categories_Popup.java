package com.example.financemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
    String ids, amount, date, time, account, schedule, notes, function;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories__popup);

        function = getIntent().getStringExtra("getFunction");
        ids = getIntent().getStringExtra("getID");
        //Toast.makeText(getApplicationContext(), function+"toast", Toast.LENGTH_SHORT).show();
        amount = getIntent().getStringExtra("getAmount");
        trType = getIntent().getStringExtra("getType");
        date = getIntent().getStringExtra("getDate");
        time = getIntent().getStringExtra("getTime");
        account = getIntent().getStringExtra("getAccount");
        schedule = getIntent().getStringExtra("getSchedule");
        notes = getIntent().getStringExtra("getNotes");
        value = trType;


        if (trType.equals("Asset")) {
            maintitle = new String[]{
                    "Cash", "Investments", "Savings", "Receivables", "Other",
            };

            subtitle = new String[]{
                    "Asset", "Asset", "Asset", "Asset", "Asset",
            };

            type = new String[]{
                    "Asset", "Asset", "Asset", "Asset", "Asset",
            };

            imgid= new Integer[]{
                    R.drawable.icon_cash, R.drawable.icon_investments, R.drawable.icon_savings, R.drawable.icon_receivables, R.drawable.icon_other,
            };

        }

        else if (trType.equals("Liability")){

            maintitle = new String[]{
                    "Credit Card", "Loans", "Lease", "Payables", "Other",
            };

            subtitle = new String[]{
                    "Liability", "Liability", "Liability", "Liability", "Liability",
            };

            type = new String[]{
                    "Liability", "Liability", "Liability", "Liability", "Liability",
            };

            imgid = new Integer[]{
                    R.drawable.icon_credit_card, R.drawable.icon_loan, R.drawable.icon_mortgage, R.drawable.icon_payables, R.drawable.icon_other,
            };
        }

        else if (trType.equals("Income")){

            maintitle= new String[]{
                    "Salary", "Bonus", "Other",
            };

            subtitle = new String[]{
                    "Income", "Income", "Income",
            };

            type = new String[]{
                    "Income", "Income", "Income",
            };

            imgid = new Integer[]{
                    R.drawable.icon_salary, R.drawable.icon_bonus, R.drawable.icon_other,
            };
        }

        else if (trType.equals("Expense")){

            subtitle = new String[]{
                    "Vehicle", "Vehicle", "Vehicle",
                    "Household", "Household", "Household", "Household", "Household",
                    "Entertainment", "Entertainment", "Entertainment", "Entertainment",
                    "Utilities", "Utilities", "Utilities", "Utilities", "Utilities",
                    "Other",
            };

            maintitle = new String[]{
                    "Fuel", "Maintenance", "Other",
                    "Grocery", "Medicine", "Education", "Clothing", "Other",
                    "Movies", "Shopping", "Dining Out", "Other",
                    "Electricity", "Water", "TV", "Internet", "Other",
                    "Other",

            };

            type = new String[]{
                    "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense", "Expense",
                    "Expense", "Expense", "Expense", "Expense", "Expense",
                    "Expense",
            };

            imgid = new Integer[]{
                    R.drawable.icon_fuel, R.drawable.icon_maintenance, R.drawable.icon_other,
                    R.drawable.icon_shopping_cart, R.drawable.icon_medicine, R.drawable.icon_education, R.drawable.icon_clothing, R.drawable.icon_other,
                    R.drawable.icon_movies, R.drawable.icon_shopping, R.drawable.icon_dining, R.drawable.icon_other,
                    R.drawable.icon_electricity, R.drawable.icon_water, R.drawable.icon_tv, R.drawable.icon_wifi, R.drawable.icon_other,
                    R.drawable.icon_other,
            };
        }


        CategoriesList adapter = new CategoriesList(this, maintitle, subtitle, type, imgid);
        list=(ListView)findViewById(R.id.category_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    itemValue = (String) list.getItemAtPosition(position);
                    passData();
                    finish();
            }
        });
    }

    private void passData() {

        function = getIntent().getStringExtra("getFunction");
        String check = function;

        Intent intent = new Intent(getApplicationContext(), AddTransactionsActivity.class);
        intent.putExtra("getAmount", amount);
        intent.putExtra("getType", trType);

       // Toast.makeText(getApplicationContext(), trType+"1", Toast.LENGTH_SHORT).show();

        intent.putExtra("getDate", date);
        intent.putExtra("getTime", time);
        intent.putExtra("getCategory", itemValue);
        intent.putExtra("getAccount", account);
        intent.putExtra("getSchedule", schedule);
        intent.putExtra("getNotes", notes);
        startActivity(intent);
    }

    public static String getValue() {
        return value;
    }

}