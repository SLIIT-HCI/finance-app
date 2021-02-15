package com.example.financemanagementapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NetEarningsFragment extends Fragment {

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.net_earnings_fragment, container, false);

        progressDialog = new ProgressDialog(getActivity());
        netEarningsListView = (ListView) view.findViewById(R.id.netEarningsListView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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
                    if (  category.equals("Income") ) {
                        transactions.add(transaction);
                        //total = total + transaction.getAmount();
                    }

                    else if ( category.equals("Expense") ) {
                        transactions.add(transaction);
                        //total = total + transaction.getAmount();
                    }

                }

                //creating adapter
                NetEarningsList transactionsAdapter = new NetEarningsList(getActivity(), transactions);
                //attaching adapter to the listview
                netEarningsListView.setAdapter(transactionsAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

