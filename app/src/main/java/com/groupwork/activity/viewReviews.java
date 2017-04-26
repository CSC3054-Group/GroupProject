package com.groupwork.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class viewReviews extends AppCompatActivity {

    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2",text);
            if(text.equals("DatabaseReadSuccessful"))
            {


            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        String resId = UrlConfig.restid;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Test", "entered thread");
                // open thread to process network access request and response
                Socket socket = null;

                try {
                    socket = new Socket(UrlConfig.Socket_IP, UrlConfig.Socket_PORT);
                    //Output data to Server
                    OutputStream out = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(out);
                    //Input sql sentence which you want to execute
                    String sqlString = "SELECT revStarRating, revText, Forename, Surname " +
                            "FROM tbl_reviews " +
                            "JOIN tbl_users on tbl_reviews.UserId = tbl_users.UserId " +
                            "WHERE tbl_reviews.restId ='"+UrlConfig.restid+"'";
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

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);


                        Log.d("Test", object.getString("avgcnt"));
                        Message message = Message.obtain();
                        message.obj = "DatabaseReadSuccessful";
                        message.what = 1;  // obj and what is similar as value-key
                        handler.sendMessage(message);// send message to handler

                    }
                    Log.d("Test2", "successful");

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
