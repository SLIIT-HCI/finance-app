package com.example.financemanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {

    private final int REQUEST_CODE=10;
    ImageView selectFromGallery;
    TextView change_password, mobile1, mobile2, home;
    TextView bankDetails;

    //private FirebaseAuth firebaseAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //getting firebase auth object
        //firebaseAuth1 = FirebaseAuth.getInstance();

        selectFromGallery = (ImageView) findViewById(R.id.selectFromGallery);
        change_password = (TextView)findViewById(R.id.changePassword);
        mobile1 = (TextView) findViewById(R.id.mobile1);
        mobile2 = (TextView) findViewById(R.id.mobile2);
        home = (TextView) findViewById(R.id.mobileHome);
        bankDetails = (TextView) findViewById(R.id.TVBankDetail);

        selectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectImage,REQUEST_CODE);
                //selectImage.setText("Select Again");
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChangePassword = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(ChangePassword);
            }
        });

        mobile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enterMobileNumber();
            }
        });

        mobile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterMobileNumber();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            enterMobileNumber();
        }
    });

        bankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bank = new Intent(getApplicationContext(), ExpandableListView_bankDetails.class);
                startActivity(bank);
            }
        });

    }



    private void enterMobileNumber(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Phone Number");
        //alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Delete phone number
            }
        });

        alert.show();
    }

//    private void enterEmail(){
//        AlertDialog.Builder emailAlert = new AlertDialog.Builder(this);
//        emailAlert.setTitle("Change email address");
//
//        final EditText enterEmail = new EditText(this);
//        emailAlert.setView(enterEmail);
//
//        emailAlert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(getApplicationContext(), EmailVerificationActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        emailAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //cancel
//            }
//        });
//    }
}