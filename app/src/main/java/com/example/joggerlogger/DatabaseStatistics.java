package com.example.joggerlogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseStatistics extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Statistics.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "my_statistics";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "statistics_title";
    private static final String COLUMN_TIME = "statistics_time";
    private static final String COLUMN_DISTANCE = "statistics_distance";
    private static final String COLUMN_KMH = "statistics_kmh";


    DatabaseStatistics(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_TIME + " TEXT, " +
                        COLUMN_DISTANCE + " INTEGER, " +
                        COLUMN_KMH + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addStatistics(String title, String time, int distance, String kmhList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_DISTANCE, distance);
        cv.put(COLUMN_KMH, kmhList);

        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1){
            Toast.makeText(context, "failed!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_LONG).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;

    }

    void updateData(String row_id, String title, String time, String distance, String kmhListe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_DISTANCE, distance);
        cv.put(COLUMN_KMH, kmhListe);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if(result == -1){
            //Toast.makeText(context, "Failed to update!", Toast.LENGTH_SHORT).show();
        }
        else{
            //Toast.makeText(context, "Successfully updated! " + title + " " + " " + time + " " + " " + distance + " !", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

    }

}
