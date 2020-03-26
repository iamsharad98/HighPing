package com.bekaim.highping.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.activities.LoginActivity;
import com.bekaim.highping.models.UserAccountSetting;
import com.bekaim.highping.models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference  mDatabase;
    private StorageReference mStorageReference;
    private String userID;
    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

// Sending Verification Email -------------//

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "Verification Email is sent to your Email. ", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void registerNewEmail(final String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                        Toast.makeText(mContext, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                        else if(task.isSuccessful()){
                        //send verificaton email
                        sendVerificationEmail();

                        userID = mAuth.getCurrentUser().getUid();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                        }
                    }
                });
    }


    public void addNewUser(String name, String username, String gameName, Long mobile, String email){
        UserProfile user = new UserProfile(name, username, gameName, userID, email,0, 0, 0 );

        mDatabase.child(mContext.getString(R.string.db_users_profile))
                .child(userID)
                .setValue(user);

        UserAccountSetting userAccountSetting = new UserAccountSetting(userID, gameName, mobile, email);
        mDatabase.child(mContext.getString(R.string.db_user_account_setting))
                .child(userID)
                .setValue(userAccountSetting);
    }
}
