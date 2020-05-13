package com.bekaim.highping.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bekaim.highping.R;
import com.bekaim.highping.activities.CheckRoomEligibility;
import com.bekaim.highping.activities.RoomActivity;
import com.bekaim.highping.activities.ViewContestActivity;
import com.bekaim.highping.models.JoinedUser;
import com.bekaim.highping.models.PlayModel;
import com.bekaim.highping.models.Spots;
import com.bekaim.highping.models.UserProfile;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class PlayFragmentListAdapter extends ArrayAdapter<PlayModel> {

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private ArrayList<PlayModel> dataSet;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();




    public PlayFragmentListAdapter(Context context, int resource, ArrayList<PlayModel> data) {
        super(context, resource, data);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        this.dataSet = data;

    }

    static class ViewHolder{
        ImageView imager_url;
        TextView match_count;
        TextView time_stamp;
        TextView win_prize;
        TextView match_type;
        TextView per_kill;
        TextView game_version;
        TextView entry_fee;
        TextView map;
        TextView spots_left;
        Button join;
        Button already_join;
        String card_id;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final PlayModel dataModel = getItem(position);
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.imager_url =  convertView.findViewById(R.id.wallpaper);
            holder.match_count =  convertView.findViewById(R.id.match_number);
            holder.time_stamp =  convertView.findViewById(R.id.match_time);
            holder.win_prize =  convertView.findViewById(R.id.val_win_prize);
            holder.match_type =  convertView.findViewById(R.id.val_match_type);
            holder.per_kill =  convertView.findViewById(R.id.val_per_kill);
            holder.game_version =  convertView.findViewById(R.id.val_version);
            holder.entry_fee =  convertView.findViewById(R.id.val_entry_fee);
            holder.map =  convertView.findViewById(R.id.val_map);
            holder.spots_left =  convertView.findViewById(R.id.val_spots_left);
            holder.join =  convertView.findViewById(R.id.btn_join);
            holder.already_join =  convertView.findViewById(R.id.btn_already_joinned);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Cover Image
        Glide.with(mContext)
                .load(dataModel.getImage_url())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.pubg_sample_wallpaper)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                )
                .into(holder.imager_url);

        holder.match_count.setText("Match #"+dataModel.getMatch_count());
        holder.time_stamp.setText(dataModel.getTime_stamp());
        holder.win_prize.setText(dataModel.getWin_prize());
        holder.match_type.setText(dataModel.getMatch_type());
        holder.per_kill.setText(dataModel.getAmt_per_kill());
        holder.game_version.setText(dataModel.getVersion());
        holder.entry_fee.setText(dataModel.getEntry_fee());
        holder.map.setText(dataModel.getMap());
        holder.spots_left.setText(dataModel.getJoined_user().size()+ "/100");
        holder.card_id = dataModel.getCard_id();

//        isAlreadyJoin(dataModel);
//        Toast.makeText(mContext, "" + dataModel.getCard_id(), Toast.LENGTH_SHORT).show();

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = FirebaseDatabase.getInstance().getReference("joined_user").orderByChild("user_id");
                final String currentUser = mUser.getUid();

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            JoinedUser user = snapshot.getValue(JoinedUser.class);

                            assert user != null;
                            String cardls = user.getCard_id().trim();
                            String cardId = dataModel.getCard_id().trim();

                            if(user.getUser_id().equals(currentUser)) {

                                if(cardls.equals(cardId)){
                                    Intent intent = new Intent(getContext(), ViewContestActivity.class);
                                    intent.putExtra(getContext().getString(R.string.db_field_card_id), dataModel.getCard_id());
                                    intent.putExtra(getContext().getString(R.string.db_field_entry_fee), dataModel.getEntry_fee());
                                    getContext().startActivity(intent);
                                }else{
                                    Intent intent = new Intent(getContext(), CheckRoomEligibility.class);
                                    intent.putExtra(getContext().getString(R.string.db_field_card_id), dataModel.getCard_id());
                                    intent.putExtra(getContext().getString(R.string.db_field_entry_fee), dataModel.getEntry_fee());
                                    getContext().startActivity(intent);
                                }
                            }else{
//                                    final DatabaseReference joinRef = FirebaseDatabase.getInstance().getReference("joined_user")
//                                            .child(dataModel.getCard_id())
//                                            .child(mUser.getUid());
//                                    joinRef.child("id").setValue(mUser.getUid());
//
                                Intent intent = new Intent(getContext(), CheckRoomEligibility.class);
                                intent.putExtra(getContext().getString(R.string.db_field_card_id), dataModel.getCard_id());
                                intent.putExtra(getContext().getString(R.string.db_field_entry_fee), dataModel.getEntry_fee());
                                getContext().startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
        return convertView;
    }

    //later I will add a list of cards in this model so that I traverse through all

    private void isJoin(PlayModel dataModel){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Toast.makeText(mContext, "on click button", Toast.LENGTH_SHORT).show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("new_card_room");

//        String userId = reference.child("user_id").getKey();
//        Toast.makeText(mContext, "" + userId, Toast.LENGTH_SHORT).show();
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlayModel playModel = (PlayModel) snapshot.getValue(PlayModel.class);
                    Toast.makeText(mContext, "user" + snapshot.getChildren(), Toast.LENGTH_SHORT).show();

//                    if(user.getUser_id().equals(mAuth.getCurrentUser().getUid())){
//                        Toast.makeText(mContext, "Already Join", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(mContext, "Able to Join", Toast.LENGTH_SHORT).show();
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
