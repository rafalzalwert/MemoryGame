package com.example.zalwe.memorygame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Images.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            DbSettingsPropertis.DbEntry.TABLE_NAME + " (" +
            DbSettingsPropertis.DbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DbSettingsPropertis.DbEntry.COLUMN_NAME + " BLOB NOT NULL " + " );";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            DbSettingsPropertis.DbEntry.TABLE_NAME;


    DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
