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

public class NetWorthFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    //view objects
    ListView netWorthListView;

    //a list to store all the artist from firebase database
    List<Transactions> transactions;
    //List<Transactions> transactions2;

    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    //view objects
    View view;

    Spinner dateRangePickerNW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.net_worth_fragment, container, false);

        progressDialog = new ProgressDialog(getActivity());
        netWorthListView = (ListView) view.findViewById(R.id.netWorthListView);

        /**********************************************************************************************************/

        // Spinner element
        dateRangePickerNW = (Spinner) view.findViewById(R.id.dateRangePickerNW);
        // Spinner click listener
        dateRangePickerNW.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
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
        dateRangePickerNW.setAdapter(dataAdapterType2);

        /**********************************************************************************************************/

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ///////////////////////////////////////////////////////////////////////////////////////////


        ArrayList<Object> list = new ArrayList<>();
        list.add(new TransactionType("Liabilities"));
        list.add(new String(" "));
        list.add(new TransactionCategories("Credit Card", "Rs. 150000.00"));
        list.add(new TransactionCategories("Loans", "Rs. 55000.00"));
        list.add(new TransactionCategories("Lease", "Rs. 82450.00"));
        list.add(new TransactionCategories("Payables", "Rs. 6540.00"));
        list.add(new TransactionCategories("Other", "Rs. 0.00"));
        list.add(new String(" "));


        list.add(new TransactionType("Assets"));
        list.add(new String(" "));
        list.add(new TransactionCategories("Cash", "Rs. 22540.00"));
        list.add(new TransactionCategories("Investments", "Rs. 17540.00"));
        list.add(new TransactionCategories("Savings", "Rs. 27540.00"));
        list.add(new TransactionCategories("Receivables", "Rs. 1740.00"));
        list.add(new TransactionCategories("Others", "Rs. 10540.00"));

        //netEarningsListView.setAdapter(new NetEarningsAdapter(this, list));
        //creating adapter
        NetWorthAdapter transactionsAdapter = new NetWorthAdapter(this, list);
        //attaching adapter to the listview
        netWorthListView.setAdapter(transactionsAdapter);


        ///////////////////////////////////////////////////////////////////////////////////////////


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
                    if (  category.equals("Asset") ) {
                        transactions.add(transaction);
                        //total = total + transaction.getAmount();
                    }

                    else if ( category.equals("Liability") ) {
                        transactions.add(transaction);
                        //total = total + transaction.getAmount();
                    }

                }

                //creating adapter
                NetWorthList transactionsAdapter = new NetWorthList(getActivity(), transactions);
                //attaching adapter to the listview
                netWorthListView.setAdapter(transactionsAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**********************************************************************************************************/

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

             */
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /**********************************************************************************************************/


}

