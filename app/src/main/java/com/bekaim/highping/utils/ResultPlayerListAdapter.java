package com.bekaim.highping.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bekaim.highping.R;
import com.bekaim.highping.models.ResultPlayerList;

import java.util.ArrayList;

public class ResultPlayerListAdapter extends ArrayAdapter<ResultPlayerList> {

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private ArrayList<ResultPlayerList> dataSet;


    public ResultPlayerListAdapter(Context context, int resource, ArrayList<ResultPlayerList> data) {
        super(context, resource, data);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        this.dataSet = data;

    }

    static class ViewHolder{
        TextView val_hashtag;
        TextView val_player_name;
        TextView val_kills;
        TextView val_winning;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ResultPlayerList dataModel = getItem(position);
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.val_hashtag =  convertView.findViewById(R.id.val_hashtag);
            holder.val_player_name =  convertView.findViewById(R.id.val_player_name);
            holder.val_kills =  convertView.findViewById(R.id.val_kills);
            holder.val_winning = convertView.findViewById(R.id.val_winning);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.val_hashtag.setText(dataModel.getWinning() + "Rs");
        holder.val_hashtag.setText(dataModel.getPlayer_num());
        holder.val_player_name.setText(dataModel.getPlayer_name());
        holder.val_kills.setText(dataModel.getKills());

        return convertView;
    }



}
