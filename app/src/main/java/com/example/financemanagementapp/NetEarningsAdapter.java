package com.example.financemanagementapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class NetEarningsAdapter extends BaseAdapter {

    ArrayList<Object> list;

    private static final int CATEGORY = 0;
    private static final int HEADER  = 1;

    private LayoutInflater inflater;
    private Activity context;


    public NetEarningsAdapter(Activity context, ArrayList<Object> list) {
        //super(context, R.layout.net_earnings_list, list);
        this.context = context;
        this.list = list;
    }

    /*
    public NetEarningsAdapter(FragmentActivity activity, ArrayList<Object> list) {
        this.list = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
    public NetEarningsAdapter(NetEarningsFragment context, ArrayList<Object> list) {
        this.list = list;
        inflater = (LayoutInflater)  context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof TransactionCategories) {
            return CATEGORY;
        }
        else {
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.net_earnings_list, null, true);


        if ( view == null ) {
            switch ( getItemViewType(i) ) {
                case CATEGORY:
                    view = inflater.inflate(R.layout.net_earnings_list, null);
                    break;
                case HEADER:
                    view = inflater.inflate(R.layout.listview_headers, null);
                    break;
            }
        }

        switch ( getItemViewType(i) ) {
            case CATEGORY:
                TextView category = (TextView) view.findViewById(R.id.categoryNE);
                TextView amount = (TextView) view.findViewById(R.id.amountNE);
                category.setText(((TransactionCategories)list.get(i)).getCategory());
                amount.setText(((TransactionCategories)list.get(i)).getAmount());
                break;
            case HEADER:
                TextView headerListView = (TextView) view.findViewById(R.id.headerListView);
                headerListView.setText(((String)list.get(i)));
                break;
        }
        return view;
    }

}
