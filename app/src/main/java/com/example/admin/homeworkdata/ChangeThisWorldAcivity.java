package com.example.admin.homeworkdata;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChangeThisWorldAcivity extends AppCompatActivity {
    private TextView name,brth,num;
    private Button accept,back,delete;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_this_world_acivity);

        int id = getIntent().getIntExtra("contact",0);
        ContactsHelper ch = new ContactsHelper(getApplicationContext());
        contact = ch.getContact(id);
        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
        brth = findViewById(R.id.brth);
        accept = findViewById(R.id.accept);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);

        name.setText(contact.getName());
        num.setText(contact.getPhone());
        brth.setText(contact.getBirthday());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeThisWorldAcivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equalsIgnoreCase("") || num.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(view,"Name and Number can't be empty",Snackbar.LENGTH_LONG).show();
                }
                else {
                    ContactsHelper ch = new ContactsHelper(getApplicationContext());
                    contact.setName(name.getText().toString());
                    contact.setPhone(num.getText().toString());
                    contact.setBirthday(brth.getText().toString());
                    ch.update(contact);
                    Intent intent = new Intent(ChangeThisWorldAcivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsHelper ch = new ContactsHelper(getApplicationContext());
                ch.delete(contact.getId());


                Intent intent = new Intent(ChangeThisWorldAcivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
