package com.example.daniel.innocv.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniel on 12/02/16.
 */
public class User implements Parcelable {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("birthdate")
    String birthdate;

    public User() {
        this.id = "";
        this.name = "";
        this.birthdate = "";
    }

    public User(Parcel in) {
        readFromParcel(in);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.birthdate.toString());
    }

    private void readFromParcel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.birthdate = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
