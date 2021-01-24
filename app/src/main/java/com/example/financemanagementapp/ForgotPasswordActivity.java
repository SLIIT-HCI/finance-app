package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    //defining views
    private Button buttonForgotPwd;
    private EditText forgotPwdEmail;
    private TextView forgotPwdSignUpBtn;
    private TextView fgtPwdSignIn;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        forgotPwdSignUpBtn  = (TextView) findViewById(R.id.forgotPwdSignUpBtn);
        forgotPwdEmail  = (EditText) findViewById(R.id.forgotPwdEmail);
        buttonForgotPwd  = (Button) findViewById(R.id.buttonForgotPwd);
        fgtPwdSignIn  = (TextView) findViewById(R.id.fgtPwdSignIn);

        progressDialog = new ProgressDialog(this);

        forgotPwdSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(signUp);
            }
        });

        fgtPwdSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(signIn);
            }
        });

        buttonForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {

        String email = forgotPwdEmail.getText().toString().trim();

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "A message has been sent to your email with instructions to reset your password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to send email!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}