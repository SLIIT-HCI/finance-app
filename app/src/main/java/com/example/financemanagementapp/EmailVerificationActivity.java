package com.example.financemanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    //defining views
    TextView verificationMsg;
    TextView resendBtn;
    String userID;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        verificationMsg = (TextView) findViewById(R.id.verificationMsg);
        resendBtn = (TextView) findViewById(R.id.resendBtn);

        userID = firebaseAuth.getCurrentUser().getUid();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        if(!user.isEmailVerified()) {
            verificationMsg.setVisibility(View.VISIBLE);
            resendBtn.setVisibility(View.VISIBLE);

            resendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send email verification link
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EmailVerificationActivity.this, "Verification email has been sent", Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EmailVerificationActivity.this, "Verification not sucessful", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

    }
}