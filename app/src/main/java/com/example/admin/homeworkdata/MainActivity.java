package com.example.admin.homeworkdata;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    ArrayList<Contact> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                intent.putExtra("size",data.size());
                startActivityForResult(intent, 2);
            }
        });
        rv = findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        new GetNewTask().execute();







    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("HELO", String.valueOf(resultCode));

        new GetNewTask().execute();
    }

    private class GetNewTask extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            data.clear();

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                RestTemplate template = new RestTemplate();
                return template.getForObject("http://5.189.85.227:8124/getContacts", String.class);
            }
            catch (Exception e){
                Log.e("ALARM",e.getMessage());
                return "lox";

            }

        }


        @Override
        protected void onPostExecute(String s) {

            if (!s.equals("lox")) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    ContactsHelper ch = new ContactsHelper(getApplicationContext());
                    ch.deleteAll();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        data.add(new Contact(i, object.getString("name"), object.getString("phone"), object.getString("birthday")));
                        ch.insert(i, object.getString("name"), object.getString("phone"), object.getString("birthday"), "main");
                    }

                    ch.db.close();

                } catch (Exception e) {
                    Log.e("Server is broken", e.getMessage());
                    ContactsHelper ch = new ContactsHelper(getApplicationContext());
                    data = ch.getAll();
                }
            }
            else{
                ContactsHelper ch = new ContactsHelper(getApplicationContext());
                data = ch.getAll();
            }


                rv.setAdapter(new Adapter(data));
        }
    }


}
