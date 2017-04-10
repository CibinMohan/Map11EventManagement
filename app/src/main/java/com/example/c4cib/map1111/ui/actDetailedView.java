package com.example.c4cib.map1111.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c4cib.map1111.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by c4cib on 18/11/2016.
 */

public class actDetailedView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat1= 33;
    double lat2=33;
    String eventDetails;
    TextView title;
    TextView details;
    TextView loc;
    TextView date;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detailed_view);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_detailedView, fragment);
        fragmentTransaction.commit();
        title = (TextView) findViewById(R.id.text_D_Title);
        details = (TextView) findViewById(R.id.text_D_Details);
        loc= (TextView) findViewById(R.id.text_D_Loc);
        date= (TextView) findViewById(R.id.text_D_Date);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        String recData = intent.getStringExtra("data");
        String[] splVal = recData.split(",");
        if(splVal.length>4) {
            title.setText(splVal[0]);
            details.setText(splVal[1]);
            loc.setText(splVal[3]);
            date.setText(splVal[2]);
            double lat1 = Double.parseDouble(splVal[4]);
            double lat2 = Double.parseDouble(splVal[5]);

            LatLng disp = new LatLng(lat1, lat2);
            mMap.addMarker(new MarkerOptions().position(disp).title(splVal[0]));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(disp));
            mMap.setMinZoomPreference(16.0f);
        }
    }


    public void addloc()
    {

        LatLng disp = new LatLng(lat1,lat2);
        mMap.addMarker(new MarkerOptions().position(disp).title(eventDetails));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(disp));
        mMap.setMinZoomPreference(10.0f);

    }
}
