package com.groupwork.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.groupwork.R;
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

public class ForgotPassword extends AppCompatActivity {
    ImageView emaillogo , securityanswerlogo;
    EditText email,securityanswer ,finalpassword;
    Button next , next1 ,complete;
    TextView securityquestion;
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Passed To Handler",text);

            if(text.equals("FieldsEmpty"))
            {
                Toast.makeText(getApplication(), "You Need To Enter An Email Address", Toast.LENGTH_LONG).show();
            }
            else if(text.equals("Security Question Found"))
            {
                Toast.makeText(getApplication(), "Please Answer The Listed Security Question" , Toast.LENGTH_SHORT).show();
                emaillogo.setVisibility(View.INVISIBLE);
                securityanswerlogo.setVisibility(View.VISIBLE);

                email.setVisibility(View.INVISIBLE);
                securityanswer.setVisibility(View.VISIBLE);

                next.setVisibility(View.INVISIBLE);
                next1.setVisibility(View.VISIBLE);

                securityquestion.setText("Your Security Question Is: " + UrlConfig.securityquestion);
                securityquestion.setVisibility(View.VISIBLE);
            }
            else if(text.equals("Fail"))
            {
                Toast.makeText(getApplication(), "Invalid Email Entered", Toast.LENGTH_LONG).show();
            }
        }
    };



    Handler handleradditional = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();

            if(text.equals("SecurityAnswerMatches"))
            {
                Toast.makeText(getApplication(), "Your Security Answer Is Correct. Now Please Enter A New Password", Toast.LENGTH_LONG).show();
                securityquestion.setText("Please Enter Your New Password");
                complete.setVisibility(View.VISIBLE);
                next1.setVisibility(View.INVISIBLE);

                securityanswer.setVisibility(View.INVISIBLE);
                finalpassword.setVisibility(View.VISIBLE);

            }
            else if (text.equals("SecurityAnswerInvalid"))
            {
                Toast.makeText(getApplication(), "Wrong Answer", Toast.LENGTH_LONG).show();
            }

        }
    };

    Handler handler3 = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();

            if(text.equals("PasswordValid"))
            {
                Toast.makeText(getApplication(), "Your Password Has Been Updated. You Will Now Be Redirected To The Login Page.", Toast.LENGTH_LONG).show();
                Intent login = new Intent(ForgotPassword.this, Login.class);
                startActivity(login);

            }
            else if(text.equals("PasswordNull"))
            {

                Toast.makeText(getApplication(), "Cant Enter A Null Password", Toast.LENGTH_LONG).show();
            }
            else if(text.equals("PasswordLessThan8"))
            {

                Toast.makeText(getApplication(), "Your Password Must Be 8 Characters Or More", Toast.LENGTH_LONG).show();
            }



        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);



        emaillogo = (ImageView)findViewById(R.id.iv_forgotpassword_emailIIcon);
        securityanswerlogo = (ImageView)findViewById(R.id.iv_forgotpassword_securitylogo);
        email = (EditText)findViewById(R.id.et_forgotpassword_enteremail);
        securityanswer = (EditText)findViewById(R.id.et_forgotpassword_securityanswer);
        next = (Button)findViewById(R.id.btn_forgetpassword_next);
        next1 = (Button)findViewById(R.id.btn_forgetpasswordnext1);
        securityquestion = (TextView)findViewById(R.id.tv_forgetpassword_securityquestion);

        complete = (Button)findViewById(R.id.btn_forgetpassword_Complete);
        finalpassword = (EditText)findViewById(R.id.et_forgetpassword_finalpassword);





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String useremailaddress = email.getText().toString();

                if(useremailaddress.equals(""))
                {
                    Message message = Message.obtain();
                    message.obj = "FieldsEmpty";
                    message.what = 1;  // obj and what is similar as value-key
                    handler.sendMessage(message);// send message to handler
                }
                else
                {
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
                                String sqlString = "select UserId,SecurityQuestion,SecurityAnswer from tbl_users where Email = '" + useremailaddress + "'";
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

                                    UrlConfig.securityquestion = object.getString("SecurityQuestion");
                                    UrlConfig.securityanswer = object.getString("SecurityAnswer");
                                    UrlConfig.userid=object.getString("UserId");


                                    Log.d("value from url config",UrlConfig.securityquestion  + " " + UrlConfig.securityanswer);
                                    //Log.d("Test2",Email+);
                                }
                                Log.d("Test2", "successful");

                                if (UrlConfig.securityquestion !=null)
                                {
                                    //Set result to textview
                                    Message message = Message.obtain();
                                    message.obj = "Security Question Found";
                                    message.what = 1;  // obj and what is similar as value-key
                                    handler.sendMessage(message);// send message to handler
                                    //Toast.makeText(getApplication(), "Login SUSCCESS", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
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


                }

            }
        });




        //complete button
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stores the value the user give for their answer to the security question
                String usersanswer = securityanswer.getText().toString();
                if(usersanswer.equals(UrlConfig.securityanswer))
                {
                    Message message = Message.obtain();
                    message.obj = "SecurityAnswerMatches";
                    message.what = 1;  // obj and what is similar as value-key
                    handleradditional.sendMessage(message);// send message to handler

                }
                else
                {
                    Message message = Message.obtain();
                    message.obj = "SecurityAnswerInvalid";
                    message.what = 1;  // obj and what is similar as value-key
                    handleradditional.sendMessage(message);// send message to handler

                }
            }
        });


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newdbpassword = finalpassword.getText().toString();
                final String userid = UrlConfig.userid.toString();
                int dbpasswordcount = newdbpassword.length();
                Log.d("",String.valueOf(dbpasswordcount));

                if(newdbpassword.equals(""))
                {
                    Message message = Message.obtain();
                    message.obj = "PasswordNull";
                    message.what = 1;  // obj and what is similar as value-key
                    handler3.sendMessage(message);// send message to handler
                }
                else if(dbpasswordcount<8)
                {
                    Message message = Message.obtain();
                    message.obj = "PasswordLessThan8";
                    message.what = 1;  // obj and what is similar as value-key
                    handler3.sendMessage(message);// send message to handler
                }

                else
                {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Socket socket = null;

                            try {
                                socket = new Socket(UrlConfig.Socket_IP, UrlConfig.Socket_PORT);
                                //Output data to Server
                                OutputStream out = socket.getOutputStream();
                                PrintWriter pw = new PrintWriter(out);
                                //Input sql sentence which you want to execute
                                String sqlString = "UPDATE tbl_users SET UserPassword = '" + newdbpassword + "' WHERE UserId = " + userid +"";
                                Log.d("ValuesUpdated" ,newdbpassword + " " + userid  );
                                pw.write(sqlString);
                                pw.flush();
                                socket.shutdownOutput();
                                Log.d("Test", "transport successfully");

                                Message message = Message.obtain();
                                message.obj = "PasswordValid";
                                message.what = 1;  // obj and what is similar as value-key
                                handler3.sendMessage(message);// send message to handler


                            } catch (Exception e) {

                            } finally {
                                try {
                                    if(socket!=null){
                                        socket.close();
                                    }
//                                socket.shutdownInput();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }).start();




                }




            }
        });

    }
}
