package com.example.c4cib.map1111.Session;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionUse {

    private SharedPreferences prefs;

    public SessionUse(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
        prefsCommit();
    }

    private void prefsCommit() {
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
}