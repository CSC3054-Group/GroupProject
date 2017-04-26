package com.groupwork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.groupwork.R;
import com.groupwork.activity.RestaurantDetails;
import com.groupwork.adapter.NearbyListAdapter;
import com.groupwork.adapter.SavedListAdapter;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */

public class FragmentTwo extends Fragment {
    private ListView listView;
    private List<NearbyItem> list;
    private SavedListAdapter listAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment2,container,false);
        listView = (ListView) view.findViewById(R.id.list_savedRes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NearbyItem near = (NearbyItem) listAdapter.getItem(position);

                Intent intent =new Intent(getActivity(), RestaurantDetails.class);
                intent.putExtra("resId",near.getResId());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        listAdapter = new SavedListAdapter(list,getActivity());
        listView.setAdapter(listAdapter);

        new Thread(new RestaurantsThread()).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.obj.toString().equals("update")){

                Log.d("HiHi","update");
                listAdapter.refresh(list);
                listAdapter.notifyDataSetChanged();
            }
            return true;
        }
    });

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
                        "AVG(revStarRating),resType,resPhoto from tbl_restaurants,tbl_restype,tbl_reviews,tbl_usersaved " +
                        "where tbl_restaurants.restId=tbl_reviews.restId " +
                        "and tbl_restaurants.resTypeId=tbl_restype.resTypeId " +
                        "and tbl_usersaved.UserID="+UrlConfig.userid+" " +
                        "and tbl_usersaved.restId=tbl_restaurants.restId " +
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

                    list=  ParserJson.parserJsonNeayBy(info,0,0);
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
