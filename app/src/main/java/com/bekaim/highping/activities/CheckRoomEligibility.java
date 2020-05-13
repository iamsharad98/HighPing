package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.models.PlayModel;
import com.bekaim.highping.models.UserProfile;
import com.bekaim.highping.utils.FirebaseMethods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CheckRoomEligibility extends AppCompatActivity {

    private TextView curr_balance;
    private TextView entry_fee;
    private TextView btn_next;
    private TextView btn_home;
    private TextView eligible;
    private TextView not_eligible;

    private String entryFee;
    private String cardId;
    private int test;
    private int balance;
    private Context mContext;
    //Firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    //private DatabaseReference mReference;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_room_eligibility);

        curr_balance = findViewById(R.id.val_balance);
        entry_fee = findViewById(R.id.val_entry_fee);
        btn_next = findViewById(R.id.btn_next);
        btn_home = findViewById(R.id.btn_home);
        eligible = findViewById(R.id.txt_eligible);
        not_eligible = findViewById(R.id.txt_not_eligible);
        mContext = CheckRoomEligibility.this;
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            userId = mAuth.getCurrentUser().getUid();
        }

        Toast.makeText(mContext, "UID " + FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        //Get Intent Extra
        Intent intent = getIntent();
        entryFee = intent.getStringExtra(mContext.getString(R.string.db_field_entry_fee));
        cardId = intent.getStringExtra(mContext.getString(R.string.db_field_card_id));
        //setProfileWidgets();
        //isAlreadyJoin();

        entry_fee.setText(entryFee);
    }

    private void isAlreadyJoin(){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("new_card_room").child(cardId).child("joined_user");

        String userId = reference.child("user_id").getKey();
        Toast.makeText(mContext, "" + userId, Toast.LENGTH_SHORT).show();

    }


    private void setProfileWidgets() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserProfile user = (UserProfile) snapshot.getValue(UserProfile.class);
                    if(user.getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        final long balance1 = user.getCurr_balance();
                        curr_balance.setText(String.valueOf(balance1));

                        if(entryFee.equals("Free") || balance1>=Integer.parseInt(entryFee)){
                            eligible.setVisibility(View.VISIBLE);
                            not_eligible.setVisibility(View.INVISIBLE);
                            btn_next.setVisibility(View.VISIBLE);
                            btn_home.setVisibility(View.INVISIBLE);
                        }else if(balance1<Integer.parseInt(entryFee)){
                            eligible.setVisibility(View.INVISIBLE);
                            not_eligible.setVisibility(View.VISIBLE);
                            btn_next.setVisibility(View.INVISIBLE);
                            btn_home.setVisibility(View.VISIBLE);
                        }
                        final long bal = balance1;
                        btn_next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(entryFee.equals("Free") ) {
                                    Toast.makeText(mContext, "Go to card activity", Toast.LENGTH_SHORT).show();
                                    addUserToRoom();
//                                    Toast.makeText(mContext, "" + cardId, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CheckRoomEligibility.this, RoomActivity.class);
                                    startActivity(intent);
                                }else if(bal>=Integer.parseInt(entryFee)){
                                    updateBalance(balance1);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateBalance(long balance1){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(mAuth.getCurrentUser().getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("curr_balance", balance1-Integer.parseInt(entryFee));
        reference.updateChildren(hashMap);
        addUserToRoom();
//        Toast.makeText(mContext, "" + cardId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(CheckRoomEligibility.this, RoomActivity.class);
        startActivity(intent);
    }
    private void addUserToRoom(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("new_room_card")
                .child(cardId).child("joined_user");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user_id", mAuth.getCurrentUser().getUid());
        reference.updateChildren(hashMap);
    }
}
