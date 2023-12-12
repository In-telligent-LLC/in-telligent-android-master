package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NotificationAttachment implements Parcelable {
    public static final Creator<NotificationAttachment> CREATOR = new Creator<NotificationAttachment>() { // from class: com.sca.in_telligent.openapi.data.network.model.NotificationAttachment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationAttachment createFromParcel(Parcel parcel) {
            return new NotificationAttachment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationAttachment[] newArray(int i) {
            return new NotificationAttachment[i];
        }
    };
    @SerializedName("filename")
    private String filename;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getUrl() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.url);
        parcel.writeString(this.type);
        parcel.writeString(this.filename);
    }

    public NotificationAttachment() {
    }

    protected NotificationAttachment(Parcel parcel) {
        this.url = parcel.readString();
        this.type = parcel.readString();
        this.filename = parcel.readString();
    }
}
