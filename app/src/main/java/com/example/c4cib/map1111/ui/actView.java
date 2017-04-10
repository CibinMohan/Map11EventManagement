package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c4cib.map1111.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by c4cib on 16/11/2016.
 */

public class actView extends Activity {

    LinkedHashMap<String,String> cityDataMap = new LinkedHashMap<String, String>();
    List<String> cityList = new ArrayList<String>();
    String[]cityAU = null;
    Spinner spinnerCities = null;
    Double latitude =null;
    Double longitude =null;
    int year =0;
    int month =0;
    int day =0;
    Button addbutton;
    String fileName;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    String userName =null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_view, fragment);
        fragmentTransaction.commit();
        cityList.add("Select Event");
        cityDataMap.put("Select Event",".,");
        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");
        fileName = userName;
        initializeUI();
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
                            if(!cityList.contains(cityAU[0])) {
                                cityList.add(cityAU[0]);
                                cityDataMap.put(cityAU[0], receiveString);
                            }
                            Log.i("NEWEVENT:",cityDataMap.toString());
                        }

                        inputStream.close();


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

            //Collections.sort(cityList, String.CASE_INSENSITIVE_ORDER);
            spinnerCities= (Spinner) findViewById(R.id.spinner3);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCities.setAdapter(dataAdapter);
            final TextView newTitle = (TextView) findViewById(R.id.tv_title);
            final TextView newDetails = (TextView) findViewById(R.id.tv_details);
            final TextView newLocation = (TextView) findViewById(R.id.tv_location);
            final TextView newDate = (TextView) findViewById(R.id.tv_date);
            final TextView newCoordinate = (TextView) findViewById(R.id.tv_coordinate);

            spinnerCities.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(
                                AdapterView<?> parent, View view, int position, long id) {
                            String sText = spinnerCities.getSelectedItem().toString();
                            Log.i("newString", sText);
                            String data = null;
                            for(String a :cityList)
                            {
                                Log.i("newStringrecOOOOa", a);
                                if(cityList.contains(sText)) {
                                    data = cityDataMap.get(sText);
                                }

                            }
                                String[] eventDetails = data.split(",");
                                String dre = "" + eventDetails.length;
                                Log.i("sasasas", dre);
                                if (eventDetails.length > 4) {
                                    newTitle.setText(eventDetails[0]);
                                    newDetails.setText(eventDetails[1]);
                                    newLocation.setText(eventDetails[2]);
                                    newDate.setText(eventDetails[3]);
                                    newCoordinate.setText(eventDetails[4]);
                                }
                                updateTime();
                            }


                        public void onNothingSelected(AdapterView<?> parent)
                        {

                        }
                    });



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

    private void initializeUI()
    {

        readCitiesAndLoad(); // setup initial values and reg. handler
        updateTime();
    }

    private void updateTime()
    {
        Spinner spinnerCities = (Spinner) findViewById(R.id.spinner3);
        String cityLoc =spinnerCities.getSelectedItem()+"".trim();
        Log.i("cityDataMap Size :",cityDataMap.size()+"");
        Log.i("cityDataMap Value :",cityDataMap.toString());

        String selectedData = cityDataMap.get(cityLoc);

    }

    public boolean isFilePresent(String fileName) {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }



}