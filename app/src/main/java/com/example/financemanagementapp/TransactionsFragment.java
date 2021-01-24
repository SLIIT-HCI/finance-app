package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TransactionsFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.transactions_fragment, container, false);

        final FloatingActionButton addTransactions = (FloatingActionButton) view.findViewById(R.id.addTransactions);

        setHasOptionsMenu(true);


        addTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(addTransactions);
                //registerForContextMenu(addTransactions);
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });



        //registerForContextMenu(addTransactions);

/*
        addTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new DashboardFragment());
                fragmentTransaction.commit();
            }
        });

 */

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


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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
                return false;
        }
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

