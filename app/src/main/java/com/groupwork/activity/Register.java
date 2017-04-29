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

public class Register extends AppCompatActivity {
    TextView existingaccount , verifyemail;
    EditText forename,surname,email,squestion,password;
    Button register;
    ImageView iv_secquestion,iv_pword;
    String Useremailvalue;
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2",text);
            if(text.equals("Email Address Matches"))
            {
                Log.d("Test2","enter into handle");
                Toast.makeText(getApplication(), "You Already Have Registered An Account", Toast.LENGTH_LONG).show();
                squestion.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);
                iv_secquestion.setVisibility(View.INVISIBLE);
                iv_pword.setVisibility(View.INVISIBLE);
            }
            else if (text.equals("Email Address Available"))
            {
                Toast.makeText(getApplication(), "Email Address Available", Toast.LENGTH_LONG).show();
                squestion.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                register.setVisibility(View.VISIBLE);
                iv_secquestion.setVisibility(View.VISIBLE);
                iv_pword.setVisibility(View.VISIBLE);
                verifyemail.setText("Email Available");
            }
        }
    };


    //Handler to control the register button

    Handler Registerhandler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2",text);
            if(text.equals("RegisterSuccess"))
            {   UrlConfig.Emailfromregister = email.getText().toString();
                Intent register = new Intent(Register.this, Login.class);
                startActivity(register);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        existingaccount = (TextView)findViewById(R.id.tv_existingaccount);
        forename = (EditText)findViewById(R.id.et_forename);
        surname = (EditText)findViewById(R.id.et_surname);
        email = (EditText)findViewById(R.id.et_emailaddress);
        verifyemail = (TextView)findViewById(R.id.tv_verifyemail);

        //should be hidden until email is validated
        squestion = (EditText)findViewById(R.id.et_securityquestion);
        password = (EditText)findViewById(R.id.et_password);
        register = (Button)findViewById(R.id.btn_register);
        iv_secquestion = (ImageView)findViewById(R.id.imageView6);
        iv_pword = (ImageView)findViewById(R.id.imageView7);

        //set an onclick listerner on the verify email option in order to check if the email already exists in the system.
        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String UserEmail = email.getText().toString();
                Useremailvalue = email.getText().toString();
                Log.d("UserEmailValue",Useremailvalue);
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
                            String sqlString = "select Email from tbl_users where Email = '"+UserEmail+"'";
                            pw.write(sqlString);
                            pw.flush();
                            socket.shutdownOutput();
                            Log.d("Test", "transport successfully");
                            //get response from server

                            InputStream is = socket.getInputStream();
                            InputStreamReader isr =new InputStreamReader(is);
                            BufferedReader br =new BufferedReader(isr);
                            String info = "";
                            String line = null;
                            while((line=br.readLine())!=null){
                                info = info + line;

                            }

                            //socket.shutdownInput();

                            Log.d("Test", info);

                            JSONArray array= new JSONArray(info);

                            String DatabseEmail = "";
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                DatabseEmail=object.getString("Email");
                                Log.d("Show Email Address",DatabseEmail);

                            }


                            if(DatabseEmail.equals(UserEmail)) {
                                //Set result to textview
                                Log.d("Email Response","Email Address Matches");
                                Message message = Message.obtain();
                                message.obj = "Email Address Matches";
                                message.what = 1;  // obj and what is similar as value-key
                                handler.sendMessage(message);// send message to handler

                            }
                            else
                            {   Log.d("Email Response","Email Address Available");
                                Message message = Message.obtain();
                                message.obj = "Email Address Available";
                                message.what = 1;  // obj and what is similar as value-key
                                handler.sendMessage(message);// send message to handler




                            }

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
        });




        //set an onclicklistener for the register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentemailvalue = email.getText().toString();
                String runsql = "";
                if(currentemailvalue.equals(Useremailvalue))
                {

                    //Toast.makeText(getApplication(), "You have successfully Registered go to login", Toast.LENGTH_LONG).show();
                    runsql = "True";
                    //Toast.makeText(getApplication(), "RunSql Value is " + runsql, Toast.LENGTH_LONG).show();


                }
                else
                {
                    Toast.makeText(getApplication(), "Your Email Address Has changed since the last validation", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplication(), "Please Reenter Your Email Address An Validate It", Toast.LENGTH_LONG).show();
                    verifyemail.setText("Check Email Availability");
                    Log.d("CurrentEmail is",currentemailvalue);
                    Log.d("ValidatedEmail is",Useremailvalue);
                    squestion.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.INVISIBLE);
                    iv_secquestion.setVisibility(View.INVISIBLE);
                    iv_pword.setVisibility(View.INVISIBLE);
                    runsql = "False";
                    //Toast.makeText(getApplication(), "RunSql Value is " + runsql, Toast.LENGTH_LONG).show();
                }




                if(runsql.equals("True"))
                {

                    //Toast.makeText(getApplication(), "Run Insert query ", Toast.LENGTH_LONG).show();
                    final String fname = forename.getText().toString();
                    Log.d("forename is ",fname);
                    final String sname = surname.getText().toString();
                    Log.d("Surname is ",sname);
                    final String emailaddress = email.getText().toString();
                    Log.d("Email",emailaddress);
                    final String securityquestionanswer = squestion.getText().toString();
                    Log.d("s question",securityquestionanswer);
                    final String Password = password.getText().toString();
                    Log.d("Password is ",Password);

                    if(fname.equals("")||sname.equals("")|| emailaddress.equals("")|| securityquestionanswer.equals("") || Password.equals("") )
                    {

                        Toast.makeText(getApplication(), "You Have Not Filled In All Fields", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        //run insert query
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
                                    String sqlString = "insert into tbl_users(Forename,Surname,Email,UserPassword,SecurityQuestion,SecurityAnswer)" +
                                            "values('"+fname+"','"+sname+"','"+emailaddress+"','"+Password+"','What Is Your Favourite City','"+securityquestionanswer+"')";
                                    pw.write(sqlString);
                                    pw.flush();
                                    socket.shutdownOutput();
                                    Log.d("Test", "transport successfully");

                                    Message message = Message.obtain();
                                    message.obj = "RegisterSuccess";
                                    message.what = 1;  // obj and what is similar as value-key
                                    Registerhandler.sendMessage(message);// send message to handler


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
                        //Toast.makeText(getApplication(), "No Fields Are Empty Run Query", Toast.LENGTH_LONG).show();

                    }
                }





            }


        });



















        existingaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Register.this, Login.class);
                startActivity(register);
            }
        });
    }
}
