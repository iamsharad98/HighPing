package com.bekaim.highping.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.models.JoinedUser;
import com.bekaim.highping.models.ResultCard;
import com.bekaim.highping.models.ResultPlayerList;
import com.bekaim.highping.utils.ResultFragmentAdapter;
import com.bekaim.highping.utils.ResultPlayerListAdapter;
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

public class ViewResultActivity extends AppCompatActivity {

    private TextView matchCount;
    private TextView matchTime;
    private TextView winPrize;
    private TextView entryFee;
    private TextView perKill;
    private ListView mListView;
    private ResultPlayerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        matchCount = findViewById(R.id.val_match_count);
        matchTime = findViewById(R.id.val_match_time);
        winPrize = findViewById(R.id.val_win_prize);
        entryFee = findViewById(R.id.val_entry_fee);
        perKill = findViewById(R.id.val_per_kill);
        mListView = findViewById(R.id.listView);

        setupGridView();

    }


    private void setupGridView() {

        final ArrayList<ResultPlayerList> resultModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("result");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    ResultPlayerList resModel = new ResultPlayerList();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    try {
                        matchCount.setText(objectMap.get("match_count").toString());
                        matchTime.setText(objectMap.get("match_time").toString());
                        winPrize.setText(objectMap.get("win_prize").toString());
                        perKill.setText(objectMap.get("per_kill").toString());
                        entryFee.setText(objectMap.get("entry_fee").toString());
                        resModel.setPlayer_num(objectMap.get("player_num").toString());
                        resModel.setPlayer_name(objectMap.get("player_name").toString());
                        resModel.setKills(objectMap.get("player_kills").toString());
                        resModel.setWinning(objectMap.get("player_winning").toString());

                        resultModels.add(resModel);
                    }catch (NullPointerException e ){
                        Toast.makeText(ViewResultActivity.this, "No Room Card Result is Available.", Toast.LENGTH_SHORT).show();
                    }

                    Collections.sort(resultModels, new Comparator<ResultPlayerList>() {
                        public int compare(ResultPlayerList o1, ResultPlayerList o2) {
                            return o2.getPer_kill().compareTo(o1.getPer_kill());
                        }
                    });

                    adapter = new ResultPlayerListAdapter(ViewResultActivity.this,
                            R.layout.snippet_card_result,
                            resultModels);

                    mListView.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "onCancelled: query cancelled.");
            }


        });

    }
}
