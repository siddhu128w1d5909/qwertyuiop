package com.siddhu.capp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by baji_g on 5/30/2017.
 */

public class Circular implements Parcelable{

    String name;
    String email;
    int photoID;





    boolean isSelected;

    public Circular(String name, String email, int photoID) {
        this.name = name;
        this.email = email;
        this.photoID = photoID;
    }

    protected Circular(Parcel in) {
        name = in.readString();
        email = in.readString();
        photoID = in.readInt();
    }

    public static final Creator<Circular> CREATOR = new Creator<Circular>() {
        @Override
        public Circular createFromParcel(Parcel in) {
            return new Circular(in);
        }

        @Override
        public Circular[] newArray(int size) {
            return new Circular[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(photoID);
    }
}
