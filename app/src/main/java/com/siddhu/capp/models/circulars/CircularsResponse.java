package com.siddhu.capp.models.circulars;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baji_g on 5/30/2017.
 */

public class CircularsResponse implements Parcelable{

    public CircularsResponse() {
    }

    public CircularsResponse(String subject, String title, String image, String createdAt) {
        this.subject = subject;
        this.title = title;
        this.image = image;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static Creator<CircularsResponse> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("title")
            @Expose
            private String title;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    boolean isSelected;

    protected CircularsResponse(Parcel in) {
        title = in.readString();
        subject = in.readString();
        image = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<CircularsResponse> CREATOR = new Creator<CircularsResponse>() {
        @Override
        public CircularsResponse createFromParcel(Parcel in) {
            return new CircularsResponse(in);
        }

        @Override
        public CircularsResponse[] newArray(int size) {
            return new CircularsResponse[size];
        }
    };


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
        dest.writeString(title);
        dest.writeString(subject);
        dest.writeString(image);
        dest.writeString(createdAt);

    }
}
