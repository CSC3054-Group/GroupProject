package com.groupwork.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.groupwork.R;
import com.groupwork.adapter.NearbyListAdapter;
import com.groupwork.asyncTask.DownloadAsyncTask;
import com.groupwork.bean.NearbyItem;
import com.groupwork.urlContrans.UrlConfig;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by admin on 2017/4/2.
 */

public class NeabyFragment extends Fragment {
    private ListView listView;
    private ArrayList<NearbyItem> list;
    private NearbyListAdapter listAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neaby,container,false);
        listView = (ListView) view.findViewById(R.id.listview_nearby);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list =new ArrayList<>();
        listAdapter = new NearbyListAdapter(list,getActivity());
        listView.setAdapter(listAdapter);

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
                socket = new Socket(UrlConfig.Socket_IP, UrlConfig.Socket_PORT);
                //Output data to Server
                OutputStream out = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(out);
                //Input sql sentence which you want to execute
                String sqlString = "select * from tbl_restaurants";
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
