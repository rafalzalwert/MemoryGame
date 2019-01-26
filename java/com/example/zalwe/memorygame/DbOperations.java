package com.example.zalwe.memorygame;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

class DbOperations {
    private DbHelper dbHelper;

    DbOperations(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    void putDataIntoSqliteDb(byte[] image){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbSettingsPropertis.DbEntry.COLUMN_NAME,image);


        db.insert(DbSettingsPropertis.DbEntry.TABLE_NAME, null, values);

    }

    List<Image> getImageFromDb(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSettingsPropertis.DbEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Image image = null;
        final List<Image> images = new ArrayList<>();

        while (cursor.moveToNext()){
            image = new Image(cursor.getBlob(1));
            images.add(image);
        }

        cursor.close();

        return images;
    }

    void deleteData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DbSettingsPropertis.DbEntry.TABLE_NAME, "1", null);
    }
}
