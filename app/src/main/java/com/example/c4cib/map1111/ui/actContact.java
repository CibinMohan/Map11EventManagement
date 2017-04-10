package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c4cib.map1111.R;

/**
 * Created by c4cib on 18/11/2016.
 */

public class actContact extends Activity {
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    String userName = "";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contact);

        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");

        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_contact, fragment);
        fragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putString("UN",userName);
        fragment.setArguments(bundle);
        final Button nwButton = (Button) findViewById(R.id.addContact);

        nwButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actContact.this, actAddAddress.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }
        });
        final Button btnView = (Button) findViewById(R.id.viewContact);

        btnView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actContact.this, actViewContact.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("uname",userName);
        setResult(RESULT_OK, intent);
    }
    }
