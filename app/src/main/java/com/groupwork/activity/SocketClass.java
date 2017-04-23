package com.groupwork.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.groupwork.R;
import com.groupwork.urlContrans.UrlConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by admin on 2017/3/31.
 */

public class SocketClass extends AppCompatActivity {
    private EditText editText;
    private Button socket_bt;
    private TextView textView;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = msg.obj.toString();

            textView.setText(text);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test);
        editText = (EditText) findViewById(R.id.edit_userID);
        socket_bt = (Button) findViewById(R.id.bt_forename);
        textView = (TextView) findViewById(R.id.text_forename);
        socket_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_number = editText.getText().toString();
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
                            String sqlString = "select tbl_restaurants.restId,resLocation,tbl_restaurants.resName," +
                                    "AVG(revStarRating),resType from tbl_restaurants,tbl_restype,tbl_reviews " +
                                    "where tbl_restaurants.restId=tbl_reviews.restId " +
                                    "and tbl_restaurants.resTypeId=tbl_restype.resTypeId " +
                                    "GROUP BY tbl_restaurants.restId";
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
                            /**
                             * Json analyse
                             * [
                             {
                             "Email": "gcunningham12@qub.ac.uk",
                             "UserId": "1",
                             "Forename": "Gerard",
                             "Title": "Mr",
                             "UserPassword": "password123",
                             "Middlename": "Anthony",
                             "Surname": "Cunningham",
                             "Postcode": "BT344HR",
                             "SecurityQuestion": "Place Of Birth",
                             "SecurityAnswer": "Newry"
                             },
                             {
                             "Email": "jmccully03@qub.ac.uk",
                             "UserId": "6",
                             "Forename": "Jamie",
                             "Title": "Mr",
                             "UserPassword": "password123",
                             "Middlename": "Ryan ",
                             "Surname": "McCully",
                             "Postcode": "BT455HR",
                             "SecurityQuestion": "Place Of Birth",
                             "SecurityAnswer": "Belfast"
                             },
                             {
                             "Email": "hwilson20@qub.ac.uk",
                             "UserId": "7",
                             "Forename": "Harry ",
                             "Title": "Mr",
                             "UserPassword": "password123",
                             "Middlename": "Jamie",
                             "Surname": "Wilson",
                             "Postcode": "BT367RD",
                             "SecurityQuestion": "Place Of Birth",
                             "SecurityAnswer": "Belfast"
                             },
                             {
                             "Email": "l@qub.ac.uk",
                             "UserId": "8",
                             "Forename": "Jianyu",
                             "Title": "Mr ",
                             "UserPassword": "password123",
                             "Middlename": "R",
                             "Surname": "Li",
                             "Postcode": "BT321SE",
                             "SecurityQuestion": "Place Of Birth",
                             "SecurityAnswer": "Belfast"
                             },
                             {
                             "Email": "r@qub.ac.uk",
                             "UserId": "9",
                             "Forename": "Lei",
                             "Title": "Mr ",
                             "UserPassword": "password123",
                             "Middlename": "R",
                             "Surname": "Tong",
                             "Postcode": "BT321RS",
                             "SecurityQuestion": "Place Of Birth",
                             "SecurityAnswer": "Belfast"
                             }
                             ]
                             *
                             * */
                             JSONArray array= new JSONArray(info);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                String Email=object.getString("Email");
                                int UserId=object.getInt("UserId");
                                String ForeName=object.getString("Forename");
                                String Title=object.getString("Title");
                                String Middlename=object.getString("Middlename");
                                String Surname=object.getString("Surname");
                                Log.d("Test2",Email+" "+UserId+" "+ForeName+" "+Title+" "+
                                        Middlename+" "+Surname);
                                //Log.d("Test2",Email+);
                            }
                            Log.d("Test2","successful");


                            //Set result to textview
                            Message message =Message.obtain();
                            message.obj = info;
                            message.what = 1;  // obj and what is similar as value-key
                            handler.sendMessage(message);// send message to handler

                        } catch (Exception e) {

                        } finally {
                            try {
                                if(socket!=null){
                                    socket.close();
                                }
                                socket.shutdownInput();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }



}
