package com.groupwork.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.groupwork.R;
import com.groupwork.customView.CircleImageView;
import com.groupwork.customView.ItemSelectedListener;

/**
 * Created by LL on 2017/3/12.
 */

public class UserActivity extends Activity implements ItemSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_layout_new);

        /*
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.user_profile_circleview);
        if (circleImageView != null) {
            circleImageView.setOnItemSelectedClickListener(this);
        }

        TextView search = (TextView)findViewById(R.id.user_profile_search);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    @Override
    public void onSelected(View view) {

    }

    @Override
    public void onUnselected(View view) {

    }
}
