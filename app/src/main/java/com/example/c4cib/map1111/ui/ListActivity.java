package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.c4cib.map1111.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by c4cib on 24/10/2016.
 */

public class ListActivity extends Activity {
    int year=2016;
    int month=10;
    int startDate=5;
    int endDate=10;

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private static LayoutInflater inflater=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragAct fragment = new fragAct();
        View rowView;
        rowView = inflater.inflate(R.layout.customlist, null);
        final TextView text1=(TextView) rowView.findViewById(R.id.listData);

        fragmentTransaction.add(R.id.frag_list, fragment);
        fragmentTransaction.commit();






        setContentView(R.layout.listact);
        DatePicker dps = (DatePicker) findViewById(R.id.datePickerStart);
        DatePicker dpe = (DatePicker) findViewById(R.id.datePickerEnd);
        year = dps.getYear();
        month =dps.getMonth();
        startDate = dps.getDayOfMonth();
        endDate = dpe.getDayOfMonth();
        final Main main = new Main();

        final Button b1= (Button) findViewById(R.id.buttonfind);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                while(startDate<endDate) {
                    String locdetails = main.listUpdate(year,month,startDate);
                    String[] loc = locdetails.split(",");
                    text1.setText(locdetails);
                    startDate =startDate+10;
                }


            }
        });
    }

}
