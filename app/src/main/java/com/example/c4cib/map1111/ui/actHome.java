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
import com.example.c4cib.map1111.Session.SessionUse;

/**
 * Created by c4cib on 15/11/2016.
 */

    public class actHome extends Activity {
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    public String userName=null;
    SessionUse session;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            userName= savedInstanceState.getString("ses");
        }

        fragAct fragment = new fragAct();
        fragmentTransaction.add(R.id.frag_h, fragment);
        fragmentTransaction.commit();
        session = new SessionUse(actHome.this);
        Intent intent = getIntent();
        userName= intent.getStringExtra("uname");
        session.setusename(userName);
        userName = session.getusename();

        Log.i("usernaem..." ,userName);

        final Button nwButton = (Button)findViewById(R.id.Create);

        nwButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actHome.this, actCreate.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }});
        final Button btnView = (Button)findViewById(R.id.View);

        btnView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actHome.this, actView.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }});
        final Button btnMap = (Button)findViewById(R.id.Map);

        btnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actHome.this, MapsActivity.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }});
        final Button btnViewAll = (Button)findViewById(R.id.ViewAll);

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actHome.this, actViewAll.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }});
        final Button btnContact = (Button)findViewById(R.id.Contact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(actHome.this, actContact.class);
                intent.putExtra("uname",userName);
                startActivity(intent);
            }});


    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        if(userName!=null)
            savedInstanceState.putString("ses",userName);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


}



