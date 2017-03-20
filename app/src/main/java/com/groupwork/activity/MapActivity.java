package com.groupwork.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
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

/**
 * Created by admin on 2017/3/1.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    private MapFragment map_Fragemnt;
    private GoogleMap mMap;
    private double get_latititude;
    private double get_longtitute;
    final static int MY_LOCATION_REQUEST_CODE = 456;



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
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

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

        // add Marker
        final Marker res_Marker = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(54.58013166666667, -5.940546666666667)));
        final Marker res_Marker1 = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(54.58013166666667, -7.940546666666667)));
        final Marker res_Marker2 = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(56.58013166666667, -8.940546666666667)));
        final Marker res_Marker3 = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(53.58013166666667, -4.940546666666667)));
        final Marker res_Marker4 = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(52.58013166666667, -5.740546666666667)));
        final Marker res_Marker5 = googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.res_icon_normal))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(50.58013166666667, -5.840546666666667)));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("Marker_position","marker_p="+marker.getPosition().toString());
                Log.d("Marker_position","Id="+marker.getId());
                Log.d("Marker_position","resmarker_p="+res_Marker.getPosition().toString());
                Log.d("Marker_position","Id="+res_Marker.getId());
                if(marker.getId().equals(res_Marker.getId())){
                    res_Marker.setIcon(BitmapDescriptorFactory.
                            fromResource(R.drawable.res_icon_click));
                    Toast.makeText(MapActivity.this,"click me ",Toast.LENGTH_SHORT).show();
                }



                return false;
            }
        });

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
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

}
