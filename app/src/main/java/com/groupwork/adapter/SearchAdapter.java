package com.groupwork.adapter;

import android.content.Context;


import com.groupwork.R;
import com.groupwork.bean.Bean;
import com.groupwork.bean.SearchItem;
import com.groupwork.utils.ViewHolder;

import java.util.List;

/**
 * Created by yetwish on 2015-05-11
 */

public class SearchAdapter extends CommonAdapter<SearchItem> {

    public SearchAdapter(Context context, List<SearchItem> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        /*
        holder.setImageResource(R.id.item_search_iv_icon,mData.get(position).getIconId())
                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
                */
        holder
                .setText(R.id.item_search_tv_title,mData.get(position).getResName())
                .setText(R.id.item_search_tv_content,mData.get(position).getResLocation());

    }
}
