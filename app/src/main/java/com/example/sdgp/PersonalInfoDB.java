package com.example.sdgp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class PersonalInfoDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "PersonalInfo.db";
    private static final String TABLE_NAME = "informations";
    private static final int DATABASE_VERSION = 1;
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_FNAME = "FIRSTNAME";
    private static final String COL_LNAME = "LASTNAME";
    private static final String COL_GENDER = "GENDER";
    private static final String COL_DOB = "DOB";


    public PersonalInfoDB(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE "+ TABLE_NAME + "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + " TEXT," +
                COL_FNAME + " TEXT," +
                COL_LNAME + " TEXT," +
                COL_GENDER + " TEXT," +
                COL_DOB + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addDetails(String email,String fname, String lname, String gender, String dob){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL,email);
        values.put(COL_FNAME,fname);
        values.put(COL_LNAME,lname);
        values.put(COL_GENDER,gender);
        values.put(COL_DOB,dob);

        long result = db.insert(TABLE_NAME,null,values);
        db.close();

        return result != -1;

    }

    public ArrayList getAllData(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COL_FNAME,COL_LNAME,COL_GENDER,COL_DOB
        };

        // Filter results by email
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = { email };

        String sortOrder = COL_FNAME + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList<String> itemIds = new ArrayList();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_FNAME));
            String lname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LNAME));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COL_GENDER));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOB));
            itemIds.add(name);
            itemIds.add(lname);
            itemIds.add(gender);
            itemIds.add(dob);
        }
        cursor.close();

        return itemIds;
    }
}
