package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionsList extends ArrayAdapter<Transactions> {

    private Activity context;
    List<Transactions> transactionsList;
    TextView category, subCategory, type, amount;

    public TransactionsList(Activity context, List<Transactions> transactionsList) {
        super(context, R.layout.transactions_list, transactionsList);
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.transactions_list, null, true);

        amount = (TextView) listViewItem.findViewById(R.id.amount);
        category = (TextView) listViewItem.findViewById(R.id.category);
        subCategory = (TextView) listViewItem.findViewById(R.id.subCategory);
        //type = (TextView) listViewItem.findViewById(R.id.type);

        Transactions transactions = transactionsList.get(position);
        amount.setText(String.valueOf(transactions.getAmount()));
        category.setText(transactions.getCategory());
        subCategory.setText(transactions.getDate());

        if (transactions.getTransactionType().equals("Income")) {
            amount.setTextColor(Color.parseColor("#0CA800"));
        }
        else if (transactions.getTransactionType().equals("Expense")){
            amount.setTextColor(Color.parseColor("#EA0000"));
        }
        else if (transactions.getTransactionType().equals("Asset")){
            amount.setTextColor(Color.parseColor("#0059b3"));
        }
        else if (transactions.getTransactionType().equals("Liability")){
            amount.setTextColor(Color.parseColor("#FF6600"));
        }

        return listViewItem;
    }

}
