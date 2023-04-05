package com.example.sdgp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "myDatabase1.db";
    private static final String TABLE_NAME = "male1";
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_AGE = "AGE";
    private static final String COL_WEIGHT = "WEIGHT";
    private static final String COL_HEIGHT = "HEIGHT";
    private static final String COL_NECK = "NECK";
    private static final String COL_CHEST = "CHEST";
    private static final String COL_ABDOMEN = "ABDOMEN";
    private static final String COL_HIP = "HIP";
    private static final String COL_THIGH= "THIGH";
    private static final String COL_KNEE = "KNEE";
    private static final String COL_ANKLE = "ANKLE";
    private static final String COL_BICEPS = "BICEPS";
    private static final String COL_FOREARM = "FOREARM";
    private static final String COL_WRIST = "WRIST";
    private static final int DATABASE_VERSION = 2;

    public MyDatabase(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE "+ TABLE_NAME + "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + "TEXT," +
                COL_AGE + "TEXT," +
                COL_WEIGHT + "TEXT," +
                COL_HEIGHT + "TEXT," +
                COL_NECK + "TEXT," +
                COL_CHEST + "TEXT," +
                COL_ABDOMEN + "TEXT,"+
                COL_HIP + "TEXT,"+
                COL_THIGH + "TEXT,"+
                COL_KNEE + "TEXT,"+
                COL_ANKLE + "TEXT,"+
                COL_BICEPS + "TEXT,"+
                COL_FOREARM + "TEXT,"+
                COL_WRIST + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public boolean addMeasurements(String email,String age, String weight, String height, String neck, String chest, String abdomen, String hip, String thigh, String knee, String ankle, String biceps, String forearm, String wrist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL+"TEXT",email);
        values.put(COL_AGE+"TEXT",age);
        values.put(COL_WEIGHT+"TEXT",weight);
        values.put(COL_HEIGHT+"TEXT",height);
        values.put(COL_NECK+"TEXT",neck);
        values.put(COL_CHEST+"TEXT",chest);
        values.put(COL_ABDOMEN+"TEXT",abdomen);
        values.put(COL_HIP+"TEXT",hip);
        values.put(COL_THIGH+"TEXT",thigh);
        values.put(COL_KNEE+"TEXT",knee);
        values.put(COL_ANKLE+"TEXT",ankle);
        values.put(COL_BICEPS+"TEXT",biceps);
        values.put(COL_FOREARM+"TEXT",forearm);
        values.put(COL_WRIST+"TEXT",wrist);

        long result = db.insert(TABLE_NAME,null,values);
        db.close();

        return result != -1;

    }

    public boolean updateMeasurements(String email,String age, String weight, String height, String neck, String chest, String abdomen, String hip, String thigh, String knee, String ankle, String biceps, String forearm, String wrist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL+"TEXT",email);
        values.put(COL_AGE+"TEXT",age);
        values.put(COL_WEIGHT+"TEXT",weight);
        values.put(COL_HEIGHT+"TEXT",height);
        values.put(COL_NECK+"TEXT",neck);
        values.put(COL_CHEST+"TEXT",chest);
        values.put(COL_ABDOMEN+"TEXT",abdomen);
        values.put(COL_HIP+"TEXT",hip);
        values.put(COL_THIGH+"TEXT",thigh);
        values.put(COL_KNEE+"TEXT",knee);
        values.put(COL_ANKLE+"TEXT",ankle);
        values.put(COL_BICEPS+"TEXT",biceps);
        values.put(COL_FOREARM+"TEXT",forearm);
        values.put(COL_WRIST+"TEXT",wrist);
        String[] selectionArgs = {email};

        long result = db.update(TABLE_NAME,values,COL_EMAIL+"TEXT"+" = ?",selectionArgs);
        db.close();

        return result != -1;

    }

    /*public ArrayList<String> getData(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COL_EMAIL};
        // Filter results by email

        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = { email };

        String sortOrder = COL_EMAIL + " DESC";

        *//*Cursor res = db.rawQuery(
                "select * from informations where EMAILTEXT='email'",null);*//*

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<String> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String itemId = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL));
            itemIds.add(itemId);
        }
        cursor.close();

        return itemIds;
    }*/


}
