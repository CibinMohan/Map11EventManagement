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

public class actUserManage extends Activity {
    public static Context appcontext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        runeDB();
    }
    private void runeDB()
    {
        final EditText name = (EditText) findViewById(R.id.edit_name_A);

        final EditText pass = (EditText) findViewById(R.id.edit_pass_c);

        final EditText newPass = (EditText) findViewById(R.id.edit_newpass_c);

        final EditText comPass = (EditText) findViewById(R.id.edit_compass_c);

        Button change =(Button) findViewById(R.id.changebt);

        try {
            appcontext = this;
            final database db = new database(actUserManage.appcontext);
            final JavaUtility utility = new JavaUtility();

            change.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    userDTO dto = new userDTO();

                        if (name.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Name!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        } else if (!utility.verifyName(name.getText().toString())) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Valid name \n Valid name contains only Character");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();}


                        else if (pass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter password");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();}


                        else if (newPass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }

                        else if (comPass.getText().toString().equalsIgnoreCase("")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Enter Confirmation Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }


                        else if(!newPass.getText().toString().equals(comPass.getText().toString()))
                        {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                            alertDialogBuilder.setMessage("Wrong New Password!");// 2. Chain together various setter methods to set the dialog characteristics
                            AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                            dialog.show();
                        }

                       else{
                            userDTO d1 =new userDTO();
                            dto.setUserName(name.getText().toString());
                            dto.setPassword(pass.getText().toString());
                            dto.setEmail("g@g.com");
                            dto = db.getPin(name.getText().toString());
                                if(pass.getText().toString().equals(dto.getRecPassword()))
                                 {
                                     Log.i("IM in ","ed");
                                     dto.setUserName(name.getText().toString());
                                    dto.setPassword(newPass.getText().toString());
                                    db.updatePin(dto);
                                    Intent intent = new Intent(actUserManage.this, actLogin.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(actUserManage.this);// 1. Instantiate an AlertDialog.Builder with its constructor
                                    alertDialogBuilder.setMessage("No user found!");// 2. Chain together various setter methods to set the dialog characteristics
                                    AlertDialog dialog = alertDialogBuilder.create();// 3. Get the AlertDialog from create()
                                    dialog.show();
                                }
                            }
                    //catch (Exception e){}

                    //  db.getPin("name1");
                    //Log.i("passss", dto.getRecPassword());
                }
            });
        }
        catch (Exception e){}
    }

}
