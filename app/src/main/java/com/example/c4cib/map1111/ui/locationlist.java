package com.example.c4cib.map1111.ui;

/**
 * Created by c4cib on 21/10/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.c4cib.map1111.R;

public class locationlist extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listloc);
        setfile();
    }
    public void setfile()
    {
        Intent intent = getIntent();
        String imagedesc = intent.getStringExtra("Data");
        String[] newLocs = imagedesc.split(",");

        final TextView D1= (TextView) findViewById(R.id.DATE1);
        final TextView D2= (TextView) findViewById(R.id.DATE2);
        final TextView D3= (TextView) findViewById(R.id.DATE3);
        final TextView D4= (TextView) findViewById(R.id.DATE4);
        final TextView D5= (TextView) findViewById(R.id.DATE5);


        final TextView SR1= (TextView) findViewById(R.id.SR1);
        final TextView SR2= (TextView) findViewById(R.id.SR2);
        final TextView SR3= (TextView) findViewById(R.id.SR3);
        final TextView SR4= (TextView) findViewById(R.id.SR4);
        final TextView SR5= (TextView) findViewById(R.id.SR5);

        final TextView SS1= (TextView) findViewById(R.id.SS1);
        final TextView SS2= (TextView) findViewById(R.id.SS2);
        final TextView SS3= (TextView) findViewById(R.id.SS3);
        final TextView SS4= (TextView) findViewById(R.id.SS4);
        final TextView SS5= (TextView) findViewById(R.id.SS5);

        D1.setText(newLocs[0]);
        SR1.setText(newLocs[1]);
        SS1.setText(newLocs[2]);

        D2.setText(newLocs[3]);
        SR2.setText(newLocs[4]);
        SS2.setText(newLocs[5]);

        D3.setText(newLocs[6]);
        SR3.setText(newLocs[7]);
        SS3.setText(newLocs[8]);

        D4.setText(newLocs[9]);
        SR4.setText(newLocs[10]);
        SS4.setText(newLocs[11]);

        D5.setText(newLocs[12]);
        SR5.setText(newLocs[13]);
        SS5.setText(newLocs[14]);

    }

}

