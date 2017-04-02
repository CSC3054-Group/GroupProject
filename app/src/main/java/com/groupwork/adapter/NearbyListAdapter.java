package com.groupwork.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.groupwork.R;
import com.groupwork.asyncTask.DownloadAsyncTask;
import com.groupwork.bean.NearbyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/2.
 */

public class NearbyListAdapter extends BaseAdapter {
    private List<NearbyItem> list;
    private Context context;

    public NearbyListAdapter(List<NearbyItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_resinfo,null);
            viewHolder.res_photo= (ImageView) convertView.findViewById(R.id.list_photo);
            viewHolder.res_Name= (TextView) convertView.findViewById(R.id.listtext_resName);
            viewHolder.res_rating= (RatingBar) convertView.findViewById(R.id.list_rating);
            viewHolder.res_type= (TextView) convertView.findViewById(R.id.list_resType);
            viewHolder.res_duration= (TextView) convertView.findViewById(R.id.list_duration);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String cover_Img=list.get(position).getRes_img();
        String cover_name=list.get(position).getResName();
        int cover_rating=list.get(position).getRating();
        String cover_type=list.get(position).getResType();
        float cover_duration=list.get(position).getDuration();
        viewHolder.res_Name.setText(cover_name);
        viewHolder.res_rating.setRating(cover_rating);
        viewHolder.res_type.setText("Restaurant Type: "+cover_type);
        viewHolder.res_duration.setText("From the current location: "+cover_duration);
        new DownloadAsyncTask(context,new DownloadAsyncTask.OnResultListener() {
            @Override
            public void OnResult(byte[] result) {
                Bitmap bitmapFactory=BitmapFactory.decodeByteArray(result,0,result.length);
                viewHolder.res_photo.setImageBitmap(bitmapFactory);
            }
        }).execute(cover_Img);
        return convertView;
    }
    class ViewHolder{
        private ImageView res_photo;
        private TextView res_Name;
        private RatingBar res_rating;
        private TextView res_type;
        private TextView res_duration;
    }
    public void refresh(List<NearbyItem> list){
        this.list.clear();
        this.list=list;
    }
}
