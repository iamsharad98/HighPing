package com.bekaim.highping.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bekaim.highping.R;
import com.bekaim.highping.models.PlayModel;
import com.bekaim.highping.models.Spots;
import com.bekaim.highping.utils.PlayFragmentListAdapter;
import com.bekaim.highping.utils.SetupViewPagerAdapter;
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

public class PlayFragment extends Fragment {
    private static final String TAG = "PlayFragment";

    private ListView mListView;
    private PlayFragmentListAdapter adapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_play, container, false);

        mListView = view.findViewById(R.id.play_listview);
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh);

        setupGridView();
        setupRefreshLayout();


        return view;
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void setupGridView() {
        Log.d(TAG, "setupGridView: Setting up video grid.");

        final ArrayList<PlayModel> playModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.db_new_room_card));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    //Video video = new Video();
                    PlayModel playModel = new PlayModel();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    try {
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

                        List<Spots> spots = new ArrayList<Spots>();
//
                        for (DataSnapshot dSnapshot : singleSnapshot
                                .child(getActivity().getString(R.string.db_field_spots_left)).getChildren()){
                            Spots spot = new Spots();
                            spot.setUser_id(dSnapshot.getValue(Spots.class).getUser_id());
                            spots.add(spot);
                        }
                        playModel.setJoined_user(spots);

                        playModels.add(playModel);
                    }catch (NullPointerException e ){
                        Log.d(TAG, "onDataChange: NullPointerException " + e.getMessage());
                    }

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                            Toast.makeText(getContext(), "photo is "+ position, Toast.LENGTH_SHORT).show();
//                            mOnGridImageSelectedListener.onGridImageSelected(videos.get(position), ACTIVITY_NUM);

                        }
                    });
//
                    Collections.sort(playModels, new Comparator<PlayModel>() {
                        public int compare(PlayModel o1, PlayModel o2) {
                            return o2.getMatch_count().compareTo(o1.getMatch_count());
                        }
                    });

                    ArrayList<PlayModel> model = new ArrayList<PlayModel>();
                    for(int i = 0; i < playModels.size(); i++){
                        model.add(playModels.get(i));
                    }
                    adapter = new PlayFragmentListAdapter(getActivity(),
                            R.layout.snippet_card_room,
                            model);
//

                    mListView.setAdapter(adapter);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }


        });

    }


}