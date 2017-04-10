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
 * Created by c4cib on 15/11/2016.
 */

public class actRegister extends Activity{
    public static Context appcontext;

    userDTO dto = new userDTO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        runeDB();
    }
    private void runeDB()
    {
        final EditText name = (EditText) findViewById(R.id.edit_name);

        final EditText pass = (EditText) findViewById(R.id.edit_pass);

        final EditText mail = (EditText) findViewById(R.id.edit_emael);

        final EditText newPass = (EditText) findViewById(R.id.edit_compass);

        Button register =(Button) findViewById(R.id.but_reg);

        try {

            appcontext = this;
            final database db = new database(actRegister.appcontext);
            final JavaUtility utility = new JavaUtility();

            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        if (name.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Name!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else if (!utility.verifyName(name.getText().toString())) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Valid name \n Valid name contains only Character");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();}


                        else if (mail.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Email");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();}

                        else if (!utility.isValidEmailAddress(mail.getText().toString())) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Valid email");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();}

                        else if (pass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }
                        else if (newPass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Confirmation Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }
                        else if(!newPass.getText().toString().equals(pass.getText().toString()))
                        {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actRegister.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Wrong Confirmation Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                            TextView t = (TextView) findViewById(R.id.textViewColor);
                            t.setBackgroundColor(0xfff00000);
                        }


                        else {
                            dto.setUserName(name.getText().toString());
                            dto.setPassword(pass.getText().toString());
                            dto.setEmail(mail.getText().toString());
                            db.addPin(dto);
                            Intent intent = new Intent(actRegister.this, actLogin.class);
                            startActivity(intent);

                        }
                    }
                    catch (Exception e){}

                    //  db.getPin("name1");
                    //Log.i("passss", dto.getRecPassword());
                }
            });
        }
        catch (Exception e){}
    }
}
