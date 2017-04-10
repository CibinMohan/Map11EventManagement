package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.c4cib.map1111.R;
import com.example.c4cib.map1111.Utility.JavaUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by c4cib on 16/11/2016.
 */

public class actCreate extends Activity {
    public float screen_width = 1;
    String[]cityAU = null;
    LinkedHashMap<String,String> cityDataMap = new LinkedHashMap<String, String>();
    List<String> cityList = new ArrayList<String>();
    String imageSelected;
    EditText eventTitle;
    EditText details;
    Spinner spinner;
    DatePicker datePicker;
    ArrayList<String> email = new ArrayList<String>();
    String strEventTitle;
    String strDetails;

    String myDataFromActivity;
    final static String DOUBLE_PATTERN = "[0-9]+(/.){0,1}[0-9]*";
    static final int READ_BLOCK_SIZE = 500;
    String readData = "";
    String fileName;
    List<String> places = new ArrayList<String>();
    LinkedHashMap<String, String> locNew = new LinkedHashMap<String, String>();
    String[] dataLoc =null ;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    String userName = null;
    boolean status = false;
    boolean cretionStatus = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.eve_frag, fragment);
        fragmentTransaction.commit();
        final Button but_addEve = (Button) findViewById(R.id.addtofile);
        userDTO dto = new userDTO();
        dto.setUserName("cibinee");
        fileName = dto.getUserName();
        initializeUI();
        final Button but_addShare = (Button) findViewById(R.id.addshare);
        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");
        Log.i("CreatrUN",userName);
        final CheckBox satView = (CheckBox)findViewById(R.id.checkBox);
        final TableLayout element =(TableLayout) findViewById(R.id.tablehide);

        final Button but_mail = (Button) findViewById(R.id.mail);

        satView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(satView.isChecked()){
                    element.setVisibility(View.VISIBLE);
                    status = true;
                }else{
                    element.setVisibility(View.GONE);
                    status = false;
                }
            }
        });
        but_addEve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName =userName;
                WriteBtn(view); //method call for write
                fileName ="toall";
                WriteBtn(view);
                if(cretionStatus) {
                    but_addShare.setVisibility(View.VISIBLE);
                    but_mail.setVisibility(View.VISIBLE);
                    but_addEve.setVisibility(View.GONE);
                }
            }
        });

        but_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fileName = "address"+userName;
                readEmaiAndLoad();
                creationStaCre();
                if(!cretionStatus) {
                    for (String a : email) {
                        new AsyncMail().execute(a, myDataFromActivity);
                    }
                    Intent intent = new Intent(actCreate.this, actHome.class);
                    intent.putExtra("uname", userName);
                    startActivity(intent);
                }


            }
        });

        but_addShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creationStaCre();
                if(!cretionStatus) {

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DATE");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, myDataFromActivity);
                    startActivity(Intent.createChooser(shareIntent, "Share vis"));
                }


            }
        });

    }

    private void creationStaCre()
    {
        cretionStatus = false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        intent.putExtra("uname",userName);
        setResult(RESULT_OK, intent);
    }


    private void initializeUI() {
        eventTitle = (EditText) findViewById(R.id.edit_title);
        details = (EditText) findViewById(R.id.edit_details);
        spinner= (Spinner) findViewById(R.id.spinner);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        addFile();


    }
    // write text to file   ........"\r\n"
    public void WriteBtn(View v) {
        // add-write text into file

        String existingData = "";
        existingData = ReadData();
        Log.i("EXISTING DATA :", existingData);
        JavaUtility utility = new JavaUtility();
        strEventTitle = eventTitle.getText().toString().trim();
        strDetails = details.getText().toString().trim();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String newDate = ""+day+"/"+month+"/"+year;
        String text = spinner.getSelectedItem().toString();
        String spl = locNew.get(text);

        if(eventTitle.getText().toString().equalsIgnoreCase(""))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
            alertDialogBuilder.setMessage("Enter Title!");// 2. Chain together various setter methods to set the dialog characteristics
            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
            dialog.show();
        }else
        if(details.getText().toString().equalsIgnoreCase(""))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
            alertDialogBuilder.setMessage("Enter event details!");// 2. Chain together various setter methods to set the dialog characteristics
            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

            dialog.show();
        }
        else
        {
            if(status)
            {
                EditText custLoc =(EditText) findViewById(R.id.edit_loc);
                EditText custLat =(EditText) findViewById(R.id.edit_lat);
                EditText custLong =(EditText) findViewById(R.id.edit_long);
                if(custLoc.getText().toString().equalsIgnoreCase(""))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Enter custom location!");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

                    dialog.show();
                }else
                if(custLat.getText().toString().equalsIgnoreCase(""))
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
                alertDialogBuilder.setMessage("Enter Latitude!");// 2. Chain together various setter methods to set the dialog characteristics
                AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

                dialog.show();
            }else
                if(!utility.isDouble(custLat.getText().toString()))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Latitude not valid!");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

                    dialog.show();
                }else
                if(custLong.getText().toString().equalsIgnoreCase(""))
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
                alertDialogBuilder.setMessage("Enter Longitude!");// 2. Chain together various setter methods to set the dialog characteristics
                AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

                dialog.show();
            }else
                if(!utility.isDouble(custLong.getText().toString()))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Latitude not valid!");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()

                    dialog.show();
                }
                else {
                    String strcustLocation = custLoc.getText().toString();
                    String strcustLat = custLat.getText().toString();
                    String strcustLong = custLong.getText().toString();
                    String place = "Unknown";
                    readData = existingData + "\r\n" + strEventTitle + "," + strDetails + "," + newDate + ","+strcustLocation+","+strcustLat+","+strcustLong+"," +place;
                    myDataFromActivity = "NEW EVENT\n" + strEventTitle + ":" + strDetails + ",\nDate -" + newDate + "\nLocation -" +strcustLocation+","+strcustLat+","+strcustLong;
                    addRead();
                    cretionStatus =true;

                }
            }
            else {
                readData = existingData + "\r\n" + strEventTitle + "," + strDetails + "," + newDate + "," + text + "," + spl;
                myDataFromActivity = "NEW EVENT\n" + strEventTitle + ":" + strDetails + ",\nDate -" + newDate + "\nLocation -" + text + ":" + spl;

                addRead();

                cretionStatus =true;
            }
            Log.i("Final STRING Write :", readData);


        }
    }

    private void addRead()
    {
        try {
            FileOutputStream fileout = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(readData.trim());
            outputWriter.close();

            Toast.makeText(getBaseContext(), "Saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public String ReadData() {

        String ret = "";
        Boolean fileExist = isFilePresent(fileName);
        Log.i("isFilePresent :", fileExist.toString());

        Context context = getApplicationContext();
        if (fileExist) {
            try {

                InputStream inputStream = context.openFileInput(fileName);

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        Log.i("TEXT LINE:", receiveString);
                        stringBuilder.append(receiveString + "\r\n");
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();

                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }

        }
        return ret;

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
                    new InputStreamReader(getAssets().open("Localloc.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            try {
                Intent intent = getIntent();
                String imagedesc = intent.getStringExtra("data");
                Log.i("++++++++++++++name", imagedesc);
                String[] dataLocAdded = imagedesc.split(",");
                String newStadded = dataLocAdded[1] + "," + dataLocAdded[2]+"," + dataLocAdded[3];
                Log.i("New Data", newStadded);
                locNew.put(dataLocAdded[0], newStadded);
                places.add(dataLocAdded[0]);
            }
            catch (Exception e)
            {}
            String mLine;
            while ((mLine = reader.readLine()) != null) {

                //Bundle extras = intent.getExtras();


                String[] dataLoc= mLine.split(",");
                String newSt = dataLoc[1]+"," +dataLoc[2]+"," +dataLoc[3];
                Log.i("datacere", newSt);
                locNew.put(dataLoc[0], newSt);
                places.add(dataLoc[0]);

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

    public void readEmaiAndLoad()
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
                                    email.add(cityAU[1]);
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


}