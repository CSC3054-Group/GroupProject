package com.groupwork.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groupwork.R;
import com.groupwork.activity.ForgotPassword;
import com.groupwork.urlContrans.UrlConfig;

/**
 * Created by admin on 2017/4/4.
 */

public class FragmentThree extends Fragment {

    private TextView user_profile_name;
    private TextView user_profile_email;
    private TextView user_profile_password;
    private TextView user_profile_changepwd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.user_layout_new,container,false);

        user_profile_name = (TextView)view.findViewById(R.id.user_profile_name);
        user_profile_email = (TextView)view.findViewById(R.id.user_profile_email);
        user_profile_password = (TextView)view.findViewById(R.id.user_profile_password);
        user_profile_changepwd = (TextView)view.findViewById(R.id.user_profile_changepwd);

        Log.d("forename", ""+UrlConfig.forename);

        user_profile_name.setText(""+UrlConfig.forename+" "+UrlConfig.surname);
        user_profile_email.setText(UrlConfig.email);
        user_profile_password.setText(UrlConfig.password);
        user_profile_changepwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        user_profile_changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changepwd = new Intent(getActivity(), ForgotPassword.class);
                startActivity(changepwd);
            }
        });

        return view;
    }
}
