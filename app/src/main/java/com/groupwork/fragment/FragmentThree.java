package com.groupwork.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.groupwork.R;
import com.groupwork.activity.ForgotPassword;
import com.groupwork.activity.HomeScreen;
import com.groupwork.urlContrans.UrlConfig;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by admin on 2017/4/4.
 */

public class FragmentThree extends Fragment {

    private TextView user_profile_name;
    private TextView user_profile_email;
    private TextView user_profile_password;
    private TextView user_profile_changepwd;
    private ImageView user_profile_img;
    private Button user_profile_logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.user_layout_new,container,false);

        user_profile_name = (TextView)view.findViewById(R.id.user_profile_name);
        user_profile_email = (TextView)view.findViewById(R.id.user_profile_email);
        user_profile_password = (TextView)view.findViewById(R.id.user_profile_password);
        user_profile_changepwd = (TextView)view.findViewById(R.id.user_profile_changepwd);
        user_profile_img = (ImageView)view.findViewById(R.id.user_profile_img);
        user_profile_logout = (Button)view.findViewById(R.id.user_profile_logout);

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

        /*
        user_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        */

        user_profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            Toast.makeText(getActivity(),"empty",Toast.LENGTH_LONG).show();
            return;//当data为空的时候，不做任何处理
        }
        Bitmap bitmap = null;
        if(requestCode==1){
            //获取从相册界面返回的缩略图
            bitmap = data.getParcelableExtra("data");
            Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
            if(bitmap==null){//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                /*
                try {
                    //通过URI得到输入流
                    //InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    //通过输入流得到bitmap对象
                    //bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                */

            }
        }else if(requestCode==2){
            Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
            //bitmap = (Bitmap) data.getExtras().get("data");
            //saveToSDCard(bitmap);
        }
        else{
            Toast.makeText(getActivity(),"3",Toast.LENGTH_LONG).show();
        }
        //将选择的图片设置到控件上
        user_profile_img.setImageBitmap(bitmap);
    }
}
