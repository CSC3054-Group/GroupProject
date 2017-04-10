package com.groupwork.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.groupwork.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        String a,b,c,d;
        a = (getIntent().getExtras().getString("UserId"));
        b = (getIntent().getExtras().getString("Forename"));
        c = (getIntent().getExtras().getString("Surname"));
        d = (getIntent().getExtras().getString("Email"));



        Log.d("Values From Intent" , a +b+c+d);
    }
}
