package com.example.c4cib.map1111.ui;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by c4cib on 19/11/2016.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class AsyncMail extends AsyncTask<String, Void, String> {

    int progressStatus ;
    int result;


    // progress=new ProgressDialog(this);
    @Override
    protected void onPreExecute() {
        System.out.println("Start");
    }
    @Override
    protected String doInBackground(String... strings) {

        String mail = strings[0];
        String data = strings[1];

        try {
            GMailSender sender = new GMailSender("cibinmohan@gmail.com", "221beminem");
            sender.sendMail("New Event",
                    data,
                    "c4cibin@gmail.com",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return "ddd";
    }

    @Override
    protected void onPostExecute(String b)
    {
        System.out.println("Finish");

    }



}
