package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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

/**
 * Created by c4cib on 18/11/2016.
 */

public class actViewContact extends Activity {

    LinkedHashMap<String,String> cityDataMap = new LinkedHashMap<String, String>();
    List<String> cityList = new ArrayList<String>();
    String[]cityAU = null;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    String fileName;
    TableLayout stk;
    String userName = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_viewContact, fragment);
        fragmentTransaction.commit();
        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");
        stk = (TableLayout) findViewById(R.id.tableContact);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("NAME");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" EMAIL");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        fileName = "address" +userName;
        initializeUI();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.putExtra("uname",userName);
        setResult(RESULT_OK, intent);
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
                                if(cityAU.length>0)
                                {
                                    init(cityAU[0],cityAU[1]);
                                }

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
                cityDataMap.put("Melbourne","-37.81" +
                        "4");
            }




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

    public void init(String name, String email) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(name);
            t1v.setTextColor(Color.DKGRAY);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(email);
            t2v.setTextColor(Color.DKGRAY);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            stk.addView(tbrow);


    }
    private void initializeUI()
    {

        readCitiesAndLoad(); // setup initial values and reg. handler
    }


    public boolean isFilePresent(String fileName) {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

}
