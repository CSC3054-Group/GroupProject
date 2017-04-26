package com.groupwork.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.groupwork.R;
import com.groupwork.adapter.viewReviewAdapter;
import com.groupwork.bean.ViewReview;
import com.groupwork.urlContrans.UrlConfig;
import com.groupwork.utils.ParserJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class viewReviews extends AppCompatActivity {

    private List<ViewReview> reviewList;
    private ListView listView;
    private viewReviewAdapter adapter;
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2",text);
            if(text.equals("DatabaseReadSuccessful"))
            {
                adapter.refresh(reviewList);
                adapter.notifyDataSetChanged();

            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_reviews);
        Button return_bt = (Button) findViewById(R.id.reviewTitle_back);

        reviewList = new ArrayList<>();
        adapter = new viewReviewAdapter(reviewList,getApplication());
        listView = (ListView) findViewById(R.id.list_savedRes);
        listView.setAdapter(adapter);
        String resId = UrlConfig.restid;
        return_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(viewReviews.this,RestaurantDetails.class);
                intent.putExtra("resId",UrlConfig.restid);
                startActivity(intent);
            }
        });
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

                    if(!info.isEmpty()&&!info.equals("")){
                        reviewList = ParserJson.paserJsonReview(info);
                    }
                    Message msg =new Message();
                    msg.obj="DatabaseReadSuccessful";
                    msg.what=1;
                    handler.sendMessage(msg);
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
