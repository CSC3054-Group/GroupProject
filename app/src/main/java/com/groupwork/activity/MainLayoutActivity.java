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
import com.groupwork.fragment.FragmentOne;
import com.groupwork.fragment.FragmentThree;
import com.groupwork.fragment.FragmentTwo;
import com.groupwork.fragment.NeabyFragment;

/**
 * Created by admin on 2017/4/2.
 */

public class MainLayoutActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        InitialBotTab();
        InitialSwitch();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.container,new FragmentOne());
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
                        FragmentManager manager4 = getSupportFragmentManager();
                        FragmentTransaction transaction4 =manager4.beginTransaction();
                        transaction4.replace(R.id.container,new NeabyFragment());
                        transaction4.commit();
                        break;
                    case R.id.rButton_popular:
                        FragmentManager manager1 = getSupportFragmentManager();
                        FragmentTransaction transaction1 =manager1.beginTransaction();
                        transaction1.replace(R.id.container,new FragmentOne());
                        transaction1.commit();
                        break;
                    case R.id.rButton_category:
                        FragmentManager manager2 = getSupportFragmentManager();
                        FragmentTransaction transaction2 =manager2.beginTransaction();
                        transaction2.replace(R.id.container,new FragmentTwo());
                        transaction2.commit();
                        break;
                    case R.id.rButton_user:
                        FragmentManager manager3 = getSupportFragmentManager();
                        FragmentTransaction transaction3 =manager3.beginTransaction();
                        transaction3.replace(R.id.container,new FragmentThree());
                        transaction3.commit();
                        break;
                    default:break;

                }
            }
        });
    }

}
