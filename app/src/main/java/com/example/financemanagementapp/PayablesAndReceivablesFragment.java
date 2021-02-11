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

public class PayablesAndReceivablesFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    //view objects
    ListView listViewPyablesAndReceivables;

    //a list to store all the artist from firebase database
    List<Transactions> transactions;
    //List<Transactions> transactions2;

    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    //view objects
    View view;

    Spinner payOrReceiveSpinner, dateRangePickerPR;
    public static String choice;
    public static Float total = 0.0f;

    static PayablesAndReceivablesFragment INSTANCE;

    /**********************************************************************************************************/

    public static PayablesAndReceivablesFragment newInstance() {
        return (new PayablesAndReceivablesFragment());
    }

    /**********************************************************************************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        INSTANCE=this;

        view = inflater.inflate(R.layout.fragment_payables_and_receivables, container, false);

        /**********************************************************************************************************/

        // Spinner element
        payOrReceiveSpinner = (Spinner) view.findViewById(R.id.payOrReceiveSpinner);
        // Spinner click listener
        payOrReceiveSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> transactionsType = new ArrayList<String>();
        transactionsType.add("Payables");
        transactionsType.add("Receivables");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, transactionsType);
        // Drop down layout style - list view with radio button
        dataAdapterType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        payOrReceiveSpinner.setAdapter(dataAdapterType);

        /**********************************************************************************************************/

        // Spinner element
        dateRangePickerPR = (Spinner) view.findViewById(R.id.dateRangePickerPR);
        // Spinner click listener
        dateRangePickerPR.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
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
        dateRangePickerPR.setAdapter(dataAdapterType2);

        /**********************************************************************************************************/

        progressDialog = new ProgressDialog(getActivity());
        listViewPyablesAndReceivables = (ListView) view.findViewById(R.id.payablesAndReceivablesListView);

        return view;
    }

    /**********************************************************************************************************/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (parent.getId()) {
            case R.id.payOrReceiveSpinner:
                choice = parent.getItemAtPosition(position).toString();
                onStart();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /**********************************************************************************************************/

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

                    if ( transaction.getCategory().equals("Payables") && choice.equals("Payables") ) {
                        transactions.add(transaction);
                        total = total + transaction.getAmount();
                    }


                    else if ( transaction.getCategory().equals("Receivables") && choice.equals("Receivables") ) {
                        transactions.add(transaction);
                        total = total + transaction.getAmount();
                    }

                }

                //creating adapter
                PayablesAndReceivablesList transactionsAdapter = new PayablesAndReceivablesList(getActivity(), transactions);
                //PayablesAndReceivablesList transactionsAdapter2 = new PayablesAndReceivablesList(getActivity(), transactions2);
                //attaching adapter to the listview
                listViewPyablesAndReceivables.setAdapter(transactionsAdapter);
                //listViewPyablesAndReceivables.setAdapter(transactionsAdapter2);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static PayablesAndReceivablesFragment getActivityInstance()
    {
        return INSTANCE;
    }

    public static Float getTotal() {
        return total;
    }

}