package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c4cib.map1111.R;
import com.example.c4cib.map1111.Utility.JavaUtility;

/**
 * Created by c4cib on 22/11/2016.
 */

public class actForpas extends Activity {
    public static Context appcontext;

    userDTO dto = new userDTO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forpass);
        runeDB();

    }
    private void runeDB()
    {
        final EditText name = (EditText) findViewById(R.id.edit_name_f);



        Button fpas =(Button) findViewById(R.id.but_fpas);

        try {

            appcontext = this;
            final database db = new database(actForpas.appcontext);


            fpas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    userDTO dto = new userDTO();
                    JavaUtility utility = new JavaUtility();
                    try {
                        if (name.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actForpas.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Name!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else if (!utility.verifyName(name.getText().toString())) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actForpas.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Valid name");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else {
                            dto = db.getEmail(name.getText().toString());
                            if (dto.getEmail().equalsIgnoreCase("") || dto.getPassword().equalsIgnoreCase("")) {

                                Intent intent = new Intent(actForpas.this, actLogin.class);
                                String userName = name.getText().toString();
                                intent.putExtra("uname", userName);
                                Log.i("true", dto.getRecPassword());
                                startActivity(intent);

                            } else {
                                new AsyncMail().execute(dto.getEmail(), dto.getPassword());

                                Intent intent = new Intent(actForpas.this, actLogin.class);
                                String userName = name.getText().toString();
                                intent.putExtra("uname", userName);
                                Log.i("true", dto.getRecPassword());
                                startActivity(intent);
                            }

                        }
                    }catch (Exception e){}

                    Intent intent = new Intent(actForpas.this, actLogin.class);

                    startActivity(intent);
                }
            });
        }
        catch (Exception e){}
    }

}

