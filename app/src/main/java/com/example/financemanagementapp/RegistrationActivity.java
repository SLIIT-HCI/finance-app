package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText regConfirmPwd;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private CheckBox checkBoxTerms;
    private TextView regSignInBtn;


    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase Auth object
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        regConfirmPwd = (EditText) findViewById(R.id.regConfirmPwd);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        checkBoxTerms = (CheckBox) findViewById(R.id.checkBoxTerms);
        progressDialog = new ProgressDialog(this);
        regSignInBtn  = (TextView) findViewById(R.id.regSignInBtn);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        regSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(signIn);
            }
        });


    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        String confirmPassword  = regConfirmPwd.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this,"Please confirm password",Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6) {
            Toast.makeText(this, "Password must be atleast 6 characters long", Toast.LENGTH_SHORT).show();
        }
        else if (confirmPassword.length() < 6) {
            Toast.makeText(this, "Password must be atleast 6 characters long", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_SHORT).show();
        }
        else if (!((CheckBox) checkBoxTerms).isChecked()) {
            Toast.makeText(this, "Agree to terms and conditions", Toast.LENGTH_LONG).show();
        }
        else {
            //if the email and password are not empty
            //displaying a progress dialog
            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {

                                // send email verification link
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            alertDialog();
                                        }
                                        else {
                                            Toast.makeText(RegistrationActivity.this, "Verification email has not been sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }


    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please verify yor email before signing in");
        dialog.setTitle("Account Created Successfully!");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        Intent signIn = new Intent(getApplicationContext(), EmailVerificationActivity.class);
                        startActivity(signIn);
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

}