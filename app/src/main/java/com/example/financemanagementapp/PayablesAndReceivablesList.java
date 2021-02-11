package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PayablesAndReceivablesList extends ArrayAdapter<Transactions> {

    private Activity context;
    List<Transactions> payablesAndReceivablesList;
    TextView personNameP, scheduledDateP, notesP, catPR, amount;

    public PayablesAndReceivablesList(Activity context, List<Transactions> payablesAndReceivablesList) {
        super(context, R.layout.payables_and_receivables_list, payablesAndReceivablesList);
        this.context = context;
        this.payablesAndReceivablesList = payablesAndReceivablesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.payables_and_receivables_list, null, true);

        catPR = (TextView) listViewItem.findViewById(R.id.catPR);
        amount = (TextView) listViewItem.findViewById(R.id.amountP);
        personNameP = (TextView) listViewItem.findViewById(R.id.personNameP);
        scheduledDateP = (TextView) listViewItem.findViewById(R.id.scheduledDateP);
        notesP = (TextView) listViewItem.findViewById(R.id.notesP);
        //type = (TextView) listViewItem.findViewById(R.id.type);

        Transactions transactions = payablesAndReceivablesList.get(position);

        amount.setText(String.valueOf(transactions.getAmount()));
        personNameP.setText(transactions.getCategory());
        scheduledDateP.setText(transactions.getSchedule());
        notesP.setText(transactions.getNotes());

        if (transactions.getCategory().equals("Receivables")) {
            amount.setTextColor(Color.parseColor("#0CA800"));
            //catPR.setText("Receivables");
        }
        else if (transactions.getCategory().equals("Payables")){
            amount.setTextColor(Color.parseColor("#EA0000"));
            //catPR.setText("Payables");
        }

        return listViewItem;
    }

}
