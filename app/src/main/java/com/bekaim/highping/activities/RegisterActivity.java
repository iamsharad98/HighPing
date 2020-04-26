package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bekaim.highping.MainActivity;
import com.bekaim.highping.R;
import com.bekaim.highping.utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

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
    private DatabaseReference reference;

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
        mAuth = FirebaseAuth.getInstance();

        //setupFirebaseAuth();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.setVisibility(View.VISIBLE);

                String txt_name = mName.getText().toString();
                String txt_username = mUsername.getText().toString();
                String txt_gamename = mGameName.getText().toString();
                String txt_mobile = mMobile.getText().toString();
                String txt_email = mEmail.getText().toString();
                String txt_password = mPassword.getText().toString();

                if ( TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_gamename) ||
                        TextUtils.isEmpty(txt_mobile) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){

                    frame.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6){
                    frame.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if (txt_mobile.length()>10 ){
                    frame.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Phone No. should be correct.", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_name, txt_username, txt_gamename, txt_mobile, txt_email, txt_password);
                }
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

    private void register(final String name, final String username, final String gamename, final String mobile, final String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("user_id", userid);
                            hashMap.put("username", username.toLowerCase());
                            hashMap.put("name", name);
                            hashMap.put("gamename", gamename);
                            hashMap.put("email_id", email);
                            hashMap.put("total_kills", "0");
                            hashMap.put("amount_won", "0");
                            hashMap.put("curr_balance", "0");
                            hashMap.put("mobile", mobile);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firebaseMethods.sendVerificationEmail();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Sign Up failed. Please, try after some time.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

