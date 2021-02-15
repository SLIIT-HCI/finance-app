package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NetWorthList extends ArrayAdapter<Transactions> {

    private Activity context;
    List<Transactions> netWorthListView;
    TextView categoryNW, amountNW;
    Float total = 0.0f;

    public NetWorthList(Activity context, List<Transactions> netEarningsListView) {
        super(context, R.layout.net_worth_list, netEarningsListView);
        this.context = context;
        this.netWorthListView = netEarningsListView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.net_worth_list, null, true);

        categoryNW = (TextView) listViewItem.findViewById(R.id.categoryNW);
        amountNW = (TextView) listViewItem.findViewById(R.id.amountNW);

        Transactions transactions = netWorthListView.get(position);

        //total = PayablesAndReceivablesFragment.getActivityInstance().getTotal();
        //Toast.makeText(getContext(), total+"1", Toast.LENGTH_SHORT).show();

        categoryNW.setText(transactions.getCategory());
        amountNW.setText(String.valueOf(transactions.getAmount()));
        //totalPR.setText(String.valueOf(total));

        String type = transactions.getTransactionType();

        if (type.equals("Asset")){
            amountNW.setTextColor(Color.parseColor("#0059b3"));
        }
        else if (type.equals("Liability")){
            amountNW.setTextColor(Color.parseColor("#FF6600"));
        }

        return listViewItem;
    }

}
