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

import com.bekaim.highping.R;
import com.bekaim.highping.activities.ViewResultActivity;
import com.bekaim.highping.models.ResultCard;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class ResultFragmentAdapter extends ArrayAdapter<ResultCard> {

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private ArrayList<ResultCard> dataSet;


    public ResultFragmentAdapter(Context context, int resource, ArrayList<ResultCard> data) {
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
        Button watch_result;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ResultCard dataModel = getItem(position);
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.imager_url =  convertView.findViewById(R.id.wallpaper);
            holder.match_count =  convertView.findViewById(R.id.match_number);
            holder.time_stamp =  convertView.findViewById(R.id.match_time);
            holder.watch_result = convertView.findViewById(R.id.watch_result);

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

        holder.watch_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewResultActivity.class);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }



}
