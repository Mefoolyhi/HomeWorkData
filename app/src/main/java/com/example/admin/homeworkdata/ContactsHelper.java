package com.example.admin.homeworkdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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


    void insert(String name, String phone, String birthday) {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_PHONE, phone);
        cv.put(DBHelper.COLUMN_BIRTHDAY, birthday);

        db.insert(TABLE_NAME, null, cv);
        db.close();
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

    void delete(long id){
        db.delete(DBHelper.TABLE_NAME,"_id=" + id,null);
        db.close();
    }

    Contact getContact(long id){
        String q = "SELECT * FROM "+DBHelper.TABLE_NAME+" WHERE _id = " + id ;
        Cursor c = db.rawQuery(q,null);
        Contact contact = new Contact(c.getLong(0),c.getString(1),c.getString(2),c.getString(3));
        db.close();
        return contact;
    }
    void update(Contact contact){
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, contact.getName());
        cv.put(DBHelper.COLUMN_PHONE, contact.getPhone());
        cv.put(DBHelper.COLUMN_BIRTHDAY, contact.getBirthday());

        Log.e("update",contact.toString());

        db.update(DBHelper.TABLE_NAME,cv,"_id=?",new String[]{String.valueOf(contact.getId())});
        db.close();


    }




}

