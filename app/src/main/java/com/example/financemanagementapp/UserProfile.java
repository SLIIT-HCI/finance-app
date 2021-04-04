package com.example.financemanagementapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    private final int REQUEST_CODE=10;
    ImageView selectFromGallery;
    TextView changePassword, changeMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        selectFromGallery = (ImageView) findViewById(R.id.selectFromGallery);
        changePassword = (TextView) findViewById(R.id.changePassword);
        changeMobile = (TextView)findViewById(R.id.changeMobile);

        selectFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectImage,REQUEST_CODE);
                //selectImage.setText("Select Again");
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChangePassword = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(ChangePassword);
            }
        });

        changeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterMobileNumber();
            }
        });


    }


    private void enterMobileNumber(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Enter your Mobile Number");
        //alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}