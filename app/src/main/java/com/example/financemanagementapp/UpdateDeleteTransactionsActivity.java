package com.example.financemanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UpdateDeleteTransactionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView updatecloseBtn;
    EditText updateTransactionsAmount, updateTransactionsCategory, updateTransactionsAccount, updateTransactionsSchedule, updateTransactionsNotes;
    TextView updateTransactionsDate, updateTransactionsTime;
    ImageView updateTransactionsCalculator;
    DatePickerDialog datepicker;
    Float amount;
    String type, time, category, account, schedule, notes;
    String transactionType;
    FloatingActionButton updateDB, deleteDB;
    int hour, minute;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_transactions);

        updatecloseBtn = (ImageView) findViewById(R.id.updatecloseBtn);
        updateTransactionsAmount = (EditText) findViewById(R.id.updateTransactionsAmount);
        updateTransactionsDate = (TextView) findViewById(R.id.updateTransactionsDate);
        updateTransactionsTime = (TextView) findViewById(R.id.updateTransactionsTime);
        updateTransactionsCategory = (EditText) findViewById(R.id.updateTransactionsCategory);
        updateTransactionsAccount = (EditText) findViewById(R.id.updateTransactionsAccount);
        updateTransactionsSchedule = (EditText) findViewById(R.id.updateTransactionsSchedule);
        updateTransactionsNotes = (EditText) findViewById(R.id.updateTransactionsNotes);
        updateTransactionsCalculator = (ImageView) findViewById(R.id.updateTransactionsCalculator);
        updateDB = (FloatingActionButton) findViewById(R.id.updateDB);
        deleteDB = (FloatingActionButton) findViewById(R.id.deleteDB);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        updateTransactionsCategory.setText("cat 1");
        updateTransactionsAmount.setText(intent.getStringExtra("amount"));
        updateTransactionsDate.setText(intent.getStringExtra("date"));
        updateTransactionsTime.setText(intent.getStringExtra("time"));
        updateTransactionsCategory.setText(intent.getStringExtra("category"));
        updateTransactionsAccount.setText(intent.getStringExtra("account"));
        updateTransactionsSchedule.setText(intent.getStringExtra("schedule"));
        updateTransactionsNotes.setText(intent.getStringExtra("notes"));


        // Spinner element
        final Spinner updateTransactionsTypeSpinner = (Spinner) findViewById(R.id.updateTransactionsType);
        // Spinner click listener
        updateTransactionsTypeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> transactionsType = new ArrayList<String>();
        transactionsType.add("Income");
        transactionsType.add("Expense");
        transactionsType.add("Asset");
        transactionsType.add("Liability");
        transactionsType.add("Payable");
        transactionsType.add("Receivable");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this, R.layout.spinner_item, transactionsType);
        // Drop down layout style - list view with radio button
        dataAdapterType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        updateTransactionsTypeSpinner.setAdapter(dataAdapterType);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateTransactionsDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        updateTransactionsDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateDeleteTransactionsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateTransactionsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateDeleteTransactionsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        hour = h;
                        minute = m;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hour, minute);
                        updateTransactionsTime.setText(hour + ":" + minute);
                    }
                }, 12, 0, false );
                timePickerDialog.updateTime(hour,minute);
                timePickerDialog.show();
            }
        });

        updateTransactionsCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculator = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(calculator);
            }
        });

        updatecloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTransaction(id);
            }
        });

        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteTransaction(id);
                alertDialog();
            }
        });

    }

    public void displayDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        // date picker dialog
        datepicker = new DatePickerDialog(UpdateDeleteTransactionsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        updateTransactionsDate.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                    }
                }, year, month, day);
        datepicker.getDatePicker();
        datepicker.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (parent.getId()) {
            case R.id.updateTransactionsType:
                transactionType = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


    private void updateTransaction(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("Transactions").child(id);

        //getting the  values to update
        amount = (Float.parseFloat(updateTransactionsAmount.getText().toString().trim()));
        type = transactionType.toString().trim();
        String date = updateTransactionsDate.getText().toString().trim();
        time = updateTransactionsTime.getText().toString().trim();
        category = updateTransactionsCategory.getText().toString().trim();
        account = updateTransactionsAccount.getText().toString().trim();
        schedule = updateTransactionsSchedule.getText().toString().trim();
        notes = updateTransactionsNotes.getText().toString().trim();

        //updating transaction
        Transactions transaction = new Transactions(id, amount, type, date, time, category, account, schedule, notes);
        dR.setValue(transaction);

        Toast.makeText(getApplicationContext(), "Transaction Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();

    }


    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are You sure you want to delete this transaction?");
        dialog.setTitle("Delete Transaction");
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
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("Transactions").child(id);
                dR.removeValue();
                Toast.makeText(getApplicationContext(), "Transaction Deleted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

}