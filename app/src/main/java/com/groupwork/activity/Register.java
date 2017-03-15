package com.groupwork.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.groupwork.R;

public class Register extends AppCompatActivity {
    TextView existingaccount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        existingaccount = (TextView)findViewById(R.id.tv_existingaccount);

        existingaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Register.this, Login.class);
                startActivity(register);
            }
        });
    }
}
