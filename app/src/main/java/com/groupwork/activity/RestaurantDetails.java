package com.groupwork.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.groupwork.R;
import com.groupwork.fragment.FragmentOne;
import com.groupwork.fragment.FragmentThree;
import com.groupwork.fragment.FragmentTwo;
import com.groupwork.fragment.NeabyFragment;
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

public class RestaurantDetails extends AppCompatActivity {
    TextView restname;
    TextView restAddress;
    TextView restdesc;
    TextView opTimes;
    Button review;
    Button call;
    Button save;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    TextView rating;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UrlConfig.restid = "1";

        //Get id from previous activity
        Intent intent  = getIntent();
        int redId = intent.getIntExtra("resId",0);
        UrlConfig.restid = String.valueOf(redId);
        Log.d("ResID",String.valueOf(redId));


        setContentView(R.layout.activity_restaurant_details);

        //set variables so they can be assigned via query
        restname = (TextView) findViewById(R.id.txtRestaurantName);
        restAddress = (TextView) findViewById(R.id.txtrestaurantaddress);
        //rOverall = (RatingBar) findViewById(R.id.ratingOverall);
        opTimes = (TextView) findViewById(R.id.txttimes);
        //number = (TextView) findViewById(R.id.textView16);
        rating = (TextView) findViewById(R.id.txtvrating);
        restdesc = (TextView) findViewById(R.id.txtDescription);
        save = (Button)findViewById(R.id.btnsave);
        call= (Button)findViewById(R.id.btncall);
        review = (Button)findViewById(R.id.btnreview);
        call.setOnClickListener(myOnClick_call());
        review.setOnClickListener(myOnClick_review());
        save.setOnClickListener(myOnClick_save());

        //use this id in query to select restaurant details from db and display them
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
                    String sqlString = "Select tbl_restaurants.restId as id, resLocation, resNumber, resName, resDText, resDTimes, AVG(tbl_reviews.revStarRating) as avgcnt " +
                            "FROM tbl_restaurants " +
                            "INNER JOIN tbl_restdetails on tbl_restaurants.restId = tbl_restdetails.restId " +
                            "LEFT JOIN tbl_reviews on tbl_restaurants.restId = tbl_reviews.restId " +
                            "WHERE tbl_restaurants.restId = '"+UrlConfig.restid+"'";
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


                        UrlConfig.restaurantnumber = object.getString("resNumber");
                        restname.setText(object.getString("resName"));
                        restAddress.setText(object.getString("resLocation"));
                        opTimes.setText(object.getString("resDTimes"));
                        restdesc.setText(object.getString("resDText"));
                        rating.setText(object.getString("avgcnt"));
                        Log.d("Test", object.getString("avgcnt"));



                        //Log.d("Test2",Email+);
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

    protected View.OnClickListener myOnClick_save() {
        View.OnClickListener v = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save current restaurant as a saved place
                //show toast to tell the user that the place has been saved
                //run insert query
                isSaved();

            }
        };
    return v;
    }

    public void isSaved(){
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
                    String sqlString = "SELECT COUNT(restid) as cnt  FROM tbl_usersaved WHERE restid = '"+  UrlConfig.restid +"' " +
                            "AND UserId= '"+ UrlConfig.userid +"')";
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

                        int x = Integer.parseInt(object.getString("cnt"));

                        if(x > 0){
                            //already saved
                            Toast.makeText(getApplication(), "Already Saved!", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            //not saved
                            saveres();
                        }




                        //Log.d("Test2",Email+);
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

    public void saveres(){
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
                    String sqlString = "INSERT INTO tbl_usersaved (restid, UserId) VALUES ('"+  UrlConfig.restid +"','"+ UrlConfig.userid +"')";
                    pw.write(sqlString);
                    pw.flush();
                    socket.shutdownOutput();
                    Log.d("Test", "transport successfully");
                    Toast.makeText(getApplication(), "Saved!", Toast.LENGTH_SHORT).show();


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
    protected View.OnClickListener myOnClick_review() {
        View.OnClickListener v = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //send to review screen with id of restaurant
                Intent intent2 = new Intent(RestaurantDetails.this, Reviewpage.class);
                //intent2.putExtra("resid", UrlConfig.restid);
                startActivity(intent2);
            }
        };
        return v;
    }
    protected View.OnClickListener myOnClick_call() {
        View.OnClickListener v = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //open call facility and call restaurant
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + UrlConfig.restaurantnumber));
                startActivity(intent);
            }
        };
        return v;
    }




}
