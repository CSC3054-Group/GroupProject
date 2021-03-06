package com.groupwork.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.groupwork.R;
import com.groupwork.activity.UserHome;
import com.groupwork.urlContrans.UrlConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Login extends AppCompatActivity {

    TextView noaccount,uname ,pword, forgotpassword;

    Button login ;
    String dbUserId ,dbForename, dbSurname,dbEmail,dbUserPassword,dbSQ,dbSA;



    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2",text);
            if(text.equals("SUCCESS"))
            {
                Log.d("Test2","enter into handle");
                //Toast.makeText(getApplication(), "Login Success. Go To The HomePage Of App", Toast.LENGTH_LONG).show();
                Intent login = new Intent(Login.this, MainLayoutActivity.class);
                startActivity(login);

            }
            else if (text.equals("Fail"))
            {
                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_LONG).show();

            }
            else if(text.equals("FieldsEmpty"))
            {
                Toast.makeText(getApplication(), "Both A Vaild Username And Password Is Required", Toast.LENGTH_LONG).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        noaccount = (TextView)findViewById(R.id.tv_noaccount);
        uname = (TextView)findViewById(R.id.et_uname_login);
        pword = (TextView)findViewById(R.id.et_pword_login);
        login = (Button)findViewById(R.id.btn_login);
        forgotpassword = (TextView)findViewById((R.id.tv_login_forgotpassword));
        String emailfromurlconfig = UrlConfig.Emailfromregister;
        if(emailfromurlconfig != null)
        {
            uname.setText(emailfromurlconfig);
        }
        else if(emailfromurlconfig == null)
        {
            uname.setText("");
        }

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetpassword = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgetpassword);
            }
        });


        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = uname.getText().toString();
                final String password = pword.getText().toString();
                if (username.equals("") || password.equals(""))
                {
                    Message message = Message.obtain();
                    message.obj = "FieldsEmpty";
                    message.what = 1;  // obj and what is similar as value-key
                    handler.sendMessage(message);// send message to handler
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // open thread to process network access request and response
                            Socket socket = null;

                            try {
                                socket = new Socket(UrlConfig.Socket_IP, UrlConfig.Socket_PORT);
                                //Output data to Server
                                OutputStream out = socket.getOutputStream();
                                PrintWriter pw = new PrintWriter(out);
                                //Input sql sentence which you want to execute
                                String sqlString = "select UserId,Forename,Surname,Email,UserPassword,SecurityQuestion,SecurityAnswer from tbl_users where Email = '" + username + "' AND UserPassword='" + password + "'";
                                pw.write(sqlString);
                                pw.flush();
                                socket.shutdownOutput();
                                Log.d("Test", "transport successfully");
                                //get response from server

                                InputStream is = socket.getInputStream();
                                InputStreamReader isr = new InputStreamReader(is);
                                BufferedReader br = new BufferedReader(isr);
                                String info = "";
                                String line = null;
                                while ((line = br.readLine()) != null) {
                                    info = info + line;

                                }

                                //socket.shutdownInput();

                                Log.d("Test", info);

                                JSONArray array = new JSONArray(info);
                                String Password = "";
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    UrlConfig.userid = object.getString("UserId");
                                    UrlConfig.forename = object.getString("Forename");
                                    UrlConfig.surname = object.getString("Surname");
                                    UrlConfig.email = object.getString("Email");
                                    UrlConfig.password = object.getString("UserPassword");
                                    //UrlConfig.securityquestion = object.getString("SecurityQuestion");
                                    //UrlConfig.securityanswer = object.getString("SecurityAnswer");

                                    dbUserId = UrlConfig.userid.toString();

                                    String Email = object.getString("Email");
                                    Password = object.getString("UserPassword");

                                    Log.d("Show credentials", Email + " " + Password);
                                    Log.d("value from url config", dbUserId);
                                    //Log.d("Test2",Email+);
                                }
                                Log.d("Test2", "successful");

                                if (Password.equals(password)) {
                                    //Set result to textview
                                    Message message = Message.obtain();
                                    message.obj = "SUCCESS";
                                    message.what = 1;  // obj and what is similar as value-key
                                    handler.sendMessage(message);// send message to handler
                                    //Toast.makeText(getApplication(), "Login SUSCCESS", Toast.LENGTH_LONG).show();
                                } else {
                                    Message message = Message.obtain();
                                    message.obj = "Fail";
                                    message.what = 1;  // obj and what is similar as value-key
                                    handler.sendMessage(message);// send message to handler


                                }

                            } catch (Exception e) {

                            } finally {
                                try {
                                    if (socket != null) {
                                        socket.close();
                                    }
//                                socket.shutdownInput();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();

                }    }
        });
    }
}

