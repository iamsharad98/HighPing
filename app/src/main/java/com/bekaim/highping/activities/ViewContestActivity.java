package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.MainActivity;
import com.bekaim.highping.R;
import com.bekaim.highping.models.PlayModel;
import com.bekaim.highping.models.Spots;
import com.bekaim.highping.utils.PlayFragmentListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewContestActivity extends AppCompatActivity {

    private TextView roomId;
    private TextView roomPass;
    private TextView matchType;
    private TextView version;
    private TextView map;
    private TextView entryFee;
    private TextView perKill;
    private TextView winPrize;
    private TextView matchTime;
    private ImageView back;

    private String intentEntryFee;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contest);

        roomId = findViewById(R.id.val_room_id);
        roomPass = findViewById(R.id.val_room_password);
        matchType = findViewById(R.id.val_match_type);
        version = findViewById(R.id.val_version);
        map = findViewById(R.id.val_map);
        entryFee = findViewById(R.id.val_entry_fee);
        perKill = findViewById(R.id.val_per_kill);
        winPrize = findViewById(R.id.val_win_prize);
        matchTime = findViewById(R.id.val_schedule);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewContestActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        intentEntryFee = intent.getStringExtra(getApplicationContext().getString(R.string.db_field_entry_fee));
        cardId = intent.getStringExtra(getApplicationContext().getString(R.string.db_field_card_id));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("new_room_card");
        Query query = reference
                .orderByChild("card_id");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    PlayModel playModel = new PlayModel();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    playModel.setRoom_id(objectMap.get(getString(R.string.db_field_room_id)).toString());
                    playModel.setRoom_password(objectMap.get(getString(R.string.db_field_room_password)).toString());
                    playModel.setAmt_per_kill(objectMap.get(getString(R.string.db_field_amt_per_kill)).toString());
                    playModel.setEntry_fee(objectMap.get(getString(R.string.db_field_entry_fee)).toString());
                    playModel.setImage_url(objectMap.get(getString(R.string.db_field_image_url)).toString());
                    playModel.setMap(objectMap.get(getString(R.string.db_field_map)).toString());
                    playModel.setMatch_count(objectMap.get(getString(R.string.db_field_match_count)).toString());
                    playModel.setMatch_type(objectMap.get(getString(R.string.db_field_match_type)).toString());
                    playModel.setWin_prize(objectMap.get(getString(R.string.db_field_win_prize)).toString());
                    playModel.setTime_stamp(objectMap.get(getString(R.string.db_field_time_stamp)).toString());
                    playModel.setVersion(objectMap.get(getString(R.string.db_field_version)).toString());
                    playModel.setCard_id(objectMap.get(getString(R.string.db_field_card_id)).toString());

                    if(playModel.getCard_id().equals(cardId)){
                        roomId.setText(playModel.getRoom_id());
                        roomPass.setText(playModel.getRoom_password());
                        perKill.setText(playModel.getAmt_per_kill());
                        entryFee.setText(playModel.getEntry_fee());
                        map.setText(playModel.getMap());
                        matchType.setText(playModel.getMatch_type());
                        winPrize.setText(playModel.getWin_prize());
                        matchTime.setText(playModel.getTime_stamp());
                        version.setText(playModel.getVersion());
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });




    }
}

//
////                    Toast.makeText(ViewContestActivity.this, "" + singleSnapshot, Toast.LENGTH_SHORT).show();
////                    PlayModel playModel = singleSnapshot.getValue(PlayModel.class);
//        if(singleSnapshot.getKey().equals(cardId)){
////                        PlayModel playModel = (PlayModel) singleSnapshot.getValue(PlayModel.class);
//
//        Toast.makeText(ViewContestActivity.this, "" + singleSnapshot.getValue(), Toast.LENGTH_SHORT).show();
////                            roomId.setText(playModel.getRoom_id());
////                            roomPass.setText(objectMap.get(getString(R.string.db_field_room_password)).toString());
////                            perKill.setText(objectMap.get(getString(R.string.db_field_amt_per_kill)).toString());
////                            entryFee.setText(objectMap.get(getString(R.string.db_field_entry_fee)).toString());
////                            map.setText(objectMap.get(getString(R.string.db_field_map)).toString());
////                            matchType.setText(objectMap.get(getString(R.string.db_field_match_type)).toString());
////                            winPrize.setText(objectMap.get(getString(R.string.db_field_win_prize)).toString());
////                            matchTime.setText(objectMap.get(getString(R.string.db_field_time_stamp)).toString());
////                            version.setText(objectMap.get(getString(R.string.db_field_version)).toString());
//        }
//
//        try {
//                        Toast.makeText(ViewContestActivity.this, playModel.getCard_id(), Toast.LENGTH_LONG).show();
//                        if(objectMap.get(getString(R.string.db_field_room_id)).toString().equals(cardId)){
//
//                        }