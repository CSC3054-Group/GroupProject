package com.groupwork;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.groupwork.utils.MysqlHelper;

public class RestaurantDetails extends AppCompatActivity {
    TextView restname;
    TextView restAddress;
    RatingBar rOverall;
    TextView opTimes;
    TextView hyg;
    TextView number;
    RatingBar price;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        //set variables so they can be assigned via query
        restname = (TextView) findViewById(R.id.txtRestaurantName);
        restAddress = (TextView) findViewById(R.id.txtrestaurantaddress);
        rOverall = (RatingBar) findViewById(R.id.ratingOverall);
        opTimes = (TextView) findViewById(R.id.txtmondaytime);
        number = (TextView) findViewById(R.id.textView16);
        price = (RatingBar) findViewById(R.id.ratingPrice);


        //Get id from previous activity
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("restID");

        //use id to get rest details from db
        MysqlHelper ms = new MysqlHelper("54.154.229.112", "RFinder");

        //Query to get data
        String sql_getRestDetails = "";

        //execute query
        ms.execute(sql_getRestDetails);

        try {
            if (ms.rs != null) {
                //ms.rs is the result of excution
                //if there is no result ms.rs is null
                while (ms.rs.next()) {
                    String rname = ms.rs.getString("username");
                    String raddress = ms.rs.getString("username");
                    String rRating = ms.rs.getString("username");
                    String rTimes = ms.rs.getString("username");
                    String rNumber = ms.rs.getString("username");
                    String rPrice = ms.rs.getString("username");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RestaurantDetails Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
