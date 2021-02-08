package com.example.financemanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        //FloatingActionButton imageView = (FloatingActionButton) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        TextView types = (TextView) rowView.findViewById(R.id.type);
        CardView cardView = (CardView) rowView.findViewById(R.id.icons);

        titleText.setText(maintitle[position]);
        imageView.setImageResource(imgid[position]);
        subtitleText.setText(subtitle[position]);
        types.setText(type[position]);
        types.setVisibility(View.INVISIBLE);

        String t = Categories_Popup.getValue();


        if (t.equals("Income")) {
            titleText.setTextColor(Color.parseColor("#0CA800"));
            imageView.setBackgroundColor(Color.parseColor("#0CA800"));
            cardView.setCardBackgroundColor(Color.parseColor("#0CA800"));
        }
        else if (t.equals("Expense")){
            titleText.setTextColor(Color.parseColor("#EA0000"));
            imageView.setBackgroundColor(Color.parseColor("#EA0000"));
            cardView.setCardBackgroundColor(Color.parseColor("#EA0000"));
        }
        else if (t.equals("Asset")) {
            titleText.setTextColor(Color.parseColor("#0059b3"));
            imageView.setBackgroundColor(Color.parseColor("#0059b3"));
            cardView.setCardBackgroundColor(Color.parseColor("#0059b3"));
        }
        else if (t.equals("Liability")) {
            titleText.setTextColor(Color.parseColor("#FF6600"));
            imageView.setBackgroundColor(Color.parseColor("#FF6600"));
            cardView.setCardBackgroundColor(Color.parseColor("#FF6600"));
        }

        return rowView;

    };

}
