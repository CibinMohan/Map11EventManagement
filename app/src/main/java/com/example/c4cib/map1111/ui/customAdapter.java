package com.example.c4cib.map1111.ui;

/**
 * Created by c4cib on 17/11/2016.
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c4cib.map1111.R;

import java.util.ArrayList;


public class customAdapter extends BaseAdapter {
    String [] res;
    String [] rating;

    ArrayList<String> viewData = new ArrayList<>(150);
    ArrayList<String> listTitle = new ArrayList<>(150);

    ArrayList<String> listDate = new ArrayList<>(150);

    ArrayList<String> listLoc = new ArrayList<>(150);

    Context context;

    int [] imId;

    private static LayoutInflater inflater=null;

    public customAdapter(actViewAll mainActivity, ArrayList<String> Title , ArrayList<String> Date , ArrayList<String> Loc,ArrayList<String> viewData) {

        //res=List;
        listTitle = Title;
        this.viewData = viewData;
        listDate =Date;
        listLoc =Loc;
        context=mainActivity;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView text1;
        TextView text2;
        TextView date;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder hold=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_listholder, null);
        hold.text1=(TextView) rowView.findViewById(R.id.textView1);
        hold.text2=(TextView) rowView.findViewById(R.id.textView2);
        hold.date=(TextView) rowView.findViewById(R.id.text_eventDate);
        hold.text2.setText(listLoc.get(position));
        hold.text1.setText(listTitle.get(position));
        hold.date.setText(listDate.get(position));
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, actDetailedView.class);

                String sendData = viewData.get(position);
                intent.putExtra("data",sendData);
                Bundle extras = new Bundle();
                context.startActivity(intent);

            }
        });
        return rowView;
    }

}