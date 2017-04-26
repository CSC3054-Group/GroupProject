package com.groupwork.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.groupwork.R;
import com.groupwork.urlContrans.UrlConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Reviewpage extends AppCompatActivity {
    RatingBar ratebar;
    EditText review;
    Button submitreview;
    float ratingvalue =0;
    private GoogleApiClient client;
    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            //textview feedback
            Log.d("Test2", text);

            if(text.equals("ReviewInsertToDb"))
            {

                Toast.makeText(getApplication(), "Review Successfully Posted Thanks!", Toast.LENGTH_SHORT).show();
            }
            if(text.equals("Blank"))
            {

                Toast.makeText(getApplication(), "All Fields Required", Toast.LENGTH_SHORT).show();
            }

        }};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewpage);
        review = (EditText) findViewById(R.id.txtaddreview);
        ratebar = (RatingBar) findViewById(R.id.ratingPrice);
        submitreview = (Button) findViewById(R.id.buttonsub);



        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingvalue = rating;

            }
        });


        submitreview.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String ReviewFromCustomer = review.getText().toString();
                Log.d("ReviewText",ReviewFromCustomer);
                if (ReviewFromCustomer.equals(""))
                {
                    Message message = Message.obtain();
                    message.obj = "Blank";
                    Log.d("resSaved","Saved");
                    message.what = 1;  // obj and what is similar as value-key
                    handler.sendMessage(message);// send message to handler
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
                                //String sqlString = "INSERT INTO tbl_usersaved (restid, UserId) VALUES ('"+  UrlConfig.restid +"','"+ UrlConfig.userid +"')";
                                //String sqlString = "INSERT INTO tbl_reviews (revStarRating,revText,UserId,restId) VALUES ('"+ ratingvalue +"','"+ ReviewFromCustomer +"','"+  UrlConfig.restid +"','"+ UrlConfig.userid +"')";
                                String sqlString = "Insert Into tbl_reviews (revStarRating,revText,UserId,restId) values ("+ratingvalue+" ,'"+ReviewFromCustomer+"' ,"+UrlConfig.userid+" ,"+UrlConfig.restid+")";
                                //String working = "INSERT INTO tbl_usersaved (restid, UserId) VALUES ('"+  UrlConfig.restid +"','"+ UrlConfig.userid +"')";


                                Log.d("sql", "Hit");

                                pw.write(sqlString);
                                pw.flush();
                                socket.shutdownOutput();

                                Message message = Message.obtain();
                                message.obj = "ReviewInsertToDb";
                                Log.d("resSaved","Saved");
                                message.what = 1;  // obj and what is similar as value-key
                                handler.sendMessage(message);// send message to handler


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