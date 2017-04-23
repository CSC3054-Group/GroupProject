package com.groupwork.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.groupwork.R;
import com.groupwork.bean.NearbyItem;
import com.groupwork.urlContrans.UrlConfig;
import com.groupwork.utils.ParserJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 2017/3/1.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    private String RestaurantId;
    private int[] PhotoId={R.drawable.costa,R.drawable.boojum,R.drawable.bootleggers,R.drawable.empire
            ,R.drawable.ashers,R.drawable.nero,R.drawable.villa,R.drawable.madisons,R.drawable.starbucks,R.drawable.kaffe,
            R.drawable.mcdonald,R.drawable.kfc,R.drawable.burgerking,R.drawable.deanes,R.drawable.jamesstreetsouth
            ,R.drawable.ritas,R.drawable.fivepoints,R.drawable.laverys,R.drawable.woodworkers,R.drawable.bobandberts,
            R.drawable.maggiemays};
    private MapFragment map_Fragemnt;
    private GoogleMap mMap;
    private double get_latititude;
    private double get_longtitute;
    final static int MY_LOCATION_REQUEST_CODE = 456;

    private LinearLayout mapItem_layout;
    private ImageView mapItem_img;
    private TextView mapItem_resName,mapItem_resType,mapItem_resDuration;
    private RatingBar mapItem_rating;
    private Marker[] markers;
    private List<NearbyItem> nearList;

    private Button maptitle_back;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.obj.toString().equals("update")) {
                markers = new Marker[nearList.size()];
                for (int i = 0; i < nearList.size(); i++) {
                    String resLocation = nearList.get(i).getResLoaction();
                    double latitude = 0;
                    double altitude = 0;
                    for (int j = 0; j < resLocation.length(); j++) {
                        if (resLocation.substring(j, j + 1).equals(",")) {

                            latitude = Double.valueOf(resLocation.substring(0, j));
                            altitude = Double.valueOf(resLocation.substring(j + 1, resLocation.length()));

                            break;
                        }
                    }

                        markers[i] = mMap.addMarker(new MarkerOptions().
                                position(new LatLng(latitude, altitude)).
                                icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal)));

                }
            }
            return false;
        }
    });
    private android.location.LocationListener mlocationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            get_latititude = location.getLatitude();
            get_longtitute = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.singlepoint_map);

        try {
            InitialView();
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void InitialView(){
        mapItem_layout = (LinearLayout) findViewById(R.id.mapresItem_layout);
        mapItem_img = (ImageView) findViewById(R.id.maplist_photo);
        mapItem_rating = (RatingBar) findViewById(R.id.map_rating);
        mapItem_resName = (TextView) findViewById(R.id.maptext_resName);
        mapItem_resType = (TextView) findViewById(R.id.map_resType);
        mapItem_resDuration = (TextView) findViewById(R.id.map_duration);

        nearList = new ArrayList<>();
        mapItem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getVisibility()==View.VISIBLE){
                    Intent intent= new Intent(MapActivity.this,IntentTest.class);
                    intent.putExtra("resName",mapItem_resName.getText());
                    startActivity(intent);
                }
            }
        });
        maptitle_back = (Button) findViewById(R.id.mapTitle_back);
        maptitle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this,MainLayoutActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        getCuttentLocation();
        new Thread(new RestaurantsThread()).start();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(get_latititude, get_longtitute), 13)
        );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                getCuttentLocation();
                Log.d("latitude",String.valueOf(get_latititude));
                Log.d("latitude",String.valueOf(get_longtitute));

                if (googleMap.getCameraPosition().target.latitude == get_latititude &&
                        googleMap.getCameraPosition().target.longitude == get_longtitute) {
                    return true;
                } else {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(get_latititude, get_longtitute), 16));
                }
                return false;
            }
        });




        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i=0;i<markers.length;i++){

                    if(marker.getId().equals(markers[i].getId())){
                        markers[i].setIcon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_click));
                        mapItem_layout.setVisibility(View.VISIBLE);
                        int resId = nearList.get(i).getResId();
                        RestaurantId = String.valueOf(resId);

                        mapItem_resName.setText(nearList.get(i).getResName());
                        mapItem_rating.setRating(nearList.get(i).getRating());
                        mapItem_resType.setText("Restaurant Type: "+nearList.get(i).getResType());
                        DecimalFormat format =new DecimalFormat("0.0");
                        mapItem_resDuration.setText("From the current location: "+format.format(nearList.get(i).getDuration())+"km");
                        if(resId>=1&&resId<=10){
                            mapItem_img.setImageResource(PhotoId[resId-1]);
                        }
                        else if (resId>=21&&resId<=31){
                            mapItem_img.setImageResource(PhotoId[resId-11]);
                        }
                    }else{
                        markers[i].setIcon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal));
                    }
                }



                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    public void initilizeMap() {
        if (map_Fragemnt == null) {
            map_Fragemnt = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
            map_Fragemnt.getMapAsync(this);
        }

        // check if map is created successfully or not
        if (map_Fragemnt == null) {
            Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }

    }

    public void getCuttentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location = null;
        if (!(isGPSEnable || isNetworkEnable)) {
            Toast.makeText(MapActivity.this, "have no permisson to get location data!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            if (isNetworkEnable) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mlocationListener);
                location  =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if(isGPSEnable){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mlocationListener);
                location  =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if(location!=null){
            get_latititude = location.getLatitude();
            get_longtitute = location.getLongitude();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                    return;
                }
                mMap.setMyLocationEnabled(true);
            }

        }
    }

    class RestaurantsThread implements Runnable{
        Socket socket=null;
        @Override
        public void run() {
            try{
                int count=1;
                socket = new Socket(UrlConfig.Socket_IP, UrlConfig.Socket_PORT);
                //Output data to Server
                OutputStream out = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(out);
                //Input sql sentence which you want to execute
                String sqlString = "select tbl_restaurants.restId,resLocation,tbl_restaurants.resName," +
                        "AVG(revStarRating),resType,resPhoto from tbl_restaurants,tbl_restype,tbl_reviews " +
                        "where tbl_restaurants.restId=tbl_reviews.restId " +
                        "and tbl_restaurants.resTypeId=tbl_restype.resTypeId " +
                        "GROUP BY tbl_restaurants.restId";
                pw.write(sqlString);
                pw.flush();
                socket.shutdownOutput();

                //get response from server

                InputStream is = socket.getInputStream();
                InputStreamReader isr =new InputStreamReader(is);
                BufferedReader br =new BufferedReader(isr);
                String info = "";
                String line = null;
                while((line=br.readLine())!=null){
                    info = info + line;
                }

                if(!info.equals("")&&!info.isEmpty()){


                    nearList=  ParserJson.parserJsonNeayBy(info,get_latititude,get_longtitute);

                    Message msg =new Message();
                    msg.obj="update";
                    msg.what=1;
                    handler.sendMessage(msg);

                }
            }catch (Exception e){

            }finally {
                if(socket!=null){
                    try {
                        socket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    socket.shutdownInput();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
