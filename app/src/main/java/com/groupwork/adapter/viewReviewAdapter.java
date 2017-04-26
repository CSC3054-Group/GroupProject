package com.groupwork.adapter;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.groupwork.R;
import com.groupwork.bean.NearbyItem;
import com.groupwork.bean.ViewReview;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */

public class viewReviewAdapter extends BaseAdapter {
    private List<ViewReview> review_list;
    private Context context;

    public viewReviewAdapter(List<ViewReview> review_list, Context context) {
        this.review_list = review_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return review_list.size();
    }

    @Override
    public Object getItem(int position) {
        return review_list.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_viewreview_list,null);
            viewHolder.review_name= (TextView) convertView.findViewById(R.id.view_name);
            viewHolder.review_rating = (RatingBar) convertView.findViewById(R.id.view_rating);
            viewHolder.review_Text= (TextView) convertView.findViewById(R.id.view_revtext);

            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }


        String cover_Forename=review_list.get(position).getForename();
        String cover_Surname = review_list.get(position).getSurname();
        float cover_raing  = review_list.get(position).getRevStarRating();
        String cover_reviewtext = review_list.get(position).getRevText();

        Log.d("Rating",cover_raing+"");
        viewHolder.review_name.setText(cover_Forename+" "+cover_Surname);
        viewHolder.review_rating.setRating(cover_raing);
        viewHolder.review_Text.setText("Review:"+cover_reviewtext);


        return convertView;

    }

    private class ViewHolder{
        private TextView review_name;
        private RatingBar review_rating;
        private TextView review_Text;

    }
    public void refresh(List<ViewReview> list){
        this.review_list.clear();
        this.review_list=list;
    }
}
