package com.example.financemanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewSMSList extends AppCompatActivity {

    //view objects
    ListView smsProvidersListVIew;

    //a list to store all the artist from firebase database
    List<SMSProvider> smsProviderList;
    List<String> phoneNumbersList;


    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    String id, name, contact;
    Button updateSMS, deleteSMS;
    public static String flag = "0";

    static ViewSMSList INSTANCE;

    ListView smsListView;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    private static ViewSMSList inst;

    public static ViewSMSList instance() {
        return inst;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_s_m_s_list);

        INSTANCE = this;

        smsProvidersListVIew = (ListView) findViewById(R.id.smsProvidersListVIew);
        updateSMS = (Button) findViewById(R.id.updateSMS);
        deleteSMS = (Button) findViewById(R.id.deleteSMS);

        progressDialog = new ProgressDialog(getApplicationContext());


        smsProvidersListVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SMSProvider smsProvider = smsProviderList.get(i);
                id = smsProvider.getId();
                name = smsProvider.getNameSMS();
                contact = smsProvider.getContactSMS();
            }
        });

        updateSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updatesms = new Intent(getApplicationContext(), UpdateSMSList.class);
                updatesms.putExtra("id", id);
                updatesms.putExtra("name", name);
                updatesms.putExtra("contact", contact);
                startActivity(updatesms);
            }
        });

        deleteSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

        smsListView = (ListView) findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

        // Add SMS Read Permision At Runtime
        // Todo : If Permission Is Not GRANTED
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(ViewSMSList.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        inst = this;

        //progressDialog.setMessage("Loading...");
        //progressDialog.show();

        //getting the reference of transactions node
        databaseTransactions = FirebaseDatabase.getInstance().getReference("SMSProviders");

        //list to store transactions
        smsProviderList = new ArrayList<>();

        //attaching value event listener
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous transaction list
                smsProviderList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting transaction
                    SMSProvider smsProvider = postSnapshot.getValue(SMSProvider.class);

                   // String phone = smsProvider.getContactSMS();
                   // phoneNumbersList.add(phone);

                    //adding transaction to the list
                    smsProviderList.add(smsProvider);

                }

                //creating adapter
                SMSList smsListAdapter = new SMSList(ViewSMSList.this, smsProviderList);
                //attaching adapter to the listview
                smsProvidersListVIew.setAdapter(smsListAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are You sure you want to delete this record?");
        dialog.setTitle("Delete SMS Provider");
        dialog.setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //Toast.makeText(getApplicationContext(), "Profile Not Deleted", Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("SMSProviders").child(id);
                dR.removeValue();
                Toast.makeText(getApplicationContext(), "Record Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public static ViewSMSList getActivityInstance() {
        return INSTANCE;
    }

    public static String getFlag() {
        return flag;
    }

    public void refreshSmsInbox() {

        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexDate = smsInboxCursor.getColumnIndex("date");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();

        //String[] mStringArray = new String[phoneNumbersList.size()];
        //mStringArray = phoneNumbersList.toArray(mStringArray);
        String[] mStringArray = {"BOC", "8822"};

        do {

            for (int i = 0; i < mStringArray.length; i++) {

                if (smsInboxCursor.getString(indexAddress).equals(mStringArray[i])) {

                    //figuring if its debt or cred
                    String mssg = smsInboxCursor.getString(indexBody);
                    String res = checkCredDebt(mssg);

                    //extracting the amount
                    String amount = grabAmount(mssg);

                    //simplifying date format
                    long dte = Long.parseLong(smsInboxCursor.getString(indexDate));
                    Date date = new Date(dte);
                    @SuppressLint("SimpleDateFormat") String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);


                    String str = "\n" +"SMS From: " + smsInboxCursor.getString(indexAddress) + "\n" +amount +
                            "\n" + res + "\n" + formattedDate + "\n";

                    arrayAdapter.add(str);

                }

            }

        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(getApplicationContext(), smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String checkCredDebt(String msg){
        String temp = msg.toLowerCase();
        if(temp.contains("debit")){
            return "Debit";
        }
        else if(temp.contains("credit")){
            return "Credit";
        }
        return "Null";
    }

    public String grabAmount(String msg){

        //String tempI = msg.contains(msg.matches("\\d{2}-\\d{2}"));
        //String testI = msg.replaceAll("[^0-9?!\\.]","");
        Pattern patternI = Pattern.compile("\\d{3}.\\d{2}");
        Matcher matcherI = patternI.matcher(msg);
        Pattern patternII = Pattern.compile("\\d,\\d{3}.\\d{2}|\\d{3},\\d{3}.\\d{2}|\\d{2},\\d{3}.\\d{2}");
        Matcher matcherII = patternII.matcher(msg);
        if(matcherII.find()){
            return matcherII.group();
        }
        else if(matcherI.find()){
            return matcherI.group();
        }
        else
            return "Null";
    }
}