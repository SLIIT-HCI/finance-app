package com.example.financemanagementapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SMSIntegrationFragment extends Fragment {

    public static SMSIntegrationFragment newInstance() {
        return (new SMSIntegrationFragment());
    }

    /************************/

    EditText addFinName, contact;
    Button addSMS, viewAllSMS;

    //a list to store all the transactions from firebase database
    List<SMSProvider> smsProviderList;

    //our database reference object
    DatabaseReference databaseReference;

    Long maxId;
    String nameSMS, contactSMS;

    /************************/

    View view;
    private static SMSIntegrationFragment inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;

    public static SMSIntegrationFragment instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_sms_integration, container, false);

        /******************************************************************************************/
        addFinName = (EditText) view.findViewById(R.id.addFinName);
        contact = (EditText) view.findViewById(R.id.contact);
        addSMS = (Button) view.findViewById(R.id.addSMS);
        viewAllSMS = (Button) view.findViewById(R.id.viewAllSMS);

        // getting the reference of bookings node
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SMSProviders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId =(dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //list to store artists
        smsProviderList = new ArrayList<>();

        addSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addTransaction()
                //the method is defined below
                //this method is actually performing the write operation
                addSMSProvider();
            }
        });

        viewAllSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewSMSList.class);
                startActivity(intent);
            }
        });

        /******************************************************************************************/

        smsListView = (ListView) view.findViewById(R.id.SMSList);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);

        // Add SMS Read Permision At Runtime
        // Todo : If Permission Is Not GRANTED
        if(ContextCompat.checkSelfPermission(getActivity().getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            // Todo : If Permission Granted Then Show SMS
            refreshSmsInbox();

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }



        return view;

    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        do {
            if(smsInboxCursor.getString(indexAddress).equals("BOC")) {
                String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n";
                arrayAdapter.add(str);
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
            Toast.makeText(getActivity(), smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**********************************************************************************************/

    /*
     * This method is saving a new transaction to the
     * Firebase Realtime Database
     * */
    private void addSMSProvider() {
        //getting the  values to save
        nameSMS = addFinName.getText().toString().trim();
        contactSMS = contact.getText().toString().trim();

        //checking if the value is provided
        if (TextUtils.isEmpty(nameSMS)) {
            //if the value is not given displaying a toast
            Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(contactSMS)) {
            //if the value is not given displaying a toast
            Toast.makeText(getActivity(), "Please enter contact details", Toast.LENGTH_SHORT).show();
        }

        else {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our transaction
            String id = databaseReference.push().getKey();
            //String id = String.valueOf(maxId+1);

            //creating an transaction Object
            SMSProvider smsProvider = new SMSProvider(id, nameSMS, contactSMS);

            //Saving the transaction
            databaseReference.child(id).setValue(smsProvider);

            //displaying a success toast
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();

        }
    }

}