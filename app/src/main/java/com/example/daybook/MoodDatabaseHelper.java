package com.example.daybook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoodDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "moodTracker.db";
    private static final int DB_VERSION = 1;
    public static final String TBL_MOOD = "mood";
    public static final String COL_ID = "_id";
    public static final String COL_MOOD = "mood";
    public static final String COL_TIMESTAMP = "timestamp";

    private static final String TBL_CREATE = "CREATE TABLE " + TBL_MOOD + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_MOOD + " TEXT, " + COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

    public MoodDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TBL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MOOD);
        onCreate(db);
    }

    public void addMood(String mood) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MOOD, mood);
        db.insert(TBL_MOOD, null, cv);
        db.close();
    }

    public String getMood() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_MOOD + " FROM " + TBL_MOOD + " ORDER BY " + COL_TIMESTAMP + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range")
            String mood = cursor.getString(cursor.getColumnIndex(COL_MOOD));
            cursor.close();
            return mood;
        } else {
            return null;
        }
    }
}
