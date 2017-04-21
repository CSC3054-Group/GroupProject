package com.groupwork;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.groupwork.utils.MysqlHelper;

public class RestaurantDetails extends AppCompatActivity {
    TextView restname;
    TextView restAddress;
    RatingBar rOverall;
    TextView opTimes;
    TextView hyg;
    TextView number;
    RatingBar price;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        //set variables so they can be assigned via query
        restname = (TextView) findViewById(R.id.txtRestaurantName);
        restAddress = (TextView) findViewById(R.id.txtrestaurantaddress);
        rOverall = (RatingBar) findViewById(R.id.ratingOverall);
        opTimes = (TextView) findViewById(R.id.txtmondaytime);
        number = (TextView) findViewById(R.id.textView16);
        price = (RatingBar) findViewById(R.id.ratingPrice);

        Intent intent  = getIntent();
        int redId = intent.getIntExtra("resId",0);
        Log.d("ResID",String.valueOf(redId));
        //Get id from previous activity
       // Bundle bundle = getIntent().getExtras();
        //String message = bundle.getString("resId");
        //Log.d("intent value " , message);



    }
}
