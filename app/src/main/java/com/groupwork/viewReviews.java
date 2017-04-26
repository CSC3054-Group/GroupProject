package com.groupwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupwork.urlContrans.UrlConfig;

public class viewReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        String resId = UrlConfig.restid;
    }
}
