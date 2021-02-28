package com.example.financemanagementapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NetEarningsFragment extends Fragment { //implements AdapterView.OnItemSelectedListener

    //view objects
    ListView netEarningsListView;

    //a list to store all the artist from firebase database
    List<Transactions> transactions;
    //List<Transactions> transactions2;

    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    //view objects
    View view;

    Spinner dateRangePickerNE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.net_earnings_fragment, container, false);

        //progressDialog = new ProgressDialog(getActivity());
        netEarningsListView = (ListView) view.findViewById(R.id.netEarningsListView);

        /**********************************************************************************************************/

        // Spinner element
        dateRangePickerNE = (Spinner) view.findViewById(R.id.dateRangePickerNE);
        // Spinner click listener
        dateRangePickerNE.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> dateRange = new ArrayList<String>();
        dateRange.add("Today");
        dateRange.add("This Week");
        dateRange.add("This Month");
        dateRange.add("This Year");
        dateRange.add("Last Week");
        dateRange.add("Last Month");
        dateRange.add("Last Year");
        dateRange.add("All");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterType2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dateRange);
        // Drop down layout style - list view with radio button
        dataAdapterType2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        dateRangePickerNE.setAdapter(dataAdapterType2);

        /**********************************************************************************************************/


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<Object> list = new ArrayList<>();
        list.add(new TransactionType("Expense"));
        list.add(new String("Vehicle"));
        list.add(new TransactionCategories("Fuel", "Rs. 15000.00"));
        list.add(new TransactionCategories("Maintenance", "Rs. 5000.00"));
        list.add(new TransactionCategories("Other", "Rs. 0.00"));
        list.add(new String("Household"));
        list.add(new TransactionCategories("Grocery", "Rs. 12540.00"));
        list.add(new TransactionCategories("Medicine", "Rs. 2450.00"));
        list.add(new TransactionCategories("Shopping", "Rs. 6540.00"));
        list.add(new TransactionCategories("Dining Out", "Rs. 3240.00"));
        list.add(new TransactionCategories("Other", "Rs. 0.00"));
        list.add(new String("Utilities"));
        list.add(new TransactionCategories("Electricity", "Rs. 10540.00"));
        list.add(new TransactionCategories("Water", "Rs. 540.00"));
        list.add(new TransactionCategories("TV", "Rs. 840.00"));
        list.add(new TransactionCategories("Internet", "Rs. 2540.00"));
        list.add(new TransactionCategories("Other", "Rs. 0.00"));
        list.add(new String("Other"));
        list.add(new TransactionCategories("Other", "Rs. 0.00"));
        list.add(new TransactionType("Income"));
        list.add(new String("Earnings"));
        list.add(new TransactionCategories("Salary", "Rs. 122540.00"));
        list.add(new TransactionCategories("Bonus", "Rs. 17540.00"));
        list.add(new TransactionCategories("Others", "Rs. 10540.00"));

        //netEarningsListView.setAdapter(new NetEarningsAdapter(this, list));
        //creating adapter
        NetEarningsAdapter transactionsAdapter = new NetEarningsAdapter(getActivity(), list);
        //attaching adapter to the listview
        netEarningsListView.setAdapter(transactionsAdapter);

    }

        ///////////////////////////////////////////////////////////////////////////////////////////

        /*
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //getting the reference of transactions node
        databaseTransactions = FirebaseDatabase.getInstance().getReference("Transactions");

        //list to store transactions
        transactions = new ArrayList<>();
        //transactions2 = new ArrayList<>();

        //attaching value event listener
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous transaction list
                transactions.clear();
                //transactions2.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting transaction
                    Transactions transaction = postSnapshot.getValue(Transactions.class);

                    //adding transaction to the list

                    String category = transaction.getTransactionType();
                    if (  category.equals("Income") || category.equals("Expense")) {
                        transactions.add(transaction);
                        //total = total + transaction.getAmount();
                    }

                }

                //creating adapter
                //NetEarningsList transactionsAdapter = new NetEarningsList(getActivity(), transactions);
                //attaching adapter to the listview
                //netEarningsListView.setAdapter(transactionsAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**********************************************************************************************************/
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (parent.getId()) {
            /*
            case R.id.payOrReceiveSpinner:
                choice = parent.getItemAtPosition(position).toString();
                catPR.setText(choice);
                onStart();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /**********************************************************************************************************/


}

