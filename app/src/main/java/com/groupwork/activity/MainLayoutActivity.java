package com.groupwork.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.groupwork.R;
import com.groupwork.fragment.NeabyFragment;

/**
 * Created by admin on 2017/4/2.
 */

public class MainLayoutActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        InitialBotTab();
        InitialSwitch();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.container,new NeabyFragment());
        transaction.commit();
    }

    private void InitialBotTab(){
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioButtons = new RadioButton[radioGroup.getChildCount()];
        for(int i=0;i<radioGroup.getChildCount();i++){
            radioButtons[i] = (RadioButton) radioGroup.getChildAt(i);
        }
    }
    private void InitialSwitch(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rButton_Nearby:
                        break;
                    default:break;
                }
            }
        });
    }
}
