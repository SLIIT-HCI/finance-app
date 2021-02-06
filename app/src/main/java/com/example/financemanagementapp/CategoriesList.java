package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoriesList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] type;
    private final Integer[] imgid;

    public CategoriesList(Activity context, String[] maintitle, String[] subtitle, String[] type, Integer[] imgid) {
        super(context, R.layout.categories_list, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.type=type;
        this.imgid=imgid;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.categories_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        TextView types = (TextView) rowView.findViewById(R.id.type);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);
        types.setText(type[position]);
        //types.setVisibility(View.INVISIBLE);

        if (types.equals("Income")) {
            titleText.setTextColor(Color.parseColor("#0CA800"));
        }
        else if (types.equals("Expense")){
            titleText.setTextColor(Color.parseColor("#EA0000"));
        }
        else if (type[0].equals("Asset")) {
            titleText.setTextColor(Color.parseColor("#0059b3"));
        }
        else {
            titleText.setTextColor(Color.parseColor("#FF6600"));
        }

        return rowView;

    };

}
