package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.c4cib.map1111.R;


/**
 * Created by c4cib on 13/10/2016.
 */
public class newLoc extends Activity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addloc);
            addfile();
        }
    public void addfile()
    {
        final EditText name= 	;

        final EditText lat= (EditText) findViewById(R.id.editText1);

        final EditText longt= (EditText) findViewById(R.id.editText2);

        final EditText tz= (EditText) findViewById(R.id.editText3);



        final Button nwButton = (Button)findViewById(R.id.button);

        nwButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String newLocation = name.getText().toString()+","+lat.getText().toString()+","+longt.getText().toString()+","+tz.getText().toString();
                Intent intent = new Intent(newLoc.this, Main.class);

                Bundle extras = new Bundle();
                Log.i("Ts+++++++", newLocation);
                intent.putExtra("data",newLocation);

                /*
                PrintWriter out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf("D:/Android Studio/WorkSp/SunCalculator/SunCalculator/app/src/main/assets/au_locations.txt"))));
                    out.println("the text");
                }catch (IOException e) {
                    System.err.println(e);
                }finally{
                    if(out != null){
                        out.close();
                    }
                }*/

            startActivity(intent);
            }
        });


    }
}
