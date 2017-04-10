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

public class actLogin extends Activity {
        public static Context appcontext;

        userDTO dto = new userDTO();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            runeDB();

        }
    @Override
    public void onSaveInstanceState(Bundle state)
    {

        final EditText name = (EditText) findViewById(R.id.edit_name);
        String input = name.getText().toString();
        state.putString("name", input);
        super.onSaveInstanceState(state);
    }
    private void runeDB()
    {
        final EditText name = (EditText) findViewById(R.id.edit_name);

        final EditText pass = (EditText) findViewById(R.id.edit_pass);

        final TextView textView = (TextView) findViewById((R.id.link));

        final TextView fpass = (TextView) findViewById((R.id.fpas));

        Button login =(Button) findViewById(R.id.but_login);


        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(actLogin.this, actRegister.class);
                startActivity(intent);
            }

        });


        fpass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(actLogin.this, actForpas.class);
                startActivity(intent);
            }

        });
        try {

            appcontext = this;
            final database db = new database(actLogin.appcontext);


            login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    userDTO dto = new userDTO();
                    JavaUtility utility = new JavaUtility();
                    try {
                        if (name.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actLogin.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Name!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else if (!utility.verifyName(name.getText().toString())) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actLogin.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Valid name");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else if (pass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actLogin.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }
                        else
                        {
                            dto = db.getPin(name.getText().toString());
                            if (pass.getText().toString().equals(dto.getRecPassword())) {
                                Intent intent = new Intent(actLogin.this, actHome.class);
                                String userName = name.getText().toString();
                                intent.putExtra("uname", userName);
                                Log.i("true", dto.getRecPassword());
                                startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actLogin.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                                alertDialogBuilder.setMessage("Wrong Username or Password!");// 2. Chain together various setter methods to set the dialog characteristics
                                AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                                dialog.show();
                            }
                        }
                    }catch (Exception e){}
                }
            });
        }
        catch (Exception e){}
    }

}
