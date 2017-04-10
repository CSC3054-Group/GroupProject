package com.groupwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.groupwork.R;

/**
 * Created by admin on 2017/4/6.
 */

public class IntentTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neaby_listintent);
        Intent intent = getIntent();
        String resName=intent.getStringExtra("resName");
        TextView textView= (TextView) findViewById(R.id.just_text);
        textView.setText("Hello, "+resName);
    }
}
