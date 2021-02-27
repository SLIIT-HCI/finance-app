package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NetEarningsList extends ArrayAdapter<Transactions> {

    private Activity context;
    List<Transactions> netEarningsListView;
    TextView categoryNE, amountNE;
    Float total = 0.0f;

    public NetEarningsList(Activity context, List<Transactions> netEarningsListView) {
        super(context, R.layout.net_earnings_list, netEarningsListView);
        this.context = context;
        this.netEarningsListView = netEarningsListView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.net_earnings_list, null, true);

        categoryNE = (TextView) listViewItem.findViewById(R.id.categoryNE);
        amountNE = (TextView) listViewItem.findViewById(R.id.amountNE);

        Transactions transactions = netEarningsListView.get(position);

        //total = PayablesAndReceivablesFragment.getActivityInstance().getTotal();
        //Toast.makeText(getContext(), total+"1", Toast.LENGTH_SHORT).show();

        categoryNE.setText(transactions.getCategory());
        amountNE.setText(String.valueOf(transactions.getAmount()));
        //totalPR.setText(String.valueOf(total));

        String type = transactions.getTransactionType();

        if (type.equals("Income")) {
            amountNE.setTextColor(Color.parseColor("#0CA800"));
        }
        else if (type.equals("Expense")){
            amountNE.setTextColor(Color.parseColor("#EA0000"));
        }

        return listViewItem;
    }

}
