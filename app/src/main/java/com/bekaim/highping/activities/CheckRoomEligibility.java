package com.bekaim.highping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private DatabaseReference mReference;

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
        mReference = FirebaseDatabase.getInstance().getReference();

        //Get Intent Extra
        Intent intent = getIntent();
        if (intent.hasExtra(mContext.getString(R.string.db_field_entry_fee))) {
            entryFee = intent.getStringExtra(mContext.getString(R.string.db_field_entry_fee));
        }
        if (intent.hasExtra(mContext.getString(R.string.db_field_card_id))) {
            cardId = intent.getStringExtra(mContext.getString(R.string.db_field_card_id));
        }

        setText();

        //curr_balance.setText(balance);
        entry_fee.setText(entryFee);


        if(entryFee.equals("Free")){
            eligible.setVisibility(View.VISIBLE);
            not_eligible.setVisibility(View.INVISIBLE);
            btn_next.setVisibility(View.VISIBLE);
            btn_home.setVisibility(View.INVISIBLE);
        }else{
            if(balance>=Integer.parseInt(entryFee)){
                eligible.setVisibility(View.VISIBLE);
                not_eligible.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_home.setVisibility(View.INVISIBLE);


            }else{
                eligible.setVisibility(View.INVISIBLE);
                not_eligible.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.INVISIBLE);
                btn_home.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setText(){

        Toast.makeText(mContext, "SetText Function", Toast.LENGTH_SHORT).show();

        //get the user object
        Query userQuery = mReference
                .child(getString(R.string.db_users_profile))
                .orderByChild(getString(R.string.field_user_id))
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    curr_balance.setText(objectMap.get(R.string.db_field_curr_balance).toString());
                    //test = Integer.parseInt(objectMap.get(R.string.db_field_curr_balance).toString());
                    Toast.makeText(mContext, "balance " + objectMap.get(R.string.db_field_curr_balance).toString(),
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Data Change Cancel", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
