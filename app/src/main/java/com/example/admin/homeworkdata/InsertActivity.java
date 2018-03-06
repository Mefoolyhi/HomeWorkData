package com.example.admin.homeworkdata;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.Button;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {
    private EditText name,birthday,num;
    private Button insert,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        name = findViewById(R.id.name);
        birthday = findViewById(R.id.brth);
        num = findViewById(R.id.num);
        insert = findViewById(R.id.insert);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("") || num.getText().toString().equals("") || birthday.getText().toString().equals("")){
                    Snackbar.make(view,"Name and Number can't be empty",Snackbar.LENGTH_LONG).show();
                }
                else {
                    ContactsHelper ch = new ContactsHelper(getApplicationContext());
                    ch.insert(name.getText().toString(), num.getText().toString(), birthday.getText().toString());
                    Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    }
