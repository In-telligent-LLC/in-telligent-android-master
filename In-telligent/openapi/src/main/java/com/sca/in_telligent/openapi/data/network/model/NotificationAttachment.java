package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NotificationAttachment implements Parcelable {

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private String type;

    public String getFilename() {
        return filename;
    }

    @SerializedName("filename")
    private String filename;

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.type);
        dest.writeString(this.filename);
    }

    public NotificationAttachment() {
    }

    protected NotificationAttachment(Parcel in) {
        this.url = in.readString();
        this.type = in.readString();
        this.filename = in.readString();
    }

    public static final Creator<NotificationAttachment> CREATOR = new Creator<NotificationAttachment>() {
        @Override
        public NotificationAttachment createFromParcel(Parcel source) {
            return new NotificationAttachment(source);
        }

        @Override
        public NotificationAttachment[] newArray(int size) {
            return new NotificationAttachment[size];
        }
    };
}
