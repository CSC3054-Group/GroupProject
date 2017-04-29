package com.groupwork.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.groupwork.R;

public class HomeScreen extends AppCompatActivity {
    Button login , register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        login = (Button)findViewById(R.id.btn_HomeScreen_Login);
        register = (Button)findViewById(R.id.btn_homescreen_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(HomeScreen.this, Login.class);
                startActivity(login);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(HomeScreen.this, Register.class);
                startActivity(register);
            }
        });






    }
}
