package com.example.admin.homeworkdata;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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


    void insert(final int id, final String name, final String phone, final String birthday, String main) {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_PHONE, phone);
        cv.put(DBHelper.COLUMN_BIRTHDAY, birthday);
        db.insert(TABLE_NAME, null, cv);


        if (!main.equals("main")) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://5.189.85.227:8124/") // Адрес сервера
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Server service = retrofit.create(Server.class);
                Gson gson = new Gson();
                Call<String> call = service.addConctact(gson.toJson(new Contact(id, name, phone, birthday)));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.e("OK", "OK");
                        } else {
                            Log.e("Не засунулся, увы", "ALARM");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("ALARM", t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
            catch (Exception e){
                Log.e("ALARM", e.getMessage());
                e.printStackTrace();
            }
        }
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

    void delete(final long id, long idForServ){
        db.delete(DBHelper.TABLE_NAME,"_id=" + id,null);



        db.close();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5.189.85.227:8124/") // Адрес сервера
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Server service = retrofit.create(Server.class);
            Call<String> call = service.deleteContact(idForServ);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.e("OK", "OK");
                    } else {
                        Log.e("Не обновился, увы", "ALARM");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ALARM", t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e){
            Log.e("ALARM", e.getMessage());
            e.printStackTrace();
        }



    }



    void deleteAll(){
        db.execSQL("delete from "+ TABLE_NAME);
    }

    Contact getContact(int id){
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null,
                null);
        c.moveToPosition(id);
        Contact contact = new Contact(c.getLong(0),c.getString(1),c.getString(2),c.getString(3));
        db.close();
        return contact;
    }
    void update(final Contact contact){
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, contact.getName());
        cv.put(DBHelper.COLUMN_PHONE, contact.getPhone());
        cv.put(DBHelper.COLUMN_BIRTHDAY, contact.getBirthday());

        Log.e("update",contact.toString());

        db.update(DBHelper.TABLE_NAME,cv,"_id=?",new String[]{String.valueOf(contact.getId())});



        db.close();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5.189.85.227:8124/") // Адрес сервера
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Server service = retrofit.create(Server.class);
            Gson gson = new Gson();
            Call<String> call = service.updateContact(gson.toJson(contact), contact.getIdForServ());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.e("OK", "OK");
                    } else {
                        Log.e("Не обновился, увы", "ALARM");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ALARM", t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        catch (Exception e){
            Log.e("ALARM", e.getMessage());
            e.printStackTrace();
        }



    }


    public interface Server {
        @POST("/saveContact")
        Call<String> addConctact(@Body String c);


        @POST("/updateContact/{id}")
        Call<String> updateContact(@Body String c, @Path("id") long id);

        @GET("/deleteContact/{id}")
        Call<String> deleteContact(@Path("id") long id);
    }


}

