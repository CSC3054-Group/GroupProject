package com.groupwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RestaurantDetails extends AppCompatActivity {
    TextView restname;
    TextView restAddress;
    RatingBar rOverall;
    TextView opTimes;
    TextView hyg;
    TextView number;
    RatingBar price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        //set variables so they can be assigned via query
        restname = (TextView)findViewById(R.id.txtRestaurantName);
        restAddress = (TextView)findViewById(R.id.txtrestaurantaddress);
        rOverall  = (RatingBar) findViewById(R.id.ratingOverall);
        opTimes = (TextView)findViewById(R.id.txtmondaytime);
        number  = (TextView)findViewById(R.id.textView16);
        price  = (RatingBar) findViewById(R.id.ratingPrice);



        //Get id from previous activity
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("restID");

        //use id to get rest details from db
        MysqlHelper ms = new MysqlHelper("54.154.229.112", "RFinder");

        //Query to get data
        String sql_getRestDetails = "";

        //execute query
        ms.execute(sql_getRestDetails);

        try{
            if(ms.rs != null){
                //ms.rs is the result of excution
                //if there is no result ms.rs is null
                while(ms.rs.next()){
                    String rname = ms.rs.getString("username");
                    String raddress = ms.rs.getString("username");
                    String rRating = ms.rs.getString("username");
                    String rTimes = ms.rs.getString("username");
                    String rNumber = ms.rs.getString("username");
                    String rPrice = ms.rs.getString("username");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{}
    }
}
