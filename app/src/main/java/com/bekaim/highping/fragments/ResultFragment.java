package com.bekaim.highping.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.activities.LoginActivity;
import com.bekaim.highping.models.JoinedUser;
import com.bekaim.highping.models.PlayModel;
import com.bekaim.highping.models.ResultCard;
import com.bekaim.highping.models.Spots;
import com.bekaim.highping.utils.PlayFragmentListAdapter;
import com.bekaim.highping.utils.ResultFragmentAdapter;
import com.google.android.gms.common.internal.ResourceUtils;
import com.google.firebase.auth.FirebaseAuth;
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

public class ResultFragment extends Fragment {


    private FirebaseAuth mAuth;
    private ListView mListView;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ResultFragmentAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result, container, false);
        mAuth = FirebaseAuth.getInstance();

        mListView = view.findViewById(R.id.result_listview);
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

        final ArrayList<ResultCard> resultModels = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("result_card");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    //Video video = new Video();
                    ResultCard resModel = new ResultCard();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();

                    try {
                        resModel.setImage_url(objectMap.get("image_url").toString());
                        resModel.setMatch_count(objectMap.get("match_count").toString());
                        resModel.setTime_stamp(objectMap.get("time_stamp").toString());

                        List<JoinedUser> joinedUsers = new ArrayList<JoinedUser>();
//
                        for (DataSnapshot dSnapshot : singleSnapshot
                                .child("joined_user").getChildren()){
                            JoinedUser user = new JoinedUser();
                            user.setUser_id(dSnapshot.getValue(JoinedUser.class).getUser_id());
                            joinedUsers.add(user);
                        }
                        resModel.setJoined_user(joinedUsers);

                        resultModels.add(resModel);
                    }catch (NullPointerException e ){
                        Toast.makeText(getActivity(), "No Room Card Result is Available.", Toast.LENGTH_SHORT).show();
                    }
//
                    Collections.sort(resultModels, new Comparator<ResultCard>() {
                        public int compare(ResultCard o1, ResultCard o2) {
                            return o2.getMatch_count().compareTo(o1.getMatch_count());
                        }
                    });


                    adapter = new ResultFragmentAdapter(getActivity(),
                            R.layout.snippet_result_room,
                            resultModels);
//

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





//    private Button logout;
//
//    logout = view.findViewById(R.id.logout);
//
//
//    logout.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//            mAuth.signOut();
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            getActivity().finish();
//            }
//            });
