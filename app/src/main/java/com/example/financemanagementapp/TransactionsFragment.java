package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TransactionsFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    //view objects
    ListView listViewTransactions;

    //a list to store all the artist from firebase database
    List<Transactions> transactions;

    //our database reference object
    DatabaseReference databaseTransactions;

    //progress dialog
    private ProgressDialog progressDialog;

    //view objects
    View view;
    String id, type, amount, date, time, cateory, account, schedule, notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.transactions_fragment, container, false);

        final FloatingActionButton addTransactions = (FloatingActionButton) view.findViewById(R.id.addTransactions);

        progressDialog = new ProgressDialog(getActivity());

        setHasOptionsMenu(true);

        addTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(addTransactions);
                //registerForContextMenu(addTransactions);
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        listViewTransactions = (ListView) view.findViewById(R.id.transactionsListView);

        listViewTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Transactions transaction = transactions.get(i);
                id = transaction.getId();
                amount = String.valueOf(transaction.getAmount());
                date = transaction.getDate();
                time = transaction.getTime();
                date = transaction.getCategory();
                date = transaction.getAccount();
                date = transaction.getSchedule();
                date = transaction.getNotes();

                Toast.makeText(getContext(), amount + date + notes, Toast.LENGTH_SHORT).show();

                //Snackbar.make(view, "Snackbar " + id, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                Intent updateTransactions = new Intent(getContext(), UpdateDeleteTransactionsActivity.class);
                updateTransactions.putExtra("id", id);
                updateTransactions.putExtra("amount", amount);
                //updateTransactions.putExtra("type", transaction.getTransactionType());
                updateTransactions.putExtra("date", date);
                updateTransactions.putExtra("time", time);
                updateTransactions.putExtra("category", cateory);
                updateTransactions.putExtra("account", account);
                updateTransactions.putExtra("schedule", schedule);
                updateTransactions.putExtra("notes", notes);
                startActivity(updateTransactions);

            }
        });

        //registerForContextMenu(addTransactions);

        return view;

    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.contextmenu, popup.getMenu());
        popup.setGravity(Gravity.LEFT);
        popup.show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.manualEntry:
                Intent addTransactions = new Intent(getContext(), AddTransactionsActivity.class);
                startActivity(addTransactions);
                return true;
            case R.id.receiptCapture:
                Toast.makeText(getContext(), "Smart Receipt Capture", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.importFromGallery:
                Toast.makeText(getContext(), "Import Receipt from Gallery", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.manualEntry:
                Intent addTransactions = new Intent(getContext(), AddTransactionsActivity.class);
                startActivity(addTransactions);
                return true;
            case R.id.receiptCapture:
                Toast.makeText(getContext(), "Smart Receipt Capture", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.importFromGallery:
                Toast.makeText(getContext(), "Import Receipt from Gallery", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //getting the reference of artists node
        databaseTransactions = FirebaseDatabase.getInstance().getReference("Transactions");

        //list to store artists
        transactions = new ArrayList<>();

        //attaching value event listener
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                transactions.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Transactions transaction = postSnapshot.getValue(Transactions.class);
                    //adding artist to the list
                    transactions.add(transaction);
                }

                //creating adapter
                TransactionsList transactionsAdapter = new TransactionsList(getActivity(), transactions);
                //attaching adapter to the listview
                listViewTransactions.setAdapter(transactionsAdapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        menu.add(0, v.getId(), 0, "Upload");
        menu.add(0, v.getId(), 0, "Search");
        menu.add(0, v.getId(), 0, "Share");
        menu.add(0, v.getId(), 0, "Bookmark");

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.contextmenu, menu);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()) {
                case R.id.manualEntry:
                    Toast.makeText(getContext(), "Manual Entry", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.receiptCapture:
                    Toast.makeText(getContext(), "Smart Receipt Capture", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.importFromGallery:
                    Toast.makeText(getContext(), "Import Receipt from Gallery", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
    }
     */


}

