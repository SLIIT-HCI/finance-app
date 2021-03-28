package com.example.financemanagementapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class NetWorthAdapter extends BaseAdapter {

    ArrayList<Object> list;

    private static final int CATEGORY = 0;
    private static final int HEADER  = 1;
    private static final int HEADING  = 2;

    private LayoutInflater inflater;

//    public NetWorthAdapter(NetWorthFragment activity, ArrayList<Object> list) {
//        this.list = list;
//        //inflater = (LayoutInflater) .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof TransactionCategories) {
            return CATEGORY;
        }
        if(list.get(position) instanceof TransactionType) {
            return HEADING;
        }
        else {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if ( view == null ) {
            switch ( getItemViewType(i) ) {
//                case CATEGORY:
//                    view = inflater.inflate(R.layout.net_earnings_list, null);
//                    break;
                case HEADER:
                    view = inflater.inflate(R.layout.listview_headers, null);
                    break;
                case HEADING:
                    view = inflater.inflate(R.layout.listview_transaction_type, null);
                    break;
            }
        }

        switch ( getItemViewType(i) ) {
            case HEADER:
                TextView headerListView = (TextView) view.findViewById(R.id.headerListView);
                headerListView.setText(((String) list.get(i)));
                break;

            case HEADING:
                TextView headingListView = (TextView) view.findViewById(R.id.headingListView);
                headingListView.setText(((TransactionType)list.get(i)).getType());
                String heading = ((TransactionType)list.get(i)).getType();
                if (heading.equals("Liabilities")) {
                    headingListView.setTextColor(Color.parseColor("#000000"));
                    headingListView.setBackgroundColor(Color.parseColor("#ffe6e6"));
                    //amount.setTextColor(Color.parseColor("#EA0000"));

                }
                else if (heading.equals("Assets")) {
                    headingListView.setTextColor(Color.parseColor("#000000"));
                    headingListView.setBackgroundColor(Color.parseColor("#d9f2d9"));
                    //headerListView.setTextSize(20);
                }
                break;

            case CATEGORY:
//                TextView category = (TextView) view.findViewById(R.id.categoryNE);
//                TextView amount = (TextView) view.findViewById(R.id.amountNE);
//                category.setText(((TransactionCategories)list.get(i)).getCategory());
//                amount.setText(((TransactionCategories)list.get(i)).getAmount());

//                String cat = ((TransactionCategories)list.get(i)).getCategory();
//                if ( cat.equals("Cash") || cat.equals("Investments") || cat.equals("Savings") || cat.equals("Receivables") || cat.equals("Others") ){
//                    amount.setTextColor(Color.parseColor("#0CA800"));
//                    //headerListView.setTextSize(20);
//                }
//                else {
//                    amount.setTextColor(Color.parseColor("#EA0000"));
//                }

                break;
        }
        return view;
    }

}
