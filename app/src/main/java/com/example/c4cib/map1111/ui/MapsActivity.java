package com.example.c4cib.map1111.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.c4cib.map1111.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ListView lv;
    Context context;
    ArrayList prgmName;
    List<String> places = new ArrayList<String>();
    LinkedHashMap<String, String> locNew = new LinkedHashMap<String, String>();
    Spinner spinner;
    double lat1= 33;
    double lat2=33;
    String eventDetails;
    LinkedHashMap<String,String> cityDataMap = new LinkedHashMap<String, String>();
    List<String> cityList = new ArrayList<String>();
    String[]cityAU = null;
    String fileName = "toall";
    ArrayList<String> listData = new ArrayList<>(150);
    String userName= null;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        spinner = (Spinner) findViewById(R.id.spinner2);
        places.add("Select City");
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_map, fragment);
        fragmentTransaction.commit();

        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");

        initializeUI();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng disp = new LatLng(lat1,lat2);
        mMap.addMarker(new MarkerOptions().position(disp).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(disp));
    }


    public void addloc()
    {

        LatLng disp = new LatLng(lat1,lat2);
        mMap.addMarker(new MarkerOptions().position(disp).title(eventDetails));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(disp));
        mMap.setMinZoomPreference(10.0f);

    }
    private void initializeUI()
    {
        addFile();

        readCitiesAndLoad(); // setup initial values and reg. handler
    }


    public void readCitiesAndLoad()
    {
        Context context = getApplicationContext();
        AssetManager am = context.getAssets();
        BufferedReader reader = null;

        try {
            //new
            Boolean fileExist = isFilePresent(fileName);
            Log.i("isFilePresent :", fileExist.toString());
            if(fileExist) {
                try {

                    InputStream inputStream = context.openFileInput(fileName);

                    if ( inputStream != null ) {
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String receiveString = "";
                        StringBuilder stringBuilder = new StringBuilder();
                        while ( (receiveString = bufferedReader.readLine()) != null )
                        {
                            if(!receiveString.equals(""))
                                cityAU = receiveString.split(",");
                            if(!cityList.contains(cityAU[0]))
                                cityList.add(cityAU[0]);
                            cityDataMap.put(cityAU[0],receiveString);
                            Log.i("NEWEVENT:",cityDataMap.toString());
                            if(cityAU.length>3) {
                                Log.i("ssssss","ssssssss");
                                listData.add(receiveString);

                            }
                        }

                        inputStream.close();


                    }
                    else {
                        //listTitle[i] = "nodata";
                        //listDate[i] = "nodata";
                        //listLoc[i] = "nodata";

                    }
                }
                catch (FileNotFoundException e) {
                    Log.e("login activity", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("login activity", "Can not read file: " + e.toString());
                }

            }
            else
            {
                cityList.add("Melbourne");
                cityDataMap.put("Melbourne","-37.814");
            }

            Collections.sort(cityList, String.CASE_INSENSITIVE_ORDER);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public boolean isFilePresent(String fileName) {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }


    private void addFile()
    {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("places.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            try {
                Intent intent = getIntent();
                String imagedesc = intent.getStringExtra("data");
                Log.i("++++++++++++++name", imagedesc);
                String[] dataLocAdded = imagedesc.split(",");
                String newStadded = dataLocAdded[1] + "," + dataLocAdded[2];
                Log.i("New Data", newStadded);
                locNew.put(dataLocAdded[0], newStadded);
                places.add(dataLocAdded[0]);
            }
            catch (Exception e)
            {}
            String mLine;
            while ((mLine = reader.readLine()) != null) {

                //Bundle extras = intent.getExtras();


                //String[] dataLoc= mLine.split(",");
                //String newSt = dataLoc[1]+"," +dataLoc[2];
                //locNew.put(dataLoc[0], newSt);
                places.add(mLine);

// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, places);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(
                                    AdapterView<?> parent, View view, int position, long id) {
                                String text = spinner.getSelectedItem().toString();
                                eventDetails=":";
                                for (String temp : listData) {
                                    String[] splitTemp =temp.split(",");
                                    if(splitTemp.length>6)
                                    {
                                        Log.i("11111",temp);
                                    if(splitTemp[6].equals(text)) {
                                        lat1 = Double.parseDouble(splitTemp[4]);
                                        lat2 = Double.parseDouble(splitTemp[5]);
                                        eventDetails = splitTemp[0]+","+splitTemp[2]+" | \n";
                                        addloc();
                                    }
                                    }

                                }
                            }
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }


}
