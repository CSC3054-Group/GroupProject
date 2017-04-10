package com.groupwork.utils;

import android.util.Log;

import com.groupwork.bean.NearbyItem;
import com.groupwork.bean.ResDetails;
import com.groupwork.bean.ResType;
import com.groupwork.bean.Restaurants;
import com.groupwork.bean.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/1.
 */

public class ParserJson {
    public static List<NearbyItem> parserJsonNeayBy(String jsonString,double current_latitude,double current_altitude){
        List<NearbyItem> resList = new ArrayList<>();
        try {
            JSONArray array =new JSONArray(jsonString);
            PointDistance distance=new PointDistance();

            double latitude=0;
            double altitude=0;
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                int restId= object.getInt("restId");
                String resName =object.getString("resName");
                String resType = object.getString("resType");
                float rating = (float)object.getDouble("AVG(revStarRating)");
                String resLocation = object.getString("resLocation");
                String resPhoto =object.getString("resPhoto");
                for(int j=0;j<resLocation.length();j++){
                    if(resLocation.substring(j,j+1).equals(",")){

                        latitude=Double.valueOf(resLocation.substring(0,j));
                        altitude = Double.valueOf(resLocation.substring(j+1,resLocation.length()));

                        break;
                    }
                }
                double s=distance.getDistance(current_latitude,current_altitude,latitude,altitude);
                NearbyItem near = new NearbyItem(restId,resLocation,resPhoto,resName,resType,rating,null,s);
                resList.add(near);
                Log.d("Hee",resName+": "+s);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resList;
    }

    public static String parserJsonPhoto(String jsonString){
        String reference="";
        try {
            JSONObject jsonObject= new JSONObject(jsonString);
            JSONArray array = jsonObject.getJSONArray("result");
            JSONObject array_object =array.getJSONObject(0);
            JSONArray photo_array = array_object.getJSONArray("photos");
            JSONObject photo_details = photo_array.getJSONObject(0);
            reference = photo_details.getString("photo_reference");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reference;
    }

}
