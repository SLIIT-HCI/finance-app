package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //defining views
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView loginSignUpBtn, loginForgotPwdBtn;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPwd);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        loginSignUpBtn  = (TextView) findViewById(R.id.loginSignUpBtn);
        loginForgotPwdBtn  = (TextView) findViewById(R.id.loginForgotPwdBtn);

        progressDialog = new ProgressDialog(this);

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            //finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
        }

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });


        loginSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(signUp);
            }
        });

        loginForgotPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(signUp);
            }
        });

    }

    //method for user login
    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }
         else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6) {
            Toast.makeText(this, "Password must be atleast 6 characters long", Toast.LENGTH_SHORT).show();
        }

        //if the email and password are not empty
        //displaying a progress dialog

        else {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

            //logging in the user
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if (task.isSuccessful()) {
                                //start the profile activity
                                finish();
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainDashboard.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    }