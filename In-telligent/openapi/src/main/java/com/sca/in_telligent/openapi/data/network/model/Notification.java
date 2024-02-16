package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Notification implements Parcelable {
    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel parcel) {
            return new Notification(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Notification[] newArray(int i) {
            return new Notification[i];
        }
    };
    @SerializedName("buildingId")
    private int buildingId;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("isSaved")
    private boolean isSaved;
    @SerializedName("language")
    private String language;
    @SerializedName("NotificationAttachments")
    private ArrayList<NotificationAttachment> notificationAttachments;
    @SerializedName("Building")
    private NotificationBuilding notificationBuilding;
    @SerializedName("NotificationsSubscriber")
    private NotificationSubscriber notificationSubscriber;
    private boolean opened;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("suggestedNotificationId")
    private String suggestedNotificationId;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.id;
    }

    public void setBuildingId(int i) {
        this.buildingId = i;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setSuggestedNotificationId(String str) {
        this.suggestedNotificationId = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setNotificationSubscriber(NotificationSubscriber notificationSubscriber) {
        this.notificationSubscriber = notificationSubscriber;
    }

    public void setNotificationAttachments(ArrayList<NotificationAttachment> arrayList) {
        this.notificationAttachments = arrayList;
    }

    public void setSaved(boolean z) {
        this.isSaved = z;
    }

    public void setNotificationBuilding(NotificationBuilding notificationBuilding) {
        this.notificationBuilding = notificationBuilding;
    }

    public int getBuildingId() {
        return this.buildingId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getSuggestedNotificationId() {
        return this.suggestedNotificationId;
    }

    public NotificationSubscriber getNotificationSubscriber() {
        return this.notificationSubscriber;
    }

    public ArrayList<NotificationAttachment> getNotificationAttachments() {
        return this.notificationAttachments;
    }

    public boolean isOpened() {
        return this.opened;
    }

    public void setOpened(boolean z) {
        this.opened = z;
    }

    public String getType() {
        return this.type;
    }

    public NotificationBuilding getNotificationBuilding() {
        return this.notificationBuilding;
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.buildingId);
        parcel.writeString(this.startDate);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.language);
        parcel.writeString(this.suggestedNotificationId);
        parcel.writeString(this.type);
        parcel.writeSerializable(this.notificationSubscriber);
        parcel.writeList(this.notificationAttachments);
        parcel.writeByte(this.isSaved ? (byte) 1 : (byte) 0);
        parcel.writeValue(this.notificationBuilding);
        parcel.writeByte(this.opened ? (byte) 1 : (byte) 0);
    }

    public Notification() {
        this.opened = false;
    }

    protected Notification(Parcel parcel) {
        this.opened = false;
        this.id = parcel.readInt();
        this.buildingId = parcel.readInt();
        this.startDate = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.language = parcel.readString();
        this.suggestedNotificationId = parcel.readString();
        this.type = parcel.readString();
        this.notificationSubscriber = (NotificationSubscriber) parcel.readSerializable();
        ArrayList<NotificationAttachment> arrayList = new ArrayList<>();
        this.notificationAttachments = arrayList;
        parcel.readList(arrayList, NotificationAttachment.class.getClassLoader());
        this.isSaved = parcel.readByte() != 0;
        this.notificationBuilding = (NotificationBuilding) parcel.readParcelable(NotificationBuilding.class.getClassLoader());
        this.opened = parcel.readByte() != 0;
    }
}
