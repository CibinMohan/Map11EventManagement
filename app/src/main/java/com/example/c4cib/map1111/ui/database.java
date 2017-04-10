package com.example.c4cib.map1111.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DropSafeDB";
    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name

    // Pin table name
    private static final String TABLE_PIN = "accessTable";

    // Books Table Columns names
    private  static  final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PIN= "pin";

    private static final String[] COLUMNS = {NAME,PIN,EMAIL};


    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        //String CREATE_PIN_TABLE = "CREATE TABLE accessPin ( " +
        //        "id INTEGER PRIMARY KEY AUTOINCREMENT, pin TEXT)";
        String CREATE_PIN_TABLE = "CREATE TABLE accessTable ( " +
                "name TEXT PRIMARY KEY, pin TEXT, email TEXT)";


        // create pin table
        db.execSQL(CREATE_PIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS accessTable");

        // create fresh books table
        this.onCreate(db);
    }




    public void addPin(userDTO dto){
        //for logging
        //Log.i("addBook", dto.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, 1);

        values.put(NAME, dto.getUserName()); // get pin
        values.put(PIN, dto.getPassword()); // get pin
        values.put(EMAIL,dto.getEmail());

        //db.delete(TABLE_PIN, null,null);

        // 3. insert
        long i = db.insert(TABLE_PIN, null, values);

        Log.i("addPin",i+"".trim());

        // 4. close
        db.close();
    }
    public void deleteTabel(SQLiteDatabase db)
    {
     //   db.execSQL("DROP TABLE IF EXISTS accessPin");

    }

    public int updatePin(userDTO dto) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("dnnbbbb", dto.getPassword());
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(NAME, dto.getUserName());
        values.put(PIN, dto.getPassword());
        values.put(EMAIL, "");

        // 3. updating row


        int i = db.update(TABLE_PIN, //table
                values, // column/value
                NAME+" = ?", // selections
                new String[] { dto.getUserName() });

        // 4. close
        db.close();

        return i;

    }


    public userDTO getPin(String name){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        /*Cursor cursor =
                db.query(TABLE_PIN, // a. table
                        COLUMNS, // b. column names
                        " name = ?", // c. selections
                        new String[] { name }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit*/

        // 3. if we got results get the first one
        Cursor c = db.rawQuery("SELECT * FROM accessTable WHERE TRIM(name) = '"+name.trim()+"'", null);
        if (c != null)
            c.moveToFirst();
        userDTO dto = new userDTO();

        // 4. build book object
        try {
            //dto.setId(Integer.parseInt(c.getString(0)));
            dto.setRecPassword(c.getString(1));


            //log
            Log.d("getPin(" + name + ")", dto.getRecPassword());
            db.close();
            // 5. return book

        }catch (Exception e)
        {}
        return dto;
    }


    public userDTO getEmail(String name){

        // 1. get reference to readable DB
        SQLiteDatabase db2 = this.getReadableDatabase();

        // 2. build query
        /*Cursor cursor =
                db.query(TABLE_PIN, // a. table
                        COLUMNS, // b. column names
                        " name = ?", // c. selections
                        new String[] { name }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit*/

        // 3. if we got results get the first one
        Cursor c = db2.rawQuery("SELECT * FROM accessTable WHERE TRIM(name) = '"+name.trim()+"'", null);
        if (c != null)
            c.moveToFirst();

        // 4. build book object
        userDTO dto =new userDTO();
        //dto.setId(Integer.parseInt(c.getString(0)));
        dto.setEmail(c.getString(2));
        dto.setPassword(c.getString(1));


        //log
        Log.d("getPin("+name+")", dto.getEmail());

        // 5. return book
        return dto;
    }

/*    public Boolean isTableEmpty(String tableName)
    {

        Boolean status = false;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String count = "SELECT count(*) FROM" + tableName;
            Cursor mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);
            if (icount > 0)
                status = false;
            else
                status = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }*/

}

