package com.example.admin.homeworkdata;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 05.03.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ContactsViewHolder> {

    private ArrayList<Contact> data;
    private Context context;
    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); // создаём вьюшку для кажого элемента
        return new ContactsViewHolder(view);
    }

    public MyAdapter(ArrayList<Contact> data,Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ContactsViewHolder holder, int position) {
        Contact contact = data.get(position);
        holder.setRecord(contact);
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
                    intent.putExtra("contact", contact);
                    context.startActivity(intent);
                }
            });


        }

        void setRecord(Contact contact){
            this.contact = contact;
        }
    }
}
