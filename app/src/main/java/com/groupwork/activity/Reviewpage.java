package com.groupwork.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.groupwork.R;
import com.groupwork.urlContrans.UrlConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Reviewpage extends AppCompatActivity {
    RatingBar ratebar;
    EditText review;
    Button submitreview;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        review = (EditText) findViewById(R.id.txtaddreview);
        ratebar = (RatingBar) findViewById(R.id.ratingPrice);
        submitreview = (Button) findViewById(R.id.buttonsub);

        setContentView(R.layout.activity_reviewpage);
    }


}