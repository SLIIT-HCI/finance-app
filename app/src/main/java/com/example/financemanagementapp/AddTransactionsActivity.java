package com.example.financemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTransactionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button closeBtn;
    EditText addTransactionsAmount, addTransactionsDate, addTransactionsTime, addTransactionsCategory, addTransactionsAccount, addTransactionsSchedule, addTransactionsNotes;
    DatePickerDialog datepicker;
    TimePicker timepicker;
    ImageView addTransactionsCalculator;
    String transactionType;
    int hour, minute;
    FloatingActionButton addToDB;

    ListView listViewArtists;

    //a list to store all the transactions from firebase database
    List<Transactions> transactionsList;

    //our database reference object
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transactions);

        // getting the reference of bookings node
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transactions");

        final Calendar myCalendar = Calendar.getInstance();

        closeBtn = (Button) findViewById(R.id.closeBtn);
        addTransactionsAmount = (EditText) findViewById(R.id.addTransactionsAmount);
        addTransactionsDate = (EditText) findViewById(R.id.addTransactionsDate);
        addTransactionsTime = (EditText) findViewById(R.id.addTransactionsTime);
        addTransactionsCategory = (EditText) findViewById(R.id.addTransactionsCategory);
        addTransactionsAccount = (EditText) findViewById(R.id.addTransactionsAccount);
        addTransactionsSchedule = (EditText) findViewById(R.id.addTransactionsSchedule);
        addTransactionsNotes = (EditText) findViewById(R.id.addTransactionsNotes);
        addTransactionsCalculator = (ImageView) findViewById(R.id.addTransactionsCalculator);
        addToDB = (FloatingActionButton) findViewById(R.id.addToDB);


        Intent intent = getIntent();
        addTransactionsAmount.setText(intent.getStringExtra("num"));

        addTransactionsCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculator = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(calculator);
            }
        });

        // Spinner element
        Spinner addTransactionsTypeSpinner = (Spinner) findViewById(R.id.addTransactionsType);
        // Spinner click listener
        addTransactionsTypeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // Spinner Drop down elements
        List<String> transactionsType = new ArrayList<String>();
        transactionsType.add("Income");
        transactionsType.add("Expense");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this, R.layout.spinner_item, transactionsType);
        // Drop down layout style - list view with radio button
        dataAdapterType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        addTransactionsTypeSpinner.setAdapter(dataAdapterType);


        /*
        addTransactionsDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayDatePicker();
            }

        });

         */

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                addTransactionsDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        addTransactionsDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTransactionsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addTransactionsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTransactionsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        hour = h;
                        minute = m;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hour, minute);
                        addTransactionsTime.setText(hour + ":" + minute);
                    }
                }, 12, 0, false );
                timePickerDialog.updateTime(hour,minute);
                timePickerDialog.show();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //list to store artists
        transactionsList = new ArrayList<>();

        addToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addTransaction()
                //the method is defined below
                //this method is actually performing the write operation
                addTransaction();
            }
        });

    }

    public void displayDatePicker() {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            // date picker dialog
            datepicker = new DatePickerDialog(AddTransactionsActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            addTransactionsDate.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                        }
                    }, year, month, day);
            datepicker.getDatePicker();
            datepicker.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        switch (parent.getId()) {
            case R.id.addTransactionsType:
                transactionType = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /*
     * This method is saving a new transaction to the
     * Firebase Realtime Database
     * */
    private void addTransaction() {
        //getting the  values to save
        Float amount = (Float.parseFloat(addTransactionsAmount.getText().toString().trim()));
        String type = transactionType.toString().trim();
        String date = addTransactionsDate.getText().toString().trim();
        String time = addTransactionsTime.getText().toString().trim();
        String category = addTransactionsCategory.getText().toString().trim();
        String account = addTransactionsAccount.getText().toString().trim();
        String schedule = addTransactionsSchedule.getText().toString().trim();
        String notes = addTransactionsNotes.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(type)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseReference.push().getKey();

            //creating an Artist Object
            Transactions transaction = new Transactions(id, amount, type, date, time, category, account, schedule, notes);

            //Saving the Artist
            databaseReference.child(id).setValue(transaction);

            //setting edittext to blank again
            addTransactionsAmount.setText("");

            //displaying a success toast
            //Intent transactions = new Intent(getApplicationContext(), TransactionsActivity.class);
            //startActivity(transactions);
            Toast.makeText(this, "Transaction added", Toast.LENGTH_LONG).show();
        }

        else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_LONG).show();
        }
    }

}