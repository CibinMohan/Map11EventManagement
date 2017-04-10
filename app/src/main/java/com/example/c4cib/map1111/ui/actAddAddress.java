package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by c4cib on 18/11/2016.
 */

public class actAddAddress extends Activity{

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    EditText name;
    String fileName;
    EditText email;
    String readData;
    String userName =null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_address, fragment);
        fragmentTransaction.commit();
        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");
        final Button but_add =(Button) findViewById(R.id.addContactFile);
        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (EditText) findViewById(R.id.edit_A_name);
                email = (EditText) findViewById(R.id.edit_A_email);
                fileName= "address"+userName;
                JavaUtility utility = new JavaUtility();

                if (name.getText().toString().equalsIgnoreCase("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actAddAddress.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Enter Name");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                    dialog.show();}

                else if (!utility.verifyName(name.getText().toString())) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actAddAddress.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Not a valid name\n Only provie characters");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                    dialog.show();}


                if (email.getText().toString().equalsIgnoreCase("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actAddAddress.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Enter Email");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                    dialog.show();}

                else if (!utility.isValidEmailAddress(email.getText().toString())) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actAddAddress.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                    alertDialogBuilder.setMessage("Enter Valid email");// 2. Chain together various setter methods to set the dialog characteristics
                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                    dialog.show();}
                else {
                    WriteBtn(view);
                    Intent intent = new Intent(actAddAddress.this, actContact.class);
                    intent.putExtra("uname", userName);
                    startActivity(intent);
                }

            }
        });


    }






    public void WriteBtn(View v) {
        // add-write text into file

        String existingData = "";
        existingData = ReadData();
        Log.i("EXISTING DATA :", existingData);

        String strName = name.getText().toString().trim();
        String strEmail = email.getText().toString().trim();

        if(name.getText().toString().equalsIgnoreCase(""))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
            alertDialogBuilder.setMessage("Enter Name!");// 2. Chain together various setter methods to set the dialog characteristics
            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
            dialog.show();
        }else
        if(email.getText().toString().equalsIgnoreCase(""))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);// 1. Instantiate an AlertDialog.Builder with its constructor
            alertDialogBuilder.setMessage("Enter email!");// 2. Chain together various setter methods to set the dialog characteristics
            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
            dialog.show();
        }
        else
        {
            readData = existingData + "\r\n" + strName + "," + strEmail ;
//            myDataFromActivity = "NEW EVENT\n"+strEventTitle + ":" + strDetails +",\nDate -" +newDate +"\nLocation -"+text+":"+spl;

            Log.i("Final STRING Write :", readData);

            try {
                FileOutputStream fileout = openFileOutput(fileName, MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(readData.trim());
                outputWriter.close();

                Toast.makeText(getBaseContext(), "Saved successfully!",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(actAddAddress.this, actContact.class);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("uname",userName);
        setResult(RESULT_OK, intent);
    }

}
