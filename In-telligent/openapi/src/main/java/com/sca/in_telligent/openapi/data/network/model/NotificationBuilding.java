package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class NotificationBuilding implements Parcelable {
    public static final Creator<NotificationBuilding> CREATOR = new Creator<NotificationBuilding>() { // from class: com.sca.in_telligent.openapi.data.network.model.NotificationBuilding.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationBuilding createFromParcel(Parcel parcel) {
            return new NotificationBuilding(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationBuilding[] newArray(int i) {
            return new NotificationBuilding[i];
        }
    };
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("name")
    private String name;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.imageUrl);
    }

    public NotificationBuilding() {
    }

    protected NotificationBuilding(Parcel parcel) {
        this.name = parcel.readString();
        this.imageUrl = parcel.readString();
    }
}
