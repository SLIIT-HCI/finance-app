package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTransactionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageView closeBtn, addTransactionsCalculator;
    EditText addTransactionsAmount, addTransactionsAccount, addTransactionsSchedule, addTransactionsNotes;
    TextView addTransactionsDate, addTransactionsTime, addTransactionsCategory;
    public static String type, transactionType;
    int hour, minute;
    FloatingActionButton addToDB;
    Long maxId;
    Spinner addTransactionsTypeSpinner;

    //database
    Float amount;
    String date, time, category, account, schedule, notes;

    public static String getValue() {
        return type;
    }

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

        /*******************************************************************************************************************/

        final Calendar myCalendar = Calendar.getInstance();

        closeBtn = (ImageView) findViewById(R.id.closeBtn);
        addTransactionsAmount = (EditText) findViewById(R.id.addTransactionsAmount);
        addTransactionsDate = (TextView) findViewById(R.id.addTransactionsDate);
        addTransactionsTime = (TextView) findViewById(R.id.addTransactionsTime);
        addTransactionsCategory = (TextView) findViewById(R.id.addTransactionsCategory);
        addTransactionsAccount = (EditText) findViewById(R.id.addTransactionsAccount);
        addTransactionsSchedule = (EditText) findViewById(R.id.addTransactionsSchedule);
        addTransactionsNotes = (EditText) findViewById(R.id.addTransactionsNotes);
        addTransactionsCalculator = (ImageView) findViewById(R.id.addTransactionsCalculator);
        addToDB = (FloatingActionButton) findViewById(R.id.addToDB);

        /*******************************************************************************************************************/

        addTransactionsCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent calculator = new Intent(getApplicationContext(), CalculatorActivity.class);
                startActivity(calculator);
                Intent intent = getIntent();
                addTransactionsAmount.setText(intent.getStringExtra("num"));
            }
        });

        /*******************************************************************************************************************/

        // Spinner element
        addTransactionsTypeSpinner = (Spinner) findViewById(R.id.addTransactionsType);
        // Spinner click listener
        addTransactionsTypeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
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
        addTransactionsTypeSpinner.setAdapter(dataAdapterType);

        /*******************************************************************************************************************/

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

        /*******************************************************************************************************************/

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

        /*******************************************************************************************************************/

        //intent.putExtra("getType", trType);
        Intent intent2 = getIntent();
        addTransactionsAmount.setText(intent2.getStringExtra("getAmount"));
        addTransactionsDate.setText(intent2.getStringExtra("getDate"));
        addTransactionsTime.setText(intent2.getStringExtra("getTime"));
        addTransactionsCategory.setText(intent2.getStringExtra("getCategory"));
        addTransactionsAccount.setText(intent2.getStringExtra("getAccount"));
        addTransactionsSchedule.setText(intent2.getStringExtra("getSchedule"));
        addTransactionsNotes.setText(intent2.getStringExtra("getNotes"));

        addTransactionsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCategories();
            }
        });

        /*******************************************************************************************************************/

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*******************************************************************************************************************/

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

    /*******************************************************************************************************************/

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

    /*******************************************************************************************************************/

    /*
     * This method is saving a new transaction to the
     * Firebase Realtime Database
     * */
    private void addTransaction() {
        //getting the  values to save
        amount = (Float.parseFloat(addTransactionsAmount.getText().toString().trim()));
        type = transactionType.toString().trim();
        date = addTransactionsDate.getText().toString().trim();
        time = addTransactionsTime.getText().toString().trim();
        category = addTransactionsCategory.getText().toString().trim();
        account = addTransactionsAccount.getText().toString().trim();
        schedule = addTransactionsSchedule.getText().toString().trim();
        notes = addTransactionsNotes.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(type)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our transaction
            String id = databaseReference.push().getKey();
            //String id = String.valueOf(maxId+1);

            //creating an transaction Object
            Transactions transaction = new Transactions(id, amount, type, date, time, category, account, schedule, notes);

            //Saving the transaction
            databaseReference.child(id).setValue(transaction);

            //displaying a success toast
            finish();
        }

        else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_LONG).show();
        }
    }

    private void displayCategories() {

        final String tType = transactionType;
        Intent categories2 = new Intent(getApplicationContext(), Categories_Popup.class);
        categories2.putExtra("getAmount", addTransactionsAmount.getText().toString());
        categories2.putExtra("getType", tType);
        categories2.putExtra("getDate", addTransactionsDate.getText().toString());
        categories2.putExtra("getTime", addTransactionsTime.getText().toString());
        categories2.putExtra("getCategory", addTransactionsCategory.getText().toString());
        categories2.putExtra("getAccount", addTransactionsAccount.getText().toString());
        categories2.putExtra("getSchedule", addTransactionsSchedule.getText().toString());
        categories2.putExtra("getNotes", addTransactionsNotes.getText().toString());
        startActivity(categories2);
        finish();

    }

}