package com.example.c4cib.map1111.ui;

/**
 * Created by c4cib on 17/11/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c4cib.map1111.R;

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


public class actViewAll extends Activity {

    ListView lv;
    Context context;
    ArrayList prgmName;
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

    //public static String[] listTitle = new String[100];
    ArrayList<String> listTitle = new ArrayList<>(150);

    ArrayList<String> listDate = new ArrayList<>(150);


    ArrayList<String> viewData = new ArrayList<>(150);


    ArrayList<String> listLoc = new ArrayList<>(150);
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventlist);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_list, fragment);
        fragmentTransaction.commit();

        fileName = "toall";
        initializeUI();
        context=this;
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new customAdapter(this, listTitle,listDate,listLoc,viewData));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initializeUI()
    {

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
                                if(!listTitle.contains(cityAU[0]))
                                {
                                    listTitle.add(cityAU[0]);
                                    listDate.add(cityAU[2]);
                                    listLoc.add(cityAU[3]);
                                    viewData.add(receiveString);

                                    Log.i("ssssss", cityAU[0]);
                                }
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


}
