package com.example.sdgp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FemaleDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "femaleDatabase.db";
    private static final String TABLE_NAME = "female";
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_AGE = "AGE";
    private static final String COL_WEIGHT = "WEIGHT";
    private static final String COL_HEIGHT = "HEIGHT";
    private static final String COL_NECK = "NECK";
    private static final String COL_CHEST = "CHEST";
    private static final String COL_CALF = "CALF";
    private static final String COL_BICEPS = "BICEPS";
    private static final String COL_HIP = "HIP";
    private static final String COL_WAIST = "WAIST";
    private static final String COL_FOREARM = "FOREARM";
    private static final String COL_PROXIMAL_THIGH= "PROXIMAL_THIGH";
    private static final String COL_MIDDLE_THIGH = "MIDDLE_THIGH";
    private static final String COL_DISTAL_THIGH = "DISTAL_THIGH";
    private static final String COL_WRIST = "WRIST";
    private static final String COL_KNEE = "KNEE";
    private static final String COL_ELBOW = "ELBOW";
    private static final String COL_ANKLE = "ANKLE";
    private static final int DATABASE_VERSION = 1;

    public FemaleDatabase(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = " CREATE TABLE "+ TABLE_NAME + "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + " TEXT," +
                COL_AGE + " TEXT," +
                COL_WEIGHT + " TEXT," +
                COL_HEIGHT + " TEXT," +
                COL_NECK + " TEXT," +
                COL_CHEST + " TEXT," +
                COL_CALF + " TEXT,"+
                COL_BICEPS + " TEXT,"+
                COL_HIP + " TEXT,"+
                COL_WAIST + " TEXT," +
                COL_FOREARM + " TEXT,"+
                COL_PROXIMAL_THIGH + " TEXT,"+
                COL_MIDDLE_THIGH + " TEXT,"+
                COL_DISTAL_THIGH + " TEXT,"+
                COL_WRIST + " TEXT,"+
                COL_KNEE + " TEXT,"+
                COL_ELBOW + " TEXT,"+
                COL_ANKLE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addMeasurements(String email,String age, String weight, String height, String neck, String chest, String calf, String biceps,
                                   String hip, String waist, String forearm, String proximalThigh, String middleThigh, String distalThigh,String wrist,String knee,String elbow,String ankle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL,email);
        values.put(COL_AGE,age);
        values.put(COL_WEIGHT,weight);
        values.put(COL_HEIGHT,height);
        values.put(COL_NECK,neck);
        values.put(COL_CHEST,chest);
        values.put(COL_CALF,calf);
        values.put(COL_BICEPS,biceps);
        values.put(COL_HIP,hip);
        values.put(COL_WAIST,waist);
        values.put(COL_FOREARM,forearm);
        values.put(COL_PROXIMAL_THIGH,proximalThigh);
        values.put(COL_MIDDLE_THIGH,middleThigh);
        values.put(COL_DISTAL_THIGH,distalThigh);
        values.put(COL_WRIST,wrist);
        values.put(COL_KNEE,knee);
        values.put(COL_ELBOW,elbow);
        values.put(COL_ANKLE,ankle);

        long result = db.insert(TABLE_NAME,null,values);
        db.close();

        return result != -1;

    }

    public boolean updateMeasurements(String email,String age, String weight, String height, String neck, String chest, String calf, String biceps,
                                      String hip, String waist, String forearm, String proximalThigh, String middleThigh, String distalThigh,String wrist,String knee,String elbow,String ankle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL,email);
        values.put(COL_AGE,age);
        values.put(COL_WEIGHT,weight);
        values.put(COL_HEIGHT,height);
        values.put(COL_NECK,neck);
        values.put(COL_CHEST,chest);
        values.put(COL_CALF,calf);
        values.put(COL_BICEPS,biceps);
        values.put(COL_HIP,hip);
        values.put(COL_WAIST,waist);
        values.put(COL_FOREARM,forearm);
        values.put(COL_PROXIMAL_THIGH,proximalThigh);
        values.put(COL_MIDDLE_THIGH,middleThigh);
        values.put(COL_DISTAL_THIGH,distalThigh);
        values.put(COL_WRIST,wrist);
        values.put(COL_KNEE,knee);
        values.put(COL_ELBOW,elbow);
        values.put(COL_ANKLE,ankle);
        String[] selectionArgs = {email};

        long result = db.update(TABLE_NAME,values,COL_EMAIL+"TEXT"+" = ?",selectionArgs);
        db.close();

        return result != -1;
    }

}
