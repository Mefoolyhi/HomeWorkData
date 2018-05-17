package com.example.admin.homeworkdata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 05.03.2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ContactsViewHolder> {

    private ArrayList<Contact> data;
    private Context context;




    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); // создаём вьюшку для кажого элемента
        context = parent.getContext();
        return new ContactsViewHolder(view);
    }

    public Adapter(ArrayList<Contact> data){
        this.data = data;
    }

    @Override
    public void onBindViewHolder(Adapter.ContactsViewHolder holder, int position) {
        Contact contact = data.get(position);
        holder.setRecord(contact,position);
        Log.e("Contact",contact.toString());
        holder.name.setText(contact.getName());
        holder.num.setText(contact.getPhone());
        holder.brth.setText(contact.getBirthday());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView name,num,brth;
        CardView cv;
        Contact contact;
        int pos;


        public ContactsViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            num = view.findViewById(R.id.num);
            brth = view.findViewById(R.id.brth);
            cv = view.findViewById(R.id.cv);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ChangeThisWorldAcivity.class);
                    intent.putExtra("contact", pos);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });




        }

        void setRecord(Contact contact,int pos){
            this.contact = contact;
            this.pos = pos;
        }
    }
}
