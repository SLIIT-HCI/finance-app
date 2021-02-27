package com.example.financemanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSMSList extends AppCompatActivity {

    //view objects
    ListView smsProvidersListVIew;

    //a list to store all the artist from firebase database
    List<SMSProvider> smsProviderList;

    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    String id, name, contact;
    Button updateSMS, deleteSMS;
    public static String flag = "0";

    static ViewSMSList INSTANCE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_s_m_s_list);

        INSTANCE=this;

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
    }

    @Override
    public void onStart() {
        super.onStart();

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

    public static ViewSMSList getActivityInstance()
    {
        return INSTANCE;
    }

    public static String getFlag() {
        return flag;
    }


}