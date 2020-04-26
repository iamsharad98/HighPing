package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.MainActivity;
import com.bekaim.highping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mSignUp;
    private TextView mForgotPassword;
    private FrameLayout frameProgress;

    private String email;
    private String password;
    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogin = (Button) findViewById(R.id.loginButton);
        mSignUp = (Button) findViewById(R.id.signUpButton);
        mForgotPassword = (TextView) findViewById(R.id.tv_forgotPassword);
        frameProgress = (FrameLayout) findViewById(R.id.frame_layout_progress);

        mAuth = FirebaseAuth.getInstance();
        setupFirebaseAuth();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameProgress.setVisibility(View.VISIBLE);

                if(validInfo()){
                    loginUser();
                }else{
                    frameProgress.setVisibility(View.GONE);
                    Toast.makeText(mContext, "Your Input is not Valid.", Toast.LENGTH_SHORT).show();
                    Log.d("LoginAttemptFailed", "validInfo return false");
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
    private boolean validInfo(){
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(mContext, "You should fill the required field.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.length() <6){
            Toast.makeText(mContext, "You should fill the required field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void loginUser(){
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user.isEmailVerified()){
                            if(task.isSuccessful()){
                                Log.d("taskSuccess", "signInWithEmail:success");
                                frameProgress.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, "Authentication successfull.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Authentication Failed. \n Please try again.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(mContext, "Email is not verified. \n Please check Inbox.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Authentication Failed. \n Please Check Your Internet.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();
                //Check if the current user is logged in

                if (user!= null){
                    Log.d("komu", "onAuthStateSignedIn : User is logged In" + user.getUid());
                }else {
                    Log.d("komu", "onAuthStateSignedIn : User is logged out");

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);

    }


    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}