package com.example.financemanagementapp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SMSList extends ArrayAdapter<SMSProvider> {

    private Activity context;
    List<SMSProvider> smsList;
    TextView name, contact;
    String flag;

    public SMSList(Activity context, List<SMSProvider> smsList) {
        super(context, R.layout.activity_s_m_s_list, smsList);
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_s_m_s_list, null, true);

        name = (TextView) listViewItem.findViewById(R.id.smsContactName);
        contact = (TextView) listViewItem.findViewById(R.id.smsContactNumber);

        SMSProvider smsProvider = smsList.get(position);
        name.setText(String.valueOf(smsProvider.getNameSMS()));
        contact.setText(smsProvider.getContactSMS());

        flag = ViewSMSList.getActivityInstance().getFlag();


        //if ( flag.equals("1") ) {
          //  name.setTextColor(Color.parseColor("#EA0000"));
        //}

        return listViewItem;
    }

}