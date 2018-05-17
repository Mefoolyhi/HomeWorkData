package com.example.admin.homeworkdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 05.03.2018.
 */

public class Contact implements Parcelable {
    private long id;
    private long idForServ;
    private String name;
    private String phone;
    private String birthday;

    public long getIdForServ() {
        return idForServ;
    }

    public void setIdForServ(long idForServ) {
        this.idForServ = idForServ;
    }

    public Contact(long id, String name, String phone, String birthday) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
    }
    protected Contact(Parcel in) {
        id = in.readLong();
        name = in.readString();
        phone = in.readString();
        birthday = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                '}'+"\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(birthday);
    }
}
