package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bekaim.highping.R;
import com.bekaim.highping.utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mUsername;
    private EditText mGameName;
    private EditText mMobile;
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignUp;
    private RelativeLayout mRelHaveAccount;
    private FrameLayout frame;

    //init vars
    private String name;
    private String username;
    private String gameName;
    private Long mobile;
    private String email;
    private String password;

    //Firebase
    private FirebaseAuth mAuth;
    public DatabaseReference mRef;
    private FirebaseMethods firebaseMethods;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;


    private Context mContext = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.et_name);
        mUsername = (EditText) findViewById(R.id.et_username);
        mGameName = (EditText) findViewById(R.id.et_gameName);
        mMobile = (EditText) findViewById(R.id.et_mobile);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        mSignUp = (Button) findViewById(R.id.bt_signup);
        mRelHaveAccount = (RelativeLayout) findViewById(R.id.rl_have_account);
        frame = (FrameLayout) findViewById(R.id.frame_layout_progress);


        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);

        setupFirebaseAuth();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        mRelHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        name = mName.getText().toString();
        username = mUsername.getText().toString();
        gameName = mGameName.getText().toString();
        mobile = Long.parseLong(mMobile.getText().toString());
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if (checkInputs(name, username, gameName, email, password)) {
            if (password.length() > 6) {
                frame.setVisibility(View.VISIBLE);
                firebaseMethods.registerNewEmail(email, password);
                firebaseMethods.addNewUser(name, username, gameName, mobile, email);
            } else {
                Toast.makeText(mContext, "Password is too short.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkInputs(String name,String username, String gameName, String email, String password){
        if (name.equals("") || username.equals("") || gameName.equals("") || email.equals("") || password.equals("") ){
            Toast.makeText(mContext, "You should fill the required field.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private void setupFirebaseAuth(){

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("message");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    final FirebaseUser user = mAuth.getCurrentUser();

                    //Check if the current user is logged in
                    if (user!= null){

                        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // firstly checkOut if username already exist
//                                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
//                                    if (singleSnapshot.exists()){
//                                        Toast.makeText(mContext, "Username Already Exist. \n Try With Another One.", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //Toast.makeText(mContext, "datachange cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });

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
