package com.vn.matric.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vn.matric.model.HistoryModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "GameMatric.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "game";
    private static final String CHECK = "check_";
    private static final String LEVEL = "level";
    private static final String TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                CHECK + " TEXT," +
                LEVEL + " INTEGER," +
                TIME + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade, if needed
    }

    public boolean insertData(String check, int level, String timeLeft) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHECK, check);
        contentValues.put(LEVEL, level);
        contentValues.put(TIME, timeLeft);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    public ArrayList<HistoryModel> getAllData() {
        ArrayList<HistoryModel> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String checkStatus = cursor.getString(cursor.getColumnIndexOrThrow(CHECK));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL));
                String timeLeft = cursor.getString(cursor.getColumnIndexOrThrow(TIME));
                HistoryModel historyModel = new HistoryModel(checkStatus, level, timeLeft);
                historyList.add(historyModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return historyList;
    }
}

