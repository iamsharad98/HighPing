package com.bekaim.highping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private Context mContext = UserProfileActivity.this;
    private RelativeLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //logout not working correctly this to do list
        logout = findViewById(R.id.rel_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    UserProfileActivity.this.finish();
                }catch(Exception e){
                    Toast.makeText(UserProfileActivity.this, "Failed to logout.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void killActivity() {
        finish();
    }
}
