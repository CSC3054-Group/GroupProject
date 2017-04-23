package com.groupwork.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.groupwork.R;
import com.groupwork.RestaurantDetails;
import com.groupwork.activity.IntentTest;
import com.groupwork.activity.MapActivity;
import com.groupwork.adapter.NearbyListAdapter;
import com.groupwork.asyncTask.DownloadAsyncTask;
import com.groupwork.asyncTask.NewAsynTask;
import com.groupwork.bean.NearbyItem;
import com.groupwork.bean.ResDetails;
import com.groupwork.bean.ResType;
import com.groupwork.bean.Restaurants;
import com.groupwork.bean.Reviews;
import com.groupwork.customView.SearchView;
import com.groupwork.urlContrans.UrlConfig;
import com.groupwork.utils.ParserJson;
import com.groupwork.utils.PointDistance;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.groupwork.activity.MapActivity.LOCATION_UPDATE_MIN_DISTANCE;
import static com.groupwork.activity.MapActivity.LOCATION_UPDATE_MIN_TIME;

/**
 * Created by admin on 2017/4/2.
 */

public class NeabyFragment extends Fragment {
    private ListView listView;
    private List<NearbyItem> list;
    private NearbyListAdapter listAdapter;
    private TextView map_text;
    private android.widget.SearchView searchView;
    private static double current_latitude;
    private static double current_altitude;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.obj.toString().equals("update")){
                listAdapter.refresh(list);
                listAdapter.notifyDataSetChanged();
                Log.d("HiHi","update");

            }
            return true;
        }
    });
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            current_latitude = location.getLatitude();
            current_altitude = location.getLongitude();
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
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neaby,container,false);
        listView = (ListView) view.findViewById(R.id.listview_nearby);
        searchView = (android.widget.SearchView) view.findViewById(R.id.searchView_nearby);
        map_text = (TextView) view.findViewById(R.id.maptext_nearby);

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(list.size()>0&&newText.length()>0){

                    ArrayList<NearbyItem> resName= new ArrayList<>();
                    Log.d("HiHi",list.size()+"");
                    for(int i=0;i<list.size();i++){
                        int index=list.get(i).getResName().indexOf(newText);
                        if(index!=-1){
                            resName.add(list.get(i));
                        }
                    }

                    listAdapter.refresh(resName);
                    listAdapter.notifyDataSetChanged();
                }
                else if(newText.equals("")&&list.size()==0){
                    new Thread(new RestaurantsThread()).start();
                }
                else if(newText.equals("")&&list.size()>0){
                  //  listAdapter.refresh(list);
                 //   listAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                  NearbyItem near = (NearbyItem) listAdapter.getItem(position);

                Intent intent =new Intent(getActivity(), RestaurantDetails.class);
                intent.putExtra("resId",near.getResId());
                startActivity(intent);
            }
        });
        map_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), MapActivity.class);
                        startActivity(intent);


            }
        });
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list =new ArrayList<>();
        listAdapter = new NearbyListAdapter(list,getActivity());
        listView.setAdapter(listAdapter);
        getCurrentLocation();
        new Thread(new RestaurantsThread()).start();

    }

        private void getCurrentLocation(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location = null;
        if (!(isGPSEnable || isNetworkEnable)) {
            Toast.makeText(getActivity(),"GPS and Network can't use",Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkEnable) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, locationListener);
                location  =locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if(isGPSEnable){
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, locationListener);
                location  =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if(location!=null){
            current_latitude = location.getLatitude();
            current_altitude = location.getLongitude();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

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

                    Log.d("Test","number");
                    list=  ParserJson.parserJsonNeayBy(info,current_latitude,current_altitude);
                    Collections.sort(list, new Comparator<NearbyItem>() {
                        @Override
                        public int compare(NearbyItem o1, NearbyItem o2) {
                            if(o1.getDuration()-o2.getDuration()>0){
                                return 1;
                            }else{
                                return -1;
                            }

                        }
                    });
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
