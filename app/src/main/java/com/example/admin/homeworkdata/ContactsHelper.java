package com.example.admin.homeworkdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.admin.homeworkdata.DBHelper.TABLE_NAME;

/**
 * Created by admin on 05.03.2018.
 */

public class ContactsHelper {
    SQLiteDatabase db;

    ContactsHelper(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    long insert(String name, String phone, String birthday) {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_PHONE, phone);
        cv.put(DBHelper.COLUMN_BIRTHDAY, birthday);

        return db.insert(TABLE_NAME, null, cv);
    }

    ArrayList<Contact> getAll() {

        Cursor mCursor = db.query(TABLE_NAME, null, null, null, null, null,
                null);
        ArrayList<Contact> arr = new ArrayList<>();

        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {

            do {
                long id = mCursor.getLong(0);
                String name = mCursor.getString(1);
                String phone = mCursor.getString(2);
                String birthday = mCursor.getString(3);
                arr.add(new Contact(id, name, phone, birthday));

            } while (mCursor.moveToNext());
        }
        db.close();
        return arr;
    }

    
}

