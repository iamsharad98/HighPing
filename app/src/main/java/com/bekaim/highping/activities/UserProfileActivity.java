package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private Context mContext = UserProfileActivity.this;
    private RelativeLayout logout;
    private RelativeLayout rel_how_to_join;
    private RelativeLayout rel_about_us;
    private RelativeLayout rel_privacy;
    private TextView textName;
    private TextView textUsername;
    private TextView val_balance;
    private TextView val_match_played;
    private TextView val_total_kills;
    private TextView val_total_win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textName = findViewById(R.id.txt_name);
        textUsername = findViewById(R.id.textUsername);
        val_balance = findViewById(R.id.val_balance);
        val_match_played = findViewById(R.id.val_match_played);
        val_total_kills = findViewById(R.id.val_total_kills);
        val_total_win = findViewById(R.id.val_total_win);

        rel_how_to_join = findViewById(R.id.rel_how_to_join);
        rel_about_us = findViewById(R.id.rel_about_us);
        rel_privacy = findViewById(R.id.rel_privacy_policy);

        rel_how_to_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HowToJoin.class);
                startActivity(intent);            }
        });
        rel_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AboutUsActivity.class);
                startActivity(intent);            }
        });
        rel_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PrivacyPolicyActivity.class);
                startActivity(intent);            }
        });

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
                    finish();
                }catch(Exception e){
                    Toast.makeText(UserProfileActivity.this, "Failed to logout.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserProfile user = (UserProfile) snapshot.getValue(UserProfile.class);

                    textName = findViewById(R.id.txt_name);
                    textUsername = findViewById(R.id.textUsername);
                    val_balance = findViewById(R.id.val_balance);
                    val_match_played = findViewById(R.id.val_match_played);
                    val_total_kills = findViewById(R.id.val_total_kills);
                    val_total_win = findViewById(R.id.val_total_win);

                    if(user.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        val_balance.setText(user.getCurr_balance());
                        textName.setText(user.getGamename());
                        textUsername.setText(user.getUsername());
                        val_match_played.setText("2");
                        val_total_kills.setText(user.getTotal_kills());
                        val_total_win.setText("0");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
